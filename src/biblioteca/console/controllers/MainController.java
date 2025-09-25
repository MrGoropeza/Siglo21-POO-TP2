package biblioteca.console.controllers;

import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.AuthorRepository;
import biblioteca.data.database.BookRepository;
import biblioteca.data.database.CategoryRepository;
import biblioteca.data.database.MemberRepository;
import biblioteca.data.database.PublisherRepository;

/**
 * Main controller for handling the primary application menu
 */
public class MainController {
    private final BookController bookController;
    private final MemberController memberController;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final MemberRepository memberRepository;

    public MainController(BookController bookController,
            MemberController memberController,
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            CategoryRepository categoryRepository,
            PublisherRepository publisherRepository,
            MemberRepository memberRepository) {
        this.bookController = bookController;
        this.memberController = memberController;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * Starts the main application menu loop
     */
    public void start() {
        boolean continuar = true;

        while (continuar) {
            try {
                DisplayHelper.clearScreen();
                DisplayHelper.renderTitle("SISTEMA DE GESTIÓN DE BIBLIOTECA");

                System.out.println("1. Gestión de Libros");
                System.out.println("2. Gestión de Socios");
                System.out.println("3. Ver estadísticas del sistema");
                System.out.println("4. Salir");

                int opcion = InputHelper.leerEnteroEnRango("Seleccione una opción", 1, 4);

                switch (opcion) {
                    case 1 -> bookController.showMenu();
                    case 2 -> memberController.showMenu();
                    case 3 -> showSystemStats();
                    case 4 -> {
                        if (InputHelper.confirmar("¿Está seguro que desea salir?")) {
                            continuar = false;
                            DisplayHelper.printSuccess("¡Gracias por usar el sistema de biblioteca!");
                        }
                    }
                }

            } catch (Exception e) {
                DisplayHelper.printErrorMessage("Error en el menú principal: " + e.getMessage());
                InputHelper.pausar();
            }
        }
    }

    private void showSystemStats() {
        DisplayHelper.renderSubtitle("Estadísticas del Sistema");

        int totalBooks = bookRepository.findAll().size();
        int totalAuthors = authorRepository.findAll().size();
        int totalCategories = categoryRepository.findAll().size();
        int totalPublishers = publisherRepository.findAll().size();
        int totalMembers = memberRepository.findAll().size();

        System.out.println("📚 Total de libros: " + totalBooks);
        System.out.println("✍️  Total de autores: " + totalAuthors);
        System.out.println("📂 Total de categorías: " + totalCategories);
        System.out.println("🏢 Total de editoriales: " + totalPublishers);
        System.out.println("👥 Total de socios: " + totalMembers);

        if (totalBooks > 0) {
            System.out.println("\n=== ÚLTIMOS LIBROS REGISTRADOS ===");
            var books = bookRepository.findAll();
            int start = Math.max(0, books.size() - 3);

            for (int i = start; i < books.size(); i++) {
                System.out.println("• " + books.get(i));
            }
        }

        if (totalMembers > 0) {
            System.out.println("\n=== ÚLTIMOS SOCIOS REGISTRADOS ===");
            var members = memberRepository.findAll();
            int start = Math.max(0, members.size() - 3);

            for (int i = start; i < members.size(); i++) {
                System.out.println("• " + members.get(i));
            }
        }

        InputHelper.pausar();
    }
}