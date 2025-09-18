package biblioteca.console.controllers;

import biblioteca.application.libros.registrar.RegisterBookRequest;
import biblioteca.application.libros.registrar.RegisterBookResult;
import biblioteca.application.libros.registrar.RegisterBookUseCase;
import biblioteca.console.forms.RegisterBookForm;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.BookRepository;
import biblioteca.domain.entities.Book;

/**
 * Controller for book-related console operations
 */
public class BookController {
    private final BookRepository bookRepository;
    private final RegisterBookUseCase registerBookUseCase;
    private final RegisterBookForm registerBookForm;

    public BookController(BookRepository bookRepository,
            RegisterBookUseCase registerBookUseCase,
            RegisterBookForm registerBookForm) {
        this.bookRepository = bookRepository;
        this.registerBookUseCase = registerBookUseCase;
        this.registerBookForm = registerBookForm;
    }

    /**
     * Displays the book management menu and handles user selection
     */
    public void showMenu() {
        boolean continuar = true;

        while (continuar) {
            try {
                DisplayHelper.mostrarTitulo("GESTIÓN DE LIBROS");

                System.out.println("1. Registrar nuevo libro");
                System.out.println("2. Listar todos los libros");
                System.out.println("3. Buscar libro por ID");
                System.out.println("4. Volver al menú principal");

                int opcion = InputHelper.leerEnteroEnRango("Seleccione una opción", 1, 4);

                switch (opcion) {
                    case 1 -> registerBook();
                    case 2 -> listAllBooks();
                    case 3 -> findBookById();
                    case 4 -> {
                        continuar = false;
                        DisplayHelper.mostrarInfo("Volviendo al menú principal...");
                    }
                }

                if (continuar) {
                    InputHelper.pausar();
                }

            } catch (Exception e) {
                DisplayHelper.mostrarError("Error en el menú: " + e.getMessage());
                InputHelper.pausar();
            }
        }
    }

    private void registerBook() {
        DisplayHelper.mostrarSubtitulo("Registrar Nuevo Libro");

        RegisterBookRequest request = registerBookForm.captureData();

        if (request == null) {
            return;
        }

        RegisterBookResult result = registerBookUseCase.execute(request);

        if (result.isSuccess()) {
            DisplayHelper.mostrarExito("Libro registrado exitosamente!");
            System.out.println("\n=== LIBRO REGISTRADO ===");
            System.out.println(result.getBook().toDetailedString());
            System.out.println("========================");
        } else {
            DisplayHelper.mostrarError(result.getMessage());
        }
    }

    private void listAllBooks() {
        DisplayHelper.mostrarSubtitulo("Lista de Todos los Libros");

        var books = bookRepository.findAll();

        if (books.isEmpty()) {
            DisplayHelper.mostrarInfo("No hay libros registrados en el sistema.");
            return;
        }

        System.out.println("\nTotal de libros: " + books.size());
        System.out.println();

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println((i + 1) + ". " + book.toDetailedString());
        }
    }

    private void findBookById() {
        DisplayHelper.mostrarSubtitulo("Buscar Libro por ID");

        int bookId = InputHelper.leerEntero("Ingrese el ID del libro");

        Book book = bookRepository.findById(bookId);

        if (book != null) {
            DisplayHelper.mostrarExito("Libro encontrado:");
            System.out.println("\n" + book.toDetailedString());
        } else {
            DisplayHelper.mostrarAdvertencia("No se encontró ningún libro con el ID: " + bookId);
        }
    }
}