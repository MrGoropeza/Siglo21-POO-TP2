package biblioteca.console.forms;

import biblioteca.application.libros.modificar.ModifyBookRequest;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.BookRepository;
import biblioteca.domain.entities.Book;

/**
 * Form for capturing book modification data from console
 */
public class ModifyBookForm {
    private final BookRepository bookRepository;

    public ModifyBookForm(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Captures book modification data from user input
     * 
     * @return ModifyBookRequest with the captured data, or null if cancelled
     */
    public ModifyBookRequest captureData() {
        try {
            // Search and select book
            Book selectedBook = searchAndSelectBook();
            if (selectedBook == null) {
                return null;
            }

            // Show modification menu and capture changes
            return showModificationMenu(selectedBook);

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al capturar datos: " + e.getMessage());
            return null;
        }
    }

    private Book searchAndSelectBook() {
        String searchQuery = InputHelper.leerTextoObligatorio("Ingrese el término de búsqueda del libro a modificar");

        var foundBooks = bookRepository.searchByText(searchQuery);

        if (foundBooks.isEmpty()) {
            DisplayHelper.printWarning("No se encontraron libros con ese término de búsqueda.");
            return null;
        }

        return InputHelper.seleccionar(foundBooks, "Seleccione el libro a modificar");
    }

    private ModifyBookRequest showModificationMenu(Book book) {
        while (true) {
            System.out.println("\n=== MODIFICAR: " + book.getTitle() + " ===");
            System.out.println("1. Modificar título");
            System.out.println("2. Modificar año de publicación");
            System.out.println("0. Volver al menú anterior");

            int option = InputHelper.leerEnteroEnRango("Seleccione una opción", 0, 2);

            switch (option) {
                case 1:
                    return captureNewTitle(book);
                case 2:
                    return captureNewYear(book);
                case 0:
                    return null;
            }
        }
    }

    private ModifyBookRequest captureNewTitle(Book book) {
        System.out.println("Título actual: " + book.getTitle());
        String newTitle = InputHelper.leerTexto("Nuevo título (Enter para mantener actual)");

        if (newTitle != null && !newTitle.trim().isEmpty()) {
            if (confirmTitleChange(book, newTitle.trim())) {
                return new ModifyBookRequest(book.getId(), newTitle.trim(), null);
            }
        } else {
            DisplayHelper.printInfo("Título sin cambios.");
        }
        return null;
    }

    private ModifyBookRequest captureNewYear(Book book) {
        System.out.println("Año actual: " + book.getYear());

        try {
            int newYear = InputHelper.leerEnteroEnRango("Nuevo año de publicación", 1000, 2030);

            if (confirmYearChange(book, newYear)) {
                return new ModifyBookRequest(book.getId(), null, newYear);
            }
        } catch (Exception e) {
            DisplayHelper.printInfo("Operación cancelada.");
        }
        return null;
    }

    private boolean confirmTitleChange(Book book, String newTitle) {
        System.out.println("\n=== CONFIRMACIÓN DE CAMBIO ===");
        System.out.println("Libro: " + book.getTitle());
        System.out.println("Título actual: " + book.getTitle());
        System.out.println("Título nuevo: " + newTitle);
        System.out.println("==============================");

        return InputHelper.confirmar("¿Confirma el cambio de título?");
    }

    private boolean confirmYearChange(Book book, int newYear) {
        System.out.println("\n=== CONFIRMACIÓN DE CAMBIO ===");
        System.out.println("Libro: " + book.getTitle());
        System.out.println("Año actual: " + book.getYear());
        System.out.println("Año nuevo: " + newYear);
        System.out.println("==============================");

        return InputHelper.confirmar("¿Confirma el cambio de año?");
    }
}