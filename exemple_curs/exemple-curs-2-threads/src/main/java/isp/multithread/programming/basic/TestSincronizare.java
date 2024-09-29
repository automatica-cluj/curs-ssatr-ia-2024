/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isp.multithread.programming.basic;

public class TestSincronizare {
public static void main(String[] args) {
    Punct p = new Punct();
    FirSet fs1 = new FirSet(p);
    FirGet fg1 = new FirGet(p);
 
    fs1.start();
    fg1.start();
}
}
 
class FirGet extends Thread {
    Punct p;
 
    public FirGet(Punct p){
        this.p = p;
    }
 
    public void run(){
        int i=0;
        int a,b;
        while(++i<150000){         
            synchronized(p){ //blocare firului apelat daca lockul obiectului p este deja detinut de alt fir 
                a= p.getX();          
                try {
                    sleep(50);
                } catch (InterruptedException e) {  
                    e.printStackTrace();
                }         
                b = p.getY();
            }//....
            System.out.println("Am citit: ["+a+","+b+"]");
        }
    }
}//.class
 
 
class FirSet extends Thread {
    Punct p;
    public FirSet(Punct p){
        this.p = p;
    } 
    public void run(){
        int i =0;
        while(++i<150000){
            int a = (int)Math.round(10*Math.random()+10);
            int b = (int)Math.round(10*Math.random()+10);
 
            synchronized(p){ ///////
                p.setXY(a,b);
            
                
                try {
                    sleep(10);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                
              }  
            }
                System.out.println("Am scris: ["+a+","+b+"]");
        }
    }
}//.class
 
class Punct {
    int x,y;
    public void setXY(int a,int b){
        x = a;y = b;
    }  
    public int getX(){return x;}
    public int getY(){return y;}   
}