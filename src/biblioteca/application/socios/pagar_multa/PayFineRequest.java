package biblioteca.application.socios.pagar_multa;

import java.util.List;

/**
 * Request object for paying member fines
 */
public class PayFineRequest {
    private final String memberId;
    private final List<String> fineIds;

    public PayFineRequest(String memberId, List<String> fineIds) {
        this.memberId = memberId;
        this.fineIds = fineIds;
    }

    public String getMemberId() {
        return memberId;
    }

    public List<String> getFineIds() {
        return fineIds;
    }
}