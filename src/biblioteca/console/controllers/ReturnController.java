package biblioteca.console.controllers;

import biblioteca.console.forms.QueryReturnsForm;
import biblioteca.console.forms.RegisterReturnForm;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;

/**
 * Controller for returns module
 */
public class ReturnController {
    private final RegisterReturnForm registerReturnForm;
    private final QueryReturnsForm queryReturnsForm;

    public ReturnController(RegisterReturnForm registerReturnForm, QueryReturnsForm queryReturnsForm) {
        this.registerReturnForm = registerReturnForm;
        this.queryReturnsForm = queryReturnsForm;
    }

    /**
     * Display returns menu and handle user interaction
     */
    public void showMenu() {
        boolean continuar = true;

        while (continuar) {
            try {
                DisplayHelper.renderTitle("GESTIÓN DE DEVOLUCIONES");

                System.out.println("1. Registrar devolución");
                System.out.println("2. Consultar historial de devoluciones");
                System.out.println("3. Volver al menú principal");

                int opcion = InputHelper.leerEnteroEnRango("Seleccione una opción", 1, 3);

                switch (opcion) {
                    case 1 -> registerReturn();
                    case 2 -> queryReturns();
                    case 3 -> {
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

    private void registerReturn() {
        registerReturnForm.display();
    }

    private void queryReturns() {
        DisplayHelper.renderSubtitle("Consultar Historial de Devoluciones");
        queryReturnsForm.display();
    }
}
