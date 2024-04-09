package io.intelliflow.support;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileOperations {

    public static File createFile(String fileName, String fileExtension) {
        File newFile = null;
        try{
            if(fileName != null && fileExtension != null){
                switch (fileExtension){
                    case "txt":
                        newFile = File.createTempFile(fileName ,".txt");
                        break;
                    case "bpmn":
                        newFile = File.createTempFile(fileName ,".bpmn");
                        break;
                    case "doc":
                        newFile = File.createTempFile(fileName , ".doc");
                        break;
                }
            }
        } catch (IOException e){
            System.out.println("Exception occurred During file creation in FileOps!!");
            e.printStackTrace();
        }
        return newFile;
    }

    public static void writeToFile(File file, String data){
        if(file.isFile() && data != null){
            try{
                FileWriter writer = new FileWriter(file);
                PrintWriter pw =new PrintWriter(writer);
                pw.print(data);
                pw.close();
            } catch (IOException e){
                System.out.println("An error Occurred During File writing \n Stacktrace:");
                e.printStackTrace();
            }
        }
    }

    public static void deleteFile(File file){
        if(file.exists()){
            file.delete();
        }
    }
}

