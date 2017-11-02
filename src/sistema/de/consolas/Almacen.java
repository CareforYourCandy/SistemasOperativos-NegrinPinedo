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
public class Almacen {
    
    private int apunEnsamblador = 0;
    private int apunProductor = 0;
    private boolean[] almacen; 
    private int cantidad = 0;

    public Almacen(int tamaño) {
        this.almacen = new boolean[tamaño]; 
    }

    public boolean[] getAlmacen() {
        return almacen;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    

    public int getApunEnsamblador() {
        return apunEnsamblador;
    }

    public int getApunProductor() {
        return apunProductor;
    }

    public void setAlmacen(boolean[] almacen) {
        this.almacen = almacen;
    }
    
    public void setApunEnsamblador(int apuntadorEnsamblador) {
        this.apunEnsamblador = apuntadorEnsamblador;
    }

    public void setApunProductor(int apuntadorProductor) {
        this.apunProductor = apuntadorProductor;
    }
    
    public void producir(int pos) {
        almacen[pos] = true;
        cantidad = cantidad +1;
        
    }
    
    public void producirPaquete(int pos) {
        almacen[pos] = true;
        cantidad = cantidad +1;
        

        
    }
    
    public void consumir(int pos) {
        almacen[pos] = false; 
        cantidad = cantidad -1;
    }
    
    public void VerAlmacen(){
        System.out.print("|");
        for(int i=0; i<almacen.length;i++){
            System.out.print(almacen[i]+" ");
        }
        System.out.println("|");
    }
    
}
