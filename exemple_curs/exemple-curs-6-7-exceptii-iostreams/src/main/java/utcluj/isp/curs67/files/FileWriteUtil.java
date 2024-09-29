/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utcluj.isp.curs67.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author mihai.hulea
 */
public class FileWriteUtil {
    
    /**
     * Use Streams when you are dealing with raw data
     * @param data
     */
    public static void writeUsingOutputStream(String data, String fileName) throws IOException {
    
        //use java 1.7 try with resources (os stream is automatically closed internaly
        try(OutputStream os = new FileOutputStream(new File(fileName))) {         
            os.write(data.getBytes(), 0, data.length());
        }
    }
    
      /**
     * Use Files class from Java 1.7 to write files, internally uses OutputStream
     * @param data
     */
    public static void writeUsingFiles(String data, String fileName) throws IOException {
            Files.write(Paths.get(fileName), data.getBytes());
    }
    
    /**
     * Use FileWriter when writing small amount of data.
     * 
     * @param data
     */
    public static void writeUsingFileWriter(String data, String fileName) throws IOException {
        File file = new File(fileName);
        
        try(FileWriter fr = new FileWriter(file)){    
            fr.write(data);
        }  
    }
    
    /**
     * Use BufferedWriter when number of write operations are more
     * It uses internal buffer to reduce real IO operations and saves time
     * @param data
     * @param noOfLines
     */
    public static void writeUsingBufferedWriter(List<String> lines, String fileName) throws IOException {
        File file = new File(fileName);
        try(BufferedWriter br = new BufferedWriter(new FileWriter(file))){
            for(String line: lines){
                br.write(line+System.getProperty("line.separator"));
            }
            br.flush();
        }        
    }
    
}
