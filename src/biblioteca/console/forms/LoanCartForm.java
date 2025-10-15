package biblioteca.console.forms;

import java.util.ArrayList;

import biblioteca.application.prestamos.carrito.LoanCart;
import biblioteca.application.prestamos.create.CreateLoanRequest;
import biblioteca.application.prestamos.create.CreateLoanResult;
import biblioteca.application.prestamos.create.CreateLoanUseCase;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.console.utils.MemberSearchHelper;
import biblioteca.data.database.CopyRepository;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Member;

/**
 * Formulario principal para manejo del carrito de préstamos
 */
public class LoanCartForm {
    private final CreateLoanUseCase createLoanUseCase;
    private final CopyRepository copyRepository;
    private final MemberRepository memberRepository;
    private final AddToCartForm addToCartForm;
    private final ConfirmLoanForm confirmLoanForm;

    public LoanCartForm(CreateLoanUseCase createLoanUseCase, CopyRepository copyRepository,
            MemberRepository memberRepository) {
        this.createLoanUseCase = createLoanUseCase;
        this.copyRepository = copyRepository;
        this.memberRepository = memberRepository;
        this.addToCartForm = new AddToCartForm(createLoanUseCase, copyRepository);
        this.confirmLoanForm = new ConfirmLoanForm(createLoanUseCase);
    }

    /**
     * Muestra el formulario principal del carrito de préstamos
     */
    public void show() {
        DisplayHelper.renderTitle("Nuevo Préstamo - Carrito");

        // Buscar socio por nombre o ID
        Member member = MemberSearchHelper.searchAndSelectMember(memberRepository, "Buscar socio para préstamo");

        if (member == null) {
            DisplayHelper.printWarning("Búsqueda cancelada");
            return;
        }

        // Validar socio
        CreateLoanResult memberValidation = createLoanUseCase.validateMember(
                new CreateLoanRequest(member.getId(), new ArrayList<>()));

        if (!memberValidation.isSuccess()) {
            DisplayHelper.printErrorMessage("Error de validación:");
            DisplayHelper.printErrorMessage(memberValidation.getMessage());
            InputHelper.pausar("Presione Enter para continuar...");
            return;
        }

        DisplayHelper.printSuccess("Socio válido para realizar préstamos");

        // Mostrar información del socio
        MemberSearchHelper.displayMemberSummary(member);
        showMemberLoanInfo(member.getId());

        // Crear carrito de préstamos
        LoanCart cart = new LoanCart(member.getId()); // Menú del carrito
        boolean continueCart = true;
        while (continueCart) {
            continueCart = showCartMenu(cart);
        }
    }

    /**
     * Muestra información de préstamos del socio
     */
    private void showMemberLoanInfo(String memberId) {
        CreateLoanResult loanInfo = createLoanUseCase.getMemberLoanInfo(memberId);
        if (loanInfo.isSuccess()) {
            DisplayHelper.renderSubtitle("Información de Préstamos del Socio");
            System.out.println(loanInfo.getMessage());
            System.out.println();
        }
    }

    /**
     * Muestra el menú del carrito de préstamos
     */
    private boolean showCartMenu(LoanCart cart) {
        DisplayHelper.renderSubtitle("Carrito de Préstamos");

        if (cart.isEmpty()) {
            DisplayHelper.printInfo("El carrito está vacío");
        } else {
            DisplayHelper.printInfo(String.format("Carrito tiene %d ejemplar(es)", cart.getItemCount()));
            showCartSummary(cart);
        }

        System.out.println("\nOpciones:");
        System.out.println("1. Agregar ejemplar al carrito");
        System.out.println("2. Ver contenido del carrito");
        System.out.println("3. Editar carrito");
        System.out.println("4. Confirmar préstamo");
        System.out.println("5. Cancelar y salir");

        int opcion = InputHelper.leerEnteroEnRango("Seleccione una opción: ", 1, 5);

        switch (opcion) {
            case 1:
                addToCartForm.show(cart);
                return true;

            case 2:
                showCartDetails(cart);
                return true;

            case 3:
                if (!cart.isEmpty()) {
                    boolean continueEditing = confirmLoanForm.editCart(cart);
                    return continueEditing && !cart.isEmpty();
                } else {
                    DisplayHelper.printWarning("El carrito está vacío");
                    return true;
                }

            case 4:
                if (cart.isEmpty()) {
                    DisplayHelper.printWarning("No puede confirmar un carrito vacío");
                    return true;
                } else {
                    boolean success = confirmLoanForm.show(cart);
                    if (success) {
                        DisplayHelper.printSuccess("Préstamo procesado exitosamente");
                        return false; // Salir después del préstamo exitoso
                    }
                    return true;
                }

            case 5:
                if (cart.isEmpty()
                        || InputHelper.confirmar("¿Está seguro de cancelar? Se perderá el contenido del carrito")) {
                    DisplayHelper.printInfo("Operación cancelada");
                    return false;
                }
                return true;

            default:
                DisplayHelper.printErrorMessage("Opción no válida");
                return true;
        }
    }

    /**
     * Muestra un resumen del carrito
     */
    private void showCartSummary(LoanCart cart) {
        if (cart.isEmpty()) {
            System.out.println("Carrito vacío");
            return;
        }

        System.out.println("Resumen:");
        System.out.printf("- Socio: %s%n", cart.getMemberId());
        System.out.printf("- Ejemplares: %d%n", cart.getItemCount());
    }

    /**
     * Muestra los detalles completos del carrito
     */
    private void showCartDetails(LoanCart cart) {
        DisplayHelper.renderSubtitle("Contenido del Carrito");

        if (cart.isEmpty()) {
            DisplayHelper.printWarning("El carrito está vacío");
            InputHelper.pausar("Presione Enter para continuar...");
            return;
        }

        System.out.printf("Socio: %s%n", cart.getMemberId());
        System.out.printf("Cantidad de ejemplares: %d%n", cart.getItemCount());
        System.out.println();

        System.out.println("Ejemplares en el carrito:");
        System.out.println("-".repeat(70));

        int counter = 1;
        for (var copy : cart.getItems()) {
            System.out.printf("%d. Código: %s%n", counter, copy.getCode());
            System.out.printf("   Título: %s%n", copy.getBook().getTitle());
            System.out.printf("   Autor: %s%n", copy.getBook().getAuthor());
            System.out.printf("   Año: %d%n", copy.getBook().getYear());
            System.out.printf("   Estado: %s%n", copy.getState().getDisplayName());

            if (counter < cart.getItemCount()) {
                System.out.println();
            }
            counter++;
        }

        System.out.println("-".repeat(70));
        InputHelper.pausar("Presione Enter para continuar...");
    }
}