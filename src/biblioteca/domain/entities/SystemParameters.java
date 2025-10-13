package biblioteca.domain.entities;

/**
 * System parameters entity
 * Contains all configurable parameters for the library system
 */
public class SystemParameters {
    private int maxLoansPerMember;
    private int loanDays;
    private double finePerDay;
    private int maxActiveReservationsPerMember;

    public SystemParameters(int maxLoansPerMember, int loanDays, double finePerDay,
            int maxActiveReservationsPerMember) {
        this.maxLoansPerMember = maxLoansPerMember;
        this.loanDays = loanDays;
        this.finePerDay = finePerDay;
        this.maxActiveReservationsPerMember = maxActiveReservationsPerMember;
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

    public int getMaxActiveReservationsPerMember() {
        return maxActiveReservationsPerMember;
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

    public void setMaxActiveReservationsPerMember(int maxActiveReservationsPerMember) {
        this.maxActiveReservationsPerMember = maxActiveReservationsPerMember;
    }
}
