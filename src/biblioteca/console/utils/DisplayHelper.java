package biblioteca.console.utils;

import java.util.List;

/**
 * Utilidad para manejar la visualización de datos en consola
 */
public class DisplayHelper {

    /**
     * Limpia la pantalla de la consola
     */
    public static void limpiarPantalla() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Muestra un título centrado con decoración
     */
    public static void mostrarTitulo(String titulo) {
        int ancho = 60;
        String linea = "=".repeat(ancho);
        int espacios = (ancho - titulo.length()) / 2;
        String espaciosStr = " ".repeat(Math.max(0, espacios));

        System.out.println("\n" + linea);
        System.out.println(espaciosStr + titulo);
        System.out.println(linea + "\n");
    }

    /**
     * Muestra un subtítulo con decoración menor
     */
    public static void mostrarSubtitulo(String subtitulo) {
        String linea = "-".repeat(40);
        System.out.println("\n" + subtitulo);
        System.out.println(linea);
    }

    /**
     * Muestra un mensaje de éxito
     */
    public static void mostrarExito(String mensaje) {
        System.out.println("✓ " + mensaje);
    }

    /**
     * Muestra un mensaje de error
     */
    public static void mostrarError(String mensaje) {
        System.out.println("✗ Error: " + mensaje);
    }

    /**
     * Muestra un mensaje de advertencia
     */
    public static void mostrarAdvertencia(String mensaje) {
        System.out.println("⚠ Advertencia: " + mensaje);
    }

    /**
     * Muestra un mensaje informativo
     */
    public static void mostrarInfo(String mensaje) {
        System.out.println("ℹ " + mensaje);
    }

    /**
     * Muestra una lista numerada de objetos
     */
    public static <T> void mostrarLista(List<T> elementos, String titulo) {
        if (titulo != null && !titulo.isEmpty()) {
            mostrarSubtitulo(titulo);
        }

        if (elementos == null || elementos.isEmpty()) {
            System.out.println("No hay elementos para mostrar.");
            return;
        }

        for (int i = 0; i < elementos.size(); i++) {
            System.out.println((i + 1) + ". " + elementos.get(i).toString());
        }
    }

    /**
     * Muestra una lista simple sin numeración
     */
    public static <T> void mostrarListaSimple(List<T> elementos, String titulo) {
        if (titulo != null && !titulo.isEmpty()) {
            mostrarSubtitulo(titulo);
        }

        if (elementos == null || elementos.isEmpty()) {
            System.out.println("No hay elementos para mostrar.");
            return;
        }

        for (T elemento : elementos) {
            System.out.println("• " + elemento.toString());
        }
    }

    /**
     * Muestra una tabla con encabezados y filas
     */
    public static void mostrarTabla(String[] encabezados, List<String[]> filas) {
        if (encabezados == null || encabezados.length == 0) {
            return;
        }

        int[] anchos = new int[encabezados.length];
        for (int i = 0; i < encabezados.length; i++) {
            anchos[i] = encabezados[i].length();
        }

        if (filas != null) {
            for (String[] fila : filas) {
                for (int i = 0; i < Math.min(fila.length, anchos.length); i++) {
                    if (fila[i] != null && fila[i].length() > anchos[i]) {
                        anchos[i] = fila[i].length();
                    }
                }
            }
        }

        imprimirFilaTabla(encabezados, anchos);
        imprimirSeparadorTabla(anchos);

        if (filas != null) {
            for (String[] fila : filas) {
                imprimirFilaTabla(fila, anchos);
            }
        }
    }

    /**
     * Muestra un mensaje con formato de caja
     */
    public static void mostrarCaja(String mensaje) {
        int ancho = mensaje.length() + 4;
        String lineaHorizontal = "+" + "-".repeat(ancho - 2) + "+";

        System.out.println(lineaHorizontal);
        System.out.println("| " + mensaje + " |");
        System.out.println(lineaHorizontal);
    }

    /**
     * Muestra múltiples mensajes en una caja
     */
    public static void mostrarCaja(List<String> mensajes) {
        if (mensajes == null || mensajes.isEmpty()) {
            return;
        }

        int anchoMax = mensajes.stream().mapToInt(String::length).max().orElse(0);
        int ancho = anchoMax + 4;
        String lineaHorizontal = "+" + "-".repeat(ancho - 2) + "+";

        System.out.println(lineaHorizontal);
        for (String mensaje : mensajes) {
            System.out.printf("| %-" + (ancho - 4) + "s |\n", mensaje);
        }
        System.out.println(lineaHorizontal);
    }

    private static void imprimirFilaTabla(String[] columnas, int[] anchos) {
        System.out.print("| ");
        for (int i = 0; i < columnas.length; i++) {
            String valor = columnas[i] != null ? columnas[i] : "";
            System.out.printf("%-" + anchos[i] + "s | ", valor);
        }
        System.out.println();
    }

    private static void imprimirSeparadorTabla(int[] anchos) {
        System.out.print("+-");
        for (int i = 0; i < anchos.length; i++) {
            System.out.print("-".repeat(anchos[i]) + "-+-");
        }
        System.out.println();
    }
}