package biblioteca.console.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utility to handle displaying data in the console
 */
public class DisplayHelper {

    public static final String SEPARATOR = "=".repeat(60);

    /**
     * Clears the console screen
     */
    public static void clearScreen() {
        // try {
        // new ProcessBuilder("clear").inheritIO().start().waitFor();
        // } catch (Exception e) {
        // for (int i = 0; i < 50; i++) {
        // System.out.println();
        // }
        // }
    }

    /**
     * Displays a centered title with decoration
     */
    public static void renderTitle(String titulo) {
        int ancho = 60;
        String linea = "=".repeat(ancho);
        int espacios = (ancho - titulo.length()) / 2;
        String espaciosStr = " ".repeat(Math.max(0, espacios));

        System.out.println("\n" + linea);
        System.out.println(espaciosStr + titulo);
        System.out.println(linea + "\n");
    }

    /**
     * Displays a subtitle with minor decoration
     */
    public static void renderSubtitle(String subtitulo) {
        String linea = "-".repeat(40);
        System.out.println("\n" + subtitulo);
        System.out.println(linea);
    }

    /**
     * Displays a success message
     */
    public static void printSuccess(String mensaje) {
        System.out.println("✓ " + mensaje);
    }

    /**
     * Displays an error message
     */
    public static void printErrorMessage(String mensaje) {
        System.out.println("✗ Error: " + mensaje);
    }

    /**
     * Displays a warning message
     */
    public static void printWarning(String mensaje) {
        System.out.println("⚠ Advertencia: " + mensaje);
    }

    /**
     * Displays an informational message
     */
    public static void printInfo(String mensaje) {
        System.out.println("(i) " + mensaje);
    }

    /**
     * Displays a numbered list of objects
     */
    public static <T> void renderNumberedList(List<T> elementos, String titulo) {
        if (titulo != null && !titulo.isEmpty()) {
            renderSubtitle(titulo);
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
     * Displays a simple bullet list
     */
    public static <T> void renderBulletList(List<T> elementos, String titulo) {
        if (titulo != null && !titulo.isEmpty()) {
            renderSubtitle(titulo);
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
     * Displays a table with headers and rows
     */
    public static void renderTable(String[] encabezados, List<String[]> filas) {
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

        renderTableRow(encabezados, anchos);
        renderTableDivider(anchos);

        if (filas != null) {
            for (String[] fila : filas) {
                renderTableRow(fila, anchos);
            }
        }
    }

    /**
     * Displays a message with box formatting
     */
    public static void renderMessageContainer(String mensaje) {
        int ancho = mensaje.length() + 4;
        String lineaHorizontal = "+" + "-".repeat(ancho - 2) + "+";

        System.out.println(lineaHorizontal);
        System.out.println("| " + mensaje + " |");
        System.out.println(lineaHorizontal);
    }

    /**
     * Displays multiple messages with box formatting
     */
    public static void renderMessageContainer(List<String> mensajes) {
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

    /**
     * Formats a LocalDate to a readable string (dd/MM/yyyy)
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "N/A";
        }
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private static void renderTableRow(String[] columnas, int[] anchos) {
        System.out.print("| ");
        for (int i = 0; i < columnas.length; i++) {
            String valor = columnas[i] != null ? columnas[i] : "";
            System.out.printf("%-" + anchos[i] + "s | ", valor);
        }
        System.out.println();
    }

    private static void renderTableDivider(int[] anchos) {
        System.out.print("+-");
        for (int i = 0; i < anchos.length; i++) {
            System.out.print("-".repeat(anchos[i]) + "-+-");
        }
        System.out.println();
    }
}