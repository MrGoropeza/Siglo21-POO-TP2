package biblioteca.application.libros.eliminar;

import biblioteca.domain.entities.Book;

/**
 * Result of deleting a book
 */
public class DeleteBookResult {
    public static DeleteBookResult success(Book deletedBook, int deletedCopiesCount) {
        String message = String.format("Libro eliminado exitosamente junto con %d ejemplares", deletedCopiesCount);
        return new DeleteBookResult(true, message, deletedBook, deletedCopiesCount);
    }

    public static DeleteBookResult failure(String message) {
        return new DeleteBookResult(false, message, null, 0);
    }

    private final boolean success;
    private final String message;

    private final Book deletedBook;

    private final int deletedCopiesCount;

    private DeleteBookResult(boolean success, String message, Book deletedBook, int deletedCopiesCount) {
        this.success = success;
        this.message = message;
        this.deletedBook = deletedBook;
        this.deletedCopiesCount = deletedCopiesCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Book getDeletedBook() {
        return deletedBook;
    }

    public int getDeletedCopiesCount() {
        return deletedCopiesCount;
    }
}