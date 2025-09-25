package biblioteca.application.libros.ingresar_stock;

import java.util.List;

import biblioteca.domain.entities.Copy;

/**
 * Result of adding stock to a book
 */
public class AddStockResult {
    public static AddStockResult success(List<Copy> createdCopies) {
        return new AddStockResult(true, "Stock añadido exitosamente", createdCopies);
    }

    public static AddStockResult failure(String message) {
        return new AddStockResult(false, message, null);
    }

    private final boolean success;

    private final String message;

    private final List<Copy> createdCopies;

    private AddStockResult(boolean success, String message, List<Copy> createdCopies) {
        this.success = success;
        this.message = message;
        this.createdCopies = createdCopies;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Copy> getCreatedCopies() {
        return createdCopies;
    }
}