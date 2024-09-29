/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isp.multithread.programming.timer;

/**
 *
 * @author mihai.hulea
 */
public interface ITimer {

    void pauseTimer();

    void resumeTimer();
    
    String getName();
    
}
