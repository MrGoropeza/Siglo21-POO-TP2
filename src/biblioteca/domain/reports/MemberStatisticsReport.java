package biblioteca.domain.reports;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import biblioteca.domain.entities.Fine;
import biblioteca.domain.entities.Loan;
import biblioteca.domain.entities.Member;
import biblioteca.domain.enums.LoanState;
import biblioteca.domain.enums.MemberType;

/**
 * Statistical report about library system.
 * Shows aggregated data about members, loans, and fines.
 * 
 * Demonstrates inheritance and polymorphism through Report base class.
 */
public class MemberStatisticsReport extends Report {

    private List<Member> members;
    private List<Loan> loans;
    private List<Fine> fines;

    // Calculated statistics
    private int totalMembers;
    private int totalLoans;
    private int activeLoans;
    private int completedLoans;
    private double totalFinesAmount;
    private int pendingFinesCount;
    private Map<MemberType, Long> membersByType;
    private Map<Member, Long> loansByMember;
    private Map<Member, Double> finesByMember;

    /**
     * Constructor
     * 
     * @param members All members
     * @param loans   All loans
     * @param fines   All fines
     */
    public MemberStatisticsReport(List<Member> members, List<Loan> loans, List<Fine> fines) {
        super("REPORTE ESTADÍSTICO DE SOCIOS");
        this.members = members;
        this.loans = loans;
        this.fines = fines;
    }

    /**
     * Get total number of members
     * 
     * @return member count
     */
    public int getTotalMembers() {
        return totalMembers;
    }

    /**
     * Get total number of active loans
     * 
     * @return active loan count
     */
    public int getActiveLoans() {
        return activeLoans;
    }

    /**
     * Get total amount of pending fines
     * 
     * @return total fines amount
     */
    public double getTotalFinesAmount() {
        return totalFinesAmount;
    }

    @Override
    public String getReportName() {
        return "estadisticas_socios";
    }

    @Override
    public String getDescription() {
        return "Estadísticas generales del sistema de biblioteca y rankings de socios";
    }

    @Override
    protected void prepareData() {
        // Basic counts
        totalMembers = members.size();
        totalLoans = loans.size();
        activeLoans = (int) loans.stream()
                .filter(loan -> loan.getState() == LoanState.ACTIVE || loan.getState() == LoanState.OVERDUE)
                .count();
        completedLoans = (int) loans.stream()
                .filter(loan -> loan.getState() == LoanState.RETURNED)
                .count();

        // Fines statistics
        List<Fine> pendingFines = fines.stream()
                .filter(fine -> !fine.isPaid())
                .toList();
        pendingFinesCount = pendingFines.size();
        totalFinesAmount = pendingFines.stream()
                .mapToDouble(Fine::getAmount)
                .sum();

        // Group by member type
        membersByType = members.stream()
                .collect(Collectors.groupingBy(Member::getType, Collectors.counting()));

        // Loans by member (top 5)
        loansByMember = loans.stream()
                .collect(Collectors.groupingBy(Loan::getMember, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Member, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Fines by member (top 5 with pending fines)
        finesByMember = pendingFines.stream()
                .collect(Collectors.groupingBy(
                        Fine::getMember,
                        Collectors.summingDouble(Fine::getAmount)))
                .entrySet().stream()
                .sorted(Map.Entry.<Member, Double>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    protected void writeContent() {
        // General statistics
        content.append("ESTADÍSTICAS GENERALES\n");
        content.append("═".repeat(80)).append("\n");
        content.append(String.format("Total de socios registrados: %d\n", totalMembers));
        content.append(String.format("Total de préstamos realizados: %d\n", totalLoans));
        content.append(String.format("  • Préstamos activos: %d\n", activeLoans));
        content.append(String.format("  • Préstamos completados: %d\n", completedLoans));
        content.append(String.format("Multas pendientes: %d (Total: $%.2f)\n", pendingFinesCount, totalFinesAmount));
        content.append("\n");

        // Members by type
        content.append("SOCIOS POR TIPO\n");
        content.append("─".repeat(80)).append("\n");
        for (Map.Entry<MemberType, Long> entry : membersByType.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalMembers;
            content.append(String.format("%-15s: %3d socios (%.1f%%)\n",
                    entry.getKey().getDisplayName(),
                    entry.getValue(),
                    percentage));
        }
        content.append("\n");

        // Top borrowers
        content.append("TOP 5 - SOCIOS MÁS ACTIVOS (Por cantidad de préstamos)\n");
        content.append("─".repeat(80)).append("\n");
        if (loansByMember.isEmpty()) {
            content.append("No hay datos de préstamos.\n");
        } else {
            content.append(String.format("%-5s %-30s %-15s %s\n",
                    "Pos.", "Nombre", "Tipo", "Préstamos"));
            content.append("─".repeat(80)).append("\n");
            int position = 1;
            for (Map.Entry<Member, Long> entry : loansByMember.entrySet()) {
                Member member = entry.getKey();
                content.append(String.format("%-5d %-30s %-15s %d\n",
                        position++,
                        truncate(member.getName(), 30),
                        member.getType().getDisplayName(),
                        entry.getValue()));
            }
        }
        content.append("\n");

        // Top members with pending fines
        content.append("TOP 5 - SOCIOS CON MAYORES MULTAS PENDIENTES\n");
        content.append("─".repeat(80)).append("\n");
        if (finesByMember.isEmpty()) {
            content.append("No hay multas pendientes.\n");
        } else {
            content.append(String.format("%-5s %-30s %-15s %s\n",
                    "Pos.", "Nombre", "Tipo", "Monto Adeudado"));
            content.append("─".repeat(80)).append("\n");
            int position = 1;
            for (Map.Entry<Member, Double> entry : finesByMember.entrySet()) {
                Member member = entry.getKey();
                content.append(String.format("%-5d %-30s %-15s $%.2f\n",
                        position++,
                        truncate(member.getName(), 30),
                        member.getType().getDisplayName(),
                        entry.getValue()));
            }
        }
        content.append("\n");

        // Additional metrics
        if (totalMembers > 0 && totalLoans > 0) {
            double avgLoansPerMember = (double) totalLoans / totalMembers;
            double avgFineAmount = pendingFinesCount > 0 ? totalFinesAmount / pendingFinesCount : 0;

            content.append("MÉTRICAS ADICIONALES\n");
            content.append("─".repeat(80)).append("\n");
            content.append(String.format("Promedio de préstamos por socio: %.2f\n", avgLoansPerMember));
            content.append(String.format("Monto promedio por multa: $%.2f\n", avgFineAmount));
        }
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
