package biblioteca.data.dummy;

import java.util.ArrayList;
import java.util.List;

import biblioteca.domain.entities.Author;
import biblioteca.domain.entities.Book;
import biblioteca.domain.entities.Category;
import biblioteca.domain.entities.Publisher;

/**
 * Dummy data provider for books with complete examples
 */
public class BookDummyData {

    /**
     * Returns a list of dummy books in Spanish with proper relationships
     */
    public static List<Book> getBooks(List<Author> authors, List<Category> categories, List<Publisher> publishers) {
        List<Book> books = new ArrayList<>();

        // Libro 1: Cien años de soledad
        books.add(new Book(1,
                "Cien años de soledad",
                findAuthorByName(authors, "Gabriel García Márquez"),
                findCategoryByName(categories, "Ficción"),
                findPublisherByName(publishers, "Sudamericana"),
                1967));

        // Libro 2: La casa de los espíritus
        books.add(new Book(2,
                "La casa de los espíritus",
                findAuthorByName(authors, "Isabel Allende"),
                findCategoryByName(categories, "Ficción"),
                findPublisherByName(publishers, "Planeta"),
                1982));

        // Libro 3: Rayuela
        books.add(new Book(3,
                "Rayuela",
                findAuthorByName(authors, "Julio Cortázar"),
                findCategoryByName(categories, "Literatura Contemporánea"),
                findPublisherByName(publishers, "Sudamericana"),
                1963));

        // Libro 4: Ficciones
        books.add(new Book(4,
                "Ficciones",
                findAuthorByName(authors, "Jorge Luis Borges"),
                findCategoryByName(categories, "Literatura Clásica"),
                findPublisherByName(publishers, "Emecé"),
                1944));

        // Libro 5: Veinte poemas de amor y una canción desesperada
        books.add(new Book(5,
                "Veinte poemas de amor y una canción desesperada",
                findAuthorByName(authors, "Pablo Neruda"),
                findCategoryByName(categories, "Poesía"),
                findPublisherByName(publishers, "Penguin Random House"),
                1924));

        return books;
    }

    private static Author findAuthorByName(List<Author> authors, String name) {
        return authors.stream()
                .filter(author -> author.getName().equals(name))
                .findFirst()
                .orElse(authors.get(0)); // Fallback to first author
    }

    private static Category findCategoryByName(List<Category> categories, String name) {
        return categories.stream()
                .filter(category -> category.getName().equals(name))
                .findFirst()
                .orElse(categories.get(0)); // Fallback to first category
    }

    private static Publisher findPublisherByName(List<Publisher> publishers, String name) {
        return publishers.stream()
                .filter(publisher -> publisher.getName().equals(name))
                .findFirst()
                .orElse(publishers.get(0)); // Fallback to first publisher
    }
}