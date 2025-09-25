package biblioteca.application.socios.registrar;

import biblioteca.domain.entities.MemberType;

/**
 * Request object for registering a new member
 */
public class RegisterMemberRequest {
    private final String id;
    private final String name;
    private final String email;
    private final String phone;
    private final MemberType type;

    public RegisterMemberRequest(String id, String name, String email, String phone, MemberType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public MemberType getType() {
        return type;
    }
}