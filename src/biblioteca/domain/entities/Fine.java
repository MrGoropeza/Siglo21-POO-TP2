package biblioteca.domain.entities;

import java.time.LocalDate;

public class Fine {
    private String id;
    private String memberId;
    private double amount;
    private boolean paid;
    private LocalDate issueDate;
    private LocalDate paidDate;

    public Fine(String id, String memberId, double amount, LocalDate issueDate) {
        this.id = id;
        this.memberId = memberId;
        this.amount = amount;
        this.issueDate = issueDate;
        this.paid = false;
    }

    public String getId() {
        return id;
    }

    public String getMemberId() {
        return memberId;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void pay(LocalDate paidDate) {
        this.paid = true;
        this.paidDate = paidDate;
    }
}
