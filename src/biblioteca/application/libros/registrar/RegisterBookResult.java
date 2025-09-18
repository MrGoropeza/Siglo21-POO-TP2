package biblioteca.application.libros.registrar;

import biblioteca.domain.entities.Book;

/**
 * Result DTO for book registration operation
 */
public class RegisterBookResult {
    /**
     * Creates a successful result
     * 
     * @param book The registered book
     * @return Success result
     */
    public static RegisterBookResult success(Book book) {
        return new RegisterBookResult(true, "Book registered successfully", book);
    }

    /**
     * Creates an error result
     * 
     * @param message The error message
     * @return Error result
     */
    public static RegisterBookResult error(String message) {
        return new RegisterBookResult(false, message, null);
    }

    private boolean success;

    private String message;

    private Book book;

    private RegisterBookResult(boolean success, String message, Book book) {
        this.success = success;
        this.message = message;
        this.book = book;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Book getBook() {
        return book;
    }
}