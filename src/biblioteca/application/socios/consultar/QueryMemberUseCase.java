package biblioteca.application.socios.consultar;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import biblioteca.data.database.FineRepository;
import biblioteca.data.database.LoanRepository;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Fine;
import biblioteca.domain.entities.Loan;
import biblioteca.domain.entities.Member;
import biblioteca.domain.enums.LoanState;

/**
 * Use case for querying comprehensive member information
 * Includes real loan and fine data from repositories.
 */
public class QueryMemberUseCase {
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    private final FineRepository fineRepository;

    public QueryMemberUseCase(MemberRepository memberRepository, LoanRepository loanRepository,
            FineRepository fineRepository) {
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
        this.fineRepository = fineRepository;
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

            // Get fine data from FineRepository
            List<Fine> unpaidFines = fineRepository.findUnpaidByMember(member);
            int totalUnpaidFines = unpaidFines.size();
            double totalUnpaidAmount = fineRepository.getTotalUnpaidAmount(member);

            // Get recent returns (last 5)
            List<Loan> recentReturns = memberLoans.stream()
                    .filter(loan -> loan.getState() == LoanState.RETURNED)
                    .sorted(Comparator.comparing(Loan::getReturnDate).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            // Create summary with real loan and fine data
            QueryMemberResult.MemberSummary summary = new QueryMemberResult.MemberSummary(
                    (int) activeLoans, // activeLoans - from LoanRepository
                    totalUnpaidFines, // totalUnpaidFines - from FineRepository
                    totalUnpaidAmount, // totalUnpaidAmount - from FineRepository
                    0 // activeReservations - will be calculated from ReservationRepository when
                      // implemented
            );

            return QueryMemberResult.success(member, summary, recentReturns);

        } catch (Exception e) {
            return QueryMemberResult.failure("Error interno al consultar el socio: " + e.getMessage());
        }
    }
}