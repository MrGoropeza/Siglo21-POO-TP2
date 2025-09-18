package biblioteca.data.dummy;

import java.util.List;

import biblioteca.domain.entities.Author;

/**
 * Dummy data provider for authors
 */
public class AuthorDummyData {

    /**
     * Returns a list of dummy authors in Spanish
     */
    public static List<Author> getAuthors() {
        return List.of(
                new Author(1, "Gabriel García Márquez"),
                new Author(2, "Isabel Allende"),
                new Author(3, "Mario Vargas Llosa"),
                new Author(4, "Jorge Luis Borges"),
                new Author(5, "Octavio Paz"),
                new Author(6, "Pablo Neruda"),
                new Author(7, "Julio Cortázar"),
                new Author(8, "Carlos Fuentes"),
                new Author(9, "Laura Esquivel"),
                new Author(10, "Sor Juana Inés de la Cruz"),
                new Author(11, "Roberto Bolaño"),
                new Author(12, "Elena Poniatowska"),
                new Author(13, "Juan Rulfo"),
                new Author(14, "Rosario Castellanos"),
                new Author(15, "Clarice Lispector"));
    }
}