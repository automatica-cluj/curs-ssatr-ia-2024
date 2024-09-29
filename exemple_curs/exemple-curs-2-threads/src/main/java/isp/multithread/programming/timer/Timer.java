/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isp.multithread.programming.timer;

import isp.multithread.programming.timer.mvc.AbstractModel;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mihai.hulea
 */
public class Timer extends AbstractModel implements Runnable, ITimer{
    private int t;
    private boolean active = true;
    private boolean paused = false;
    private String name;
    Integer obj = 10;
    
    public void run(){
        name = Thread.currentThread().getName();
        while(active){
            
            if(paused){
                synchronized(this){
                    try {
                        System.out.println(Thread.currentThread().getName()+" is executing wait()");
                        wait(); //pune in asteptare firl de executie
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            int old =t;
            t++;
            firePropertyChange("t", old, t); //notify all listeners value has changed
            
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void pauseTimer(){
        
        paused = true;
    }
    
    @Override
    public synchronized void resumeTimer(){
        //synchronized(this){
            System.out.println(Thread.currentThread().getName()+" is executing notify()");
            paused = false;
            notify();
        //}
    }

    public int getT() {
        return t;
    }

    @Override
    public String getName() {
        return name;
    }
    
    
    
}
