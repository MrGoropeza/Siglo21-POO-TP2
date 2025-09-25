package biblioteca.application.socios.modificar;

import biblioteca.domain.entities.Member;

/**
 * Result object for member modification operation
 */
public class ModifyMemberResult {
    public static ModifyMemberResult success(Member member) {
        return new ModifyMemberResult(true, "Socio modificado exitosamente", member);
    }

    public static ModifyMemberResult failure(String message) {
        return new ModifyMemberResult(false, message, null);
    }

    private final boolean success;

    private final String message;

    private final Member member;

    private ModifyMemberResult(boolean success, String message, Member member) {
        this.success = success;
        this.message = message;
        this.member = member;
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
}