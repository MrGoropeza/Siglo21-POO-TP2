package biblioteca.console.controllers;

import java.util.List;

import biblioteca.application.configuracion.actualizar.UpdateConfigRequest;
import biblioteca.application.configuracion.actualizar.UpdateConfigResult;
import biblioteca.application.configuracion.actualizar.UpdateConfigUseCase;
import biblioteca.application.configuracion.ver.ViewConfigRequest;
import biblioteca.application.configuracion.ver.ViewConfigResult;
import biblioteca.application.configuracion.ver.ViewConfigUseCase;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.domain.entities.SystemParameters;
import biblioteca.domain.enums.MemberType;

/**
 * Controller for system configuration management
 */
public class ConfigController {
    private final ViewConfigUseCase viewConfigUseCase;
    private final UpdateConfigUseCase updateConfigUseCase;

    public ConfigController(ViewConfigUseCase viewConfigUseCase, UpdateConfigUseCase updateConfigUseCase) {
        this.viewConfigUseCase = viewConfigUseCase;
        this.updateConfigUseCase = updateConfigUseCase;
    }

    public void run() {
        boolean running = true;

        while (running) {
            DisplayHelper.renderTitle("Configuración del Sistema");

            List<String> options = List.of(
                    "Días estándar de préstamo",
                    "Multa diaria",
                    "Cupo máximo por socio",
                    "Tope de reservas activas por socio",
                    "Beneficios por categoría",
                    "Volver");

            String selection = InputHelper.seleccionar(options, "Seleccione una opción");
            int optionIndex = options.indexOf(selection);

            switch (optionIndex) {
                case 0:
                    updateLoanDays();
                    break;
                case 1:
                    updateFinePerDay();
                    break;
                case 2:
                    updateMaxLoans();
                    break;
                case 3:
                    updateMaxReservations();
                    break;
                case 4:
                    showBenefits();
                    break;
                case 5:
                    running = false;
                    break;
            }
        }
    }

    private void updateLoanDays() {
        DisplayHelper.renderSubtitle("Días Estándar de Préstamo");

        ViewConfigResult result = viewConfigUseCase.execute(new ViewConfigRequest());
        SystemParameters params = result.getParameters();

        System.out.println("Valor actual: " + params.getLoanDays() + " días");
        int newValue = InputHelper.leerEntero("Ingrese nuevo valor: ");

        UpdateConfigRequest request = new UpdateConfigRequest();
        request.setLoanDays(newValue);

        UpdateConfigResult updateResult = updateConfigUseCase.execute(request);
        if (updateResult.isSuccess()) {
            DisplayHelper.printSuccess("✓ Actualizado");
        } else {
            DisplayHelper.printErrorMessage(updateResult.getMessage());
        }

        InputHelper.pausar();
    }

    private void updateFinePerDay() {
        DisplayHelper.renderSubtitle("Multa Diaria");

        ViewConfigResult result = viewConfigUseCase.execute(new ViewConfigRequest());
        SystemParameters params = result.getParameters();

        System.out.println("Valor actual: $" + String.format("%.2f", params.getFinePerDay()));
        String input = InputHelper.leerTextoObligatorio("Ingrese nuevo valor: ");

        try {
            double newValue = Double.parseDouble(input);
            UpdateConfigRequest request = new UpdateConfigRequest();
            request.setFinePerDay(newValue);

            UpdateConfigResult updateResult = updateConfigUseCase.execute(request);
            if (updateResult.isSuccess()) {
                DisplayHelper.printSuccess("✓ Actualizado");
            } else {
                DisplayHelper.printErrorMessage(updateResult.getMessage());
            }
        } catch (NumberFormatException e) {
            DisplayHelper.printErrorMessage("Valor inválido. Debe ser un número.");
        }

        InputHelper.pausar();
    }

    private void updateMaxLoans() {
        DisplayHelper.renderSubtitle("Cupo Máximo por Socio");

        ViewConfigResult result = viewConfigUseCase.execute(new ViewConfigRequest());
        SystemParameters params = result.getParameters();

        System.out.println("Valor actual: " + params.getMaxLoansPerMember() + " préstamos");
        int newValue = InputHelper.leerEntero("Ingrese nuevo valor: ");

        UpdateConfigRequest request = new UpdateConfigRequest();
        request.setMaxLoansPerMember(newValue);

        UpdateConfigResult updateResult = updateConfigUseCase.execute(request);
        if (updateResult.isSuccess()) {
            DisplayHelper.printSuccess("✓ Actualizado");
        } else {
            DisplayHelper.printErrorMessage(updateResult.getMessage());
        }

        InputHelper.pausar();
    }

    private void updateMaxReservations() {
        DisplayHelper.renderSubtitle("Tope de Reservas Activas por Socio");

        ViewConfigResult result = viewConfigUseCase.execute(new ViewConfigRequest());
        SystemParameters params = result.getParameters();

        System.out.println("Valor actual: " + params.getMaxActiveReservationsPerMember() + " reservas");
        int newValue = InputHelper.leerEntero("Ingrese nuevo valor: ");

        UpdateConfigRequest request = new UpdateConfigRequest();
        request.setMaxActiveReservationsPerMember(newValue);

        UpdateConfigResult updateResult = updateConfigUseCase.execute(request);
        if (updateResult.isSuccess()) {
            DisplayHelper.printSuccess("✓ Actualizado");
        } else {
            DisplayHelper.printErrorMessage(updateResult.getMessage());
        }

        InputHelper.pausar();
    }

    private void showBenefits() {
        DisplayHelper.renderSubtitle("Beneficios por Categoría");

        System.out.println("\n📋 BENEFICIOS ACTUALES POR TIPO DE SOCIO:\n");

        // Standard
        System.out.println("🔹 " + MemberType.STANDARD.getDisplayName() + ":");
        System.out.println("   • Sin beneficios adicionales");
        System.out.println("   • Descuento en multas: 0%");
        System.out.println("   • Días extra de préstamo: 0\n");

        // Student
        System.out.println("🔹 " + MemberType.STUDENT.getDisplayName() + ":");
        System.out.println("   • Descuento en multas: " +
                (int) (MemberType.STUDENT.getFineDiscountPercentage() * 100) + "%");
        System.out.println("   • Días extra de préstamo: " + MemberType.STUDENT.getExtraLoanDays() + "\n");

        // Retired
        System.out.println("🔹 " + MemberType.RETIRED.getDisplayName() + ":");
        System.out.println("   • Descuento en multas: " +
                (int) (MemberType.RETIRED.getFineDiscountPercentage() * 100) + "%");
        System.out.println("   • Días extra de préstamo: " + MemberType.RETIRED.getExtraLoanDays() + "\n");

        DisplayHelper.printInfo("ℹ️  Los beneficios están definidos en el código (enum MemberType)");
        DisplayHelper.printInfo("ℹ️  No son modificables desde la configuración del sistema");

        InputHelper.pausar();
    }
}
