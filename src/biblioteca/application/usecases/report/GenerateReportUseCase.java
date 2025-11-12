package biblioteca.application.usecases.report;

import java.io.IOException;
import java.util.List;

import biblioteca.data.database.FineRepository;
import biblioteca.data.database.LoanRepository;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Fine;
import biblioteca.domain.entities.Loan;
import biblioteca.domain.entities.Member;
import biblioteca.domain.reports.ActiveLoansReport;
import biblioteca.domain.reports.MemberStatisticsReport;
import biblioteca.domain.reports.PendingFinesReport;
import biblioteca.domain.reports.Report;

/**
 * Use case for generating system reports.
 * Demonstrates exception handling and polymorphism.
 */
public class GenerateReportUseCase {

    /**
     * Types of reports available in the system
     */
    public enum ReportType {
        ACTIVE_LOANS("Préstamos Activos"),
        PENDING_FINES("Multas Pendientes"),
        MEMBER_STATISTICS("Estadísticas de Socios");

        private final String displayName;

        ReportType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    private final FineRepository fineRepository;

    /**
     * Constructor with dependency injection
     * 
     * @param memberRepository Member repository
     * @param loanRepository   Loan repository
     * @param fineRepository   Fine repository
     */
    public GenerateReportUseCase(
            MemberRepository memberRepository,
            LoanRepository loanRepository,
            FineRepository fineRepository) {
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
        this.fineRepository = fineRepository;
    }

    /**
     * Generate a report based on type
     * 
     * @param request Report generation request
     * @return Result of operation
     */
    public GenerateReportResult execute(GenerateReportRequest request) {
        try {
            // Get data from repositories
            List<Member> members = memberRepository.findAll();
            List<Loan> loans = loanRepository.findAll();
            List<Fine> fines = fineRepository.findAll();

            // Create report instance based on type (Polymorphism)
            Report report = createReport(request.getReportType(), members, loans, fines);

            // Generate report (may throw IOException)
            report.generate();

            return GenerateReportResult.success(
                    "Reporte generado exitosamente: " + report.getFilename());

        } catch (IOException e) {
            // Wrap IOException in custom exception
            return GenerateReportResult.error(
                    "Error al escribir el archivo del reporte: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            // Invalid report type
            return GenerateReportResult.error("Tipo de reporte inválido: " + e.getMessage());

        } catch (Exception e) {
            // Catch-all for unexpected errors
            return GenerateReportResult.error(
                    "Error inesperado al generar el reporte: " + e.getMessage());
        }
    }

    /**
     * Create report instance based on type.
     * Demonstrates polymorphism - returns Report base type.
     * 
     * @param type    Report type
     * @param members All members
     * @param loans   All loans
     * @param fines   All fines
     * @return Report instance
     * @throws IllegalArgumentException if type is invalid
     */
    private Report createReport(ReportType type, List<Member> members, List<Loan> loans, List<Fine> fines) {
        return switch (type) {
            case ACTIVE_LOANS -> new ActiveLoansReport(loans);
            case PENDING_FINES -> new PendingFinesReport(fines);
            case MEMBER_STATISTICS -> new MemberStatisticsReport(members, loans, fines);
        };
    }
}
