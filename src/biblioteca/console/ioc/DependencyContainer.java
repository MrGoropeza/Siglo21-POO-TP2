package biblioteca.console.ioc;

import biblioteca.application.libros.eliminar.DeleteBookUseCase;
import biblioteca.application.libros.ingresar_stock.AddStockUseCase;
import biblioteca.application.libros.modificar.ModifyBookUseCase;
import biblioteca.application.libros.registrar.RegisterBookUseCase;
import biblioteca.application.socios.consultar.QueryMemberUseCase;
import biblioteca.application.socios.modificar.ModifyMemberUseCase;
import biblioteca.application.socios.pagar_multa.PayFineUseCase;
import biblioteca.application.socios.registrar.RegisterMemberUseCase;
import biblioteca.console.controllers.BookController;
import biblioteca.console.controllers.MainController;
import biblioteca.console.controllers.MemberController;
import biblioteca.console.controllers.LoanController;
import biblioteca.application.prestamos.create.CreateLoanUseCase;
import biblioteca.console.forms.prestamos.LoanCartForm;
import biblioteca.data.LoanRepository;
import biblioteca.console.forms.AddStockForm;
import biblioteca.console.forms.DeleteBookForm;
import biblioteca.console.forms.FindBookForm;
import biblioteca.console.forms.FindMemberForm;
import biblioteca.console.forms.ModifyBookForm;
import biblioteca.console.forms.ModifyMemberForm;
import biblioteca.console.forms.PayFineForm;
import biblioteca.console.forms.RegisterBookForm;
import biblioteca.console.forms.RegisterMemberForm;
import biblioteca.data.database.AuthorRepository;
import biblioteca.data.database.BookRepository;
import biblioteca.data.database.CategoryRepository;
import biblioteca.data.database.CopyRepository;
import biblioteca.data.database.MemberRepository;
import biblioteca.data.database.PublisherRepository;
import biblioteca.data.dummy.AuthorDummyData;
import biblioteca.data.dummy.BookDummyData;
import biblioteca.data.dummy.CategoryDummyData;
import biblioteca.data.dummy.CopyDummyData;
import biblioteca.data.dummy.LoanDummyData;
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
    private MemberRepository memberRepository;
    private LoanRepository loanRepository;

    private RegisterBookUseCase registerBookUseCase;
    private AddStockUseCase addStockUseCase;
    private ModifyBookUseCase modifyBookUseCase;
    private DeleteBookUseCase deleteBookUseCase;
    private RegisterMemberUseCase registerMemberUseCase;
    private ModifyMemberUseCase modifyMemberUseCase;
    private QueryMemberUseCase queryMemberUseCase;
    private PayFineUseCase payFineUseCase;
    private CreateLoanUseCase createLoanUseCase;

    private RegisterBookForm registerBookForm;
    private AddStockForm addStockForm;
    private ModifyBookForm modifyBookForm;
    private DeleteBookForm deleteBookForm;
    private FindBookForm findBookForm;
    private RegisterMemberForm registerMemberForm;
    private ModifyMemberForm modifyMemberForm;
    private FindMemberForm findMemberForm;
    private PayFineForm payFineForm;
    private LoanCartForm loanCartForm;

    private BookController bookController;
    private MemberController memberController;
    private LoanController loanController;
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

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    public RegisterMemberUseCase getRegisterMemberUseCase() {
        return registerMemberUseCase;
    }

    public ModifyMemberUseCase getModifyMemberUseCase() {
        return modifyMemberUseCase;
    }

    public QueryMemberUseCase getQueryMemberUseCase() {
        return queryMemberUseCase;
    }

    public PayFineUseCase getPayFineUseCase() {
        return payFineUseCase;
    }

    public RegisterMemberForm getRegisterMemberForm() {
        return registerMemberForm;
    }

    public ModifyMemberForm getModifyMemberForm() {
        return modifyMemberForm;
    }

    public FindMemberForm getFindMemberForm() {
        return findMemberForm;
    }

    public PayFineForm getPayFineForm() {
        return payFineForm;
    }

    public MemberController getMemberController() {
        return memberController;
    }

    public MainController getMainController() {
        return mainController;
    }

    public LoanRepository getLoanRepository() {
        return loanRepository;
    }

    public CreateLoanUseCase getCreateLoanUseCase() {
        return createLoanUseCase;
    }

    public LoanCartForm getLoanCartForm() {
        return loanCartForm;
    }

    public LoanController getLoanController() {
        return loanController;
    }

    private void initializeRepositories() {
        bookRepository = new BookRepository();
        authorRepository = new AuthorRepository();
        categoryRepository = new CategoryRepository();
        publisherRepository = new PublisherRepository();
        copyRepository = new CopyRepository();
        memberRepository = new MemberRepository(); // Ya carga MemberDummyData en constructor
        loanRepository = new LoanRepository(); // Se cargarán datos dummy después

        // Cargar datos dummy de repositorios base
        authorRepository.loadDummyData(AuthorDummyData.getAuthors());
        categoryRepository.loadDummyData(CategoryDummyData.getCategories());
        publisherRepository.loadDummyData(PublisherDummyData.getPublishers());

        // Cargar datos dummy que dependen de otros repositorios
        bookRepository.loadDummyData(BookDummyData.getBooks(
                authorRepository.findAll(),
                categoryRepository.findAll(),
                publisherRepository.findAll()));

        // Cargar datos dummy de ejemplares (depende de que los libros estén cargados)
        copyRepository.loadDummyData(CopyDummyData.getCopies(bookRepository.findAll()));

        // Cargar datos dummy de préstamos (depende de miembros y ejemplares)
        loanRepository.loadDummyData(LoanDummyData.getLoans(
                memberRepository.findAll(),
                copyRepository.findAll()));
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

        registerMemberUseCase = new RegisterMemberUseCase(memberRepository);
        modifyMemberUseCase = new ModifyMemberUseCase(memberRepository);
        queryMemberUseCase = new QueryMemberUseCase(memberRepository, loanRepository);
        payFineUseCase = new PayFineUseCase(memberRepository);
        createLoanUseCase = new CreateLoanUseCase(memberRepository, loanRepository, copyRepository);
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

        registerMemberForm = new RegisterMemberForm();
        modifyMemberForm = new ModifyMemberForm(memberRepository);

        findMemberForm = new FindMemberForm(memberRepository, queryMemberUseCase);
        payFineForm = new PayFineForm(payFineUseCase, memberRepository);
        loanCartForm = new LoanCartForm(createLoanUseCase, copyRepository, memberRepository);
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

        memberController = new MemberController(
                registerMemberUseCase,
                modifyMemberUseCase,
                payFineUseCase,
                registerMemberForm,
                modifyMemberForm,
                findMemberForm,
                payFineForm);

        loanController = new LoanController(
                createLoanUseCase,
                loanRepository,
                memberRepository,
                copyRepository);

        mainController = new MainController(
                bookController,
                memberController,
                loanController,
                bookRepository,
                authorRepository,
                categoryRepository,
                publisherRepository,
                memberRepository);
    }
}