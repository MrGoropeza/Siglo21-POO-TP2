package biblioteca.console.ioc;

import biblioteca.application.libros.eliminar.DeleteBookUseCase;
import biblioteca.application.libros.ingresar_stock.AddStockUseCase;
import biblioteca.application.libros.modificar.ModifyBookUseCase;
import biblioteca.application.libros.registrar.RegisterBookUseCase;
import biblioteca.console.controllers.BookController;
import biblioteca.console.controllers.MainController;
import biblioteca.console.forms.AddStockForm;
import biblioteca.console.forms.DeleteBookForm;
import biblioteca.console.forms.FindBookForm;
import biblioteca.console.forms.ModifyBookForm;
import biblioteca.console.forms.RegisterBookForm;
import biblioteca.data.database.AuthorRepository;
import biblioteca.data.database.BookRepository;
import biblioteca.data.database.CategoryRepository;
import biblioteca.data.database.CopyRepository;
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
    private CopyRepository copyRepository;

    private RegisterBookUseCase registerBookUseCase;
    private AddStockUseCase addStockUseCase;
    private ModifyBookUseCase modifyBookUseCase;
    private DeleteBookUseCase deleteBookUseCase;

    private RegisterBookForm registerBookForm;
    private AddStockForm addStockForm;
    private ModifyBookForm modifyBookForm;
    private DeleteBookForm deleteBookForm;
    private FindBookForm findBookForm;

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

    public CopyRepository getCopyRepository() {
        return copyRepository;
    }

    public RegisterBookUseCase getRegisterBookUseCase() {
        return registerBookUseCase;
    }

    public AddStockUseCase getAddStockUseCase() {
        return addStockUseCase;
    }

    public ModifyBookUseCase getModifyBookUseCase() {
        return modifyBookUseCase;
    }

    public DeleteBookUseCase getDeleteBookUseCase() {
        return deleteBookUseCase;
    }

    public RegisterBookForm getRegisterBookForm() {
        return registerBookForm;
    }

    public AddStockForm getAddStockForm() {
        return addStockForm;
    }

    public ModifyBookForm getModifyBookForm() {
        return modifyBookForm;
    }

    public DeleteBookForm getDeleteBookForm() {
        return deleteBookForm;
    }

    public FindBookForm getFindBookForm() {
        return findBookForm;
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
        copyRepository = new CopyRepository();

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

        addStockUseCase = new AddStockUseCase(
                bookRepository,
                copyRepository);

        modifyBookUseCase = new ModifyBookUseCase(
                bookRepository);

        deleteBookUseCase = new DeleteBookUseCase(
                bookRepository,
                copyRepository);
    }

    private void initializeForms() {
        registerBookForm = new RegisterBookForm(
                authorRepository,
                categoryRepository,
                publisherRepository);

        addStockForm = new AddStockForm(
                bookRepository);

        modifyBookForm = new ModifyBookForm(
                bookRepository);

        deleteBookForm = new DeleteBookForm(
                bookRepository,
                copyRepository);

        findBookForm = new FindBookForm(
                bookRepository,
                copyRepository);
    }

    private void initializeControllers() {
        bookController = new BookController(
                registerBookUseCase,
                addStockUseCase,
                modifyBookUseCase,
                deleteBookUseCase,
                registerBookForm,
                addStockForm,
                modifyBookForm,
                deleteBookForm,
                findBookForm);

        mainController = new MainController(
                bookController,
                bookRepository,
                authorRepository,
                categoryRepository,
                publisherRepository);
    }
}