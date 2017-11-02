/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.de.consolas;

/**
 *
 * @author Luciano Pinedo
 */
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ensamblador extends Thread {
    
    private Almacen almacenConsolas;
    private Almacen almacenControles;
    private Almacen almacenPaquetes; 
    private Semaphore SPConsolas;
    private Semaphore SPControles;
    private Semaphore SPPaquetes; 
    private Semaphore SCConsolas;
    private Semaphore SCControles;
    private Semaphore SCPaquetes; 
    private Semaphore SEConsolas;
    private Semaphore SEControles;
    private Semaphore SEPaquetes; 
    private float tiempo; 
    private boolean stop = false; 
    
    public Ensamblador(Almacen almacenConsolas, Almacen almacenControl, Almacen almacenPaquete, Semaphore SP1, Semaphore SP2, Semaphore SP3, Semaphore SC1, Semaphore SC2, Semaphore SC3, Semaphore SE1, Semaphore SE2, Semaphore SE3, float tiempo) {
        this.almacenConsolas = almacenConsolas;
        this.almacenControles = almacenControl;
        this.almacenPaquetes = almacenPaquete;
        this.SPConsolas = SP1;
        this.SPControles = SP2;
        this.SPPaquetes = SP3;
        this.SCConsolas = SC1;
        this.SCControles = SC2;
        this.SCPaquetes = SC3;
        this.SEConsolas = SE1;
        this.SEControles = SE2;
        this.SEPaquetes = SE3;
        this.tiempo = tiempo; 
    }
    
     public void despedir() {
         
      stop = true; 
        
         if (stop = true) {
              
         }
    }
    
    public void run(){
        while(stop == false){
            try {
                //Consolas
                
                SCConsolas.acquire(1);
                SEConsolas.acquire(1);
                    almacenConsolas.consumir(almacenConsolas.getApunEnsamblador());
                    almacenConsolas.setApunEnsamblador((almacenConsolas.getApunEnsamblador()+1)%almacenConsolas.getAlmacen().length);
                SEConsolas.release(1);
                SPConsolas.release(1);
                System.out.println("CONSOLAS ENSAMBLA");
                almacenConsolas.VerAlmacen();
                
                //Controles
                SCControles.acquire(2);
                SEControles.acquire(1);
                    almacenControles.consumir(almacenControles.getApunEnsamblador());
                    almacenControles.consumir((almacenControles.getApunEnsamblador()+1)%almacenControles.getAlmacen().length);
                    almacenControles.setApunEnsamblador((almacenControles.getApunEnsamblador()+2)%almacenControles.getAlmacen().length);
                SEControles.release(1);
                SPControles.release(2);
                System.out.println("CONTROLES ENSAMBLA");
                almacenControles.VerAlmacen();
                
                //Paquetes
                SCPaquetes.acquire(1);
                SEPaquetes.acquire(1);
                    almacenPaquetes.consumir(almacenPaquetes.getApunEnsamblador());
                    almacenPaquetes.setApunEnsamblador((almacenPaquetes.getApunEnsamblador()+1)%almacenPaquetes.getAlmacen().length);
                SEPaquetes.release(1);
                SPPaquetes.release(1);
                System.out.println("PAQUETES ENSAMBLA");
                almacenPaquetes.VerAlmacen();
                SistemaDeConsolas.unidades++;
                SistemaDeConsolas.unidadesDespacho++; 
                sleep((long) (tiempo * 2));
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Ensamblador.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    
    
    
}
