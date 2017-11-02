/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.de.consolas;


import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luciano Pinedo
 */

//EQUIVALENTE AL ESCRITOR

public class Cronometrador extends Thread {
     private Semaphore s1;
     private int[] cont;
     private float tiempo; 
     private int dias; 
     private boolean[] dormido; 
     
    public Cronometrador(Semaphore s1, int cont[], float tiempo, int dias, boolean[] dormido) {
        this.s1 = s1;
        this.cont = cont;
        this.tiempo = tiempo; 
        this.dias = dias; 
        this.dormido = dormido; 
    }
    
    public void run(){
        double tiempoEscritura = (tiempo*(1.2)/24); 
        while (true) {
            try {
                s1.acquire();
                dormido[0] = false; 
                sleep((long) tiempoEscritura); 
                if (cont[0] == 0) {
                    cont[0] = dias + 1; 
                }
                cont[0]--;
                System.out.println(cont[0]);
                s1.release(); 
                dormido[0] = true; 
                sleep((long) (tiempo - tiempoEscritura)); 
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Cronometrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        
                }
}
}