package biblioteca.domain.entities;

import java.util.Objects;

/**
 * Book entity representing a book in the library
 */
public class Book {
    private int id;
    private String title;
    private Author author;
    private Category category;
    private Publisher publisher;
    private int year;

    public Book() {
    }

    public Book(int id, String title, Author author, Category category, Publisher publisher, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Book book = (Book) obj;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d)",
                title,
                author != null ? author.getName() : "Unknown author",
                year);
    }

    /**
     * Detailed string representation showing all book information
     */
    public String toDetailedString() {
        return String.format("ID: %d | Title: %s | Author: %s | Category: %s | Publisher: %s | Year: %d",
                id,
                title,
                author != null ? author.getName() : "Unknown author",
                category != null ? category.getName() : "Unknown category",
                publisher != null ? publisher.getName() : "Unknown publisher",
                year);
    }
}