package biblioteca.console.forms;

import biblioteca.application.socios.registrar.RegisterMemberRequest;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.domain.enums.MemberType;

/**
 * Form for capturing member registration data
 */
public class RegisterMemberForm {

    /**
     * Captures data for registering a new member
     * 
     * @return RegisterMemberRequest with captured data, null if cancelled
     */
    public RegisterMemberRequest captureData() {
        try {
            DisplayHelper.renderSubtitle("Registrar Nuevo Socio");

            // Capture member ID (optional)
            String id = InputHelper.leerTexto("ID socio (Enter para auto-generar)");
            if (id != null && id.trim().isEmpty()) {
                id = null; // Let the use case generate it
            }

            // Capture name
            String name = InputHelper.leerTextoObligatorio("Nombre y apellido");

            // Capture email
            String email = InputHelper.leerTextoObligatorio("Email");

            // Capture phone
            String phone = InputHelper.leerTextoObligatorio("Teléfono");

            // Capture member type
            System.out.println("\nSeleccione la categoría del socio:");
            System.out.println("1. " + MemberType.STANDARD.getDisplayName() + " (sin beneficios)");
            System.out.println("2. " + MemberType.STUDENT.getDisplayName() + " (50% descuento en multas)");
            System.out.println("3. " + MemberType.RETIRED.getDisplayName() + " (+3 días al plazo de préstamo)");

            int typeOption = InputHelper.leerEnteroEnRango("Seleccione una opción", 1, 3);
            MemberType type = switch (typeOption) {
                case 1 -> MemberType.STANDARD;
                case 2 -> MemberType.STUDENT;
                case 3 -> MemberType.RETIRED;
                default -> MemberType.STANDARD;
            };

            // Show confirmation
            System.out.println("\n=== CONFIRMACIÓN DE DATOS ===");
            System.out.println("ID: " + (id != null ? id : "(auto-generado)"));
            System.out.println("Nombre: " + name);
            System.out.println("Email: " + email);
            System.out.println("Teléfono: " + phone);
            System.out.println("Categoría: " + type.getDisplayName());
            System.out.println("=============================");

            if (InputHelper.confirmar("¿Está seguro que desea registrar este socio?")) {
                return new RegisterMemberRequest(id, name, email, phone, type);
            } else {
                DisplayHelper.printInfo("Operación cancelada");
                return null;
            }

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al capturar datos: " + e.getMessage());
            return null;
        }
    }
}