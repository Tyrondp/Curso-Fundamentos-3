package grafos;

import java.util.ArrayList;

public class Nodos{
    String nombre;
    int peso; 
    ArrayList<Nodos>adyacentes;
    public Nodos(String Nombre){
        this.nombre = Nombre;
        this.peso= 0; 
              this.adyacentes = new ArrayList<>();
        
    }
    public void agregarAdyacenciate (Nodos nodo, int peso ){
        adyacentes.add(nodo);
        nodo.peso = peso; 
    }
    public void display(){
        System.out.println(nombre);
        System.out.println("|adyacentes:");
        for(Nodos nodo : adyacentes){
            System.out.println(nodo.nombre + " " +  nodo.peso + " ");
          }
        System.out.println("");
    }
    public String getNombre(){
        return nombre;
    }
       public ArrayList<Nodos> getAdyacentes(){
        return adyacentes;
    }
    public int getPeso(){
        return peso;
    }

}
    