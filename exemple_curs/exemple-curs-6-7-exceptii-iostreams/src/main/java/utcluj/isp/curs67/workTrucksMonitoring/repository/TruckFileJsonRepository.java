/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utcluj.isp.curs67.workTrucksMonitoring.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import utcluj.isp.curs67.files.FileReadUtil;
import utcluj.isp.curs67.files.FilesAndFoldersUtil;
import utcluj.isp.curs67.workTrucksMonitoring.model.Truck;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author mihai.hulea
 */
public class TruckFileJsonRepository implements ITruckRepository {

    private String workingFolder;
    private ObjectMapper objectMapper = new ObjectMapper();

    public TruckFileJsonRepository(String workingFolder) {
        FilesAndFoldersUtil.createFolder(workingFolder);
        this.workingFolder = workingFolder;
    }

    @Override
    public void save(Truck t) {
        try {
            objectMapper.writeValue(new FileWriter(workingFolder+"\\"+"record_"+t.getPlateNumber()+"_"+System.currentTimeMillis()+".json"), t);
        } catch (IOException ex) {
            Logger.getLogger(TruckFileJsonRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Truck> readAll() {
        List<Truck> trucks = new ArrayList<Truck>();
        try {
            List<String> files = FilesAndFoldersUtil.getFilesInFolder(workingFolder);
            for(String file: files){
                //read content of the file in a single string object
                String jsonContent = FileReadUtil.readAllFileLines(workingFolder+"\\"+file).stream().collect( Collectors.joining( "\n" ));
                Truck t = objectMapper.readValue(jsonContent, Truck.class);
                trucks.add(t);
            }
        } catch (IOException ex) {
            Logger.getLogger(TruckFileJsonRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trucks;
    }
    
}
