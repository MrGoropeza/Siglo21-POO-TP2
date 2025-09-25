package biblioteca.domain.entities;

/**
 * Enum representing the origin of a book copy acquisition
 */
public enum CopyOrigin {
    PURCHASE("Compra"),
    DONATION("Donación");

    private final String displayName;

    CopyOrigin(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}