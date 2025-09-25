package biblioteca.application.socios.pagar_multa;

/**
 * Result object for fine payment operation
 */
public class PayFineResult {
    public static PayFineResult success(int finesPaid, double totalAmount) {
        String message = String.format("Pago registrado exitosamente: %d multa(s) por $%.2f", finesPaid, totalAmount);
        return new PayFineResult(true, message, finesPaid, totalAmount);
    }

    public static PayFineResult failure(String message) {
        return new PayFineResult(false, message, 0, 0.0);
    }

    private final boolean success;
    private final String message;

    private final int finesPaid;

    private final double totalAmount;

    private PayFineResult(boolean success, String message, int finesPaid, double totalAmount) {
        this.success = success;
        this.message = message;
        this.finesPaid = finesPaid;
        this.totalAmount = totalAmount;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getFinesPaid() {
        return finesPaid;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}