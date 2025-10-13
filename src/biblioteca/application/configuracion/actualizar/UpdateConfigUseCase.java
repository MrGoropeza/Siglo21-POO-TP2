package biblioteca.application.configuracion.actualizar;

import biblioteca.data.database.SystemParametersRepository;

/**
 * Use case for updating system configuration
 * Validates and applies individual parameter updates
 */
public class UpdateConfigUseCase {
    private final SystemParametersRepository systemParametersRepository;

    public UpdateConfigUseCase(SystemParametersRepository systemParametersRepository) {
        this.systemParametersRepository = systemParametersRepository;
    }

    public UpdateConfigResult execute(UpdateConfigRequest request) {
        try {
            // Update only non-null fields
            if (request.getLoanDays() != null) {
                if (request.getLoanDays() <= 0) {
                    return new UpdateConfigResult(false, "Los días de préstamo deben ser mayor a 0");
                }
                systemParametersRepository.updateLoanDays(request.getLoanDays());
            }

            if (request.getFinePerDay() != null) {
                if (request.getFinePerDay() < 0) {
                    return new UpdateConfigResult(false, "La multa diaria no puede ser negativa");
                }
                systemParametersRepository.updateFinePerDay(request.getFinePerDay());
            }

            if (request.getMaxLoansPerMember() != null) {
                if (request.getMaxLoansPerMember() < 0) {
                    return new UpdateConfigResult(false, "El cupo máximo no puede ser negativo");
                }
                systemParametersRepository.updateMaxLoansPerMember(request.getMaxLoansPerMember());
            }

            if (request.getMaxActiveReservationsPerMember() != null) {
                if (request.getMaxActiveReservationsPerMember() < 0) {
                    return new UpdateConfigResult(false, "El tope de reservas no puede ser negativo");
                }
                systemParametersRepository
                        .updateMaxActiveReservationsPerMember(request.getMaxActiveReservationsPerMember());
            }

            return new UpdateConfigResult(true, "Configuración actualizada correctamente");

        } catch (Exception e) {
            return new UpdateConfigResult(false, "Error al actualizar configuración: " + e.getMessage());
        }
    }
}
