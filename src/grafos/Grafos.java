package grafos;
class GrafoArrays {

    private String[] vertices;
    private int numVertices;
    private int[][] matrizAdyacencia;

    // Constructor
    public GrafoArrays(String origen) {
        creaTablaVertices(origen);
        matrizAdyacencia = new int[numVertices][numVertices];
        populateMatrizAdyacencia(origen);
    }

    // Crear tabla de vertices
    private void creaTablaVertices(String origen) {

        String[] lineas = origen.split("\\R");

        vertices = new String[lineas.length * 2];
        numVertices = 0;

        for (String linea : lineas) {

            String[] extremos = obtenerExtremos(linea);

            if (extremos != null) {
                agregarVertice(extremos[0]);
                agregarVertice(extremos[1]);
            }
        }
    }

    // Obtener los extremos de cada arista
    private String[] obtenerExtremos(String linea) {

        String arista = linea.trim();

        if (!arista.contains("--")) {
            return null;
        }

        int etiqueta = arista.indexOf('[');

        if (etiqueta >= 0) {
            arista = arista.substring(0, etiqueta);
        }

        arista = arista.replace(";", "").trim();

        String[] extremos = arista.split("--", 2);

        if (extremos.length == 2) {
            return new String[] {
                extremos[0].trim(),
                extremos[1].trim()
            };
        }

        return null;
    }

    // Agregar vertices sin repetir
    private void agregarVertice(String vertice) {

        for (int i = 0; i < numVertices; i++) {

            if (vertices[i].equals(vertice)) {
                return;
            }
        }

        vertices[numVertices] = vertice;
        numVertices++;
    }

    // Llenar matriz de adyacencia
    private void populateMatrizAdyacencia(String origen) {

        String[] lineas = origen.split("\\R");

        for (String linea : lineas) {

            String[] extremos = obtenerExtremos(linea);

            if (extremos != null) {

                int peso = obtenerPeso(linea);

                agregarArista(
                    extremos[0],
                    extremos[1],
                    peso
                );
            }
        }
    }

    // Obtener el peso de cada arista
    private int obtenerPeso(String linea) {

        int inicio = linea.indexOf('[');
        int fin = linea.indexOf(']');

        if (inicio >= 0 && fin > inicio) {

            String etiqueta = linea.substring(inicio + 1, fin);

            java.util.regex.Matcher matcher =
                java.util.regex.Pattern
                    .compile("(?:weight|label)\\s*=\\s*\"?(-?\\d+)")
                    .matcher(etiqueta);

            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        }

        return 1;
    }

    // Obtener indice de un vertice
    private int obtenerIndiceVertice(String vertice) {

        for (int i = 0; i < numVertices; i++) {

            if (vertices[i].equals(vertice)) {
                return i;
            }
        }

        return -1;
    }

    // Agregar arista a la matriz
    private void agregarArista(String vertice1, String vertice2, int peso) {

        int indice1 = obtenerIndiceVertice(vertice1);
        int indice2 = obtenerIndiceVertice(vertice2);

        if (indice1 >= 0 && indice2 >= 0) {

            matrizAdyacencia[indice1][indice2] = peso;
            matrizAdyacencia[indice2][indice1] = peso;
        }
    }

    // Mostrar informacion del grafo
    public void display() {

        System.out.println("Vertices del grafo:");
        displayVertices();

        System.out.println("\nMatriz de adyacencia:");
        displayMatrizAdyacencia();
    }

    // Mostrar vertices
    private void displayVertices() {

        for (int i = 0; i < numVertices; i++) {
            System.out.println(vertices[i]);
        }
    }

    // Mostrar matriz de adyacencia
    private void displayMatrizAdyacencia() {

        System.out.printf("%10s", "");

        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%10s", vertices[i]);
        }

        System.out.println();

        for (int i = 0; i < numVertices; i++) {

            System.out.printf("%10s", vertices[i]);

            for (int j = 0; j < numVertices; j++) {
                System.out.printf("%10d", matrizAdyacencia[i][j]);
            }

            System.out.println();
        }
    }
}


// Clase principal
public class Grafos {

    public static void main(String[] args) {

        String texto = cargaArchivo("grafo_salon.txt");

        System.out.println("Contenido del archivo:\n" + texto);

        GrafoArrays grafo = new GrafoArrays(texto);

        grafo.display();
    }

    // Cargar archivo de texto
    public static String cargaArchivo(String ruta) {

        StringBuilder contenido = new StringBuilder();

        try {

            java.io.File archivo = new java.io.File(ruta);

            java.util.Scanner scanner =
                new java.util.Scanner(archivo);

            while (scanner.hasNextLine()) { 
                contenido.append(scanner.nextLine()).append("\n");
            }

            scanner.close();

        } catch (java.io.FileNotFoundException e) {

            System.out.println("Archivo no encontrado: " + ruta);
        }

        return contenido.toString();
    }
}
