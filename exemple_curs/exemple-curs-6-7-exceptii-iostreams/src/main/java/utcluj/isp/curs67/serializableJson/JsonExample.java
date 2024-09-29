/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utcluj.isp.curs67.serializableJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import utcluj.isp.curs67.files.FileReadUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Use jackson library to read and write json objects.
 * 
 * @author mihai.hulea
 */
public class JsonExample {
    public static void main(String[] args) throws IOException {
    
        String workingFolder = "c:\\_tmp";
        ObjectMapper objectMapper = new ObjectMapper();

        Vehicle v1 = new Vehicle("CJ01AAA", "150,78", "673,90", 70);
        
        objectMapper.writeValue(new FileWriter(workingFolder+"\\"+"car1.json"), v1);
        
        //use collection streams to concatenate all lines from file into a single String. We can also use simple for loop here.
        String jsonContent = FileReadUtil.readAllFileLines(workingFolder+"\\"+"car1.json").stream().collect( Collectors.joining( "\n" ));
        
        Vehicle v2 = objectMapper.readValue(jsonContent, Vehicle.class);
        System.out.println("Vehicle read from file: "+v2);
       
    }
 
}
