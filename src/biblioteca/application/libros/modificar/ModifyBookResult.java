package biblioteca.application.libros.modificar;

import biblioteca.domain.entities.Book;

/**
 * Result of modifying a book
 */
public class ModifyBookResult {
    public static ModifyBookResult success(Book book) {
        return new ModifyBookResult(true, "Libro modificado exitosamente", book);
    }

    public static ModifyBookResult failure(String message) {
        return new ModifyBookResult(false, message, null);
    }

    private final boolean success;

    private final String message;

    private final Book book;

    private ModifyBookResult(boolean success, String message, Book book) {
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