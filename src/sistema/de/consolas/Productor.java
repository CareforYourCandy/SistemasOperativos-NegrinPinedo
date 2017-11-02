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
public abstract class Productor extends Thread {

    protected Almacen almacen; 
    protected Semaphore SP;
    protected Semaphore SC;
    protected Semaphore SE; 
    protected boolean stop = false; 
    protected float tiempo; 
    
    public Productor(Almacen almacen, Semaphore SP, Semaphore SC, Semaphore SE, float tiempo) {
    
        this.almacen = almacen;
        this.SP = SP;
        this.SC = SC;
        this.SE = SE;
        this.tiempo = tiempo; 
    }
    
    public abstract void run();
    
    public void despedir() {
        stop = true; 
      
       
        
    }
    
}
