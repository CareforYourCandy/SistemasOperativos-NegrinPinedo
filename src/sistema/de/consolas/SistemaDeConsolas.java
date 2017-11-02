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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SistemaDeConsolas {
    
    private int tamControles;
    private int tamConsolas;
    private int tamPaquetes;
    
    private int inicialControles;
    private int inicialConsolas;
    private int inicialPaquetes; 
    private int inicialEnsambladores; 
    
    private int totalControles = 0;
    private int totalConsolas = 0;
    private int totalPaquetes = 0;
    private int totalEnsambladores = 0; 
    
    private Productor[] productoresConsola;
    private Productor[] productoresPaquetes;
    private Productor[] productoresControles;
    private Ensamblador[] Ensambladores; 
    
    private Semaphore SPControles;
    private Semaphore SPConsolas;
    private Semaphore SPPaquetes;
    
    private Semaphore SCConsolas = new Semaphore(0);
    private Semaphore SCControles = new Semaphore(0);
    private Semaphore SCPaquetes = new Semaphore(0);
    
    private Semaphore SEConsolas = new Semaphore(1);
    private Semaphore SEControles = new Semaphore(1);
    private Semaphore SEPaquetes = new Semaphore(1);
    
    private Almacen almacenControles; 
    private Almacen almacenConsolas;
    private Almacen almacenPaquetes; 
    
    private float tiempo; 
    private int[] cont = new int [1]; 
    private int dias; 
    
    private boolean[] dormido = new boolean[2];
   
    public static int unidades = 0;
    public static int unidadesDespacho = 0; 
    
    public int[] getCont() {
        return cont;
    }
   
    

    public SistemaDeConsolas() {
    }

    public Almacen getAlmacenControles() {
        return almacenControles;
    }

    public Almacen getAlmacenConsolas() {
        return almacenConsolas;
    }

    public Almacen getAlmacenPaquetes() {
        return almacenPaquetes;
    }

    public int getTotalControles() {
        return totalControles;
    }

    public int getTotalConsolas() {
        return totalConsolas;
    }

    public int getTotalPaquetes() {
        return totalPaquetes;
    }

    public int getTotalEnsambladores() {
        return totalEnsambladores;
    }

    public boolean[] getDormido() {
        return dormido;
    }
    
    
    public void iniciar() throws IOException {
        leerArchivo(); 
       
        
    almacenControles = new Almacen(tamControles); 
    almacenConsolas = new Almacen(tamConsolas);
    almacenPaquetes = new Almacen(tamPaquetes);
        
    SPControles = new Semaphore(tamControles);
    SPConsolas = new Semaphore(tamConsolas);
    SPPaquetes = new Semaphore(tamPaquetes);   
    
   Semaphore s1 = new Semaphore(1); 
    cont[0] = dias; 
    dormido[0] = true;
    dormido[1] = true; 
    
     Gerente gerente = new Gerente(s1, cont, tiempo, dormido);
     Cronometrador cronometrador = new Cronometrador(s1, cont, tiempo, dias, dormido ); 
     gerente.start();
     cronometrador.start(); 
    
   int i = 0;
   
   while (inicialControles != 0) {
       
      Controles prodControles1 = crearProductorControles();
      productoresControles[i] = prodControles1; 
      productoresControles[i].start(); 
      inicialControles--; 
      i++; 
   }
     i = 0; 
  while (inicialPaquetes != 0) {
      Paquetes prodPaquetes1 = crearProductorPaquetes();
      productoresPaquetes[i] = prodPaquetes1;
      productoresPaquetes[i].start();
      inicialPaquetes--; 
      i++; 
  }
      i = 0; 
   while (inicialEnsambladores != 0) {
       Ensamblador Ensamblador1 = crearEnsamblador();
       Ensambladores[i] = Ensamblador1;
       Ensambladores[i].start();
       inicialEnsambladores--; 
       i++; 
   }     
   i = 0; 
   
    totalConsolas = inicialConsolas; 
   while (inicialConsolas != 0) {
        Consola prodConsola1 = new Consola(almacenConsolas, SPConsolas, SCConsolas, SEConsolas, tiempo);
        productoresConsola[i] = prodConsola1; 
        prodConsola1.start();
        
        inicialConsolas--; 
        i++; 
   }
   
    }
    
        
   public void contratarProductorControles() {
       if ( totalControles != productoresControles.length) {
       for (int i = 0; i < productoresControles.length; i++) {
           if (productoresControles[i] == null) {
               productoresControles[i] = crearProductorControles();
               productoresControles[i].start(); 
               i = productoresControles.length; 
               
           }
       }
       }
       else
           System.out.println("TA FULL ");
   }             
        
     public void contratarProductorConsola() {
       if ( totalConsolas != productoresConsola.length) {
       for (int i = 0; i < productoresConsola.length; i++) {
           if (productoresConsola[i] == null) {
               productoresConsola[i] = new Consola(almacenConsolas, SPConsolas, SCConsolas, SEConsolas, tiempo);
               productoresConsola[i].start(); 
               i = productoresConsola.length; 
               totalConsolas++; 
           }
       }
       }
       else
           System.out.println("TA FULL ");
   }    
     
     public void contratarProductorPaquetes() {
       if ( totalPaquetes != productoresPaquetes.length) {
       for (int i = 0; i < productoresPaquetes.length; i++) {
           if (productoresPaquetes[i] == null) {
               productoresPaquetes[i] = crearProductorPaquetes(); 
               productoresPaquetes[i].start(); 
               i = productoresPaquetes.length; 
           }
       }
       }
       else
           System.out.println("TA FULL ");
   }    
     
      public void contratarEnsamblador() {
       if ( totalEnsambladores != Ensambladores.length) {
       for (int i = 0; i < Ensambladores.length; i++) {
           if (Ensambladores[i] == null) {
               Ensambladores[i] = crearEnsamblador(); 
               Ensambladores[i].start(); 
               i = Ensambladores.length; 
           }
       }
       }
       else
           System.out.println("TA FULL ");
   }    
     
    public Controles crearProductorControles() {
        
        if (totalControles == productoresControles.length  ) {
            System.out.println("ESTA FULL CONTROLES");
            return null; 
        }
        else {
            totalControles++;
        Controles prodControles = new Controles(almacenControles, SPControles, SCControles, SEControles, tiempo);
        return prodControles; 
            
        }
        }
        
    
    
    public Paquetes crearProductorPaquetes() {
      if (totalPaquetes == productoresPaquetes.length  ) {
            System.out.println("ESTA FULL PAQUETES");
            return null; 
        }
        else {
          totalPaquetes++; 
        Paquetes prodPaquetes = new Paquetes(almacenPaquetes, SPPaquetes, SCPaquetes, SEPaquetes, tiempo);
       return prodPaquetes; 
        }
    
    
        }
    
    
    public Consola crearProductorConsola() {
         
               if (totalConsolas == productoresConsola.length  ) {
            System.out.println("ESTA FULL CONSOLA");
            return null; 
        }
        else {
         totalConsolas++;           
        Consola prodConsola = new Consola(almacenConsolas, SPPaquetes, SCPaquetes, SEPaquetes, tiempo);
       return prodConsola; 
        }
        
    }
        
    public Ensamblador crearEnsamblador() {
       
        
        if (totalEnsambladores == Ensambladores.length) {
            System.out.println("ESTA FULL ENSAMBLADORES");
            return null; 
        }
        else {
          Ensamblador Matienzo = new Ensamblador(almacenConsolas, almacenControles, almacenPaquetes, 
                SPConsolas, SPControles, SPPaquetes, SCConsolas, SCControles, SCPaquetes, SEConsolas, SEControles, SEPaquetes, tiempo);
              totalEnsambladores++;
              System.out.println("Total Ensambladores:" + totalEnsambladores);
            return Matienzo; 
        }
    }
    
    public void despedirProductorControles() {
        if ( totalControles != 0) {
       for (int i = productoresControles.length - 1; i >= 0; i--) {
           if (productoresControles[i]!= null) {
               
               productoresControles[i].despedir(); 
               totalControles--; 
               productoresControles[i] = null; 
               i = 0; 
               
           }
       } 
        }
        else
            System.out.println("NO FURULA. NO HAY PRODUCTORES DE CONSOLA");
    }
    
    public void despedirProductorPaquetes() {
        if (totalPaquetes != 0) {
         for (int i = productoresPaquetes.length - 1; i >= 0; i--) {
           if (productoresPaquetes[i]!= null) {
               
               productoresPaquetes[i].despedir(); 
               totalPaquetes--; 
               productoresPaquetes[i] = null; 
               i = 0; 
               
           }
       } 
        }
        else
            System.out.println("NO FORULA. NO PAQUETES");
    }
    
    public void despedirProductorConsola() {
        if (totalConsolas != 0) {
         for (int i = productoresConsola.length - 1; i >= 0; i--) {
           if (productoresConsola[i]!= null) {
               
               productoresConsola[i].despedir(); 
               totalConsolas--; 
               productoresConsola[i] = null; 
               i = 0; 
               
           }
       } 
        }
        else
            System.out.println("NO FURULA NO CONSOLA ");
    }
    
    public void despedirEnsamblador() {
        if (totalEnsambladores != 0) {
         for (int i = Ensambladores.length - 1; i >= 0; i--) {
           if (Ensambladores[i]!= null) {
               
               Ensambladores[i].despedir(); 
               totalEnsambladores--; 
               Ensambladores[i] = null; 
               i = -1; 
               
           }
          
       } 
        }
        else
            System.out.println("NO FURULA NO ENSAMBLADORES");
    }
    public void leerArchivo() throws FileNotFoundException, IOException {
       String  cadena;  
       
       String archivo = "src\\sistema\\de\\consolas\\ConfiguracionEmpresa.txt"; 
       FileReader fr = new FileReader(archivo); 
       BufferedReader br = new BufferedReader(fr); 
        cadena = br.readLine(); 
        try {
        if (!cadena.equals("Tiempo (en segundos):")) {
            System.out.println("Archivo corrompido (Lineas con strings o desplazamiento de lineas) ");
        }
        cadena = br.readLine(); 

        tiempo = Float.parseFloat(cadena);
        if (tiempo <= 0) {
            System.out.println("Inserto un tiempo inválido");
        }
        tiempo = tiempo * 1000; 
       
        cadena = br.readLine();
         if (!cadena.equals("Dias entre despacho: ")) {
            System.out.println("Archivo corrompido (Lineas con strings o desplazamiento de lineas) ");
        }
        cadena = br.readLine(); 
  
        dias = Integer.parseInt(cadena);
        if (dias <= 0) {
            System.out.println("Insertó un monto de dias inválido (menor o igual a 0)"); 
        }
       
        cadena = br.readLine();
         if (!cadena.equals("Capacidad Maxima Almacen Controles:")) {
            System.out.println("Archivo corrompido (Lineas con strings o desplazamiento de lineas) ");
        }
        cadena = br.readLine(); 
        tamControles = Integer.parseInt(cadena);
        if (tamControles <= 0) {
            System.out.println("El tamaño del almacen de controles es inválido (menor o igual a 0)");
        }
        
        cadena = br.readLine();
        if (!cadena.equals("Capacidad Maxima Almacen Consolas:")) {
            System.out.println("Archivo corrompido (Lineas con strings o desplazamiento de lineas) ");
        }
        
        cadena = br.readLine();
        tamConsolas = Integer.parseInt(cadena); 
        if (tamConsolas <= 0) {
            System.out.println("El tamaño del almacen de controles es inválido (menor o igual a 0)");
        }
        cadena = br.readLine();
          if (!cadena.equals("Capacidad Maxima Almacen Paquetes:")) {
            System.out.println("Archivo corrompido (Lineas con strings o desplazamiento de lineas) ");
        }
        cadena = br.readLine();
        tamPaquetes = Integer.parseInt(cadena); 
        if (tamPaquetes <= 0) {
            System.out.println("El tamaño del almaen de paquetes es inválido (menor o igual  a 0)");
        }
        cadena = br.readLine();
          if (!cadena.equals("Cantidad Inicial Controles:")) {
            System.out.println("Archivo corrompido (Lineas con strings o desplazamiento de lineas) ");
        }
          
        cadena = br.readLine();
        inicialControles = Integer.parseInt(cadena); 
        if (inicialControles < 0) {
            System.out.println("La cantidad inicial de productores de controles es menor a 0");
        }
        cadena = br.readLine();
          if (!cadena.equals("Cantidad Inicial Consolas:")) {
            System.out.println("Archivo corrompido (Lineas con strings o desplazamiento de lineas) ");
        }
        cadena = br.readLine();
        inicialConsolas = Integer.parseInt(cadena); 
        if (inicialConsolas <0) {
            System.out.println("La cantidad inicial de productores de consoals es menor a 0");
        }
        cadena = br.readLine();
          if (!cadena.equals("Cantidad Inicial Paquetes:")) {
            System.out.println("Archivo corrompido (Lineas con strings o desplazamiento de lineas) ");
        }
        cadena = br.readLine();
        inicialPaquetes = Integer.parseInt(cadena);
        if (inicialPaquetes <0) {
        System.out.println("La cantidad inicial de productores de paquetes es menor a 0 ");
        }
        
        cadena = br.readLine();
          if (!cadena.equals("Cantidad M�xima Controles:")) {
            System.out.println("Archivo corrompido (Lineas con strings o desplazamiento de lineas) ");
        }
        cadena = br.readLine();
        if (Integer.parseInt(cadena) <= 0) {
            System.out.println("La cantidad máxima de productores de controles es menor o igual a 0");
        }
        productoresControles = new Productor[Integer.parseInt(cadena)]; 
        cadena = br.readLine();
          if (!cadena.equals("Cantidad M�xima Consolas:")) {
            System.out.println("Archivo corrompido (Lineas con strings o desplazamiento de lineas) ");
        }
        cadena = br.readLine();
        if (Integer.parseInt(cadena) <= 0) {
            System.out.println("La cantidad máxima de productores de consola es menor o igual a 0");
        }
        productoresConsola = new Productor[Integer.parseInt(cadena)]; 
        cadena = br.readLine();
          if (!cadena.equals("Cantidad M�xima Paquetes:")) {
            System.out.println("Archivo corrompido (Lineas con strings o desplazamiento de lineas) ");
        }
        cadena = br.readLine();
        if (Integer.parseInt(cadena) <= 0) {
            System.out.println("La cantidad máxima de productores de paquetes es menor o igual a 0");
        }
        productoresPaquetes = new Productor[Integer.parseInt(cadena)]; 
        cadena = br.readLine();
          if (!cadena.equals("Cantidad Inicial Ensambladores:")) {
            System.out.println("Archivo corrompido (Lineas con strings o desplazamiento de lineas) ");
        }
        cadena = br.readLine();
        if (Integer.parseInt(cadena) < 0) {
            System.out.println("La cantidad inicial de ensambladores es menor a 0"); 
        }
        inicialEnsambladores = Integer.parseInt(cadena);
        cadena = br.readLine();
          if (!cadena.equals("Cantidad M�xima Ensambladores:")) {
            System.out.println("Archivo corrompido (Desplazamiento de lineas o modificacion de lineas no modificables) ");
        }
        cadena = br.readLine();
        if (Integer.parseInt(cadena) <= 0) {
            System.out.println("La cantidad maxima de ensambladores es menor o igual a 0");
            
        }
         Ensambladores = new Ensamblador[Integer.parseInt(cadena)]; 
       br.close(); 
       
        }
       catch (NumberFormatException ex) {
           System.out.println("Se ha ingresado un valor decimal en un lugar donde va un entero ");
       }
       
     
    }
    
    public void expropiese() {
        while (totalEnsambladores != 0) {
            despedirEnsamblador(); 
        }
        while (totalPaquetes != 0) {
            despedirProductorPaquetes();
        }
        while (totalConsolas != 0) {
            despedirProductorConsola();
        }
        while (totalControles != 0) {
            despedirProductorControles(); 
        }
    }
  
}
