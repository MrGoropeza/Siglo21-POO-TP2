package biblioteca.console.forms;

import java.util.ArrayList;
import java.util.List;

import biblioteca.application.socios.pagar_multa.PayFineRequest;
import biblioteca.application.socios.pagar_multa.PayFineResult;
import biblioteca.application.socios.pagar_multa.PayFineUseCase;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Member;

/**
 * Form for processing member fine payments
 * Basic implementation that will be enhanced when Fine module is implemented
 */
public class PayFineForm {
    private final PayFineUseCase payFineUseCase;
    private final MemberRepository memberRepository;

    public PayFineForm(PayFineUseCase payFineUseCase, MemberRepository memberRepository) {
        this.payFineUseCase = payFineUseCase;
        this.memberRepository = memberRepository;
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

            // Show current implementation limitation
            DisplayHelper.renderSubtitle("ESTADO ACTUAL DEL SISTEMA");
            DisplayHelper.printInfo("NOTA: El sistema de multas será implementado en el módulo de DEVOLUCIONES.");
            DisplayHelper.printInfo("Por ahora, esta funcionalidad registra pagos de manera simulada.");

            System.out.println();

            // Ask if user wants to continue with simulation
            if (!InputHelper.confirmar("¿Desea continuar con la simulación de pago de multas?")) {
                DisplayHelper.printInfo("Operación cancelada.");
                return;
            }

            // Simulate fine payment
            simulateFinePayment(member);

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
     * Simulates fine payment process
     * 
     * @param member The member paying fines
     */
    private void simulateFinePayment(Member member) {
        System.out.println();
        DisplayHelper.renderSubtitle("SIMULACIÓN DE PAGO DE MULTAS");

        // Simulate having fines to pay
        int simulatedFines = (int) (Math.random() * 3) + 1; // 1-3 fines
        double simulatedAmount = Math.round((Math.random() * 500 + 50) * 100.0) / 100.0; // $50-$550

        System.out.printf("Multas simuladas encontradas: %d%n", simulatedFines);
        System.out.printf("Monto total simulado: $%.2f%n", simulatedAmount);

        // Apply member type discount
        double discountedAmount = member.calculateFineAmount(simulatedAmount);
        if (discountedAmount < simulatedAmount) {
            System.out.printf("Descuento aplicado (%s): -$%.2f%n",
                    member.getType().getDisplayName(),
                    simulatedAmount - discountedAmount);
            System.out.printf("Monto final a pagar: $%.2f%n", discountedAmount);
        }

        System.out.println();

        if (!InputHelper.confirmar("¿Confirma el pago de las multas seleccionadas?")) {
            DisplayHelper.printInfo("Pago cancelado.");
            return;
        }

        // Create simulated fine IDs
        List<String> simulatedFineIds = new ArrayList<>();
        for (int i = 1; i <= simulatedFines; i++) {
            simulatedFineIds.add("FINE_SIM_" + member.getId() + "_" + i);
        }

        // Execute payment
        PayFineRequest request = new PayFineRequest(member.getId(), simulatedFineIds);
        PayFineResult result = payFineUseCase.execute(request);

        // Display result
        System.out.println();
        if (result.isSuccess()) {
            DisplayHelper.renderSubtitle("PAGO PROCESADO");
            DisplayHelper.printSuccess(result.getMessage());

            System.out.println();
            DisplayHelper.printInfo("RECORDATORIO: Cuando se implemente el módulo de DEVOLUCIONES,");
            DisplayHelper.printInfo("este proceso manejará multas reales del sistema.");
        } else {
            DisplayHelper.printErrorMessage(result.getMessage());
        }

        System.out.println();
        DisplayHelper.printInfo("Presione Enter para continuar...");
        InputHelper.leerTexto("");
    }
}