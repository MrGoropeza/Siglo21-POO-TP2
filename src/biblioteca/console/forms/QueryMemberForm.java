package biblioteca.console.forms;

import biblioteca.application.socios.consultar.QueryMemberRequest;
import biblioteca.application.socios.consultar.QueryMemberResult;
import biblioteca.application.socios.consultar.QueryMemberUseCase;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;

/**
 * Form for querying detailed member information
 * Displays comprehensive member data including loans, fines, and activity
 * summary
 */
public class QueryMemberForm {
    private final QueryMemberUseCase queryMemberUseCase;

    public QueryMemberForm(QueryMemberUseCase queryMemberUseCase) {
        this.queryMemberUseCase = queryMemberUseCase;
    }

    /**
     * Executes the member query form workflow
     */
    public void execute() {
        try {
            DisplayHelper.renderTitle("CONSULTAR INFORMACIÓN DE SOCIO");

            // Get member ID to query
            String memberId = getMemberId();
            if (memberId == null) {
                return; // User cancelled
            }

            // Execute query
            QueryMemberRequest request = new QueryMemberRequest(memberId);
            QueryMemberResult result = queryMemberUseCase.execute(request);

            // Display results
            if (result.isSuccess()) {
                displayMemberInformation(result);
            } else {
                DisplayHelper.printErrorMessage(result.getMessage());
            }

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Gets the member ID to query from user input
     * 
     * @return Member ID or null if cancelled
     */
    private String getMemberId() {
        System.out.println("Ingrese el ID del socio a consultar:");
        String memberId = InputHelper.leerTexto("ID del socio");

        if (memberId == null || memberId.trim().isEmpty()) {
            DisplayHelper.printInfo("Operación cancelada.");
            return null;
        }

        return memberId.trim();
    }

    /**
     * Displays comprehensive member information
     * 
     * @param result The query result containing member data and summary
     */
    private void displayMemberInformation(QueryMemberResult result) {
        DisplayHelper.renderTitle("INFORMACIÓN DEL SOCIO");

        // Display basic member information
        System.out.println(result.getMember().toDetailedString());

        // Display activity summary
        System.out.println();
        DisplayHelper.renderSubtitle("RESUMEN DE ACTIVIDAD");

        QueryMemberResult.MemberSummary summary = result.getSummary();

        System.out.printf("📚 Préstamos activos: %d%n", summary.getActiveLoans());
        System.out.printf("⚠️  Multas pendientes: %d%n", summary.getTotalUnpaidFines());

        if (summary.getTotalUnpaidAmount() > 0) {
            System.out.printf("💰 Monto total adeudado: $%.2f%n", summary.getTotalUnpaidAmount());
        }

        System.out.printf("📋 Reservas activas: %d%n", summary.getActiveReservations());

        // Display member status
        System.out.println();
        DisplayHelper.renderSubtitle("ESTADO DEL SOCIO");

        if (summary.getTotalUnpaidFines() == 0) {
            DisplayHelper.printSuccess("✅ El socio no tiene multas pendientes");
        } else {
            DisplayHelper.printWarning("⚠️  El socio tiene multas pendientes");
        }

        if (summary.getActiveLoans() == 0) {
            DisplayHelper.printInfo("📚 El socio no tiene préstamos activos");
        } else {
            DisplayHelper.printInfo("📚 El socio tiene " + summary.getActiveLoans() + " préstamo(s) activo(s)");
        }

        // Display member benefits
        System.out.println();
        DisplayHelper.renderSubtitle("BENEFICIOS DE CATEGORÍA");
        System.out.println("Categoría: " + result.getMember().getType().getDisplayName());
        System.out.println(
                "Descuento en multas: " + (int) (result.getMember().getType().getFineDiscountPercentage() * 100) + "%");
        System.out.println("Extensión de préstamo: +" + result.getMember().getType().getExtraLoanDays() + " días");

        System.out.println();
        DisplayHelper.printInfo("Presione Enter para continuar...");
        InputHelper.leerTexto("");
    }
}