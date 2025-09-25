package biblioteca.domain.entities;

/**
 * Enum representing the types of library members with their benefits
 */
public enum MemberType {
    STANDARD("Estándar", 0.0, 0),
    STUDENT("Estudiante", 0.5, 0), // 50% discount on fines
    RETIRED("Jubilado", 0.0, 3); // +3 days to standard loan period

    private final String displayName;
    private final double fineDiscountPercentage;
    private final int extraLoanDays;

    MemberType(String displayName, double fineDiscountPercentage, int extraLoanDays) {
        this.displayName = displayName;
        this.fineDiscountPercentage = fineDiscountPercentage;
        this.extraLoanDays = extraLoanDays;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getFineDiscountPercentage() {
        return fineDiscountPercentage;
    }

    public int getExtraLoanDays() {
        return extraLoanDays;
    }

    /**
     * Calculates the final fine amount after applying member type benefits
     * 
     * @param originalAmount Original fine amount
     * @return Final amount after discount
     */
    public double applyFineDiscount(double originalAmount) {
        return originalAmount * (1.0 - fineDiscountPercentage);
    }

    /**
     * Calculates the total loan days including member type benefits
     * 
     * @param standardDays Standard loan period in days
     * @return Total loan days including extra days
     */
    public int calculateLoanDays(int standardDays) {
        return standardDays + extraLoanDays;
    }

    @Override
    public String toString() {
        return displayName;
    }
}