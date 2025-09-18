package biblioteca.application.libros.registrar;

/**
 * Request DTO for registering a new book
 */
public class RegisterBookRequest {
    private String title;
    private int authorId;
    private int categoryId;
    private int publisherId;
    private int year;

    public RegisterBookRequest() {
    }

    public RegisterBookRequest(String title, int authorId, int categoryId, int publisherId, int year) {
        this.title = title;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.publisherId = publisherId;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}