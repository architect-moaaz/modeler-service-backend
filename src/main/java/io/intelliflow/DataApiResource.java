package io.intelliflow;

import io.intelliflow.dto.FileStoreDto;
import io.intelliflow.model.FileData;
import io.intelliflow.service.FileStoreService;
import io.intelliflow.support.FileOperations;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.io.File;
import java.net.URI;
import java.nio.file.Paths;

@Path("/data")
public class DataApiResource {

    @Inject
    FileStoreService fileStoreService;

    @Inject
    @Channel("uppercase")
    Emitter<String> emitter;

    private static java.nio.file.Path FILE;


    //new partition using filechannel
    @POST
    @Path("/saveFile")
    public FileStoreDto saveAsPartition(FileData fileData){
        Log.info("Save to Db Call invoked");
        FileStoreDto fileStoreDto = null;
        File file = FileOperations.createFile(fileData.getFileName(), fileData.getFormat());
        if(file.isFile()){
            Log.info("Temporary file created:::" + file.getName());
            FileOperations.writeToFile(file, fileData.getContent());
            URI uri = file.toURI();
            FILE = Paths.get(uri);
            String fileName = fileData.getFileName() + "." + fileData.getFormat();
            fileStoreDto = fileStoreService.savePartition(FILE, fileName);
        }
        file.deleteOnExit();
        return fileStoreDto;
    }

    @GET
    @Path("/getFile/{fileName}")
    public String getFile(@PathParam("fileName") String fileName){
        Log.info("Get from Db Call invoked");
        fileStoreService.getFiles(fileName);
        //kafka that send both file id and name
        emitter.send("Hello World");
        return "Success";
    }

    @DELETE
    @Path("/deleteFile/{fileName}")
    public Boolean deleteFileByName(@PathParam("fileName") String fileName){
        Log.info("Delete File Invoked");
        return fileStoreService.deleteFile(fileName);
    }
}
