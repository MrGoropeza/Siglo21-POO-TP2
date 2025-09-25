package biblioteca.domain.entities;

/**
 * Enum representing the possible states of a book copy
 */
public enum CopyState {
    AVAILABLE("Disponible"),
    LOANED("Prestado"),
    RESERVED("Reservado");

    private final String displayName;

    CopyState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}