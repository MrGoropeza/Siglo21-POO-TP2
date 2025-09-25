package biblioteca.data.database;

import java.util.ArrayList;
import java.util.List;

import biblioteca.domain.entities.Book;

/**
 * Repository for Book entities - handles CRUD operations
 */
public class BookRepository {
    private List<Book> books = new ArrayList<>();
    private int nextId = 1;

    public BookRepository() {
    }

    public void loadDummyData(List<Book> initialData) {
        this.books.clear();
        this.books.addAll(initialData);
        this.nextId = initialData.stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Saves a new book to the repository
     * 
     * @param book The book to save
     * @return The saved book with assigned ID
     */
    public Book save(Book book) {
        book.setId(nextId++);
        books.add(book);
        return book;
    }

    /**
     * Finds a book by its ID
     * 
     * @param id The book ID
     * @return The book if found, null otherwise
     */
    public Book findById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all books
     * 
     * @return List of all books
     */
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    /**
     * Checks if a book with the given title exists
     * 
     * @param title The title to check
     * @return true if exists, false otherwise
     */
    public boolean existsByTitle(String title) {
        return books.stream()
                .anyMatch(book -> book.getTitle().equalsIgnoreCase(title.trim()));
    }

    /**
     * Checks if a book with the given ID exists
     * 
     * @param id The ID to check
     * @return true if exists, false otherwise
     */
    public boolean existsById(int id) {
        return books.stream()
                .anyMatch(book -> book.getId() == id);
    }

    /**
     * Gets the next available ID
     * 
     * @return The next ID to assign
     */
    public int getNextId() {
        return nextId;
    }

    /**
     * Deletes a book by ID
     * 
     * @param id The ID of the book to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteById(int id) {
        return books.removeIf(book -> book.getId() == id);
    }

    /**
     * Updates an existing book
     * 
     * @param book The book to update
     * @return The updated book if found, null otherwise
     */
    public Book update(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == book.getId()) {
                books.set(i, book);
                return book;
            }
        }
        return null;
    }

    /**
     * Searches books by a text query in all fields (case insensitive)
     * 
     * @param query The search text
     * @return List of books matching the query
     */
    public List<Book> searchByText(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchTerm = query.toLowerCase().trim();

        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(searchTerm) ||
                        book.getAuthor().getName().toLowerCase().contains(searchTerm) ||
                        book.getCategory().getName().toLowerCase().contains(searchTerm) ||
                        book.getPublisher().getName().toLowerCase().contains(searchTerm) ||
                        String.valueOf(book.getYear()).contains(searchTerm))
                .toList();
    }
}