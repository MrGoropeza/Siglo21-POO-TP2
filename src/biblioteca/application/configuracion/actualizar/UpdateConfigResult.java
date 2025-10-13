package biblioteca.application.configuracion.actualizar;

/**
 * Result for updating system configuration
 */
public class UpdateConfigResult {
    private final boolean success;
    private final String message;

    public UpdateConfigResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
