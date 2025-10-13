package biblioteca.application.devoluciones.registrar;

/**
 * Request for registering a book return
 */
public class RegisterReturnRequest {
    private final String copyCode;

    public RegisterReturnRequest(String copyCode) {
        this.copyCode = copyCode;
    }

    public String getCopyCode() {
        return copyCode;
    }
}
