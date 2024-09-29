/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utcluj.isp.curs67.files;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mihai.hulea
 */
public class FilesAndFoldersUtil {
     public static void createFolder(String folder){
      File d = new File(folder);
      d.mkdirs();
    }
    
    public static List<String> getFilesInFolder(String folder){
      File file = null;
      String[] paths;
      file = new File(folder);
      return Arrays.asList(file.list());
    }
}
