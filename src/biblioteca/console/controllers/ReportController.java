package biblioteca.console.controllers;

import biblioteca.application.usecases.report.GenerateReportRequest;
import biblioteca.application.usecases.report.GenerateReportResult;
import biblioteca.application.usecases.report.GenerateReportUseCase;
import biblioteca.application.usecases.report.GenerateReportUseCase.ReportType;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.FineRepository;
import biblioteca.data.database.LoanRepository;
import biblioteca.data.database.MemberRepository;

/**
 * Controller for report generation functionality.
 * Handles user interaction for generating different types of reports.
 */
public class ReportController {

    private final GenerateReportUseCase generateReportUseCase;

    /**
     * Constructor with dependency injection
     * 
     * @param memberRepository Member repository
     * @param loanRepository   Loan repository
     * @param fineRepository   Fine repository
     */
    public ReportController(
            MemberRepository memberRepository,
            LoanRepository loanRepository,
            FineRepository fineRepository) {
        this.generateReportUseCase = new GenerateReportUseCase(
                memberRepository,
                loanRepository,
                fineRepository);
    }

    /**
     * Show report generation menu and handle user selection
     */
    public void showMenu() {
        while (true) {
            DisplayHelper.renderTitle("GENERACIÓN DE REPORTES");
            System.out.println();
            System.out.println("Tipos de reportes disponibles:");
            System.out.println();
            System.out.println("1. Préstamos Activos");
            System.out.println("2. Multas Pendientes");
            System.out.println("3. Estadísticas de Socios");
            System.out.println();
            System.out.println("0. Volver al menú principal");
            System.out.println();

            int option = InputHelper.leerEnteroEnRango("Seleccione el tipo de reporte", 0, 3);

            if (option == 0) {
                return;
            }

            generateReportByOption(option);

            System.out.println();
            InputHelper.pausar();
        }
    }

    /**
     * Generate report based on menu option
     * 
     * @param option Menu option (1-3)
     */
    private void generateReportByOption(int option) {
        ReportType reportType = switch (option) {
            case 1 -> ReportType.ACTIVE_LOANS;
            case 2 -> ReportType.PENDING_FINES;
            case 3 -> ReportType.MEMBER_STATISTICS;
            default -> null;
        };

        if (reportType == null) {
            DisplayHelper.printErrorMessage("❌ Opción inválida");
            return;
        }

        System.out.println();
        DisplayHelper.printInfo("Generando reporte de " + reportType.getDisplayName() + "...");
        System.out.println();

        GenerateReportRequest request = new GenerateReportRequest(reportType);
        GenerateReportResult result = generateReportUseCase.execute(request);

        if (result.isSuccess()) {
            DisplayHelper.printSuccess(result.getMessage());
            System.out.println();
            DisplayHelper.printInfo("El archivo se guardó en el directorio actual del proyecto.");
        } else {
            DisplayHelper.printErrorMessage(result.getMessage());
        }
    }
}
