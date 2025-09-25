package biblioteca.application.socios.registrar;

import biblioteca.domain.entities.Member;

/**
 * Result object for member registration operation
 */
public class RegisterMemberResult {
    public static RegisterMemberResult success(Member member) {
        return new RegisterMemberResult(true, "Socio registrado exitosamente", member);
    }

    public static RegisterMemberResult failure(String message) {
        return new RegisterMemberResult(false, message, null);
    }

    private final boolean success;

    private final String message;

    private final Member member;

    private RegisterMemberResult(boolean success, String message, Member member) {
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