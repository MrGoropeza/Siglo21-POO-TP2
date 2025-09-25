package biblioteca.application.libros.ingresar_stock;

import biblioteca.domain.entities.CopyOrigin;

/**
 * Request for adding stock to a book
 */
public class AddStockRequest {
    private final int bookId;
    private final int quantity;
    private final CopyOrigin origin;

    public AddStockRequest(int bookId, int quantity, CopyOrigin origin) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.origin = origin;
    }

    public int getBookId() {
        return bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public CopyOrigin getOrigin() {
        return origin;
    }
}