package biblioteca.application.prestamos.create;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import biblioteca.application.prestamos.carrito.LoanCart;
import biblioteca.data.LoanRepository;
import biblioteca.data.database.CopyRepository;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Copy;
import biblioteca.domain.entities.Loan;
import biblioteca.domain.entities.Member;
import biblioteca.domain.enums.CopyState;
import biblioteca.domain.enums.MemberState;

/**
 * Caso de uso para crear préstamos utilizando un carrito.
 * Valida todas las reglas de negocio antes de crear el préstamo.
 */
public class CreateLoanUseCase {
    private static final int MAX_LOANS_PER_MEMBER = 3;
    private static final int LOAN_DURATION_DAYS = 7;

    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;

    public CreateLoanUseCase(MemberRepository memberRepository, LoanRepository loanRepository,
            CopyRepository copyRepository) {
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
    }

    /**
     * Valida si un socio puede realizar préstamos.
     */
    public CreateLoanResult validateMember(CreateLoanRequest request) {
        Member member = memberRepository.findById(request.getMemberId());

        if (member == null) {
            return CreateLoanResult.error("El socio no existe en el sistema");
        }

        // Verificar estado del socio
        if (member.getState() != MemberState.ACTIVE) {
            return CreateLoanResult.error("El socio debe estar en estado ACTIVO para realizar préstamos");
        }

        // Verificar multas pendientes
        if (member.hasPendingFines()) {
            return CreateLoanResult
                    .error("El socio tiene multas pendientes. Debe pagarlas antes de realizar préstamos");
        }

        // Verificar límite de préstamos activos
        long activeLoanCount = loanRepository.countActiveLoansByMemberId(request.getMemberId());
        if (activeLoanCount >= MAX_LOANS_PER_MEMBER) {
            return CreateLoanResult.error(
                    String.format("El socio ya tiene %d préstamos activos (máximo permitido: %d)",
                            activeLoanCount, MAX_LOANS_PER_MEMBER));
        }

        return CreateLoanResult.success("Socio válido para préstamos", member);
    }

    /**
     * Valida si se puede agregar un ejemplar al carrito.
     */
    public CreateLoanResult validateCopyForCart(String copyCode, LoanCart cart) {
        // Verificar que el ejemplar no esté ya en el carrito
        if (cart.containsCopyByCode(copyCode)) {
            return CreateLoanResult.error("El ejemplar ya está en el carrito");
        }

        // Verificar límite del carrito + préstamos activos
        Member member = memberRepository.findById(cart.getMemberId());
        long activeLoanCount = loanRepository.countActiveLoansByMemberId(cart.getMemberId());

        if (activeLoanCount + cart.getItemCount() >= MAX_LOANS_PER_MEMBER) {
            return CreateLoanResult.error(
                    String.format("Se alcanzará el límite de préstamos (%d). Préstamos activos: %d, en carrito: %d",
                            MAX_LOANS_PER_MEMBER, activeLoanCount, cart.getItemCount()));
        }

        return CreateLoanResult.success("Ejemplar válido para agregar al carrito");
    }

    /**
     * Confirma el préstamo creando las entradas correspondientes.
     */
    public CreateLoanResult confirmLoan(LoanCart cart) {
        if (cart.isEmpty()) {
            return CreateLoanResult.error("El carrito está vacío");
        }

        // Validar nuevamente el socio
        CreateLoanResult memberValidation = validateMember(
                new CreateLoanRequest(cart.getMemberId(), new ArrayList<>()));

        if (!memberValidation.isSuccess()) {
            return memberValidation;
        }

        try {
            List<Loan> createdLoans = new ArrayList<>();
            LocalDate loanDate = LocalDate.now();
            LocalDate dueDate = loanDate.plusDays(LOAN_DURATION_DAYS);

            // Buscar el objeto Member por ID una sola vez
            Member member = memberRepository.findById(cart.getMemberId());

            // Crear un préstamo para cada ejemplar del carrito
            for (Copy copy : cart.getItems()) {
                Loan loan = new Loan(
                        null, // Dejar que el repositorio genere el ID
                        member,
                        copy,
                        loanDate,
                        dueDate);

                Loan savedLoan = loanRepository.save(loan);
                createdLoans.add(savedLoan);

                // CRÍTICO: Actualizar el estado del ejemplar a LOANED
                Copy updatedCopy = new Copy(
                        copy.getCode(),
                        CopyState.LOANED, // Cambiar estado a LOANED
                        copy.getOrigin(),
                        copy.getBook());
                copyRepository.update(updatedCopy);
            }

            // Marcar carrito como confirmado
            cart.confirm();

            String message = String.format("Préstamo confirmado exitosamente. %d ejemplares prestados hasta %s",
                    createdLoans.size(), dueDate.toString());

            return CreateLoanResult.success(message, createdLoans);

        } catch (Exception e) {
            return CreateLoanResult.error("Error al confirmar el préstamo: " + e.getMessage());
        }
    }

    /**
     * Obtiene información de préstamos activos de un socio.
     */
    public CreateLoanResult getMemberLoanInfo(String memberId) {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            return CreateLoanResult.error("El socio no existe");
        }

        List<Loan> activeLoans = loanRepository.findActiveLoansByMemberId(memberId);
        long activeLoanCount = activeLoans.size();
        int availableSlots = MAX_LOANS_PER_MEMBER - (int) activeLoanCount;

        StringBuilder info = new StringBuilder();
        info.append(String.format("Información de préstamos para socio %s:\n", memberId));
        info.append(String.format("- Préstamos activos: %d/%d\n", activeLoanCount, MAX_LOANS_PER_MEMBER));
        info.append(String.format("- Espacios disponibles: %d\n", availableSlots));
        info.append(String.format("- Estado del socio: %s\n", member.getState()));
        info.append(String.format("- Multas pendientes: %s\n", member.hasPendingFines() ? "SÍ" : "NO"));

        return CreateLoanResult.success(info.toString(), activeLoans);
    }
}