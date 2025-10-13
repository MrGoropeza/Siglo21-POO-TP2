package biblioteca.data.database;

import biblioteca.domain.entities.SystemParameters;

/**
 * Repository for system parameters (singleton pattern)
 */
public class SystemParametersRepository {
    private SystemParameters parameters;

    public SystemParametersRepository() {
        // Initialize with default parameters
        this.parameters = new SystemParameters(
                3, // maxLoansPerMember - default
                14, // loanDays - 14 days standard
                5.0, // finePerDay - $5 per day
                5 // maxActiveReservationsPerMember - 5 reservations max
        );
    }

    /**
     * Get system parameters
     */
    public SystemParameters get() {
        return parameters;
    }

    /**
     * Update system parameters
     */
    public void update(SystemParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Update individual parameters
     */
    public void updateMaxLoansPerMember(int maxLoans) {
        parameters.setMaxLoansPerMember(maxLoans);
    }

    public void updateLoanDays(int loanDays) {
        parameters.setLoanDays(loanDays);
    }

    public void updateFinePerDay(double finePerDay) {
        parameters.setFinePerDay(finePerDay);
    }

    public void updateMaxActiveReservationsPerMember(int maxReservations) {
        parameters.setMaxActiveReservationsPerMember(maxReservations);
    }
}
