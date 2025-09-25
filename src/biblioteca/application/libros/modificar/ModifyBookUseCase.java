package biblioteca.application.libros.modificar;

import biblioteca.data.database.BookRepository;
import biblioteca.domain.entities.Book;

/**
 * Use case for modifying a book
 */
public class ModifyBookUseCase {
    private final BookRepository bookRepository;

    public ModifyBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ModifyBookResult execute(ModifyBookRequest request) {
        // Find the book
        Book book = bookRepository.findById(request.getBookId());
        if (book == null) {
            return ModifyBookResult.failure("No se encontró el libro con ID: " + request.getBookId());
        }

        boolean hasChanges = false;

        // Update title if provided
        if (request.hasTitleChange()) {
            String trimmedTitle = request.getNewTitle().trim();
            if (trimmedTitle.length() < 2) {
                return ModifyBookResult.failure("El título debe tener al menos 2 caracteres");
            }
            book.setTitle(trimmedTitle);
            hasChanges = true;
        }

        // Update year if provided
        if (request.hasYearChange()) {
            int newYear = request.getNewYear();
            if (newYear < 1000 || newYear > 2030) {
                return ModifyBookResult.failure("El año debe estar entre 1000 y 2030");
            }
            book.setYear(newYear);
            hasChanges = true;
        }

        if (!hasChanges) {
            return ModifyBookResult.failure("No se especificaron cambios para realizar");
        }

        try {
            Book updatedBook = bookRepository.update(book);
            if (updatedBook != null) {
                return ModifyBookResult.success(updatedBook);
            } else {
                return ModifyBookResult.failure("Error al actualizar el libro en el repositorio");
            }
        } catch (Exception e) {
            return ModifyBookResult.failure("Error al modificar el libro: " + e.getMessage());
        }
    }
}