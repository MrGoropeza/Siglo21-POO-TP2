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
    private final LoanController loanController;
    private final ReturnController returnController;
    private final ConfigController configController;
    private final ReportController reportController;
    private final NotificationController notificationController;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final MemberRepository memberRepository;

    public MainController(BookController bookController,
            MemberController memberController,
            LoanController loanController,
            ReturnController returnController,
            ConfigController configController,
            ReportController reportController,
            NotificationController notificationController,
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            CategoryRepository categoryRepository,
            PublisherRepository publisherRepository,
            MemberRepository memberRepository) {
        this.bookController = bookController;
        this.memberController = memberController;
        this.loanController = loanController;
        this.returnController = returnController;
        this.configController = configController;
        this.reportController = reportController;
        this.notificationController = notificationController;
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
                System.out.println("3. Gestión de Préstamos");
                System.out.println("4. Gestión de Devoluciones");
                System.out.println("5. Configuración del Sistema");
                System.out.println("6. Generar reportes del sistema");
                System.out.println("7. Gestión de notificaciones");
                System.out.println("8. Salir");

                int opcion = InputHelper.leerEnteroEnRango("Seleccione una opción", 1, 8);

                switch (opcion) {
                    case 1 -> bookController.showMenu();
                    case 2 -> memberController.showMenu();
                    case 3 -> loanController.showMainMenu();
                    case 4 -> returnController.showMenu();
                    case 5 -> configController.run();
                    case 6 -> reportController.showMenu();
                    case 7 -> notificationController.showMenu();
                    case 8 -> {
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
}
