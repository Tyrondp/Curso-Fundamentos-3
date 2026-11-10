package grafos;
import java.util.ArrayList;
public class grafos {
    public static void main(String[] args) {

        String texto = cargaArchivo("src/grafo_salon3.txt");

        System.out.println("Contenido del archivo:\n" + texto);

        GrafoArrays grafo = new GrafoArrays(texto);

        grafo.display();

        grafo.DFS("Carol", "Diego");
        grafo.BFS("Carol", "Diego");

        grafo.despliegaArcos();
    }

    public static String cargaArchivo(String ruta) {
        String contenido = "";

        try {
            java.io.File archivo = new java.io.File(ruta);
            java.util.Scanner scanner = new java.util.Scanner(archivo);

            while (scanner.hasNextLine()) {
                contenido += scanner.nextLine() + "\n";
            }

            scanner.close();

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + ruta);
        }

        return contenido;
    }
}