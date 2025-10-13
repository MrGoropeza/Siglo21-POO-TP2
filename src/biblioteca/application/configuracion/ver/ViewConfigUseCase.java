package biblioteca.application.configuracion.ver;

import biblioteca.data.database.SystemParametersRepository;
import biblioteca.domain.entities.SystemParameters;

/**
 * Use case for viewing system configuration
 */
public class ViewConfigUseCase {
    private final SystemParametersRepository systemParametersRepository;

    public ViewConfigUseCase(SystemParametersRepository systemParametersRepository) {
        this.systemParametersRepository = systemParametersRepository;
    }

    public ViewConfigResult execute(ViewConfigRequest request) {
        SystemParameters parameters = systemParametersRepository.get();
        return new ViewConfigResult(parameters, true);
    }
}
