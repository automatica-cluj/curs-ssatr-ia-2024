/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.ssatr.parking;
import java.io.*;
import java.net.*;

/**
 *
 * @author mihai.hulea
 */
public class ServerNet {
    
    ParkingAccessManager2 manager;

    public ServerNet(ParkingAccessManager2 manager) {
        this.manager = manager;
    }
    
    public void waitForClientConnections(){
        //1. astept conexiune de la client si accept conexiunea
        //2. setez fluxurile de comunicatie
        //3. primesc si tirmit mesaje
        //4. inchid conexiunea
        
         try{
        
        ServerSocket ss =new ServerSocket(4050);
        
        while(true){
            System.out.println("Astept conexiune de la client...");
            Socket s = ss.accept(); //metoda blocanta
            System.out.println("Clientul s-a conectat!");
            //...... 
            BufferedReader fluxIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter fluxOut = new PrintWriter(new OutputStreamWriter(s.getOutputStream()),true);
            //......
            String line = "";
            //while(!line.equals("close connection")){
                String msg = fluxIn.readLine();
                String result = "";
                result = manager.handleCar(msg);
                //result = manager.handleCar(String msg);
                fluxOut.println(result);
            //}

            s.close();
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
//    public static void main(String[] args) {
//        ParkingAccessManager2 mng = new ParkingAccessManager2();
//        ServerNet server = new ServerNet(mng);
//        server.waitForClientConnections();
//        
//    }
}
