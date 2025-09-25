package biblioteca.console.controllers;

import biblioteca.application.libros.eliminar.DeleteBookRequest;
import biblioteca.application.libros.eliminar.DeleteBookResult;
import biblioteca.application.libros.eliminar.DeleteBookUseCase;
import biblioteca.application.libros.ingresar_stock.AddStockRequest;
import biblioteca.application.libros.ingresar_stock.AddStockResult;
import biblioteca.application.libros.ingresar_stock.AddStockUseCase;
import biblioteca.application.libros.modificar.ModifyBookRequest;
import biblioteca.application.libros.modificar.ModifyBookResult;
import biblioteca.application.libros.modificar.ModifyBookUseCase;
import biblioteca.application.libros.registrar.RegisterBookRequest;
import biblioteca.application.libros.registrar.RegisterBookResult;
import biblioteca.application.libros.registrar.RegisterBookUseCase;
import biblioteca.console.forms.AddStockForm;
import biblioteca.console.forms.DeleteBookForm;
import biblioteca.console.forms.FindBookForm;
import biblioteca.console.forms.ModifyBookForm;
import biblioteca.console.forms.RegisterBookForm;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.domain.entities.Copy;

/**
 * Controller for book-related console operations
 */
public class BookController {
    private final RegisterBookUseCase registerBookUseCase;
    private final AddStockUseCase addStockUseCase;
    private final ModifyBookUseCase modifyBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;
    private final RegisterBookForm registerBookForm;
    private final AddStockForm addStockForm;
    private final ModifyBookForm modifyBookForm;
    private final DeleteBookForm deleteBookForm;
    private final FindBookForm findBookForm;

    public BookController(RegisterBookUseCase registerBookUseCase,
            AddStockUseCase addStockUseCase,
            ModifyBookUseCase modifyBookUseCase,
            DeleteBookUseCase deleteBookUseCase,
            RegisterBookForm registerBookForm,
            AddStockForm addStockForm,
            ModifyBookForm modifyBookForm,
            DeleteBookForm deleteBookForm,
            FindBookForm findBookForm) {
        this.registerBookUseCase = registerBookUseCase;
        this.addStockUseCase = addStockUseCase;
        this.modifyBookUseCase = modifyBookUseCase;
        this.deleteBookUseCase = deleteBookUseCase;
        this.registerBookForm = registerBookForm;
        this.addStockForm = addStockForm;
        this.modifyBookForm = modifyBookForm;
        this.deleteBookForm = deleteBookForm;
        this.findBookForm = findBookForm;
    }

    /**
     * Displays the book management menu and handles user selection
     */
    public void showMenu() {
        boolean continuar = true;

        while (continuar) {
            try {
                DisplayHelper.renderTitle("GESTIÓN DE LIBROS");

                System.out.println("1. Registrar nuevo libro");
                System.out.println("2. Ingresar stock (compra/donación)");
                System.out.println("3. Modificar libro");
                System.out.println("4. Eliminar libro");
                System.out.println("5. Buscar libro");
                System.out.println("6. Volver al menú principal");

                int opcion = InputHelper.leerEnteroEnRango("Seleccione una opción", 1, 6);

                switch (opcion) {
                    case 1 -> registerBook();
                    case 2 -> addStock();
                    case 3 -> modifyBook();
                    case 4 -> deleteBook();
                    case 5 -> findBook();
                    case 6 -> {
                        continuar = false;
                        DisplayHelper.printInfo("Volviendo al menú principal...");
                    }
                }

                if (continuar) {
                    InputHelper.pausar();
                }

            } catch (Exception e) {
                DisplayHelper.printErrorMessage("Error en el menú: " + e.getMessage());
                InputHelper.pausar();
            }
        }
    }

    private void registerBook() {
        DisplayHelper.renderSubtitle("Registrar Nuevo Libro");

        RegisterBookRequest request = registerBookForm.captureData();

        if (request == null) {
            return;
        }

        RegisterBookResult result = registerBookUseCase.execute(request);

        if (result.isSuccess()) {
            DisplayHelper.printSuccess("Libro registrado exitosamente!");
            System.out.println("\n=== LIBRO REGISTRADO ===");
            System.out.println(result.getBook().toDetailedString());
            System.out.println("========================");
        } else {
            DisplayHelper.printErrorMessage(result.getMessage());
        }
    }

    private void addStock() {
        DisplayHelper.renderSubtitle("Ingresar Stock (Compra/Donación)");

        AddStockRequest request = addStockForm.captureData();

        if (request == null) {
            return;
        }

        AddStockResult result = addStockUseCase.execute(request);

        if (result.isSuccess()) {
            DisplayHelper.printSuccess(result.getMessage());

            System.out.println("\n=== CÓDIGOS GENERADOS ===");
            for (Copy copy : result.getCreatedCopies()) {
                System.out.println("• " + copy.getCode());
            }
            System.out.println("========================");
        } else {
            DisplayHelper.printErrorMessage(result.getMessage());
        }
    }

    private void modifyBook() {
        DisplayHelper.renderSubtitle("Modificar Libro");

        ModifyBookRequest request = modifyBookForm.captureData();

        if (request == null) {
            return;
        }

        ModifyBookResult result = modifyBookUseCase.execute(request);

        if (result.isSuccess()) {
            DisplayHelper.printSuccess(result.getMessage());
        } else {
            DisplayHelper.printErrorMessage(result.getMessage());
        }
    }

    private void deleteBook() {
        DisplayHelper.renderSubtitle("Eliminar Libro");

        DeleteBookRequest request = deleteBookForm.captureData();

        if (request == null) {
            return;
        }

        DeleteBookResult result = deleteBookUseCase.execute(request);

        if (result.isSuccess()) {
            DisplayHelper.printSuccess(result.getMessage());
        } else {
            DisplayHelper.printErrorMessage(result.getMessage());
        }
    }

    private void findBook() {
        DisplayHelper.renderSubtitle("Buscar Libro");
        findBookForm.executeSearch();
    }

}