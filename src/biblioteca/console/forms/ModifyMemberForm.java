package biblioteca.console.forms;

import java.util.List;

import biblioteca.application.socios.modificar.ModifyMemberRequest;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Member;
import biblioteca.domain.enums.MemberType;

/**
 * Form for capturing member modification data
 */
public class ModifyMemberForm {
    private final MemberRepository memberRepository;

    public ModifyMemberForm(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Captures data for modifying a member
     * 
     * @return ModifyMemberRequest with captured data, null if cancelled
     */
    public ModifyMemberRequest captureData() {
        try {
            DisplayHelper.renderSubtitle("Modificar Socio");

            // Search for member
            Member selectedMember = searchAndSelectMember();
            if (selectedMember == null) {
                return null;
            }

            // Display current member information
            DisplayHelper.printSuccess("Socio seleccionado:");
            System.out.println(selectedMember.toDetailedString());

            // Capture new data (ENTER to keep current)
            System.out.println("\nIngrese los nuevos datos (ENTER para mantener actual):");

            String newEmail = InputHelper.leerTexto("Nuevo email (actual: " + selectedMember.getEmail() + ")");
            if (newEmail != null && newEmail.trim().isEmpty()) {
                newEmail = null; // Keep current
            }

            String newPhone = InputHelper.leerTexto("Nuevo teléfono (actual: " + selectedMember.getPhone() + ")");
            if (newPhone != null && newPhone.trim().isEmpty()) {
                newPhone = null; // Keep current
            }

            // Capture new member type
            System.out.println("\nCategoría actual: " + selectedMember.getType().getDisplayName());
            System.out.println("Seleccione nueva categoría (ENTER para mantener actual):");
            System.out.println("1. " + MemberType.STANDARD.getDisplayName());
            System.out.println("2. " + MemberType.STUDENT.getDisplayName());
            System.out.println("3. " + MemberType.RETIRED.getDisplayName());

            String typeInput = InputHelper.leerTexto("Seleccione opción (1-3)");
            MemberType newType = null;
            if (typeInput != null && !typeInput.trim().isEmpty()) {
                try {
                    int typeOption = Integer.parseInt(typeInput.trim());
                    newType = switch (typeOption) {
                        case 1 -> MemberType.STANDARD;
                        case 2 -> MemberType.STUDENT;
                        case 3 -> MemberType.RETIRED;
                        default -> null;
                    };
                } catch (NumberFormatException e) {
                    DisplayHelper.printWarning("Opción inválida, se mantendrá la categoría actual");
                }
            }

            // Check if any changes were made
            if (newEmail == null && newPhone == null && newType == null) {
                DisplayHelper.printInfo("No se realizaron cambios");
                return null;
            }

            // Show confirmation
            System.out.println("\n=== CONFIRMACIÓN DE CAMBIOS ===");
            System.out.println("Socio: " + selectedMember.getName() + " (ID: " + selectedMember.getId() + ")");
            if (newEmail != null) {
                System.out.println("Email: " + selectedMember.getEmail() + " → " + newEmail);
            }
            if (newPhone != null) {
                System.out.println("Teléfono: " + selectedMember.getPhone() + " → " + newPhone);
            }
            if (newType != null) {
                System.out.println(
                        "Categoría: " + selectedMember.getType().getDisplayName() + " → " + newType.getDisplayName());
            }
            System.out.println("===============================");

            if (InputHelper.confirmar("¿Confirma modificar el socio?")) {
                return new ModifyMemberRequest(selectedMember.getId(), newEmail, newPhone, newType);
            } else {
                DisplayHelper.printInfo("Operación cancelada");
                return null;
            }

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al capturar datos: " + e.getMessage());
            return null;
        }
    }

    private Member searchAndSelectMember() {
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por Nombre");
        int searchOption = InputHelper.leerEnteroEnRango("Seleccione método de búsqueda", 1, 2);

        if (searchOption == 1) {
            // Search by ID
            String memberId = InputHelper.leerTextoObligatorio("Ingrese ID del socio");
            Member member = memberRepository.findById(memberId);

            if (member == null) {
                DisplayHelper.printWarning("No se encontró socio con ID: " + memberId);
                return null;
            }
            return member;

        } else {
            // Search by name
            String searchQuery = InputHelper.leerTextoObligatorio("Ingrese el nombre del socio");
            List<Member> foundMembers = memberRepository.searchByName(searchQuery);

            if (foundMembers.isEmpty()) {
                DisplayHelper.printWarning("No se encontraron socios que contengan: \"" + searchQuery + "\"");
                return null;
            }

            if (foundMembers.size() == 1) {
                return foundMembers.get(0);
            } else {
                return InputHelper.seleccionar(foundMembers, "Seleccione el socio a modificar");
            }
        }
    }
}