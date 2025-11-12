package biblioteca.domain.reports;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import biblioteca.domain.entities.Fine;
import biblioteca.domain.entities.Member;

/**
 * Report of all pending fines in the system.
 * Groups fines by member and shows totals.
 * 
 * Demonstrates inheritance and polymorphism.
 */
public class PendingFinesReport extends Report {

    private List<Fine> pendingFines;
    private Map<Member, List<Fine>> finesByMember;
    private double totalAmount;
    private int totalFines;

    /**
     * Constructor
     * 
     * @param fines List of all fines to filter
     */
    public PendingFinesReport(List<Fine> fines) {
        super("REPORTE DE MULTAS PENDIENTES");
        this.pendingFines = fines.stream()
                .filter(fine -> !fine.isPaid())
                .toList();
    }

    /**
     * Get total amount of pending fines
     * 
     * @return total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Get count of pending fines
     * 
     * @return number of fines
     */
    public int getTotalFines() {
        return totalFines;
    }

    @Override
    public String getReportName() {
        return "multas_pendientes";
    }

    @Override
    public String getDescription() {
        return "Listado de multas pendientes de pago agrupadas por socio";
    }

    @Override
    protected void prepareData() {
        totalFines = pendingFines.size();
        totalAmount = pendingFines.stream()
                .mapToDouble(Fine::getAmount)
                .sum();

        // Group fines by member
        finesByMember = pendingFines.stream()
                .collect(Collectors.groupingBy(Fine::getMember));
    }

    @Override
    protected void writeContent() {
        // Summary section
        content.append("RESUMEN\n");
        content.append("─".repeat(80)).append("\n");
        content.append(String.format("Total de multas pendientes: %d\n", totalFines));
        content.append(String.format("Monto total adeudado: $%.2f\n", totalAmount));
        content.append(String.format("Socios con multas: %d\n", finesByMember.size()));
        content.append("\n");

        if (pendingFines.isEmpty()) {
            content.append("No hay multas pendientes en el sistema.\n");
            return;
        }

        // Detailed list grouped by member
        content.append("DETALLE POR SOCIO\n");
        content.append("═".repeat(80)).append("\n");

        for (Map.Entry<Member, List<Fine>> entry : finesByMember.entrySet()) {
            Member member = entry.getKey();
            List<Fine> memberFines = entry.getValue();
            double memberTotal = memberFines.stream()
                    .mapToDouble(Fine::getAmount)
                    .sum();

            // Member header
            content.append(String.format("\n👤 Socio: %s (ID: %s)\n",
                    member.getName(), member.getId()));
            content.append(String.format("   Tipo: %s | Total adeudado: $%.2f\n",
                    getMemberTypeText(member.getType()), memberTotal));
            content.append("   ").append("─".repeat(77)).append("\n");

            // Member's fines
            content.append(String.format("   %-10s %-12s %s\n",
                    "ID Multa", "Monto", "Fecha"));
            content.append("   ").append("─".repeat(77)).append("\n");

            for (Fine fine : memberFines) {
                content.append(String.format("   %-10s $%-11.2f %s\n",
                        fine.getId(),
                        fine.getAmount(),
                        fine.getIssueDate().toString()));
            }
        }

        content.append("\n").append("═".repeat(80)).append("\n");
    }

    /**
     * Get text representation of member type
     * 
     * @param type Member type enum
     * @return human-readable text
     */
    private String getMemberTypeText(biblioteca.domain.enums.MemberType type) {
        return switch (type) {
            case STUDENT -> "Estudiante";
            case RETIRED -> "Jubilado";
            case STANDARD -> "Estándar";
        };
    }
}
