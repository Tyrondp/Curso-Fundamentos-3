/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafos;

import java.util.ArrayList;

/**
 *
 * @author HP
 */
class GrafoArrays {
    private ArrayList<String> arcosDFS;
    private ArrayList<String> arcosBFS;
    private String[] vertices;
    private int numVertices;
    private int[][] matrizAdyacencia;

    public GrafoArrays(String origen) {
        creaTablaVertices(origen);
        matrizAdyacencia = new int[numVertices][numVertices];
        populateMatrizAdyacencia(origen);
        arcosDFS = new ArrayList<>();
        arcosBFS = new ArrayList<>();
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
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                agregarVertice(extremos[0].trim());
                agregarVertice(extremos[1].trim());
            }
        }
        // Crear método para agregar aristas después de crear la tabla de vértices populateMatrizAdyacencia(origen);
        // Dividir el archivo de texto en lineas y procesar cada línea para agregar aristas
        // Cada linea se separa en dos vértices y un peso, luego se llama al método agregarArista para agregarlas a la matriz de adyacencia
        // Crear un método para desplegar la Matriz de adyacencia
    }
    private void populateMatrizAdyacencia(String origen) {

        String[] lineas = origen.split("\\R");
        for (String linea : lineas) {
            String arista = linea.trim();
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }

            int peso = 1;
            java.util.regex.Matcher matcher =
                    java.util.regex.Pattern
                            .compile("\\[\\s*\\w+\\s*=\\s*\"(\\d+)\"\\s*\\]")
                            .matcher(arista);

            if (matcher.find()) {
                peso = Integer.parseInt(matcher.group(1));
            }

            int etiqueta = arista.indexOf('[');
            if (etiqueta >= 0) {
                arista = arista.substring(0, etiqueta);
            }

            String[] extremos = arista.split("--", 2);

            if (extremos.length == 2) {
                agregarArista(
                        extremos[0].trim(),
                        extremos[1].trim(),
                        peso
                );
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
        displayMatrizAdyacencia();
    }

    private void displayVertices() {
        for (int i = 0; i < numVertices; i++) {
            System.out.println(vertices[i]);
        }
    }
    private void displayMatrizAdyacencia() {
        System.out.println("Matriz de Adyacencia:");
        // Imprimir encabezado de columnas
        System.out.print("    ");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("   " +vertices[i].substring(0,3) );
        }
        System.out.println();
        for (int i = 0; i < numVertices; i++) {
            System.out.print(vertices[i].substring(0, 4) + "    ");
            for (int j = 0; j < numVertices; j++) {
                System.out.print(matrizAdyacencia[i][j] + "     ");
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

        arcosDFS.clear();

        boolean[] visitado = new boolean[numVertices];

        int indiceInicial = obtenerIndiceVertice(verticeInicial);
        int indiceFinal = obtenerIndiceVertice(verticeFinal);

        if (indiceInicial == -1 || indiceFinal == -1) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }

        System.out.println("Recorrido DFS desde " + verticeInicial + " hasta " + verticeFinal + ":");

        java.util.Stack<Integer> stack = new java.util.Stack<>();

        stack.push(indiceInicial);
        visitado[indiceInicial] = true;

        System.out.print(vertices[indiceInicial] + " ");

        while (!stack.isEmpty()) {

            int indiceActual = stack.peek();

            if (indiceActual == indiceFinal) {
                System.out.println("\nSe ha alcanzado el vértice final.");
                return;
            }

            int vecino = -1;

            for (int i = 0; i < numVertices; i++) {
                if (matrizAdyacencia[indiceActual][i] != 0 && !visitado[i]) {
                    vecino = i;
                    break;
                }
            }

            if (vecino != -1) {

                visitado[vecino] = true;

                String arco = vertices[indiceActual] + " -- " + vertices[vecino];
                arcosDFS.add(arco);

                stack.push(vecino);

                System.out.print(vertices[vecino] + " ");

            } else {
                stack.pop();
            }
        }

        System.out.println("\nNo se encontró un camino al vértice final.");
    }
    public void BFS(String verticeInicial, String verticeFinal) {

        arcosBFS.clear();

        boolean[] visitado = new boolean[numVertices];

        int indiceInicial = obtenerIndiceVertice(verticeInicial);
        int indiceFinal = obtenerIndiceVertice(verticeFinal);

        if (indiceInicial == -1 || indiceFinal == -1) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }

        System.out.println("Recorrido BFS desde " + verticeInicial + " hasta " + verticeFinal + ":");

        java.util.Queue<Integer> queue = new java.util.LinkedList<>();

        queue.add(indiceInicial);
        visitado[indiceInicial] = true;

        while (!queue.isEmpty()) {

            int indiceActual = queue.poll();

            System.out.print(vertices[indiceActual] + " ");

            if (indiceActual == indiceFinal) {
                System.out.println("\nSe ha alcanzado el vértice final.");
                return;
            }

            for (int i = 0; i < numVertices; i++) {

                if (matrizAdyacencia[indiceActual][i] != 0 && !visitado[i]) {

                    visitado[i] = true;

                    String arco = vertices[indiceActual] + " -- " + vertices[i];

                    arcosBFS.add(arco);

                    queue.add(i);
                }
            }
        }

        System.out.println("\nNo se encontró un camino al vértice final.");
    }
    public void despliegaArcos() {

        System.out.println("\n========== ARCOS DFS ==========");

        if (arcosDFS != null) {
            for (String arco : arcosDFS) {
                System.out.println(arco);
            }
        } else {
            System.out.println("arcosDFS es nulo.");
        }

        System.out.println("\n========== ARCOS BFS ==========");

        if (arcosBFS != null) {
            for (String arco : arcosBFS) {
                System.out.println(arco);
            }
        } else {
            System.out.println("arcosBFS es nulo.");
        }

        System.out.println("\n========== ESTADÍSTICAS ==========");

        System.out.println("Total de vértices: " + numVertices);

        int totalArcos = numVertices * (numVertices - 1) / 2;
        int arcosOcupados = 0;

        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] != 0) {
                    arcosOcupados++;
                }
            }
        }

        System.out.println("Total de arcos posibles: " + totalArcos);
        System.out.println("Total de arcos ocupados: " + arcosOcupados);

        System.out.println("\n========== COMPARACIÓN ==========");

        if (arcosDFS != null && arcosBFS != null) {

            System.out.println("Cantidad de arcos DFS: " + arcosDFS.size());
            System.out.println("Cantidad de arcos BFS: " + arcosBFS.size());

            if (arcosDFS.size() < arcosBFS.size()) {
                System.out.println("DFS tiene menos arcos.");

            } else if (arcosBFS.size() < arcosDFS.size()) {
                System.out.println("BFS tiene menos arcos.");

            } else {
                System.out.println("Ambos tienen la misma cantidad de arcos.");
            }

        } else {
            System.out.println("No se pueden comparar los recorridos.");
        }
    }
}