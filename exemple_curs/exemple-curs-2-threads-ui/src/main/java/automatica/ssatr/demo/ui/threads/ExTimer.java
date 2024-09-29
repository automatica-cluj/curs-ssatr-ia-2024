/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatica.ssatr.demo.ui.threads;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mihai
 */
public class ExTimer extends Observable implements Runnable{

    private boolean active = true;
    private Integer lock = new Integer(0);
    private boolean running = false;
    
    public void start(){
        Thread t = new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        long k = 0;
        while(active){
            k++;
            setChanged();
            notifyObservers(""+k);
            if(!running){
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ExTimer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }//.if
            else{
                
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(ExTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void pause(){
        running = false;
    }
    
    public void resum(){
        synchronized (lock) {
            running = true;
            lock.notify();
        }
    }
    
}
