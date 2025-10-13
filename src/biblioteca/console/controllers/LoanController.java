package biblioteca.console.controllers;

import java.time.LocalDate;
import java.util.List;

import biblioteca.application.prestamos.create.CreateLoanUseCase;
import biblioteca.console.forms.prestamos.LoanCartForm;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.console.utils.MemberSearchHelper;
import biblioteca.data.database.LoanRepository;
import biblioteca.data.database.CopyRepository;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Loan;
import biblioteca.domain.entities.Member;
import biblioteca.domain.enums.LoanState;

/**
 * Controlador para el módulo de préstamos
 */
public class LoanController {
    private final CreateLoanUseCase createLoanUseCase;
    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final CopyRepository copyRepository;
    private final LoanCartForm loanCartForm;

    public LoanController(CreateLoanUseCase createLoanUseCase, LoanRepository loanRepository,
            MemberRepository memberRepository, CopyRepository copyRepository) {
        this.createLoanUseCase = createLoanUseCase;
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
        this.copyRepository = copyRepository;
        this.loanCartForm = new LoanCartForm(createLoanUseCase, copyRepository, memberRepository);
    }

    /**
     * Muestra el menú principal de préstamos
     */
    public void showMainMenu() {
        boolean continueMenu = true;

        while (continueMenu) {
            DisplayHelper.renderTitle("Gestión de Préstamos");

            System.out.println("1. Nuevo préstamo");
            System.out.println("2. Ver préstamos activos");
            System.out.println("3. Ver préstamos vencidos");
            System.out.println("4. Buscar préstamos por socio");
            System.out.println("5. Estadísticas de préstamos");
            System.out.println("6. Volver al menú principal");

            int opcion = InputHelper.leerEnteroEnRango("Seleccione una opción: ", 1, 6);

            switch (opcion) {
                case 1:
                    createNewLoan();
                    break;
                case 2:
                    showActiveLoans();
                    break;
                case 3:
                    showOverdueLoans();
                    break;
                case 4:
                    searchLoansByMember();
                    break;
                case 5:
                    showLoanStatistics();
                    break;
                case 6:
                    continueMenu = false;
                    break;
            }
        }
    }

    /**
     * Crea un nuevo préstamo usando el carrito
     */
    private void createNewLoan() {
        loanCartForm.show();
    }

    /**
     * Muestra todos los préstamos activos
     */
    private void showActiveLoans() {
        DisplayHelper.renderTitle("Préstamos Activos");

        List<Loan> activeLoans = loanRepository.findByState(LoanState.ACTIVE);

        if (activeLoans.isEmpty()) {
            DisplayHelper.printInfo("No hay préstamos activos en el sistema");
        } else {
            DisplayHelper.printInfo(String.format("Total de préstamos activos: %d", activeLoans.size()));
            System.out.println();

            displayLoanList(activeLoans);
        }

        InputHelper.pausar("Presione Enter para continuar...");
    }

    /**
     * Muestra todos los préstamos vencidos
     */
    private void showOverdueLoans() {
        DisplayHelper.renderTitle("Préstamos Vencidos");

        List<Loan> overdueLoans = loanRepository.findOverdueLoans();

        if (overdueLoans.isEmpty()) {
            DisplayHelper.printSuccess("No hay préstamos vencidos en el sistema");
        } else {
            DisplayHelper.printWarning(String.format("Total de préstamos vencidos: %d", overdueLoans.size()));
            System.out.println();

            displayLoanList(overdueLoans);
        }

        InputHelper.pausar("Presione Enter para continuar...");
    }

    /**
     * Busca préstamos por socio
     */
    private void searchLoansByMember() {
        DisplayHelper.renderTitle("Buscar Préstamos por Socio");

        // Buscar socio por nombre o ID
        Member member = MemberSearchHelper.searchAndSelectMember(memberRepository, "Buscar socio");

        if (member == null) {
            DisplayHelper.printWarning("Búsqueda cancelada");
            InputHelper.pausar("Presione Enter para continuar...");
            return;
        }

        DisplayHelper.printSuccess("Socio seleccionado: " + member.getName());
        MemberSearchHelper.displayMemberSummary(member);
        System.out.println();

        // Buscar préstamos del socio
        List<Loan> memberLoans = loanRepository.findByMemberId(member.getId());

        if (memberLoans.isEmpty()) {
            DisplayHelper.printInfo("El socio no tiene préstamos registrados");
        } else {
            DisplayHelper.printInfo(String.format("Total de préstamos del socio: %d", memberLoans.size()));
            System.out.println();

            // Mostrar estadísticas rápidas
            long activeCount = memberLoans.stream().filter(loan -> loan.getState() == LoanState.ACTIVE).count();
            long returnedCount = memberLoans.stream().filter(loan -> loan.getState() == LoanState.RETURNED).count();
            long overdueCount = memberLoans.stream().filter(loan -> loan.getState() == LoanState.OVERDUE).count();

            System.out.printf("Activos: %d | Devueltos: %d | Vencidos: %d%n", activeCount, returnedCount, overdueCount);
            System.out.println();

            displayLoanList(memberLoans);
        }

        InputHelper.pausar("Presione Enter para continuar...");
    }

