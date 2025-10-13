package biblioteca.application.configuracion.ver;

import biblioteca.domain.entities.SystemParameters;

/**
 * Result for viewing system configuration
 * Contains the current system parameters
 */
public class ViewConfigResult {
    private final SystemParameters parameters;
    private final boolean success;

    public ViewConfigResult(SystemParameters parameters, boolean success) {
        this.parameters = parameters;
        this.success = success;
    }

    public SystemParameters getParameters() {
        return parameters;
    }

    public boolean isSuccess() {
        return success;
    }
}
