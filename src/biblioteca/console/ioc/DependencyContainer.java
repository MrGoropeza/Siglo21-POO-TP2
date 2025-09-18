package biblioteca.console.ioc;

import biblioteca.application.libros.registrar.RegisterBookUseCase;
import biblioteca.console.controllers.BookController;
import biblioteca.console.controllers.MainController;
import biblioteca.console.forms.RegisterBookForm;
import biblioteca.data.database.AuthorRepository;
import biblioteca.data.database.BookRepository;
import biblioteca.data.database.CategoryRepository;
import biblioteca.data.database.PublisherRepository;
import biblioteca.data.dummy.AuthorDummyData;
import biblioteca.data.dummy.BookDummyData;
import biblioteca.data.dummy.CategoryDummyData;
import biblioteca.data.dummy.PublisherDummyData;

/**
 * Dependency injection container for initializing all application components
 */
public class DependencyContainer {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;
    private PublisherRepository publisherRepository;

    private RegisterBookUseCase registerBookUseCase;

    private RegisterBookForm registerBookForm;

    private BookController bookController;
    private MainController mainController;

    /**
     * Initializes all dependencies in the correct order
     */
    public void initialize() {
        initializeRepositories();
        initializeUseCases();
        initializeForms();
        initializeControllers();
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public AuthorRepository getAuthorRepository() {
        return authorRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public PublisherRepository getPublisherRepository() {
        return publisherRepository;
    }

    public RegisterBookUseCase getRegisterBookUseCase() {
        return registerBookUseCase;
    }

    public RegisterBookForm getRegisterBookForm() {
        return registerBookForm;
    }

    public BookController getBookController() {
        return bookController;
    }

    public MainController getMainController() {
        return mainController;
    }

    private void initializeRepositories() {
        bookRepository = new BookRepository();
        authorRepository = new AuthorRepository();
        categoryRepository = new CategoryRepository();
        publisherRepository = new PublisherRepository();

        authorRepository.loadDummyData(AuthorDummyData.getAuthors());
        categoryRepository.loadDummyData(CategoryDummyData.getCategories());
        publisherRepository.loadDummyData(PublisherDummyData.getPublishers());

        bookRepository.loadDummyData(BookDummyData.getBooks(
                authorRepository.findAll(),
                categoryRepository.findAll(),
                publisherRepository.findAll()));
    }

    private void initializeUseCases() {
        registerBookUseCase = new RegisterBookUseCase(
                bookRepository,
                authorRepository,
                categoryRepository,
                publisherRepository);
    }

    private void initializeForms() {
        registerBookForm = new RegisterBookForm(
                authorRepository,
                categoryRepository,
                publisherRepository);
    }

    private void initializeControllers() {
        bookController = new BookController(
                bookRepository,
                registerBookUseCase,
                registerBookForm);

        mainController = new MainController(
                bookController,
                bookRepository,
                authorRepository,
                categoryRepository,
                publisherRepository);
    }
}