package biblioteca.console.forms;

import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.BookRepository;
import biblioteca.data.database.CopyRepository;
import biblioteca.domain.entities.Book;
import biblioteca.domain.entities.Copy;

/**
 * Form for capturing book search data from console
 */
public class FindBookForm {
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;

    public FindBookForm(BookRepository bookRepository, CopyRepository copyRepository) {
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    /**
     * Captures search query and displays results
     */
    public void executeSearch() {
        try {
            String searchQuery = InputHelper
                    .leerTextoObligatorio("Ingrese el texto a buscar (título, autor, categoría, editorial o año)");

            var foundBooks = bookRepository.searchByText(searchQuery);

            if (foundBooks.isEmpty()) {
                DisplayHelper.printWarning("No se encontraron libros que contengan: \"" + searchQuery + "\"");
                return;
            }

            if (foundBooks.size() == 1) {
                DisplayHelper.printSuccess("Se encontró 1 libro:");
                displayBookWithCopyInfo(foundBooks.get(0));
            } else {
                DisplayHelper.printSuccess("Se encontraron " + foundBooks.size() + " libros:");

                // Mostrar lista con información de ejemplares
                displayBookList(foundBooks);

                Book selectedBook = InputHelper.seleccionar(foundBooks,
                        "Seleccione un libro para ver el detalle completo");

                if (selectedBook != null) {
                    DisplayHelper.renderSubtitle("Detalle del Libro Seleccionado");
                    displayBookWithCopyInfo(selectedBook);
                }
            }

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al buscar libros: " + e.getMessage());
        }
    }

    private void displayBookList(java.util.List<Book> books) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            java.util.List<Copy> copies = copyRepository.findByBook(book);
            long availableCopies = copies.stream()
                    .filter(copy -> copy.getState() == biblioteca.domain.entities.CopyState.AVAILABLE)
                    .count();
            long loanedCopies = copies.stream()
                    .filter(copy -> copy.getState() == biblioteca.domain.entities.CopyState.LOANED)
                    .count();

            System.out.printf("%d. %s | DISPONIBLES: %d | PRESTADOS: %d | TOTAL: %d\n",
                    i + 1, book.toString(), availableCopies, loanedCopies, copies.size());
        }
    }

    private void displayBookWithCopyInfo(Book book) {
        System.out.println("\n=== INFORMACIÓN DEL LIBRO ===");
        System.out.println("ID: " + book.getId());
        System.out.println("Título: " + book.getTitle());
        System.out.println("Autor: " + book.getAuthor().getName());
        System.out.println("Categoría: " + book.getCategory().getName());
        System.out.println("Editorial: " + book.getPublisher().getName());
        System.out.println("Año: " + book.getYear());

        // Obtener información de ejemplares
        java.util.List<Copy> copies = copyRepository.findByBook(book);

        if (copies.isEmpty()) {
            System.out.println("\n=== EJEMPLARES ===");
            System.out.println("No hay ejemplares registrados para este libro.");
        } else {
            long availableCopies = copies.stream()
                    .filter(copy -> copy.getState() == biblioteca.domain.entities.CopyState.AVAILABLE)
                    .count();
            long loanedCopies = copies.stream()
                    .filter(copy -> copy.getState() == biblioteca.domain.entities.CopyState.LOANED)
                    .count();
            long reservedCopies = copies.stream()
                    .filter(copy -> copy.getState() == biblioteca.domain.entities.CopyState.RESERVED)
                    .count();

            System.out.println("\n=== EJEMPLARES ===");
            System.out.println("Total: " + copies.size());
            System.out.println("Disponibles: " + availableCopies);
            System.out.println("Prestados: " + loanedCopies);
            System.out.println("Reservados: " + reservedCopies);

            // Mostrar algunos códigos de ejemplares como muestra
            System.out.println("\nCódigos de ejemplares (primeros 5):");
            copies.stream()
                    .limit(5)
                    .forEach(copy -> System.out.println("- " + copy.getCode() + " (" + copy.getState() + ")"));

            if (copies.size() > 5) {
                System.out.println("... y " + (copies.size() - 5) + " más");
            }
        }
    }
}