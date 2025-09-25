package biblioteca.application.socios.consultar;

import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Member;

/**
 * Use case for querying comprehensive member information
 * Note: This is a basic implementation. Full functionality requires Loan and
 * Fine repositories
 * which will be implemented in later modules.
 */
public class QueryMemberUseCase {
    private final MemberRepository memberRepository;
    // TODO: Add LoanRepository and FineRepository when implemented

    public QueryMemberUseCase(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public QueryMemberResult execute(QueryMemberRequest request) {
        try {
            // Validate input
            if (request.getId() == null || request.getId().trim().isEmpty()) {
                return QueryMemberResult.failure("El ID del socio es obligatorio");
            }

            // Find member
            Member member = memberRepository.findById(request.getId().trim());
            if (member == null) {
                return QueryMemberResult.failure("No se encontró un socio con ID: " + request.getId());
            }

            // Create summary (basic implementation for now)
            // TODO: Replace with real data from LoanRepository and FineRepository
            QueryMemberResult.MemberSummary summary = new QueryMemberResult.MemberSummary(
                    0, // activeLoans - will be calculated from LoanRepository
                    0, // totalUnpaidFines - will be calculated from FineRepository
                    0.0, // totalUnpaidAmount - will be calculated from FineRepository
                    0 // activeReservations - will be calculated from ReservationRepository
            );

            return QueryMemberResult.success(member, summary);

        } catch (Exception e) {
            return QueryMemberResult.failure("Error interno al consultar el socio: " + e.getMessage());
        }
    }
}