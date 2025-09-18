package biblioteca.application.libros.registrar;

import biblioteca.data.database.AuthorRepository;
import biblioteca.data.database.BookRepository;
import biblioteca.data.database.CategoryRepository;
import biblioteca.data.database.PublisherRepository;
import biblioteca.domain.entities.Author;
import biblioteca.domain.entities.Book;
import biblioteca.domain.entities.Category;
import biblioteca.domain.entities.Publisher;

/**
 * Use case for registering a new book in the library
 */
public class RegisterBookUseCase {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public RegisterBookUseCase(BookRepository bookRepository,
            AuthorRepository authorRepository,
            CategoryRepository categoryRepository,
            PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }

    /**
     * Executes the register book use case
     * 
     * @param request The book registration data
     * @return The operation result
     */
    public RegisterBookResult execute(RegisterBookRequest request) {
        // Validate basic data
        if (request == null) {
            return RegisterBookResult.error("Book data is required");
        }

        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            return RegisterBookResult.error("Book title is required");
        }

        if (request.getYear() <= 0) {
            return RegisterBookResult.error("Year must be a positive number");
        }

        // Validate title uniqueness
        if (bookRepository.existsByTitle(request.getTitle().trim())) {
            return RegisterBookResult.error("A book with title '" + request.getTitle() + "' already exists");
        }

        // Validate author exists
        if (!authorRepository.existsById(request.getAuthorId())) {
            return RegisterBookResult.error("Author with ID " + request.getAuthorId() + " does not exist");
        }

        // Validate category exists
        if (!categoryRepository.existsById(request.getCategoryId())) {
            return RegisterBookResult.error("Category with ID " + request.getCategoryId() + " does not exist");
        }

        // Validate publisher exists
        if (!publisherRepository.existsById(request.getPublisherId())) {
            return RegisterBookResult.error("Publisher with ID " + request.getPublisherId() + " does not exist");
        }

        // Retrieve related entities
        Author author = authorRepository.findById(request.getAuthorId());
        Category category = categoryRepository.findById(request.getCategoryId());
        Publisher publisher = publisherRepository.findById(request.getPublisherId());

        // Create new book
        Book newBook = new Book(
                0, // ID will be assigned by repository
                request.getTitle().trim(),
                author,
                category,
                publisher,
                request.getYear());

        // Save book
        try {
            Book savedBook = bookRepository.save(newBook);
            return RegisterBookResult.success(savedBook);
        } catch (Exception e) {
            return RegisterBookResult.error("Error saving book: " + e.getMessage());
        }
    }
}