package biblioteca.console.forms;

import biblioteca.application.libros.registrar.RegisterBookRequest;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.AuthorRepository;
import biblioteca.data.database.CategoryRepository;
import biblioteca.data.database.PublisherRepository;
import biblioteca.domain.entities.Author;
import biblioteca.domain.entities.Category;
import biblioteca.domain.entities.Publisher;

/**
 * Form for capturing book registration data from console
 */
public class RegisterBookForm {
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public RegisterBookForm(AuthorRepository authorRepository,
            CategoryRepository categoryRepository,
            PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }

    /**
     * Captures book registration data from user input
     * 
     * @return RegisterBookRequest with the captured data, or null if cancelled
     */
    public RegisterBookRequest captureData() {
        DisplayHelper.mostrarSubtitulo("Registrar Nuevo Libro");

        try {
            // Capture title
            String title = InputHelper.leerTextoObligatorio("Ingrese el título del libro");

            // Select author
            Author selectedAuthor = selectAuthor();
            if (selectedAuthor == null) {
                DisplayHelper.mostrarAdvertencia("Registro cancelado - No se seleccionó autor");
                return null;
            }

            // Select category
            Category selectedCategory = selectCategory();
            if (selectedCategory == null) {
                DisplayHelper.mostrarAdvertencia("Registro cancelado - No se seleccionó categoría");
                return null;
            }

            // Select publisher
            Publisher selectedPublisher = selectPublisher();
            if (selectedPublisher == null) {
                DisplayHelper.mostrarAdvertencia("Registro cancelado - No se seleccionó editorial");
                return null;
            }

            // Capture year
            int year = InputHelper.leerEnteroEnRango("Ingrese el año de publicación", 1000, 2030);

            // Confirm data before creating request
            if (confirmData(title, selectedAuthor, selectedCategory, selectedPublisher, year)) {
                return new RegisterBookRequest(title, selectedAuthor.getId(),
                        selectedCategory.getId(), selectedPublisher.getId(), year);
            } else {
                DisplayHelper.mostrarAdvertencia("Registro cancelado por el usuario");
                return null;
            }

        } catch (Exception e) {
            DisplayHelper.mostrarError("Error al capturar datos: " + e.getMessage());
            return null;
        }
    }

    private Author selectAuthor() {
        var authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            DisplayHelper.mostrarError("No hay autores disponibles. Debe crear autores primero.");
            return null;
        }

        return InputHelper.seleccionar(authors, "Seleccione el autor del libro:");
    }

    private Category selectCategory() {
        var categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            DisplayHelper.mostrarError("No hay categorías disponibles. Debe crear categorías primero.");
            return null;
        }

        return InputHelper.seleccionar(categories, "Seleccione la categoría del libro:");
    }

    private Publisher selectPublisher() {
        var publishers = publisherRepository.findAll();
        if (publishers.isEmpty()) {
            DisplayHelper.mostrarError("No hay editoriales disponibles. Debe crear editoriales primero.");
            return null;
        }

        return InputHelper.seleccionar(publishers, "Seleccione la editorial del libro:");
    }

    private boolean confirmData(String title, Author author, Category category, Publisher publisher, int year) {
        System.out.println("\n=== CONFIRMACIÓN DE DATOS ===");
        System.out.println("Título: " + title);
        System.out.println("Autor: " + author.getName());
        System.out.println("Categoría: " + category.getName());
        System.out.println("Editorial: " + publisher.getName());
        System.out.println("Año: " + year);
        System.out.println("=============================");

        return InputHelper.confirmar("¿Está seguro que desea registrar este libro?");
    }
}