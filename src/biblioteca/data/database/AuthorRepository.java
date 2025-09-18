package biblioteca.data.database;

import java.util.ArrayList;
import java.util.List;

import biblioteca.domain.entities.Author;

/**
 * Repository for Author entities - handles CRUD operations
 */
public class AuthorRepository {
    private List<Author> authors = new ArrayList<>();

    public AuthorRepository() {
        // Test data
        authors.add(new Author(1, "Gabriel García Márquez"));
        authors.add(new Author(2, "Isabel Allende"));
        authors.add(new Author(3, "Mario Vargas Llosa"));
        authors.add(new Author(4, "Jorge Luis Borges"));
        authors.add(new Author(5, "Octavio Paz"));
    }

    /**
     * Finds an author by ID
     * 
     * @param id The author ID
     * @return The author if found, null otherwise
     */
    public Author findById(int id) {
        return authors.stream()
                .filter(author -> author.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all authors
     * 
     * @return List of all authors
     */
    public List<Author> findAll() {
        return new ArrayList<>(authors);
    }

    /**
     * Checks if an author with the given ID exists
     * 
     * @param id The ID to check
     * @return true if exists, false otherwise
     */
    public boolean existsById(int id) {
        return authors.stream()
                .anyMatch(author -> author.getId() == id);
    }

    /**
     * Finds authors by name (partial match, case insensitive)
     * 
     * @param name The name to search for
     * @return List of matching authors
     */
    public List<Author> findByNameContaining(String name) {
        return authors.stream()
                .filter(author -> author.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    /**
     * Saves a new author
     * 
     * @param author The author to save
     * @return The saved author
     */
    public Author save(Author author) {
        if (author.getId() == 0) {
            int maxId = authors.stream()
                    .mapToInt(Author::getId)
                    .max()
                    .orElse(0);
            author.setId(maxId + 1);
        }
        authors.add(author);
        return author;
    }
}