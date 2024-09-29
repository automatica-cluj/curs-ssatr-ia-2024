/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isp.multithread.programming.pools;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mihai.hulea
 */
public class Main {
    
    public static void pools1() throws InterruptedException{
        ExecutorService executor =  Executors.newFixedThreadPool(1);
        
        Runnable r = ()->{
            System.out.println("Execute activity1.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Activity completed.");

        };
        
        //this loop will end after 5 seconds, why     
        for(int i =0;i<5;i++){
            executor.execute(r);
        }
        
        //executor.shutdown(); //wait existing task to complete and then shutdown
        
        //shutdown methd recomanded by Oracle: 
        executor.shutdown();
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            } 
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
    
    public static void pools2() throws InterruptedException, ExecutionException{
        
        //create a cached threadpool which can be extended as needed.
        ExecutorService executor =  Executors.newCachedThreadPool();
        final Random r = new Random();
        Callable<Integer> callableTask = () -> {
            int k = r.nextInt(100);
            TimeUnit.MILLISECONDS.sleep(3000);
            System.out.println("Task executed!");
            return k;
        };
        
        //ArrayList<Integer> nrs = new ArrayList<>();
        System.out.println("Submit task for execution:");
        Future<Integer> future = executor.submit(callableTask);
        System.out.println("Wait for result.");
        Integer i =  future.get(); //blocking
        System.out.println("Result = "+i);
        
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //pools1();
        pools2();
    }
}
