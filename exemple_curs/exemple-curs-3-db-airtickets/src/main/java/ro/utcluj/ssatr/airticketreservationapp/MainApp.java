/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp;

import ro.utcluj.ssatr.airticketreservationapp.service.FlightReservationService;
import ro.utcluj.ssatr.airticketreservationapp.ui.FlightManagementSystemJFrame;

/**
 *
 * @author mihai
 */
public class MainApp {
    public static void main(String[] args) {
        FlightReservationService frs = new FlightReservationService();
        FlightManagementSystemJFrame frame = new FlightManagementSystemJFrame(frs);
        frame.setVisible(true);
    }
}
