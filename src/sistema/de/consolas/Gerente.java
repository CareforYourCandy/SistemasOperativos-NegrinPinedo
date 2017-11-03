/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.de.consolas;


import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random; 

/**
 *
 * @author Luciano Pinedo
 */

//EQUIVALENTE AL LECTOR 

public class Gerente extends Thread {
    
     private Semaphore s1;
 
     private int cont[];
     private float tiempo; 
     private boolean[] dormido; 

    public Gerente(Semaphore s1, int cont[], float tiempo, boolean[] dormido) {
        this.s1 = s1;
        this.cont = cont;
        this.tiempo = tiempo; 
        this.dormido = dormido; 
    }
    
     @Override
    public void run() {
      Random aux = new Random();
      int hora = aux.nextInt(18 - 6  + 1) + 6; 
      while (true) {
            try {
                s1.acquire();
                dormido[1] = false; 
                if (cont[0] == 0) {
                    despachar(); 
                    sleep(100); 
                }
                s1.release(); 
                dormido[1] = true; 
                sleep((long) (hora*tiempo/24)-100);
                hora = aux.nextInt(18 - 6  + 1) + 6; 
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Gerente.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
      
    }
    
    public void despachar() {
        System.out.println("Se despacho ");
        SistemaDeConsolas.unidadesDespacho = 0; 
    }
    
}
