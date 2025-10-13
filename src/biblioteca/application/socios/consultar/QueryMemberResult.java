package biblioteca.application.socios.consultar;

import java.util.Collections;
import java.util.List;

import biblioteca.application.socios.consultar.QueryMemberResult.MemberSummary;
import biblioteca.domain.entities.Loan;
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
        return new QueryMemberResult(true, "Consulta realizada exitosamente", member, summary, Collections.emptyList());
    }

    public static QueryMemberResult success(Member member, MemberSummary summary, List<Loan> recentReturns) {
        return new QueryMemberResult(true, "Consulta realizada exitosamente", member, summary, recentReturns);
    }

    public static QueryMemberResult failure(String message) {
        return new QueryMemberResult(false, message, null, null, Collections.emptyList());
    }

    private final boolean success;

    private final String message;

    private final Member member;

    private final MemberSummary summary;

    private final List<Loan> recentReturns;

    private QueryMemberResult(boolean success, String message, Member member, MemberSummary summary,
            List<Loan> recentReturns) {
        this.success = success;
        this.message = message;
        this.member = member;
        this.summary = summary;
        this.recentReturns = recentReturns != null ? recentReturns : Collections.emptyList();
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

    public List<Loan> getRecentReturns() {
        return recentReturns;
    }

    public boolean hasRecentReturns() {
        return recentReturns != null && !recentReturns.isEmpty();
    }
}