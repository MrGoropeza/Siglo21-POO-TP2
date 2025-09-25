package biblioteca.console.forms;

import biblioteca.application.libros.ingresar_stock.AddStockRequest;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.BookRepository;
import biblioteca.domain.entities.Book;
import biblioteca.domain.entities.CopyOrigin;

/**
 * Form for capturing add stock data from console
 */
public class AddStockForm {
    private final BookRepository bookRepository;

    public AddStockForm(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Captures add stock data from user input
     * 
     * @return AddStockRequest with the captured data, or null if cancelled
     */
    public AddStockRequest captureData() {
        try {
            // Search for book
            Book selectedBook = searchAndSelectBook();
            if (selectedBook == null) {
                return null;
            }

            // Display selected book
            DisplayHelper.printSuccess("Libro seleccionado:");
            System.out.println(selectedBook.toDetailedString());

            // Select origin
            CopyOrigin origin = selectOrigin();
            if (origin == null) {
                return null;
            }

            // Enter quantity
            int quantity = InputHelper.leerEnteroEnRango("Cantidad de ejemplares", 1, 100);

            // Confirm operation
            if (confirmData(selectedBook, origin, quantity)) {
                return new AddStockRequest(selectedBook.getId(), quantity, origin);
            } else {
                DisplayHelper.printInfo("Operación cancelada");
                return null;
            }

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al capturar datos: " + e.getMessage());
            return null;
        }
    }

    private Book searchAndSelectBook() {
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por Título");
        int searchOption = InputHelper.leerEnteroEnRango("Seleccione método de búsqueda", 1, 2);

        if (searchOption == 1) {
            int bookId = InputHelper.leerEntero("Ingrese ID del libro");
            Book book = bookRepository.findById(bookId);

            if (book == null) {
                DisplayHelper.printWarning("No se encontró libro con ID: " + bookId);
                return null;
            }
            return book;

        } else {
            String searchQuery = InputHelper.leerTextoObligatorio("Ingrese título del libro");
            var foundBooks = bookRepository.searchByText(searchQuery);

            if (foundBooks.isEmpty()) {
                DisplayHelper.printWarning("No se encontraron libros que contengan: \"" + searchQuery + "\"");
                return null;
            }

            if (foundBooks.size() == 1) {
                return foundBooks.get(0);
            } else {
                return InputHelper.seleccionar(foundBooks, "Seleccione el libro para ingresar stock");
            }
        }
    }

    private CopyOrigin selectOrigin() {
        System.out.println("\n1. Compra");
        System.out.println("2. Donación");
        int originOption = InputHelper.leerEnteroEnRango("Seleccione origen", 1, 2);
        return (originOption == 1) ? CopyOrigin.PURCHASE : CopyOrigin.DONATION;
    }

    private boolean confirmData(Book book, CopyOrigin origin, int quantity) {
        System.out.println("\n=== CONFIRMACIÓN DE DATOS ===");
        System.out.println("Libro: " + book.getTitle());
        System.out.println("Autor: " + book.getAuthor().getName());
        System.out.println("Origen: " + origin.getDisplayName());
        System.out.println("Cantidad: " + quantity + " ejemplares");
        System.out.println("=============================");

        return InputHelper.confirmar("¿Confirma ingresar " + quantity + " ejemplares de origen " +
                origin.getDisplayName() + "?");
    }
}