/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utcluj.isp.curs67.workTrucksMonitoring;

import utcluj.isp.curs67.workTrucksMonitoring.model.Truck;
import utcluj.isp.curs67.workTrucksMonitoring.service.TrackingVehicleService;
import utcluj.isp.curs67.workTrucksMonitoring.repository.TruckFileJsonRepository;
import utcluj.isp.curs67.workTrucksMonitoring.service.TruckNotFoundException;
import utcluj.isp.curs67.workTrucksMonitoring.view.MapViewerJFrame;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * @author mihai.hulea
 */
public class App1 {


    public static void main(String[] args) {
        TruckFileJsonRepository repository = new TruckFileJsonRepository("c:\\_tmp");
        TrackingVehicleService service = new TrackingVehicleService(repository);


        //populate the repository with some data
        service.addNewTruckRecord(46.770439, 23.591423, "CJ-01-ABC");
        service.addNewTruckRecord(46.06667, 23.58333, "CJ-01-ABC");
        service.addNewTruckRecord(46.5531,24.5984, "CJ-01-ABC");
        
        service.addNewTruckRecord(46.06667, 23.58333, "CJ-99-GTT");
        service.addNewTruckRecord(46.5531,24.5984, "CJ-99-GTT");

        //print all records
        service.getAllUnique().stream().forEach(s -> System.out.println(s));
        System.out.println("....");

        //print all records for a given plate number

        List<Truck> list = null;
        try {
            list = service.getALlRecordsByTruck("CJ-01-ABC");
            list.stream().forEach(s -> System.out.println(s));
        } catch (TruckNotFoundException e) {
            throw new RuntimeException(e);
        }

        //display all records for a given plate number on a OpenStreetap canvas
        MapViewerJFrame viewer = new MapViewerJFrame();
        List<GeoPosition> locations =
                null;
        try {
            locations = service.getALlRecordsByTruck("CJ-01-ABC").stream()
                    .map(truck -> new GeoPosition(truck.getLatitude(),truck.getLongitudel()))
                    .collect(toList());
        } catch (TruckNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        viewer.updateRoute(locations);
        viewer.setVisible(true);
    }
}
