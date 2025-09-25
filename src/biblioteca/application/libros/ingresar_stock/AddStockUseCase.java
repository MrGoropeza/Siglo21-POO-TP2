package biblioteca.application.libros.ingresar_stock;

import java.util.List;

import biblioteca.data.database.BookRepository;
import biblioteca.data.database.CopyRepository;
import biblioteca.domain.entities.Book;
import biblioteca.domain.entities.Copy;

/**
 * Use case for adding stock (copies) to a book
 */
public class AddStockUseCase {
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;

    public AddStockUseCase(BookRepository bookRepository, CopyRepository copyRepository) {
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    public AddStockResult execute(AddStockRequest request) {
        // Validate request
        if (request.getQuantity() <= 0) {
            return AddStockResult.failure("La cantidad debe ser mayor a 0");
        }

        if (request.getQuantity() > 100) {
            return AddStockResult.failure("No se puede añadir más de 100 ejemplares a la vez");
        }

        // Find the book
        Book book = bookRepository.findById(request.getBookId());
        if (book == null) {
            return AddStockResult.failure("No se encontró el libro con ID: " + request.getBookId());
        }

        try {
            // Create the copies
            List<Copy> createdCopies = copyRepository.createCopies(
                    book,
                    request.getQuantity(),
                    request.getOrigin());

            return AddStockResult.success(createdCopies);

        } catch (Exception e) {
            return AddStockResult.failure("Error al crear los ejemplares: " + e.getMessage());
        }
    }
}