package biblioteca.console.forms;

import java.util.ArrayList;
import java.util.List;

import biblioteca.application.socios.pagar_multa.PayFineRequest;
import biblioteca.application.socios.pagar_multa.PayFineResult;
import biblioteca.application.socios.pagar_multa.PayFineUseCase;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.FineRepository;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Fine;
import biblioteca.domain.entities.Member;

/**
 * Form for processing member fine payments
 * Handles real fine payment from the system
 */
public class PayFineForm {
    private final PayFineUseCase payFineUseCase;
    private final MemberRepository memberRepository;
    private final FineRepository fineRepository;

    public PayFineForm(PayFineUseCase payFineUseCase, MemberRepository memberRepository,
            FineRepository fineRepository) {
        this.payFineUseCase = payFineUseCase;
        this.memberRepository = memberRepository;
        this.fineRepository = fineRepository;
    }

    /**
     * Executes the fine payment form workflow
     */
    public void execute() {
        try {
            DisplayHelper.renderTitle("PAGAR MULTAS DE SOCIO");

            // Get member for fine payment
            Member member = selectMember();
            if (member == null) {
                return; // User cancelled or member not found
            }

            // Display member information
            displayMemberInfo(member);

            // Get unpaid fines for this member
            List<Fine> unpaidFines = fineRepository.findUnpaidByMember(member);

            if (unpaidFines.isEmpty()) {
                System.out.println();
                DisplayHelper.printSuccess("¡Este socio no tiene multas pendientes!");
                return;
            }

            // Display unpaid fines
            displayUnpaidFines(unpaidFines, member);

            // Select fines to pay
            List<String> selectedFineIds = selectFinesToPay(unpaidFines);
            if (selectedFineIds.isEmpty()) {
                DisplayHelper.printInfo("Operación cancelada.");
                return;
            }

            // Process payment
            processFinePayment(member, selectedFineIds, unpaidFines);

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Allows user to select a member by ID or name search
     * 
     * @return Selected member or null if cancelled/not found
     */
    private Member selectMember() {
        DisplayHelper.renderSubtitle("BUSCAR SOCIO PARA PAGO DE MULTAS");

        // Create search options using InputHelper
        List<String> searchOptions = List.of("Buscar por ID", "Buscar por nombre");
        String selectedOption = InputHelper.seleccionar(searchOptions, "Seleccione el método de búsqueda:");

        if (selectedOption == null) {
            DisplayHelper.printInfo("Búsqueda cancelada.");
            return null;
        }

        // Execute search based on option
        List<Member> results = null;
        if (selectedOption.equals("Buscar por ID")) {
            results = searchById();
        } else if (selectedOption.equals("Buscar por nombre")) {
            results = searchByName();
        }

        // Process search results
        if (results == null || results.isEmpty()) {
            DisplayHelper.printInfo("No se encontraron socios que coincidan con los criterios de búsqueda.");
            return null;
        }

        // If only one result, select it automatically
        if (results.size() == 1) {
            Member selectedMember = results.get(0);
            DisplayHelper.printInfo("Socio encontrado: " + selectedMember.toString());
            return selectedMember;
        }

        // Multiple results - let user choose using InputHelper
        return InputHelper.seleccionar(results, "Seleccione el socio para procesar pago de multas:");
    }

    /**
     * Performs search by member ID
     * 
     * @return List containing the found member or empty list
     */
    private List<Member> searchById() {
        System.out.println();
        String memberId = InputHelper.leerTexto("Ingrese el ID del socio");

        if (memberId == null || memberId.trim().isEmpty()) {
            DisplayHelper.printInfo("Búsqueda cancelada.");
            return List.of();
        }

        Member member = memberRepository.findById(memberId.trim());
        if (member != null) {
            return List.of(member);
        } else {
            return List.of();
        }
    }

    /**
     * Performs search by member name (partial matching)
     * 
     * @return List of members matching the search criteria
     */
    private List<Member> searchByName() {
        System.out.println();
        String searchText = InputHelper.leerTexto("Ingrese el nombre o parte del nombre del socio");

        if (searchText == null || searchText.trim().isEmpty()) {
            DisplayHelper.printInfo("Búsqueda cancelada.");
            return List.of();
        }

        return memberRepository.searchByName(searchText.trim());
    }

    /**
     * Displays basic member information
     * 
     * @param member The member whose information to display
     */
    private void displayMemberInfo(Member member) {
        System.out.println();
        DisplayHelper.renderSubtitle("INFORMACIÓN DEL SOCIO");
        System.out.println(member.toDetailedString());
    }

    /**
     * Displays unpaid fines for a member
     * 
     * @param fines  List of unpaid fines
     * @param member The member
     */
    private void displayUnpaidFines(List<Fine> fines, Member member) {
        System.out.println();
        DisplayHelper.renderSubtitle("MULTAS PENDIENTES");

        double totalAmount = 0.0;

        for (int i = 0; i < fines.size(); i++) {
            Fine fine = fines.get(i);
            System.out.printf("%d. Multa %s - $%.2f - Emitida: %s%n",
                    i + 1,
                    fine.getId(),
                    fine.getAmount(),
                    DisplayHelper.formatDate(fine.getIssueDate()));
            totalAmount += fine.getAmount();
        }

        System.out.println();
        System.out.printf("Total de multas pendientes: %d%n", fines.size());
        System.out.printf("Monto total: $%.2f%n", totalAmount);

        // Show member type benefits if applicable
        if (member.getType().getFineDiscountPercentage() > 0) {
            System.out.println();
            DisplayHelper.printInfo(String.format(
                    "Como socio %s, tiene un descuento del %.0f%% en multas",
                    member.getType().getDisplayName(),
                    member.getType().getFineDiscountPercentage() * 100));
        }
    }

    /**
     * Allows user to select which fines to pay
     * 
     * @param fines List of unpaid fines
     * @return List of selected fine IDs
     */
    private List<String> selectFinesToPay(List<Fine> fines) {
        System.out.println();
        System.out.println("Opciones:");
        System.out.println("  - Ingrese los números de las multas a pagar (separados por coma). Ej: 1,3,5");
        System.out.println("  - Escriba 'todas' para pagar todas las multas");
        System.out.println("  - Presione Enter sin texto para cancelar");

        String input = InputHelper.leerTexto("\nSelección");

        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }

        List<String> selectedIds = new ArrayList<>();

        if (input.trim().equalsIgnoreCase("todas")) {
            // Select all fines
            for (Fine fine : fines) {
                selectedIds.add(fine.getId());
            }
        } else {
            // Parse comma-separated numbers
            String[] parts = input.split(",");
            for (String part : parts) {
                try {
                    int index = Integer.parseInt(part.trim()) - 1;
                    if (index >= 0 && index < fines.size()) {
                        selectedIds.add(fines.get(index).getId());
                    } else {
                        DisplayHelper.printWarning("Número inválido: " + (index + 1));
                    }
                } catch (NumberFormatException e) {
                    DisplayHelper.printWarning("Entrada inválida: " + part);
                }
            }
        }

        return selectedIds;
    }

    /**
     * Processes fine payment
     * 
     * @param member          The member paying
     * @param selectedFineIds List of fine IDs to pay
     * @param allFines        All unpaid fines (for display)
     */
    private void processFinePayment(Member member, List<String> selectedFineIds, List<Fine> allFines) {
        System.out.println();
        DisplayHelper.renderSubtitle("RESUMEN DE PAGO");

        // Calculate total to pay
        double totalAmount = 0.0;
        System.out.println("Multas seleccionadas para pago:");
        for (String fineId : selectedFineIds) {
            Fine fine = allFines.stream()
                    .filter(f -> f.getId().equals(fineId))
                    .findFirst()
                    .orElse(null);

            if (fine != null) {
                System.out.printf("  • %s - $%.2f%n", fine.getId(), fine.getAmount());
                totalAmount += fine.getAmount();
            }
        }

        System.out.println();
        System.out.printf("Total a pagar: $%.2f%n", totalAmount);

        System.out.println();
        if (!InputHelper.confirmar("¿Confirma el pago de las multas seleccionadas?")) {
            DisplayHelper.printInfo("Pago cancelado.");
            return;
        }

        // Execute payment
        PayFineRequest request = new PayFineRequest(member.getId(), selectedFineIds);
        PayFineResult result = payFineUseCase.execute(request);

        // Display result
        System.out.println();
        if (result.isSuccess()) {
            DisplayHelper.renderSubtitle("PAGO PROCESADO EXITOSAMENTE");
            DisplayHelper.printSuccess(result.getMessage());
        } else {
            DisplayHelper.printErrorMessage(result.getMessage());
        }
    }
}