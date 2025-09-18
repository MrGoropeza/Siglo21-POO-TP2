package biblioteca.data.dummy;

import java.util.List;

import biblioteca.domain.entities.Category;

/**
 * Dummy data provider for categories
 */
public class CategoryDummyData {

    /**
     * Returns a list of dummy categories in Spanish
     */
    public static List<Category> getCategories() {
        return List.of(
                new Category(1, "Ficción"),
                new Category(2, "No Ficción"),
                new Category(3, "Ciencia"),
                new Category(4, "Historia"),
                new Category(5, "Biografía"),
                new Category(6, "Poesía"),
                new Category(7, "Drama"),
                new Category(8, "Ensayo"),
                new Category(9, "Filosofía"),
                new Category(10, "Psicología"),
                new Category(11, "Arte"),
                new Category(12, "Literatura Clásica"),
                new Category(13, "Literatura Contemporánea"),
                new Category(14, "Ciencia Ficción"),
                new Category(15, "Misterio y Suspenso"),
                new Category(16, "Romance"),
                new Category(17, "Aventura"),
                new Category(18, "Autoayuda"),
                new Category(19, "Educación"),
                new Category(20, "Tecnología"));
    }
}