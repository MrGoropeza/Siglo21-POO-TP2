package biblioteca.application.configuracion.actualizar;

/**
 * Request for updating system configuration
 */
public class UpdateConfigRequest {
    private Integer loanDays;
    private Double finePerDay;
    private Integer maxLoansPerMember;
    private Integer maxActiveReservationsPerMember;

    public UpdateConfigRequest() {
        // Empty constructor - fields can be set individually
    }

    // Getters
    public Integer getLoanDays() {
        return loanDays;
    }

    public Double getFinePerDay() {
        return finePerDay;
    }

    public Integer getMaxLoansPerMember() {
        return maxLoansPerMember;
    }

    public Integer getMaxActiveReservationsPerMember() {
        return maxActiveReservationsPerMember;
    }

    // Setters
    public void setLoanDays(Integer loanDays) {
        this.loanDays = loanDays;
    }

    public void setFinePerDay(Double finePerDay) {
        this.finePerDay = finePerDay;
    }

    public void setMaxLoansPerMember(Integer maxLoansPerMember) {
        this.maxLoansPerMember = maxLoansPerMember;
    }

    public void setMaxActiveReservationsPerMember(Integer maxActiveReservationsPerMember) {
        this.maxActiveReservationsPerMember = maxActiveReservationsPerMember;
    }
}
