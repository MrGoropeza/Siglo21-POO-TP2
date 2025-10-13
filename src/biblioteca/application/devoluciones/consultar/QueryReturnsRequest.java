package biblioteca.application.devoluciones.consultar;

import java.time.LocalDate;

/**
 * Request for querying returns history
 */
public class QueryReturnsRequest {
    private final String memberId;
    private final String bookId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Boolean withFine; // null = all, true = only with fine, false = only without fine

    public QueryReturnsRequest(String memberId, String bookId, LocalDate startDate, LocalDate endDate,
            Boolean withFine) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.withFine = withFine;
    }

    // Factory methods for common queries
    public static QueryReturnsRequest all() {
        return new QueryReturnsRequest(null, null, null, null, null);
    }

    public static QueryReturnsRequest byMember(String memberId) {
        return new QueryReturnsRequest(memberId, null, null, null, null);
    }

    public static QueryReturnsRequest byBook(String bookId) {
        return new QueryReturnsRequest(null, bookId, null, null, null);
    }

    public static QueryReturnsRequest withFines() {
        return new QueryReturnsRequest(null, null, null, null, true);
    }

    public static QueryReturnsRequest withoutFines() {
        return new QueryReturnsRequest(null, null, null, null, false);
    }

    public static QueryReturnsRequest byDateRange(LocalDate startDate, LocalDate endDate) {
        return new QueryReturnsRequest(null, null, startDate, endDate, null);
    }

    // Getters
    public String getMemberId() {
        return memberId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Boolean getWithFine() {
        return withFine;
    }
}
