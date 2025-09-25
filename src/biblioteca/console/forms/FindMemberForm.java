package biblioteca.console.forms;

import java.util.List;

import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Member;

/**
 * Form for finding/searching members by different criteria
 * Provides search functionality by ID or name with result display
 */
public class FindMemberForm {
    private final MemberRepository memberRepository;

    public FindMemberForm(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Executes the find member form workflow
     */
    public void execute() {
        try {
            DisplayHelper.renderTitle("BUSCAR SOCIOS");

            // Show search options
            displaySearchOptions();

            // Get search method
            int searchOption = getSearchOption();
            if (searchOption == -1) {
                return; // User cancelled
            }

            // Execute search based on option
            List<Member> results = null;
            switch (searchOption) {
                case 1:
                    results = searchById();
                    break;
                case 2:
                    results = searchByName();
                    break;
                default:
                    DisplayHelper.printErrorMessage("Opción no válida");
                    return;
            }

            // Display results
            displaySearchResults(results);

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Displays the available search options
     */
    private void displaySearchOptions() {
        System.out.println("Seleccione el método de búsqueda:");
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por nombre");
        System.out.println("0. Cancelar");
    }

    /**
     * Gets the search option from user input
     * 
     * @return Search option (1-2) or -1 if cancelled
     */
    private int getSearchOption() {
        try {
            int option = InputHelper.leerEnteroEnRango("Seleccione opción", 0, 2);
            if (option == 0) {
                DisplayHelper.printInfo("Búsqueda cancelada.");
                return -1;
            }
            return option;
        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Entrada no válida");
            return -1;
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

        // Display results in a numbered list
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
     * Shows detailed information for a selected member from the search results
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

            System.out.println();
            DisplayHelper.renderSubtitle("INFORMACIÓN DETALLADA");
            System.out.println(selectedMember.toDetailedString());

            // Show member type benefits
            System.out.println();
            DisplayHelper.renderSubtitle("BENEFICIOS DE CATEGORÍA");
            System.out.println("Categoría: " + selectedMember.getType().getDisplayName());
            System.out.println(
                    "Descuento en multas: " + (int) (selectedMember.getType().getFineDiscountPercentage() * 100) + "%");
            System.out.println("Extensión de préstamo: +" + selectedMember.getType().getExtraLoanDays() + " días");

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Selección no válida: " + e.getMessage());
        }

        System.out.println();
        DisplayHelper.printInfo("Presione Enter para continuar...");
        InputHelper.leerTexto("");
    }
}