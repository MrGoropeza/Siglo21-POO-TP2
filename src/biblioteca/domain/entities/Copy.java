package biblioteca.domain.entities;

import java.util.Objects;

/**
 * Copy entity representing a physical copy of a book in the library
 */
public class Copy {
    private String code;
    private CopyState state;
    private CopyOrigin origin;
    private Book book;

    public Copy() {
    }

    public Copy(String code, CopyState state, CopyOrigin origin, Book book) {
        this.code = code;
        this.state = state;
        this.origin = origin;
        this.book = book;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CopyState getState() {
        return state;
    }

    public void setState(CopyState state) {
        this.state = state;
    }

    public CopyOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(CopyOrigin origin) {
        this.origin = origin;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isAvailable() {
        return state == CopyState.AVAILABLE;
    }

    public void markAsLoaned() {
        this.state = CopyState.LOANED;
    }

    public void markAsAvailable() {
        this.state = CopyState.AVAILABLE;
    }

    public void markAsReserved() {
        this.state = CopyState.RESERVED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Copy copy = (Copy) o;
        return Objects.equals(code, copy.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%s)",
                code, book.getTitle(), state.getDisplayName(), origin.getDisplayName());
    }

    public String toDetailedString() {
        return String.format("Código: %s | Libro: %s | Estado: %s | Origen: %s",
                code, book.getTitle(), state.getDisplayName(), origin.getDisplayName());
    }
}