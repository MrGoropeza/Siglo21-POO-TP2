package biblioteca.application.prestamos.create;

import java.util.List;

/**
 * Request para crear préstamos.
 */
public class CreateLoanRequest {
    private String memberId;
    private List<String> copyCodes;

    public CreateLoanRequest(String memberId, List<String> copyCodes) {
        this.memberId = memberId;
        this.copyCodes = copyCodes;
    }

    public String getMemberId() {
        return memberId;
    }

    public List<String> getCopyCodes() {
        return copyCodes;
    }
}