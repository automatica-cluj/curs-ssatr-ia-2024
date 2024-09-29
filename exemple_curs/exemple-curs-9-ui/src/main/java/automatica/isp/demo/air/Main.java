/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package automatica.isp.demo.air;

import automatica.isp.demo.air.model.Airway;
import automatica.isp.demo.air.model.Waypoint;
import automatica.isp.demo.air.view.AirwaysMainUI;
import automatica.isp.demo.air.service.AirwaysManager;
import automatica.isp.demo.air.service.SerializableUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author mihai
 */
public class Main {




    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Hello World!");
        ArrayList<Airway> r = SerializableUtil.loadSerializedObjects("c:\\tmp2");
        for(Object o : r){
            ((Airway)o).printAll();
        }


          AirwaysManager airwaysManager = new AirwaysManager();
          airwaysManager.setAirways(r);
          
        ArrayList<Waypoint> waypoints1 = new ArrayList<Waypoint>();
        waypoints1.add(new Waypoint("W1", "100"));
        waypoints1.add(new Waypoint("W2", "220"));

        ArrayList<Waypoint> waypoints2 = new ArrayList<Waypoint>();
        waypoints2.add(new Waypoint("W3", "100"));
        waypoints2.add(new Waypoint("W4", "220"));
        waypoints2.add(new Waypoint("W5", "320"));

        ArrayList<Waypoint> waypoints3 = new ArrayList<Waypoint>();
        waypoints3.add(new Waypoint("W6", "100"));
        waypoints3.add(new Waypoint("W7", "220"));
        waypoints3.add(new Waypoint("W8", "120"));
        waypoints3.add(new Waypoint("W9", "100"));

        airwaysManager.addAirway(new Airway("LH123", "Bucharest", "Berlin", waypoints1));
        airwaysManager.addAirway(new Airway("LH124", "Cluj-Napoca", "Londra", waypoints2));
        airwaysManager.addAirway(new Airway("LH125", "Bucharest", "Istambul", waypoints3));

        airwaysManager.saveAll();

        AirwaysMainUI ui = new AirwaysMainUI(airwaysManager);
        ui.setVisible(true);


    }
}
