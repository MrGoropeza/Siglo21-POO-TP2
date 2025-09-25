package biblioteca.console.forms;

import java.util.List;

import biblioteca.application.socios.consultar.QueryMemberRequest;
import biblioteca.application.socios.consultar.QueryMemberResult;
import biblioteca.application.socios.consultar.QueryMemberUseCase;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Member;

/**
 * Form for finding/searching members by different criteria
 * Provides search functionality by ID or name with detailed result display
 * including activity summary
 */
public class FindMemberForm {
    private final MemberRepository memberRepository;
    private final QueryMemberUseCase queryMemberUseCase;

    public FindMemberForm(MemberRepository memberRepository, QueryMemberUseCase queryMemberUseCase) {
        this.memberRepository = memberRepository;
        this.queryMemberUseCase = queryMemberUseCase;
    }

    /**
     * Executes the find member form workflow
     */
    public void execute() {
        try {
            DisplayHelper.renderTitle("BUSCAR SOCIOS");

            // Show search options using InputHelper
            List<String> searchOptions = List.of("Buscar por ID", "Buscar por nombre");
            String selectedOption = InputHelper.seleccionar(searchOptions, "Seleccione el método de búsqueda:");

            if (selectedOption == null) {
                DisplayHelper.printInfo("Búsqueda cancelada.");
                return;
            }

            // Execute search based on option
            List<Member> results = null;
            if (selectedOption.equals("Buscar por ID")) {
                results = searchById();
            } else if (selectedOption.equals("Buscar por nombre")) {
                results = searchByName();
            }

            // Display results
            displaySearchResults(results);

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error inesperado: " + e.getMessage());
        }
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
     * Displays the search results in a formatted manner
     * 
     * @param results List of members found
     */
    private void displaySearchResults(List<Member> results) {
        System.out.println();

        if (results == null || results.isEmpty()) {
            DisplayHelper.renderSubtitle("RESULTADOS DE BÚSQUEDA");
            DisplayHelper.printInfo("No se encontraron socios que coincidan con los criterios de búsqueda.");
            return;
        }

        DisplayHelper.renderSubtitle("RESULTADOS DE BÚSQUEDA (" + results.size() + " encontrado(s))");

        // If only one result, show it directly and ask for detailed info
        if (results.size() == 1) {
            Member member = results.get(0);
            System.out.println("Socio encontrado: " + member.toString());

            System.out.println();
            if (InputHelper.confirmar("¿Desea ver información detallada?")) {
                showDetailedMemberInfoSingle(member);
            }
            return;
        }

        // Display results in a numbered list for multiple matches
        for (int i = 0; i < results.size(); i++) {
            Member member = results.get(i);
            System.out.printf("%d. %s%n", (i + 1), member.toString());
        }

        // Ask if user wants to see detailed information
        System.out.println();
        if (InputHelper.confirmar("¿Desea ver información detallada de algún socio?")) {
            showDetailedMemberInfo(results);
        }
    }

    /**
     * Shows detailed information for a single member (when only one result is
     * found)
     * Includes comprehensive member data with activity summary
     * 
     * @param member The member to show detailed information for
     */
    private void showDetailedMemberInfoSingle(Member member) {
        try {
            // Use QueryMemberUseCase to get complete information including activity summary
            QueryMemberRequest request = new QueryMemberRequest(member.getId());
            QueryMemberResult result = queryMemberUseCase.execute(request);

            System.out.println();
            if (result.isSuccess()) {
                displayCompleteMemberInformation(result);
            } else {
                // Fallback to basic information if QueryMemberUseCase fails
                DisplayHelper.renderSubtitle("INFORMACIÓN DETALLADA");
                System.out.println(member.toDetailedString());

                // Show member type benefits
                System.out.println();
                DisplayHelper.renderSubtitle("BENEFICIOS DE CATEGORÍA");
                System.out.println("Categoría: " + member.getType().getDisplayName());
                System.out.println(
                        "Descuento en multas: " + (int) (member.getType().getFineDiscountPercentage() * 100) + "%");
                System.out.println("Extensión de préstamo: +" + member.getType().getExtraLoanDays() + " días");

                System.out.println();
                DisplayHelper.printWarning("Nota: No se pudo obtener el resumen de actividad completo.");
            }

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al obtener información detallada: " + e.getMessage());
        }
    }

    /**
     * Shows detailed information for a selected member from the search results
     * Includes comprehensive member data with activity summary
     * 
     * @param results List of members from search results
     */
    private void showDetailedMemberInfo(List<Member> results) {
        try {
            System.out.println();
            int selection = InputHelper.leerEnteroEnRango(
                    "Seleccione el número del socio para ver detalles",
                    1,
                    results.size());

            Member selectedMember = results.get(selection - 1);

            // Use QueryMemberUseCase to get complete information including activity summary
            QueryMemberRequest request = new QueryMemberRequest(selectedMember.getId());
            QueryMemberResult result = queryMemberUseCase.execute(request);

            System.out.println();
            if (result.isSuccess()) {
                displayCompleteMemberInformation(result);
            } else {
                // Fallback to basic information if QueryMemberUseCase fails
                DisplayHelper.renderSubtitle("INFORMACIÓN DETALLADA");
                System.out.println(selectedMember.toDetailedString());

                // Show member type benefits
                System.out.println();
                DisplayHelper.renderSubtitle("BENEFICIOS DE CATEGORÍA");
                System.out.println("Categoría: " + selectedMember.getType().getDisplayName());
                System.out.println(
                        "Descuento en multas: " + (int) (selectedMember.getType().getFineDiscountPercentage() * 100)
                                + "%");
                System.out.println("Extensión de préstamo: +" + selectedMember.getType().getExtraLoanDays() + " días");

                System.out.println();
                DisplayHelper.printWarning("Nota: No se pudo obtener el resumen de actividad completo.");
            }

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Selección no válida: " + e.getMessage());
        }
    }

    /**
     * Displays comprehensive member information including activity summary
     * 
     * @param result The query result containing member data and summary
     */
    private void displayCompleteMemberInformation(QueryMemberResult result) {
        DisplayHelper.renderSubtitle("INFORMACIÓN COMPLETA DEL SOCIO");

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
    }
}