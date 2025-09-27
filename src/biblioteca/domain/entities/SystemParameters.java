package biblioteca.domain.entities;

public class SystemParameters {
    private int maxLoansPerMember;
    private int loanDays;
    private double finePerDay;

    public SystemParameters(int maxLoansPerMember, int loanDays, double finePerDay) {
        this.maxLoansPerMember = maxLoansPerMember;
        this.loanDays = loanDays;
        this.finePerDay = finePerDay;
    }

    public int getMaxLoansPerMember() {
        return maxLoansPerMember;
    }

    public int getLoanDays() {
        return loanDays;
    }

    public double getFinePerDay() {
        return finePerDay;
    }

    public void setMaxLoansPerMember(int maxLoansPerMember) {
        this.maxLoansPerMember = maxLoansPerMember;
    }

    public void setLoanDays(int loanDays) {
        this.loanDays = loanDays;
    }

    public void setFinePerDay(double finePerDay) {
        this.finePerDay = finePerDay;
    }
}
