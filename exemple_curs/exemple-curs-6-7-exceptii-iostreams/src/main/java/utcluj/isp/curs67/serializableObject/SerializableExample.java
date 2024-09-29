/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utcluj.isp.curs67.serializableObject;

import utcluj.isp.curs67.files.FilesAndFoldersUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 * @author mihai.hulea
 */
public class SerializableExample {
   static void writeVehicle(Vehicle v, String destinationFile) throws IOException{
       try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(destinationFile))){
           os.writeObject(v);
           os.flush();
        }
   } 
   
   static Vehicle readVehicle(String sourceFile) throws IOException, ClassNotFoundException{
       try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(sourceFile))){
           return (Vehicle)in.readObject();
       }
   }
   
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String workingFolder = "c:\\_tmp2";
//        FilesAndFoldersUtil.createFolder(workingFolder);
//        
//        writeVehicle(new Vehicle("CJ01AAA", "150,78", "673,90", 70), workingFolder+"\\"+"car1.dat");
//        writeVehicle(new Vehicle("CJ02AAA", "150,78", "673,90", 50), workingFolder+"\\"+"car2.dat");
//        writeVehicle(new Vehicle("CJ03AAA", "150,78", "673,90", 90), workingFolder+"\\"+"car3.dat");
//        writeVehicle(new Vehicle("CJ04AAA", "150,78", "673,90", 170), workingFolder+"\\"+"car4.dat");
//        
        FilesAndFoldersUtil.getFilesInFolder(workingFolder).stream().forEach((s)->System.out.println(""));
    
          List<String> files = FilesAndFoldersUtil.getFilesInFolder(workingFolder);
          for(String f: files){
              Vehicle v = readVehicle(workingFolder+"\\"+f);
              System.out.println(v);
          }
    }
}
