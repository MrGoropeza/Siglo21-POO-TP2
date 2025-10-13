package biblioteca.application.devoluciones.registrar;

import biblioteca.domain.entities.Fine;

/**
 * Result of registering a book return
 */
public class RegisterReturnResult {
    private final boolean success;
    private final String message;
    private final Fine fine; // null if no fine

    public RegisterReturnResult(boolean success, String message, Fine fine) {
        this.success = success;
        this.message = message;
        this.fine = fine;
    }

    public RegisterReturnResult(boolean success, String message) {
        this(success, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Fine getFine() {
        return fine;
    }

    public boolean hasFine() {
        return fine != null;
    }
}
