package biblioteca.console.forms;

import biblioteca.application.libros.eliminar.DeleteBookRequest;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.BookRepository;
import biblioteca.data.database.CopyRepository;
import biblioteca.domain.entities.Book;
import biblioteca.domain.entities.Copy;

/**
 * Form for capturing book deletion data from console
 */
public class DeleteBookForm {
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;

    public DeleteBookForm(BookRepository bookRepository, CopyRepository copyRepository) {
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    /**
     * Captures book deletion confirmation from user input
     * 
     * @return DeleteBookRequest with the captured data, or null if cancelled
     */
    public DeleteBookRequest captureData() {
        try {
            // Search and select book
            Book selectedBook = searchAndSelectBook();
            if (selectedBook == null) {
                return null;
            }

            // Show book information and confirm deletion
            if (showBookInfoAndConfirm(selectedBook)) {
                return new DeleteBookRequest(selectedBook.getId());
            } else {
                DisplayHelper.printInfo("Operación cancelada.");
                return null;
            }

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al capturar datos: " + e.getMessage());
            return null;
        }
    }

    private Book searchAndSelectBook() {
        String searchQuery = InputHelper.leerTextoObligatorio("Ingrese el término de búsqueda del libro a eliminar");

        var foundBooks = bookRepository.searchByText(searchQuery);

        if (foundBooks.isEmpty()) {
            DisplayHelper.printWarning("No se encontraron libros con ese término de búsqueda.");
            return null;
        }

        return InputHelper.seleccionar(foundBooks, "Seleccione el libro a eliminar");
    }

    private boolean showBookInfoAndConfirm(Book book) {
        // Obtener información de ejemplares
        java.util.List<Copy> copies = copyRepository.findByBook(book);
        long loanedCopies = copies.stream()
                .filter(copy -> copy.getState() == biblioteca.domain.entities.CopyState.LOANED)
                .count();

        // Mostrar información del libro a eliminar
        System.out.println("\n=== INFORMACIÓN DEL LIBRO A ELIMINAR ===");
        System.out.println("Título: " + book.getTitle());
        System.out.println("Autor: " + book.getAuthor().getName());
        System.out.println("Categoría: " + book.getCategory().getName());
        System.out.println("Editorial: " + book.getPublisher().getName());
        System.out.println("Año: " + book.getYear());
        System.out.println("Ejemplares totales: " + copies.size());
        System.out.println("Ejemplares disponibles: " + (copies.size() - loanedCopies));
        System.out.println("Ejemplares prestados: " + loanedCopies);
        System.out.println("=========================================");

        // Advertencia si hay ejemplares prestados
        if (loanedCopies > 0) {
            DisplayHelper.printErrorMessage("ADVERTENCIA: Este libro tiene " + loanedCopies + " ejemplares prestados.");
            DisplayHelper.printErrorMessage("No se podrá eliminar hasta que todos los ejemplares sean devueltos.");
            return false;
        }

        // Confirmar eliminación
        return InputHelper.confirmar("¿Está seguro que desea eliminar este libro y todos sus ejemplares?");
    }
}