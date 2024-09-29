/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.ssatr.parking;

/**
 *
 * @author mihai.hulea
 */
public class AppParkingServer {
    public static void main(String[] args) {
        ParkingAccessManager2 mng = new ParkingAccessManager2();
        ServerNet srv = new ServerNet(mng);
        ParkingJFrame ui = new ParkingJFrame(mng);
        ui.setVisible(true);
        srv.waitForClientConnections();
    }
   
}
