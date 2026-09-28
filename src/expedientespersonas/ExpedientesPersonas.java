package expedientespersonas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Personas {

    private String nombre;
    private int expediente;
    private int edad;

    public Personas(String nombre, int expediente, int edad) {
        this.nombre = nombre;
        this.expediente = expediente;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getExpediente() {
        return expediente;
    }

    public void setExpediente(int expediente) {
        this.expediente = expediente;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void mostrarDatos() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Expediente: " + expediente);
        System.out.println("Edad: " + edad);
    }

    public static Personas buscarPersonaEnLista(ArrayList<Personas> personas, String nombre) {
        for (Personas persona : personas) {
            if (persona.getNombre().equalsIgnoreCase(nombre)) {
                return persona;
            }
        }

        return null;
    }

    public static void buscarVariasPersonasEnLista(ArrayList<Personas> personas, String[] nombres) {

        long startTime = System.nanoTime();

        for (String nombre : nombres) {

            Personas personaEncontrada = buscarPersonaEnLista(personas, nombre);

            if (personaEncontrada != null) {
                personaEncontrada.mostrarDatos();
            } else {
                System.out.println("No se encontró a la persona con nombre: " + nombre);
            }
        }

        long endTime = System.nanoTime();

        System.out.println("Tiempo de búsqueda: "
                + (endTime - startTime) / 1000000.0 + " ms");
    }
}

public class ExpedientesPersonas {

    public static ArrayList<Personas> cargarArchivoPersonas(String nombreArchivo) {

        ArrayList<Personas> personas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split(",");

                if (partes.length == 3) {

                    String nombre = partes[0].trim();
                    int expediente = Integer.parseInt(partes[1].trim());
                    int edad = Integer.parseInt(partes[2].trim());

                    personas.add(new Personas(nombre, expediente, edad));
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return personas;
    }

    public static void main(String[] args) {

        String nombreArchivo = "personas.txt";

        ArrayList<Personas> personas = cargarArchivoPersonas(nombreArchivo);

        Personas personaEncontrada = personas.get(10);

        if (personaEncontrada != null) {

            System.out.println("Nombre: " + personaEncontrada.getNombre());
            System.out.println("Expediente: " + personaEncontrada.getExpediente());
            System.out.println("Edad: " + personaEncontrada.getEdad());
        }
    }
}