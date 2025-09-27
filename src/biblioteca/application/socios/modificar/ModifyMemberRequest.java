package biblioteca.application.socios.modificar;

import biblioteca.domain.enums.MemberType;

/**
 * Request object for modifying a member
 */
public class ModifyMemberRequest {
    private final String id;
    private final String email;
    private final String phone;
    private final MemberType type;

    public ModifyMemberRequest(String id, String email, String phone, MemberType type) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.type = type;
    }

    public String getId() {
        return id;
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