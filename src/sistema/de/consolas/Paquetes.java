/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.de.consolas;

import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luciano Pinedo
 */
public class Paquetes extends Productor {

    public Paquetes( Almacen almacen, Semaphore SP, Semaphore SC, Semaphore SE, float tiempo) {
        super(almacen, SP, SC, SE, tiempo);
    }
    
    public void run() {
        while(stop == false){
            try {
                SP.acquire(1);
                sleep((long) tiempo);
               
                
                SE.acquire(1);
                    almacen.producir(almacen.getApunProductor());
                    almacen.setApunProductor((almacen.getApunProductor()+1)%almacen.getAlmacen().length);
                    System.out.println("PAQUETES");
                    almacen.VerAlmacen();
                SE.release(1);
                SC.release(1);
                
                
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
