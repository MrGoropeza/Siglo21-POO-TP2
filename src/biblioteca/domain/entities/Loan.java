package biblioteca.domain.entities;

import java.time.LocalDate;

import biblioteca.domain.enums.LoanState;

public class Loan {
    private String id;
    private Member member;
    private Copy copy;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanState state;

    public Loan(String id, Member member, Copy copy, LocalDate loanDate, LocalDate dueDate, LoanState state) {
        this.id = id;
        this.member = member;
        this.copy = copy;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.state = state;
    }

    // Constructor simplificado que inicializa con estado ACTIVE
    public Loan(String id, Member member, Copy copy, LocalDate loanDate, LocalDate dueDate) {
        this(id, member, copy, loanDate, dueDate, LoanState.ACTIVE);
    }

    public String getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Copy getCopy() {
        return copy;
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
