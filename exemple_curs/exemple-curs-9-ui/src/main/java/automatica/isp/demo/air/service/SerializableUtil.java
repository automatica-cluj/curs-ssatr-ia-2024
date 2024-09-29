/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatica.isp.demo.air.service;

import automatica.isp.demo.air.model.Airway;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author mihai
 */
public class SerializableUtil {
    
    private String folderPath = "c:\\tmp2";
    
    public static ArrayList<Airway> loadSerializedObjects(String folderPath) throws IOException, ClassNotFoundException {
        ArrayList<Airway> objects = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".ser");
            }
        });

        for (File file : files) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Airway obj = (Airway)ois.readObject();
            objects.add(obj);
            ois.close();
            fis.close();
        }

        return objects;
    }
    
    
  public static void saveSerializedObjects(ArrayList<Airway> objects, String folderPath) throws IOException {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (int i = 0; i < objects.size(); i++) {
            Object obj = objects.get(i);
            String filePath = folderPath + "/object" + i + ".ser";
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
            fos.close();
        }
    }  
}
