package biblioteca.console.forms;

import biblioteca.application.prestamos.carrito.LoanCart;
import biblioteca.application.prestamos.create.CreateLoanResult;
import biblioteca.application.prestamos.create.CreateLoanUseCase;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.CopyRepository;
import biblioteca.domain.entities.Copy;
import biblioteca.domain.enums.CopyState;

/**
 * Formulario para agregar ejemplares al carrito de préstamos
 */
public class AddToCartForm {
    private final CreateLoanUseCase createLoanUseCase;
    private final CopyRepository copyRepository;

    public AddToCartForm(CreateLoanUseCase createLoanUseCase, CopyRepository copyRepository) {
        this.createLoanUseCase = createLoanUseCase;
        this.copyRepository = copyRepository;
    }

    /**
     * Muestra el formulario para agregar ejemplares al carrito
     */
    public void show(LoanCart cart) {
        DisplayHelper.renderTitle("Agregar Ejemplar al Carrito");

        if (cart == null) {
            DisplayHelper.printErrorMessage("Error: No hay carrito de préstamos activo");
            return;
        }

        DisplayHelper.printInfo(String.format("Carrito actual: %d ejemplar(es) para socio %s",
                cart.getItemCount(), cart.getMemberId()));

        while (true) {
            System.out.println("\n" + "-".repeat(50));

            // Solicitar código del ejemplar
            String copyCode = InputHelper.leerTexto("Ingrese el código del ejemplar (o 'salir' para terminar): ");

            if (copyCode.equalsIgnoreCase("salir")) {
                break;
            }

            if (copyCode.trim().isEmpty()) {
                DisplayHelper.printErrorMessage("El código del ejemplar no puede estar vacío");
                continue;
            }

            // Buscar el ejemplar
            Copy copy = copyRepository.findByCode(copyCode);
            if (copy == null) {
                DisplayHelper.printErrorMessage("Ejemplar no encontrado: " + copyCode);

                if (InputHelper.confirmar("¿Desea intentar con otro código?")) {
                    continue;
                } else {
                    break;
                }
            }

            // Verificar estado del ejemplar
            if (copy.getState() != CopyState.AVAILABLE) {
                DisplayHelper.printErrorMessage(String.format("El ejemplar %s no está disponible (Estado: %s)",
                        copyCode, copy.getState().getDisplayName()));
                continue;
            }

            // Mostrar información del ejemplar
            DisplayHelper.printSuccess(String.format("Ejemplar encontrado: %s - %s",
                    copy.getCode(), copy.getBook().getTitle()));

            // Validar si se puede agregar al carrito
            CreateLoanResult validation = createLoanUseCase.validateCopyForCart(copyCode, cart);

            if (!validation.isSuccess()) {
                DisplayHelper.printErrorMessage("No se puede agregar al carrito: " + validation.getMessage());
                continue;
            }

            // Agregar al carrito
            boolean added = cart.addItem(copy);
            if (added) {
                DisplayHelper.printSuccess(String.format("Ejemplar '%s' agregado al carrito", copyCode));
                DisplayHelper.printInfo(String.format("Carrito ahora tiene %d ejemplar(es)", cart.getItemCount()));
            } else {
                DisplayHelper.printWarning("El ejemplar ya estaba en el carrito");
            }

            // Preguntar si desea agregar más ejemplares
            if (!InputHelper.confirmar("¿Desea agregar otro ejemplar?")) {
                break;
            }
        }

        // Mostrar resumen del carrito
        if (!cart.isEmpty()) {
            System.out.println("\n" + "=".repeat(50));
            showCartSummary(cart);
        }
    }

    /**
     * Muestra el resumen del carrito actual
     */
    private void showCartSummary(LoanCart cart) {
        DisplayHelper.renderSubtitle("Resumen del Carrito");
        DisplayHelper.printInfo(String.format("Socio: %s", cart.getMemberId()));
        DisplayHelper.printInfo(String.format("Total de ejemplares: %d", cart.getItemCount()));

        System.out.println("-".repeat(40));
        System.out.println("Ejemplares en el carrito:");

        int counter = 1;
        for (Copy copy : cart.getItems()) {
            System.out.printf("%d. %s - %s%n",
                    counter++,
                    copy.getCode(),
                    copy.getBook().getTitle());
        }

        System.out.println("-".repeat(40));
    }
}