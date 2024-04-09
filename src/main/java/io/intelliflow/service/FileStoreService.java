package io.intelliflow.service;

import io.intelliflow.dao.FileStoreDao;
import io.intelliflow.dto.FileStoreDto;
import io.intelliflow.model.FileData;
import io.intelliflow.model.FileStore;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FileStoreService {

    @Inject
    FileStoreDao fileStoreDao;  //can this be static?

    public FileStoreDto savePartition(Path FILE, String fileName){
        UUID dbId = null;

        //time to live for file, default 24hr
        int ttlForFile = 86400;

        //size for each chunk in db partition, default 2MB
        int chunkSize = 1024*1024*2;

        //loop variable
        int i =0;

        try {
            //creating channel of whole file
            FileChannel channel = FileChannel.open(FILE);
            //creating a buffer of 2MB , for chunking
            ByteBuffer buff = ByteBuffer.allocateDirect(chunkSize);

            int loopKey = (int)channel.size() / chunkSize;
            int backlogBytes = (int)channel.size() % chunkSize;
            int position = 0;
            dbId = UUID.randomUUID();
            while(i < loopKey){
                channel.read(buff, position);
                buff.flip();
                FileStore fileStore = new FileStore(fileName, i, buff, dbId, "Pending");
                FileStore savedFile = fileStoreDao.insert(fileStore, ttlForFile);
                buff.clear();
                i++;
                position += chunkSize;
            }

            if(backlogBytes > 0){
                ByteBuffer buffRemain = ByteBuffer.allocateDirect(backlogBytes);
                channel.read(buffRemain, position);
                buffRemain.flip();
                FileStore fileStore = new FileStore(fileName, i, buffRemain, dbId, "Pending");
                FileStore savedFile = fileStoreDao.insert(fileStore, ttlForFile);
                buffRemain.clear();
                position += backlogBytes;
            }

            if((int)channel.size() == position){
                System.out.println("Full Data Read");
            }
        } catch (IOException e) {
            Log.error("Partition & Save Operation Failed!!");
            Log.error(e);
            e.printStackTrace();
        }
        return dbId != null ? new FileStoreDto(fileName, i+1, null, dbId, "Pending") : null;
    }

    public void getFiles(String name){
        try {
            List<FileStore> fileStores = fileStoreDao.findByName(name).all();
            FileOutputStream fout = new FileOutputStream("D:/Projects/SampleApp/newtext.txt");
            for(FileStore fileStore : fileStores){
                assert fileStore!= null;
                System.out.println("Order Key : " + fileStore.getOrderkey());
                fout.write(fileStore.getPartition().array());
            }
            fout.close();
            System.out.println("Sample");
        } catch (IOException e) {
            Log.error("Partition Retrieve Operation Failed!!");
            e.printStackTrace();
        }
    }

    public Boolean deleteFile(String fileName){
        return fileStoreDao.deleteFile(fileName);
    }
}
