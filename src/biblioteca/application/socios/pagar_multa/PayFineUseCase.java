package biblioteca.application.socios.pagar_multa;

import java.util.ArrayList;
import java.util.List;

import biblioteca.data.database.FineRepository;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Fine;
import biblioteca.domain.entities.Member;

/**
 * Use case for paying member fines
 */
public class PayFineUseCase {
    private final MemberRepository memberRepository;
    private final FineRepository fineRepository;

    public PayFineUseCase(MemberRepository memberRepository, FineRepository fineRepository) {
        this.memberRepository = memberRepository;
        this.fineRepository = fineRepository;
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

            // Process payment for each fine
            List<String> paidFines = new ArrayList<>();
            List<String> failedFines = new ArrayList<>();
            double totalAmount = 0.0;

            for (String fineId : request.getFineIds()) {
                Fine fine = fineRepository.findById(fineId);

                if (fine == null) {
                    failedFines.add(fineId + " (no encontrada)");
                    continue;
                }

                if (!fine.getMember().equals(member)) {
                    failedFines.add(fineId + " (no pertenece al socio)");
                    continue;
                }

                if (fine.isPaid()) {
                    failedFines.add(fineId + " (ya pagada)");
                    continue;
                }

                // Mark fine as paid
                boolean success = fineRepository.markAsPaid(fineId);
                if (success) {
                    paidFines.add(fineId);
                    totalAmount += fine.getAmount();
                } else {
                    failedFines.add(fineId + " (error al procesar)");
                }
            }

            // Build result
            if (paidFines.isEmpty()) {
                return PayFineResult.failure("No se pudo pagar ninguna multa. " +
                        String.join(", ", failedFines));
            }

            if (!failedFines.isEmpty()) {
                return PayFineResult.partialSuccess(
                        paidFines.size(),
                        totalAmount,
                        "Multas pagadas: " + paidFines.size() + ". Fallidas: " + String.join(", ", failedFines));
            }

            return PayFineResult.success(paidFines.size(), totalAmount);

        } catch (Exception e) {
            return PayFineResult.failure("Error interno al procesar el pago: " + e.getMessage());
        }
    }
}