package biblioteca.domain.entities;

import java.time.LocalDate;

import biblioteca.domain.enums.LoanState;

public class Loan {
    private String id;
    private String memberId;
    private String bookId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanState state;

    public Loan(String id, String memberId, String bookId, LocalDate loanDate, LocalDate dueDate, LoanState state) {
        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LoanState getState() {
        return state;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setState(LoanState state) {
        this.state = state;
    }
}
