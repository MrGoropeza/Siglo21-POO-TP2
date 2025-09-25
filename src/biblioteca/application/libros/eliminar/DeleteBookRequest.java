package biblioteca.application.libros.eliminar;

/**
 * Request for deleting a book
 */
public class DeleteBookRequest {
    private final int bookId;

    public DeleteBookRequest(int bookId) {
        this.bookId = bookId;
    }

    public int getBookId() {
        return bookId;
    }
}