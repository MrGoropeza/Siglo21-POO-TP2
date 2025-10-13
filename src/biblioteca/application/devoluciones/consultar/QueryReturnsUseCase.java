package biblioteca.application.devoluciones.consultar;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import biblioteca.data.database.FineRepository;
import biblioteca.data.database.LoanRepository;
import biblioteca.domain.entities.Loan;
import biblioteca.domain.enums.LoanState;

/**
 * Use case for querying returns history with filters
 */
public class QueryReturnsUseCase {
    private final LoanRepository loanRepository;
    private final FineRepository fineRepository;

    public QueryReturnsUseCase(LoanRepository loanRepository, FineRepository fineRepository) {
        this.loanRepository = loanRepository;
        this.fineRepository = fineRepository;
    }

    public QueryReturnsResult execute(QueryReturnsRequest request) {
        try {
            // Get all returned loans
            List<Loan> allReturns = loanRepository.findByState(LoanState.RETURNED);

            if (allReturns.isEmpty()) {
                return QueryReturnsResult.success(allReturns, "No se encontraron devoluciones registradas");
            }

            // Apply filters
            List<Loan> filteredReturns = allReturns.stream()
                    .filter(loan -> matchesMemberFilter(loan, request.getMemberId()))
                    .filter(loan -> matchesBookFilter(loan, request.getBookId()))
                    .filter(loan -> matchesDateRangeFilter(loan, request.getStartDate(), request.getEndDate()))
                    .filter(loan -> matchesFineFilter(loan, request.getWithFine()))
                    .sorted(Comparator.comparing(Loan::getReturnDate).reversed()) // Most recent first
                    .collect(Collectors.toList());

            if (filteredReturns.isEmpty()) {
                return QueryReturnsResult.success(filteredReturns,
                        "No se encontraron devoluciones con los filtros aplicados");
            }

            String message = String.format("Se encontraron %d devolución(es)", filteredReturns.size());
            return QueryReturnsResult.success(filteredReturns, message);

        } catch (Exception e) {
            return QueryReturnsResult.failure("Error al consultar devoluciones: " + e.getMessage());
        }
    }

    /**
     * Get recent returns for a specific member (used for member details)
     */
    public List<Loan> getRecentReturnsByMember(String memberId, int limit) {
        return loanRepository.findByState(LoanState.RETURNED).stream()
                .filter(loan -> loan.getMember().getId().equals(memberId))
                .sorted(Comparator.comparing(Loan::getReturnDate).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    private boolean matchesMemberFilter(Loan loan, String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            return true;
        }
        return loan.getMember().getId().equalsIgnoreCase(memberId.trim());
    }

    private boolean matchesBookFilter(Loan loan, String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            return true;
        }
        return loan.getCopy().getBook().getId() == Integer.parseInt(bookId.trim());
    }

    private boolean matchesDateRangeFilter(Loan loan, LocalDate startDate, LocalDate endDate) {
        LocalDate returnDate = loan.getReturnDate();
        if (returnDate == null) {
            return false;
        }

        if (startDate != null && returnDate.isBefore(startDate)) {
            return false;
        }

        if (endDate != null && returnDate.isAfter(endDate)) {
            return false;
        }

        return true;
    }

    private boolean matchesFineFilter(Loan loan, Boolean withFine) {
        if (withFine == null) {
            return true;
        }

        // Check if this loan generated a fine
        boolean hasFine = !fineRepository.findByMember(loan.getMember()).stream()
                .filter(fine -> fine.getIssueDate().equals(loan.getReturnDate()))
                .collect(Collectors.toList())
                .isEmpty();

        return withFine == hasFine;
    }
}
