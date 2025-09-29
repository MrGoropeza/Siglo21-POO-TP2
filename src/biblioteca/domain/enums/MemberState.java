package biblioteca.domain.enums;

/**
 * Estados posibles de un socio en el sistema de biblioteca
 */
public enum MemberState {
    ACTIVE("Activo"),
    SUSPENDED("Suspendido"),
    INACTIVE("Inactivo");

    private final String displayName;

    MemberState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}