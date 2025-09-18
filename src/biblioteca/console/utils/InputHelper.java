package biblioteca.console.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utilidad para manejar entrada de datos desde consola
 */
public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Lee una línea de texto desde consola
     */
    public static String leerTexto(String mensaje) {
        System.out.print(mensaje + ": ");
        return scanner.nextLine().trim();
    }

    /**
     * Lee un texto obligatorio (no puede estar vacío)
     */
    public static String leerTextoObligatorio(String mensaje) {
        String texto;
        do {
            texto = leerTexto(mensaje);
            if (texto.isEmpty()) {
                System.out.println("Este campo no puede estar vacío. Intente nuevamente.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    /**
     * Lee un número entero desde consola
     */
    public static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + ": ");
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }
    }

    /**
     * Lee un número entero dentro de un rango específico
     */
    public static int leerEnteroEnRango(String mensaje, int min, int max) {
        int numero;
        do {
            numero = leerEntero(mensaje);
            if (numero < min || numero > max) {
                System.out.println("El número debe estar entre " + min + " y " + max + ".");
            }
        } while (numero < min || numero > max);
        return numero;
    }

    /**
     * Permite al usuario seleccionar un objeto de una lista
     * 
     * @param <T>      Tipo de objeto a seleccionar
     * @param opciones Lista de opciones disponibles
     * @param mensaje  Mensaje a mostrar al usuario
     * @return El objeto seleccionado por el usuario
     */
    public static <T> T seleccionar(List<T> opciones, String mensaje) {
        if (opciones == null || opciones.isEmpty()) {
            System.out.println("No hay opciones disponibles.");
            return null;
        }

        System.out.println("\n" + mensaje);
        for (int i = 0; i < opciones.size(); i++) {
            System.out.println((i + 1) + ". " + opciones.get(i).toString());
        }

        int seleccion = leerEnteroEnRango("Seleccione una opción", 1, opciones.size());
        return opciones.get(seleccion - 1);
    }

    /**
     * Permite al usuario seleccionar múltiples objetos de una lista
     * 
     * @param <T>      Tipo de objeto a seleccionar
     * @param opciones Lista de opciones disponibles
     * @param mensaje  Mensaje a mostrar al usuario
     * @return Lista de objetos seleccionados por el usuario
     */
    public static <T> List<T> seleccionarMultiple(List<T> opciones, String mensaje) {
        if (opciones == null || opciones.isEmpty()) {
            System.out.println("No hay opciones disponibles.");
            return new ArrayList<>();
        }

        List<T> seleccionados = new ArrayList<>();

        System.out.println("\n" + mensaje);
        System.out.println("(Ingrese 0 para terminar la selección)");

        while (true) {
            for (int i = 0; i < opciones.size(); i++) {
                System.out.println((i + 1) + ". " + opciones.get(i).toString());
            }

            int seleccion = leerEnteroEnRango("Seleccione una opción (0 para terminar)", 0, opciones.size());

            if (seleccion == 0) {
                break;
            }

            T objetoSeleccionado = opciones.get(seleccion - 1);
            if (!seleccionados.contains(objetoSeleccionado)) {
                seleccionados.add(objetoSeleccionado);
                System.out.println("✓ Agregado: " + objetoSeleccionado.toString());
            } else {
                System.out.println("Este elemento ya fue seleccionado.");
            }

            System.out.println("Elementos seleccionados: " + seleccionados.size());
        }

        return seleccionados;
    }

    /**
     * Pregunta al usuario si desea continuar (S/N)
     */
    public static boolean confirmar(String mensaje) {
        while (true) {
            String respuesta = leerTexto(mensaje + " (S/N)").toLowerCase();
            if (respuesta.equals("s") || respuesta.equals("si")) {
                return true;
            } else if (respuesta.equals("n") || respuesta.equals("no")) {
                return false;
            } else {
                System.out.println("Por favor responda con S (Sí) o N (No).");
            }
        }
    }

    /**
     * Pausa la ejecución hasta que el usuario presione Enter
     */
    public static void pausar() {
        pausar("Presione Enter para continuar...");
    }

    /**
     * Pausa la ejecución con un mensaje personalizado
     */
    public static void pausar(String mensaje) {
        System.out.println(mensaje);
        scanner.nextLine();
    }
}