package biblioteca.domain.entities;

import java.util.regex.Pattern;

/**
 * Domain entity representing a library member
 */
public class Member {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private String id;
    private String name;
    private String email;
    private String phone;
    private MemberType type;

    public Member() {
    }

    public Member(String id, String name, String email, String phone, MemberType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.type = type;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    // Business methods
    /**
     * Validates if the member's email has a valid format
     * 
     * @return true if email is valid
     */
    public boolean isValidEmail() {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Checks if the member can request loans based on current active loans and
     * system parameters
     * 
     * @param activeLoansCount  Current number of active loans
     * @param maxLoansPerMember Maximum loans allowed per member from system
     *                          parameters
     * @return true if member can request more loans
     */
    public boolean canRequestLoan(int activeLoansCount, int maxLoansPerMember) {
        return activeLoansCount < maxLoansPerMember;
    }

    /**
     * Calculates the fine amount for this member type
     * 
     * @param originalFineAmount Original fine amount before discounts
     * @return Final fine amount after applying member type benefits
     */
    public double calculateFineAmount(double originalFineAmount) {
        return type.applyFineDiscount(originalFineAmount);
    }

    /**
     * Calculates the loan period for this member type
     * 
     * @param standardLoanDays Standard loan period in days
     * @return Total loan days including member type benefits
     */
    public int calculateLoanPeriod(int standardLoanDays) {
        return type.calculateLoanDays(standardLoanDays);
    }

    @Override
    public String toString() {
        return String.format("ID: %s | %s | %s", id, name, type.getDisplayName());
    }

    public String toDetailedString() {
        return String.format(
                "ID: %s%n" +
                        "Nombre: %s%n" +
                        "Email: %s%n" +
                        "Teléfono: %s%n" +
                        "Categoría: %s",
                id, name, email, phone, type.getDisplayName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Member member = (Member) obj;
        return id != null ? id.equals(member.id) : member.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}