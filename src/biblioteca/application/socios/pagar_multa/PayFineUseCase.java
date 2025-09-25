package biblioteca.application.socios.pagar_multa;

import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Member;

/**
 * Use case for paying member fines
 * Note: This is a basic implementation. Full functionality requires Fine
 * repository
 * which will be implemented in the devoluciones (returns) module.
 */
public class PayFineUseCase {
    private final MemberRepository memberRepository;
    // TODO: Add FineRepository when implemented

    public PayFineUseCase(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public PayFineResult execute(PayFineRequest request) {
        try {
            // Validate input
            if (request.getMemberId() == null || request.getMemberId().trim().isEmpty()) {
                return PayFineResult.failure("El ID del socio es obligatorio");
            }

            if (request.getFineIds() == null || request.getFineIds().isEmpty()) {
                return PayFineResult.failure("Debe seleccionar al menos una multa para pagar");
            }

            // Validate member exists
            Member member = memberRepository.findById(request.getMemberId().trim());
            if (member == null) {
                return PayFineResult.failure("No se encontró un socio con ID: " + request.getMemberId());
            }

            // TODO: Implement fine payment logic when FineRepository is available
            // For now, return a basic success response
            return PayFineResult.success(request.getFineIds().size(), 0.0);

        } catch (Exception e) {
            return PayFineResult.failure("Error interno al procesar el pago: " + e.getMessage());
        }
    }
}