package biblioteca.console.forms;

import java.util.List;

import biblioteca.application.prestamos.carrito.LoanCart;
import biblioteca.application.prestamos.create.CreateLoanResult;
import biblioteca.application.prestamos.create.CreateLoanUseCase;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.domain.entities.Copy;
import biblioteca.domain.entities.Loan;

/**
 * Formulario para confirmar préstamos desde el carrito
 */
public class ConfirmLoanForm {
    private final CreateLoanUseCase createLoanUseCase;

    public ConfirmLoanForm(CreateLoanUseCase createLoanUseCase) {
        this.createLoanUseCase = createLoanUseCase;
    }

    /**
     * Muestra el formulario de confirmación de préstamo
     */
    public boolean show(LoanCart cart) {
        DisplayHelper.renderTitle("Confirmar Préstamo");

        if (cart == null || cart.isEmpty()) {
            DisplayHelper.printErrorMessage("Error: No hay ejemplares en el carrito para procesar");
            return false;
        }

        // Mostrar resumen del préstamo
        showLoanSummary(cart);

        // Confirmación final
        if (!InputHelper.confirmar("¿Confirma el préstamo de estos ejemplares?")) {
            DisplayHelper.printWarning("Préstamo cancelado por el usuario");
            return false;
        }

        // Procesar el préstamo
        CreateLoanResult result = createLoanUseCase.confirmLoan(cart);

        if (result.isSuccess()) {
            DisplayHelper.printSuccess("¡Préstamo procesado exitosamente!");
            DisplayHelper.printInfo(result.getMessage());

            // Mostrar detalles de los préstamos creados
            @SuppressWarnings("unchecked")
            List<Loan> loans = (List<Loan>) result.getData();
            if (loans != null && !loans.isEmpty()) {
                showLoanDetails(loans);
            }

            InputHelper.pausar("Presione Enter para continuar...");
            return true;

        } else {
            DisplayHelper.printErrorMessage("Error al procesar el préstamo:");
            DisplayHelper.printErrorMessage(result.getMessage());
            InputHelper.pausar("Presione Enter para continuar...");
            return false;
        }
    }

    /**
     * Permite editar el carrito antes de confirmar
     */
    public boolean editCart(LoanCart cart) {
        DisplayHelper.renderSubtitle("Editar Carrito");

        if (cart.isEmpty()) {
            DisplayHelper.printWarning("El carrito está vacío");
            return false;
        }

        while (true) {
            showCartItems(cart);

            System.out.println("\nOpciones:");
            System.out.println("1. Remover ejemplar");
            System.out.println("2. Limpiar carrito");
            System.out.println("3. Volver al menú anterior");

            int opcion = InputHelper.leerEnteroEnRango("Seleccione una opción: ", 1, 3);

            switch (opcion) {
                case 1:
                    removeItemFromCart(cart);
                    break;
                case 2:
                    if (InputHelper.confirmar("¿Está seguro de limpiar todo el carrito?")) {
                        cart.clear();
                        DisplayHelper.printSuccess("Carrito limpiado");
                        return false; // Carrito vacío, salir
                    }
                    break;
                case 3:
                    return true; // Continuar con el carrito actual
            }

            if (cart.isEmpty()) {
                DisplayHelper.printWarning("El carrito está vacío");
                return false;
            }
        }
    }

    /**
     * Muestra el resumen del préstamo antes de confirmar
     */
    private void showLoanSummary(LoanCart cart) {
        DisplayHelper.renderSubtitle("Resumen del Préstamo");

        System.out.printf("Socio: %s%n", cart.getMemberId());
        System.out.printf("Cantidad de ejemplares: %d%n", cart.getItemCount());
        System.out.println();

        System.out.println("Ejemplares a prestar:");
        System.out.println("-".repeat(60));

        int counter = 1;
        for (Copy copy : cart.getItems()) {
            System.out.printf("%d. Código: %s%n", counter, copy.getCode());
            System.out.printf("   Título: %s%n", copy.getBook().getTitle());
            System.out.printf("   Autor: %s%n", copy.getBook().getAuthor());
            System.out.printf("   Estado: %s%n", copy.getState().getDisplayName());

            if (counter < cart.getItemCount()) {
                System.out.println();
            }
            counter++;
        }

        System.out.println("-".repeat(60));
        System.out.println();
    }

    /**
     * Muestra los detalles de los préstamos creados
     */
    private void showLoanDetails(List<Loan> loans) {
        DisplayHelper.renderSubtitle("Préstamos Creados");

        System.out.println("Detalles de los préstamos:");
        System.out.println("-".repeat(60));

        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.get(i);
            System.out.printf("Préstamo %d:%n", i + 1);
            System.out.printf("  ID: %s%n", loan.getId());
            System.out.printf("  Ejemplar: %s%n", loan.getCopy().getCode());
            System.out.printf("  Fecha de préstamo: %s%n", loan.getLoanDate());
            System.out.printf("  Fecha de vencimiento: %s%n", loan.getDueDate());
            System.out.printf("  Estado: %s%n", loan.getState().getDisplayName());

            if (i < loans.size() - 1) {
                System.out.println();
            }
        }

        System.out.println("-".repeat(60));
    }

    /**
     * Muestra los items del carrito para edición
     */
    private void showCartItems(LoanCart cart) {
        System.out.println("\nContenido del carrito:");
        System.out.println("-".repeat(50));

        List<Copy> items = cart.getItems();
        for (int i = 0; i < items.size(); i++) {
            Copy copy = items.get(i);
            System.out.printf("%d. %s - %s%n",
                    i + 1,
                    copy.getCode(),
                    copy.getBook().getTitle());
        }
        System.out.println("-".repeat(50));
    }

    /**
     * Remueve un item del carrito
     */
    private void removeItemFromCart(LoanCart cart) {
        if (cart.isEmpty()) {
            DisplayHelper.printWarning("No hay ejemplares para remover");
            return;
        }

        String copyCode = InputHelper.leerTextoObligatorio("Ingrese el código del ejemplar a remover: ");

        if (cart.removeItemByCode(copyCode)) {
            DisplayHelper.printSuccess("Ejemplar removido del carrito: " + copyCode);
        } else {
            DisplayHelper.printErrorMessage("Ejemplar no encontrado en el carrito: " + copyCode);
        }
    }
}