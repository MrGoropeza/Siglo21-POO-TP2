package biblioteca.domain.reports;

import java.time.LocalDate;
import java.util.List;

import biblioteca.domain.entities.Loan;
import biblioteca.domain.enums.LoanState;

/**
 * Report of all active loans in the system.
 * Shows loans that are currently active (not returned yet).
 * 
 * Demonstrates inheritance by extending Report base class.
 */
public class ActiveLoansReport extends Report {

    private List<Loan> activeLoans;
    private int totalActive;
    private int overdueCount;

    /**
     * Constructor
     * 
     * @param loans List of all loans to filter
     */
    public ActiveLoansReport(List<Loan> loans) {
        super("REPORTE DE PRÉSTAMOS ACTIVOS");
        this.activeLoans = loans.stream()
                .filter(loan -> loan.getState() == LoanState.ACTIVE || loan.getState() == LoanState.OVERDUE)
                .toList();
    }

    @Override
    public String getReportName() {
        return "prestamos_activos";
    }

    @Override
    public String getDescription() {
        return "Listado de todos los préstamos activos y vencidos del sistema";
    }

    /**
     * Get total count of active loans
     * 
     * @return number of active loans
     */
    public int getTotalActive() {
        return totalActive;
    }

    /**
     * Get count of overdue loans
     * 
     * @return number of overdue loans
     */
    public int getOverdueCount() {
        return overdueCount;
    }

    @Override
    protected void prepareData() {
        totalActive = activeLoans.size();
        LocalDate today = LocalDate.now();
        overdueCount = (int) activeLoans.stream()
                .filter(loan -> loan.getDueDate().isBefore(today))
                .count();
    }

    @Override
    protected void writeContent() {
        // Summary section
        content.append("RESUMEN\n");
        content.append("─".repeat(80)).append("\n");
        content.append(String.format("Total de préstamos activos: %d\n", totalActive));
        content.append(String.format("Préstamos vencidos: %d\n", overdueCount));
        content.append(String.format("Préstamos al día: %d\n", totalActive - overdueCount));
        content.append("\n");

        if (activeLoans.isEmpty()) {
            content.append("No hay préstamos activos en el sistema.\n");
            return;
        }

        // Detailed list
        content.append("DETALLE DE PRÉSTAMOS\n");
        content.append("─".repeat(80)).append("\n");
        content.append(String.format("%-12s %-20s %-15s %-12s %-12s %s\n",
                "ID", "Socio", "Ejemplar", "F. Préstamo", "F. Vencim.", "Estado"));
        content.append("─".repeat(80)).append("\n");

        for (Loan loan : activeLoans) {
            String memberName = truncate(loan.getMember().getName(), 20);
            String copyCode = loan.getCopy().getCode();
            String loanDate = loan.getLoanDate().toString();
            String dueDate = loan.getDueDate().toString();
            String status = loan.getDueDate().isBefore(LocalDate.now()) ? "VENCIDO" : "Al día";

            content.append(String.format("%-12s %-20s %-15s %-12s %-12s %s\n",
                    loan.getId(),
                    memberName,
                    copyCode,
                    loanDate,
                    dueDate,
                    status));
        }

        content.append("─".repeat(80)).append("\n");
    }

    /**
     * Truncate text to specified length
     * 
     * @param text      Text to truncate
     * @param maxLength Maximum length
     * @return truncated text
     */
    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
}
