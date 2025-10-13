package biblioteca.application.devoluciones.consultar;

import java.util.List;

import biblioteca.domain.entities.Loan;

/**
 * Result for returns history query
 */
public class QueryReturnsResult {
    public static QueryReturnsResult success(List<Loan> returns) {
        return new QueryReturnsResult(true, "Consulta realizada exitosamente", returns, returns.size());
    }

    public static QueryReturnsResult success(List<Loan> returns, String message) {
        return new QueryReturnsResult(true, message, returns, returns.size());
    }

    public static QueryReturnsResult failure(String message) {
        return new QueryReturnsResult(false, message, List.of(), 0);
    }

    private final boolean success;

    private final String message;

    private final List<Loan> returns;

    private final int totalCount;

    private QueryReturnsResult(boolean success, String message, List<Loan> returns, int totalCount) {
        this.success = success;
        this.message = message;
        this.returns = returns;
        this.totalCount = totalCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Loan> getReturns() {
        return returns;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public boolean hasReturns() {
        return returns != null && !returns.isEmpty();
    }
}
