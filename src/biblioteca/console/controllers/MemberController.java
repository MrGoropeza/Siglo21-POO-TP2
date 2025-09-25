package biblioteca.console.controllers;

import biblioteca.application.socios.consultar.QueryMemberUseCase;
import biblioteca.application.socios.modificar.ModifyMemberUseCase;
import biblioteca.application.socios.pagar_multa.PayFineUseCase;
import biblioteca.application.socios.registrar.RegisterMemberUseCase;
import biblioteca.console.forms.FindMemberForm;
import biblioteca.console.forms.ModifyMemberForm;
import biblioteca.console.forms.PayFineForm;
import biblioteca.console.forms.QueryMemberForm;
import biblioteca.console.forms.RegisterMemberForm;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;

/**
 * Controller for member-related console operations
 * Manages all member operations following the same pattern as BookController
 */
public class MemberController {
    private final RegisterMemberUseCase registerMemberUseCase;
    private final ModifyMemberUseCase modifyMemberUseCase;
    private final QueryMemberUseCase queryMemberUseCase;
    private final PayFineUseCase payFineUseCase;
    private final RegisterMemberForm registerMemberForm;
    private final ModifyMemberForm modifyMemberForm;
    private final QueryMemberForm queryMemberForm;
    private final FindMemberForm findMemberForm;
    private final PayFineForm payFineForm;

    public MemberController(RegisterMemberUseCase registerMemberUseCase,
            ModifyMemberUseCase modifyMemberUseCase,
            QueryMemberUseCase queryMemberUseCase,
            PayFineUseCase payFineUseCase,
            RegisterMemberForm registerMemberForm,
            ModifyMemberForm modifyMemberForm,
            QueryMemberForm queryMemberForm,
            FindMemberForm findMemberForm,
            PayFineForm payFineForm) {
        this.registerMemberUseCase = registerMemberUseCase;
        this.modifyMemberUseCase = modifyMemberUseCase;
        this.queryMemberUseCase = queryMemberUseCase;
        this.payFineUseCase = payFineUseCase;
        this.registerMemberForm = registerMemberForm;
        this.modifyMemberForm = modifyMemberForm;
        this.queryMemberForm = queryMemberForm;
        this.findMemberForm = findMemberForm;
        this.payFineForm = payFineForm;
    }

    /**
     * Displays the member management menu and handles user selection
     */
    public void showMenu() {
        boolean continuar = true;

        while (continuar) {
            try {
                DisplayHelper.renderTitle("GESTIÓN DE SOCIOS");

                System.out.println("1. Registrar nuevo socio");
                System.out.println("2. Modificar datos del socio");
                System.out.println("3. Consultar información del socio");
                System.out.println("4. Buscar socio");
                System.out.println("5. Pagar multas");
                System.out.println("6. Volver al menú principal");

                int opcion = InputHelper.leerEnteroEnRango("Seleccione una opción", 1, 6);

                switch (opcion) {
                    case 1 -> registerMember();
                    case 2 -> modifyMember();
                    case 3 -> queryMember();
                    case 4 -> findMember();
                    case 5 -> payFines();
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

    /**
     * Handles member registration through the form
     */
    private void registerMember() {
        try {
            // Use the form to capture data and execute use case
            var request = registerMemberForm.captureData();
            if (request != null) {
                var result = registerMemberUseCase.execute(request);
                if (result.isSuccess()) {
                    DisplayHelper.printSuccess(result.getMessage());
                } else {
                    DisplayHelper.printErrorMessage(result.getMessage());
                }
            }
        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al registrar socio: " + e.getMessage());
        }
    }

    /**
     * Handles member data modification through the form
     */
    private void modifyMember() {
        try {
            // Use the form to capture data and execute use case
            var request = modifyMemberForm.captureData();
            if (request != null) {
                var result = modifyMemberUseCase.execute(request);
                if (result.isSuccess()) {
                    DisplayHelper.printSuccess(result.getMessage());
                } else {
                    DisplayHelper.printErrorMessage(result.getMessage());
                }
            }
        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al modificar socio: " + e.getMessage());
        }
    }

    /**
     * Handles member information query through the form
     */
    private void queryMember() {
        try {
            queryMemberForm.execute();
        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al consultar socio: " + e.getMessage());
        }
    }

    /**
     * Handles member search through the form
     */
    private void findMember() {
        try {
            findMemberForm.execute();
        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al buscar socio: " + e.getMessage());
        }
    }

    /**
     * Handles fine payment through the form
     */
    private void payFines() {
        try {
            payFineForm.execute();
        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al procesar pago de multas: " + e.getMessage());
        }
    }
}