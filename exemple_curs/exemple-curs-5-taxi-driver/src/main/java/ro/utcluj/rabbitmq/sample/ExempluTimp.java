/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.rabbitmq.sample;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mihai
 */
public class ExempluTimp {
    public static void main(String[] args) {
        long crtT = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExempluTimp.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(""+(System.currentTimeMillis()-crtT));
        
    }
}