    /**
     * Muestra estadísticas generales de préstamos
     */
    private void showLoanStatistics() {
        DisplayHelper.renderTitle("Estadísticas de Préstamos");

        List<Loan> allLoans = loanRepository.findAll();

        if (allLoans.isEmpty()) {
            DisplayHelper.printInfo("No hay préstamos en el sistema");
            InputHelper.pausar("Presione Enter para continuar...");
            return;
        }

        // Calcular estadísticas
        long totalLoans = allLoans.size();
        long activeLoans = allLoans.stream().filter(loan -> loan.getState() == LoanState.ACTIVE).count();
        long returnedLoans = allLoans.stream().filter(loan -> loan.getState() == LoanState.RETURNED).count();
        long overdueLoans = allLoans.stream().filter(loan -> loan.getState() == LoanState.OVERDUE).count();

        // Estadísticas por fecha
        LocalDate today = LocalDate.now();
        long loansToday = allLoans.stream()
                .filter(loan -> loan.getLoanDate().equals(today))
                .count();

        LocalDate weekAgo = today.minusDays(7);
        long loansThisWeek = allLoans.stream()
                .filter(loan -> loan.getLoanDate().isAfter(weekAgo) || loan.getLoanDate().equals(today))
                .count();

        // Mostrar estadísticas
        System.out.println("=== ESTADÍSTICAS GENERALES ===");
        System.out.printf("Total de préstamos: %d%n", totalLoans);
        System.out.printf("Préstamos activos: %d (%.1f%%)%n", activeLoans, (activeLoans * 100.0 / totalLoans));
        System.out.printf("Préstamos devueltos: %d (%.1f%%)%n", returnedLoans, (returnedLoans * 100.0 / totalLoans));
        System.out.printf("Préstamos vencidos: %d (%.1f%%)%n", overdueLoans, (overdueLoans * 100.0 / totalLoans));
        System.out.println();

        System.out.println("=== ESTADÍSTICAS TEMPORALES ===");
        System.out.printf("Préstamos hoy: %d%n", loansToday);
        System.out.printf("Préstamos esta semana: %d%n", loansThisWeek);
        System.out.println();

        // Top socios con más préstamos
        System.out.println("=== TOP SOCIOS (Préstamos Activos) ===");
        loanRepository.findByState(LoanState.ACTIVE)
                .stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        loan -> loan.getMember().getId(),
                        java.util.stream.Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(java.util.Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> {
                    Member member = memberRepository.findById(entry.getKey());
                    String memberName = member != null ? member.getName() : "Desconocido";
                    System.out.printf("- %s (%s): %d préstamos%n",
                            memberName, entry.getKey(), entry.getValue());
                });

        InputHelper.pausar("Presione Enter para continuar...");
    }

    /**
     * Muestra una lista de préstamos en formato tabular
     */
    private void displayLoanList(List<Loan> loans) {
        if (loans.isEmpty()) {
            return;
        }

        System.out.println("-".repeat(100));
        System.out.printf("%-15s %-12s %-12s %-12s %-12s %-10s%n",
                "ID Préstamo", "Socio", "Ejemplar", "F. Préstamo", "F. Vencim.", "Estado");
        System.out.println("-".repeat(100));

        for (Loan loan : loans) {
            // Obtener nombre del socio (ahora es directo del objeto)
            String memberName = loan.getMember().getName();
            if (memberName.length() > 11) {
                memberName = memberName.substring(0, 8) + "...";
            }

            System.out.printf("%-15s %-12s %-12s %-12s %-12s %-10s%n",
                    loan.getId(),
                    memberName,
                    loan.getCopy().getCode(),
                    loan.getLoanDate().toString(),
                    loan.getDueDate().toString(),
                    loan.getState().getDisplayName());
        }

        System.out.println("-".repeat(100));
    }
}