package biblioteca.application.libros.eliminar;

import java.util.List;

import biblioteca.data.database.BookRepository;
import biblioteca.data.database.CopyRepository;
import biblioteca.domain.entities.Book;
import biblioteca.domain.entities.Copy;
import biblioteca.domain.enums.CopyState;

/**
 * Use case for deleting a book and its copies
 */
public class DeleteBookUseCase {
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;

    public DeleteBookUseCase(BookRepository bookRepository, CopyRepository copyRepository) {
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    public DeleteBookResult execute(DeleteBookRequest request) {
        // Find the book
        Book book = bookRepository.findById(request.getBookId());
        if (book == null) {
            return DeleteBookResult.failure("No se encontró el libro con ID: " + request.getBookId());
        }

        // Check if book has loaned copies
        List<Copy> copies = copyRepository.findByBook(book);
        long loanedCopies = copies.stream()
                .filter(copy -> copy.getState() == CopyState.LOANED)
                .count();

        if (loanedCopies > 0) {
            return DeleteBookResult.failure(
                    String.format("No se puede eliminar el libro porque tiene %d ejemplares prestados", loanedCopies));
        }

        try {
            // Delete all copies first
            int deletedCopiesCount = copyRepository.deleteByBook(book);

            // Delete the book
            boolean bookDeleted = bookRepository.deleteById(book.getId());

            if (bookDeleted) {
                return DeleteBookResult.success(book, deletedCopiesCount);
            } else {
                return DeleteBookResult.failure("Error al eliminar el libro del repositorio");
            }

        } catch (Exception e) {
            return DeleteBookResult.failure("Error al eliminar el libro: " + e.getMessage());
        }
    }
}