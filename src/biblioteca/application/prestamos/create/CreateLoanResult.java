package biblioteca.application.prestamos.create;

/**
 * Resultado de operaciones de préstamo.
 */
public class CreateLoanResult {
    public static CreateLoanResult success(String message) {
        return new CreateLoanResult(true, message, null);
    }

    public static CreateLoanResult success(String message, Object data) {
        return new CreateLoanResult(true, message, data);
    }

    public static CreateLoanResult error(String message) {
        return new CreateLoanResult(false, message, null);
    }

    private boolean success;

    private String message;

    private Object data;

    private CreateLoanResult(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    @SuppressWarnings("unchecked")
    public <T> T getData(Class<T> type) {
        return (T) data;
    }
}