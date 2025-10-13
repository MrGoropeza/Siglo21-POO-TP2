package biblioteca.console.devoluciones;

import biblioteca.application.devoluciones.registrar.RegisterReturnRequest;
import biblioteca.application.devoluciones.registrar.RegisterReturnResult;
import biblioteca.application.devoluciones.registrar.RegisterReturnUseCase;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;

/**
 * Form for registering a book return
 */
public class RegisterReturnForm {
    private final RegisterReturnUseCase registerReturnUseCase;

    public RegisterReturnForm(RegisterReturnUseCase registerReturnUseCase) {
        this.registerReturnUseCase = registerReturnUseCase;
    }

    /**
     * Display form and process return
     */
    public void display() {
        DisplayHelper.renderSubtitle("REGISTRAR DEVOLUCIÓN");

        try {
            // Step 1: Get copy code
            String copyCode = InputHelper.leerTextoObligatorio("Ingrese el código del ejemplar a devolver");

            if (copyCode == null || copyCode.trim().isEmpty()) {
                DisplayHelper.printErrorMessage("Código de ejemplar inválido");
                return;
            }

            copyCode = copyCode.trim().toUpperCase();

            // Step 2: Preview return information
            System.out.println("\n" + DisplayHelper.SEPARATOR);
            System.out.println("📋 INFORMACIÓN DE LA DEVOLUCIÓN");
            System.out.println(DisplayHelper.SEPARATOR);

            RegisterReturnResult previewResult = registerReturnUseCase.previewReturn(copyCode);

            if (!previewResult.isSuccess()) {
                DisplayHelper.printErrorMessage(previewResult.getMessage());
                return;
            }

            System.out.println(previewResult.getMessage());
            System.out.println(DisplayHelper.SEPARATOR);

            // Step 3: Confirm return
            boolean confirm = InputHelper.confirmar("\n¿Confirmar devolución?");

            if (!confirm) {
                DisplayHelper.printInfo("Devolución cancelada");
                return;
            }

            // Step 4: Process return
            RegisterReturnRequest request = new RegisterReturnRequest(copyCode);
            RegisterReturnResult result = registerReturnUseCase.execute(request);

            System.out.println();
            if (result.isSuccess()) {
                DisplayHelper.printSuccess(result.getMessage());

                // Show fine details if applicable
                if (result.hasFine()) {
                    System.out.println("\n⚠️  MULTA GENERADA");
                    System.out.println(DisplayHelper.SEPARATOR);
                    System.out.println("ID Multa:     " + result.getFine().getId());
                    System.out.println("Monto:        $" + String.format("%.2f", result.getFine().getAmount()));
                    System.out.println("Fecha:        " + DisplayHelper.formatDate(result.getFine().getIssueDate()));
                    System.out.println("Estado:       " + (result.getFine().isPaid() ? "Pagada" : "Pendiente"));
                    System.out.println(DisplayHelper.SEPARATOR);
                    System.out.println("\n💡 El socio puede pagar la multa en el módulo de Pagos");
                }
            } else {
                DisplayHelper.printErrorMessage(result.getMessage());
            }

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al procesar la devolución: " + e.getMessage());
        }
    }
}
