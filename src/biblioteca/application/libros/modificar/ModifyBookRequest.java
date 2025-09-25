package biblioteca.application.libros.modificar;

/**
 * Request for modifying a book
 */
public class ModifyBookRequest {
    private final int bookId;
    private final String newTitle;
    private final Integer newYear;

    public ModifyBookRequest(int bookId, String newTitle, Integer newYear) {
        this.bookId = bookId;
        this.newTitle = newTitle;
        this.newYear = newYear;
    }

    public int getBookId() {
        return bookId;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public Integer getNewYear() {
        return newYear;
    }

    public boolean hasTitleChange() {
        return newTitle != null && !newTitle.trim().isEmpty();
    }

    public boolean hasYearChange() {
        return newYear != null;
    }
}