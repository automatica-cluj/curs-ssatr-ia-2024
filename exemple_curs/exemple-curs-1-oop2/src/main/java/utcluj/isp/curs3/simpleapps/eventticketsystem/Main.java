package utcluj.isp.curs3.simpleapps.eventticketsystem;

import com.google.zxing.NotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        //Create ticket

          TicketsManager tm = new TicketsManager();
    //      PurchasedTicket pt = tm.createTicket("Alin ", "L1@.L1.com", "123", "Event1", LocalDateTime.now(), "REG", 100, "22-12-10");
    //      tm.generateElectronicTicket(pt);
//        PurchasedTicket ticket1 = tm.createTicket("GeorgeSM GeorgeSM","a@b.c","1234567890","Concert", LocalDateTime.now(),"VIP",100,"2020-12-12");
//
//        tm.generateElectronicTicket(ticket1);

        //validate ticket

        try {
            tm.checkinTicket("C:\\Tickets2\\ticket_Alin .png", "123");
            //System.out.println("QR Code data: " + qrCodeData);
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }

    }
}
