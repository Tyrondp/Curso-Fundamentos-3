package grafos;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class GrafoArrays {
    private String[] vertices;
    private int numVertices;
    private int[][] matrizAdyacencia;

    public GrafoArrays(String origen) {
        creaTablaVertices(origen);
        matrizAdyacencia = new int[numVertices][numVertices];
        populateMatrizAdyacencia(origen);
    }

    private void creaTablaVertices(String origen) {
        String[] lineas = origen.split("\\R");
        vertices = new String[lineas.length * 2];
        numVertices = 0;
        for (String linea : lineas) {
            String arista = linea.trim();
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }
            int etiqueta = arista.indexOf('[');
            if (etiqueta >= 0) {
                arista = arista.substring(0, etiqueta);
            }
            arista = arista.replace(";", "");
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                agregarVertice(extremos[0].trim());
                agregarVertice(extremos[1].trim());
            }
        }
    }

    // Divide el texto en líneas; cada línea se separa en dos vértices y un peso,
    // y se llama a agregarArista para guardarlos en la matriz de adyacencia.
    private void populateMatrizAdyacencia(String origen) {
        String[] lineas = origen.split("\\R");
        Pattern numero = Pattern.compile("-?\\d+");
        for (String linea : lineas) {
            String arista = linea.trim();
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }
            int peso = 1; // peso por defecto si la línea no trae número
            int etiqueta = arista.indexOf('[');
            if (etiqueta >= 0) {
                Matcher m = numero.matcher(arista.substring(etiqueta));
                if (m.find()) {
                    peso = Integer.parseInt(m.group());
                }
                arista = arista.substring(0, etiqueta);
            }
            arista = arista.replace(";", "");
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                agregarArista(extremos[0].trim(), extremos[1].trim(), peso);
            }
        }
    }

    private void agregarVertice(String vertice) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertice)) {
                return;
            }
        }
        vertices[numVertices] = vertice;
        numVertices++;
    }

    public void display() {
        System.out.println("Vértices del grafo:");
        displayVertices();
        System.out.println("\nMatriz de adyacencia:");
        displayMatrizAdyacencia();
    }

    private void displayVertices() {
        for (int i = 0; i < numVertices; i++) {
            System.out.println(vertices[i]);
        }
    }

    private void displayMatrizAdyacencia() {
        System.out.printf("%-8s", "");
        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%-8s", vertices[i]);
        }
        System.out.println();
        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%-8s", vertices[i]);
            for (int j = 0; j < numVertices; j++) {
                System.out.printf("%-8d", matrizAdyacencia[i][j]);
            }
            System.out.println();
        }
    }

    private int obtenerIndiceVertice(String vertice) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertice)) {
                return i;
            }
        }
        return -1;
    }

    private void agregarArista(String vertice1, String vertice2, int peso) {
        int indice1 = obtenerIndiceVertice(vertice1);
        int indice2 = obtenerIndiceVertice(vertice2);
        if (indice1 >= 0 && indice2 >= 0) {
            matrizAdyacencia[indice1][indice2] = peso;
            matrizAdyacencia[indice2][indice1] = peso; // Para grafos no dirigidos
        }
    }
    public void DFS(String verticeInicial, String verticeFinal) {
        boolean[] visitado = new boolean[numVertices];
        int indiceInicial = obtenerIndiceVertice(verticeInicial);
        int indiceFinal = obtenerIndiceVertice(verticeFinal);
        if (indiceInicial == 1 || indiceFinal == -1) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        System.out.println("Recorrido DFS desde " + verticeInicial + " hasta " + verticeFinal + ":");
        java.util.Stack<Integer> stack = new java.util.Stack<>();
        stack.push(indiceInicial);
        while (!stack.isEmpty()){
            int indiceActual = stack.pop();
            if (!visitado[indiceActual]) {
                visitado[indiceActual] = true;
                System.out.println(vertices[indiceActual] + " ");
                if (indiceActual == indiceFinal){
                    System.out.println("\nSe ha alcanzado el vértice final.");
                    return;
                }
            }
            // Agregar vecinos no visitados al stack
            for (int i = 0; i < numVertices; i++) {
                if (matrizAdyacencia[indiceActual][i] != 0 && !visitado[i]){
                    stack.push(i);
                }
            }
        }
    }
}

public class Grafos {
    public static void main(String[] args) {
        String texto = cargaArchivo("grafo_salon.txt");
        System.out.println("Contenido del archivo:\n" + texto);
        GrafoArrays grafo = new GrafoArrays(texto);
        grafo.display();
        grafo.DFS("Arcangel", "Lucia");
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