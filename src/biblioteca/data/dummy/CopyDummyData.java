package biblioteca.data.dummy;

import java.util.ArrayList;
import java.util.List;

import biblioteca.domain.entities.Book;
import biblioteca.domain.entities.Copy;
import biblioteca.domain.enums.CopyOrigin;
import biblioteca.domain.enums.CopyState;

/**
 * Datos dummy para ejemplares de la biblioteca
 * Proporciona una lista de ejemplares de prueba para inicialización del sistema
 */
public class CopyDummyData {

    /**
     * Genera una lista de ejemplares de prueba con diferentes estados y orígenes
     * Requiere que los libros ya estén cargados en BookRepository
     * 
     * @param books Lista de libros disponibles para crear ejemplares
     * @return Lista de ejemplares dummy
     */
    public static List<Copy> getCopies(List<Book> books) {
        List<Copy> copies = new ArrayList<>();

        if (books.isEmpty()) {
            return copies; // No se pueden crear ejemplares sin libros
        }

        // Crear múltiples ejemplares para los primeros libros (para préstamos)
        for (int i = 0; i < Math.min(5, books.size()); i++) {
            Book book = books.get(i);

            // Crear 3 ejemplares por libro (2 disponibles, 1 prestado)

            // Ejemplar 1 - Disponible (Compra)
            Copy copy1 = new Copy(
                    String.format("COPY-%03d-1", i + 1),
                    CopyState.AVAILABLE,
                    CopyOrigin.PURCHASE,
                    book);
            copies.add(copy1);

            // Ejemplar 2 - Disponible (Donación)
            Copy copy2 = new Copy(
                    String.format("COPY-%03d-2", i + 1),
                    CopyState.AVAILABLE,
                    CopyOrigin.DONATION,
                    book);
            copies.add(copy2);

            // Ejemplar 3 - Prestado (ya referenciado en LoanDummyData)
            Copy copy3 = new Copy(
                    String.format("COPY-%03d-3", i + 1),
                    CopyState.LOANED,
                    CopyOrigin.PURCHASE,
                    book);
            copies.add(copy3);
        }

        // Crear ejemplares adicionales para más libros (solo disponibles)
        for (int i = 5; i < Math.min(10, books.size()); i++) {
            Book book = books.get(i);

            // 2 ejemplares disponibles por libro
            Copy copy1 = new Copy(
                    String.format("COPY-%03d-1", i + 1),
                    CopyState.AVAILABLE,
                    CopyOrigin.PURCHASE,
                    book);
            copies.add(copy1);

            Copy copy2 = new Copy(
                    String.format("COPY-%03d-2", i + 1),
                    CopyState.AVAILABLE,
                    CopyOrigin.DONATION,
                    book);
            copies.add(copy2);

            // Agregar un tercer ejemplar solo para el libro 10 (para caso de prueba)
            if (i == 9) { // Libro 10 (índice 9)
                Copy copy3 = new Copy(
                        String.format("COPY-%03d-3", i + 1),
                        CopyState.AVAILABLE,
                        CopyOrigin.PURCHASE,
                        book);
                copies.add(copy3);
            }
        }

        return copies;
    }

    /**
     * Genera el siguiente código único disponible para un nuevo ejemplar
     * 
     * @param book           El libro para el cual generar el código
     * @param existingCopies Lista de ejemplares existentes para evitar duplicados
     * @return Código único para el nuevo ejemplar
     */
    public static String getNextAvailableCode(Book book, List<Copy> existingCopies) {
        // Buscar el siguiente número disponible para este libro
        int maxNumber = 0;
        String bookPrefix = String.format("COPY-%03d-", book.getId());

        for (Copy copy : existingCopies) {
            if (copy.getCode().startsWith(bookPrefix)) {
                try {
                    String numberPart = copy.getCode().substring(bookPrefix.length());
                    int number = Integer.parseInt(numberPart);
                    maxNumber = Math.max(maxNumber, number);
                } catch (NumberFormatException e) {
                    // Ignorar códigos con formato diferente
                }
            }
        }

        return bookPrefix + (maxNumber + 1);
    }
}