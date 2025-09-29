package biblioteca.console.utils;

import java.util.List;

import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Member;

/**
 * Utilidad para ayudar a buscar y seleccionar socios por nombre o ID
 */
public class MemberSearchHelper {

    /**
     * Permite al usuario buscar un socio por nombre o ID de forma interactiva
     */
    public static Member searchAndSelectMember(MemberRepository memberRepository, String prompt) {
        while (true) {
            String searchTerm = InputHelper.leerTextoObligatorio(prompt + " (nombre o ID): ");

            // Intentar buscar por ID exacto primero
            Member memberById = memberRepository.findById(searchTerm);
            if (memberById != null) {
                DisplayHelper.printSuccess("Socio encontrado por ID: " + memberById.getName());
                return memberById;
            }

            // Buscar por nombre
            List<Member> membersByName = memberRepository.searchByName(searchTerm);

            if (membersByName.isEmpty()) {
                DisplayHelper.printErrorMessage("No se encontraron socios con el término: " + searchTerm);

                if (!InputHelper.confirmar("¿Desea intentar otra búsqueda?")) {
                    return null;
                }
                continue;
            }

            if (membersByName.size() == 1) {
                Member member = membersByName.get(0);
                DisplayHelper.printSuccess("Socio encontrado: " + member.getName() + " (ID: " + member.getId() + ")");
                return member;
            }

            // Múltiples resultados - mostrar opciones
            DisplayHelper.printInfo(String.format("Se encontraron %d socios:", membersByName.size()));
            System.out.println();

            for (int i = 0; i < membersByName.size(); i++) {
                Member member = membersByName.get(i);
                System.out.printf("%d. %s (ID: %s) - %s%n",
                        i + 1,
                        member.getName(),
                        member.getId(),
                        member.getType().getDisplayName());
            }

            System.out.println((membersByName.size() + 1) + ". Buscar otro término");

            int selection = InputHelper.leerEnteroEnRango(
                    "Seleccione un socio: ", 1, membersByName.size() + 1);

            if (selection <= membersByName.size()) {
                return membersByName.get(selection - 1);
            }
            // Si selecciona "Buscar otro término", continúa el bucle
        }
    }

    /**
     * Muestra información resumida de un socio
     */
    public static void displayMemberSummary(Member member) {
        if (member == null) {
            DisplayHelper.printErrorMessage("Socio no encontrado");
            return;
        }

        System.out.println("-".repeat(50));
        System.out.printf("ID: %s%n", member.getId());
        System.out.printf("Nombre: %s%n", member.getName());
        System.out.printf("Email: %s%n", member.getEmail());
        System.out.printf("Tipo: %s%n", member.getType().getDisplayName());
        System.out.printf("Estado: %s%n", member.getState().getDisplayName());
        if (member.hasPendingFines()) {
            System.out.printf("Multas pendientes: $%.2f%n", member.getPendingFines());
        }
        System.out.println("-".repeat(50));
    }
}