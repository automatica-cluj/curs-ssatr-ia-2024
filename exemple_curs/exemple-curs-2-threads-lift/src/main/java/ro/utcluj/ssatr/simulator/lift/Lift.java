package ro.utcluj.ssatr.simulator.lift;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 1. Transformare clasa Lift in thread (fir de executie)
 * 2. Adaugare atribut comandCurentaEtaj pentru a stoca comanda curenta 
 * 3. Implementare logica run() cu variabila boleana pentru pornire\oprire motor si setare etaj curent
 * 4. Modificare metoda executaComanda 
 * 5. Adaugare in constructor a metodei start() pentru pornirea firului - care in spate apeleaza si executa in bulca run()
 * @author mihai.hulea
 */

public class Lift extends Thread{
    private PanouLiftJFrame ui;
    private int etajCurent;
    private int comandaCurentaEtaj;
    private boolean engineStarted;
    private ArrayList<String> coadaComenzi = new ArrayList<>();

    public Lift(PanouLiftJFrame ui) {
        this.ui = ui;
        start();
        comandaCurentaEtaj=-1;
    }
    
    public void run(){
        int k = 0;
        while(true){
            if(coadaComenzi.size()>0&&comandaCurentaEtaj==-1){
                String cmd = coadaComenzi.remove(0);
                engineStarted = true;
                System.out.println("Liftul executa comanda: "+cmd);
                comandaCurentaEtaj = Integer.parseInt(cmd);
                System.out.println("Etaj curent: "+etajCurent+". Liftul se deplaseaza la etajul: "+comandaCurentaEtaj);        
            }
            
            if(engineStarted){
                System.out.println("Lift engine is running..."+k);
                k++;
            }
            
            if(k==10){
                engineStarted = false;
                k = 0;
                System.out.println("Lift engine is stopped.");
                etajCurent = comandaCurentaEtaj;
                System.out.println("Liftul a ajuns la etaul "+etajCurent);
                ui.setText("Etaj curent="+etajCurent);
                comandaCurentaEtaj = -1;
            }
            
            if(!engineStarted){
                System.out.println("Wait for command...");
            }
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Lift.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * 
     * @param cmd String care reprezinta etajul destinatie (ex: "2").
     */
    public void executaComanda(String cmd){
        System.out.println(">>>>"+Integer.parseInt(cmd)+" "+ comandaCurentaEtaj);
        if(!coadaComenzi.contains(cmd)&&Integer.parseInt(cmd)!=comandaCurentaEtaj)
         coadaComenzi.add(cmd);
               
    }
        
    
}
