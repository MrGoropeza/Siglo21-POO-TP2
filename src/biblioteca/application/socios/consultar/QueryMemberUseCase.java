package biblioteca.application.socios.consultar;

import java.util.List;

import biblioteca.data.LoanRepository;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Loan;
import biblioteca.domain.entities.Member;
import biblioteca.domain.enums.LoanState;

/**
 * Use case for querying comprehensive member information
 * Includes real loan data from LoanRepository.
 */
public class QueryMemberUseCase {
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    // TODO: Add FineRepository when implemented

    public QueryMemberUseCase(MemberRepository memberRepository, LoanRepository loanRepository) {
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
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

            // Get loan data from LoanRepository
            List<Loan> memberLoans = loanRepository.findByMemberId(member.getId());
            long activeLoans = memberLoans.stream()
                    .filter(loan -> loan.getState() == LoanState.ACTIVE)
                    .count();

            // Create summary with real loan data
            // TODO: Replace fine data with real data from FineRepository when implemented
            QueryMemberResult.MemberSummary summary = new QueryMemberResult.MemberSummary(
                    (int) activeLoans, // activeLoans - now using real data
                    0, // totalUnpaidFines - will be calculated from FineRepository
                    member.getPendingFines(), // totalUnpaidAmount - using member's pending fines
                    0 // activeReservations - will be calculated from ReservationRepository
            );

            return QueryMemberResult.success(member, summary);

        } catch (Exception e) {
            return QueryMemberResult.failure("Error interno al consultar el socio: " + e.getMessage());
        }
    }
}