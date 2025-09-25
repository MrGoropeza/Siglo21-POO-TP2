package biblioteca.application.socios.consultar;

import biblioteca.domain.entities.Member;

/**
 * Result object for member query operation
 */
public class QueryMemberResult {
    /**
     * Summary of member's activity and status
     */
    public static class MemberSummary {
        private final int activeLoans;
        private final int totalUnpaidFines;
        private final double totalUnpaidAmount;
        private final int activeReservations;

        public MemberSummary(int activeLoans, int totalUnpaidFines, double totalUnpaidAmount, int activeReservations) {
            this.activeLoans = activeLoans;
            this.totalUnpaidFines = totalUnpaidFines;
            this.totalUnpaidAmount = totalUnpaidAmount;
            this.activeReservations = activeReservations;
        }

        public int getActiveLoans() {
            return activeLoans;
        }

        public int getTotalUnpaidFines() {
            return totalUnpaidFines;
        }

        public double getTotalUnpaidAmount() {
            return totalUnpaidAmount;
        }

        public int getActiveReservations() {
            return activeReservations;
        }

        public boolean hasUnpaidFines() {
            return totalUnpaidFines > 0;
        }
    }

    public static QueryMemberResult success(Member member, MemberSummary summary) {
        return new QueryMemberResult(true, "Consulta realizada exitosamente", member, summary);
    }

    public static QueryMemberResult failure(String message) {
        return new QueryMemberResult(false, message, null, null);
    }

    private final boolean success;

    private final String message;

    private final Member member;

    private final MemberSummary summary;

    private QueryMemberResult(boolean success, String message, Member member, MemberSummary summary) {
        this.success = success;
        this.message = message;
        this.member = member;
        this.summary = summary;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Member getMember() {
        return member;
    }

    public MemberSummary getSummary() {
        return summary;
    }
}