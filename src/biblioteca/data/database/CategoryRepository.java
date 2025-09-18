package biblioteca.data.database;

import java.util.ArrayList;
import java.util.List;

import biblioteca.domain.entities.Category;

/**
 * Repository for Category entities - handles CRUD operations
 */
public class CategoryRepository {
    private List<Category> categories = new ArrayList<>();

    public CategoryRepository() {
        // Test data
        categories.add(new Category(1, "Fiction"));
        categories.add(new Category(2, "Non-Fiction"));
        categories.add(new Category(3, "Science"));
        categories.add(new Category(4, "History"));
        categories.add(new Category(5, "Biography"));
        categories.add(new Category(6, "Poetry"));
        categories.add(new Category(7, "Drama"));
        categories.add(new Category(8, "Essay"));
    }

    /**
     * Finds a category by ID
     * 
     * @param id The category ID
     * @return The category if found, null otherwise
     */
    public Category findById(int id) {
        return categories.stream()
                .filter(category -> category.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all categories
     * 
     * @return List of all categories
     */
    public List<Category> findAll() {
        return new ArrayList<>(categories);
    }

    /**
     * Checks if a category with the given ID exists
     * 
     * @param id The ID to check
     * @return true if exists, false otherwise
     */
    public boolean existsById(int id) {
        return categories.stream()
                .anyMatch(category -> category.getId() == id);
    }

    /**
     * Finds categories by name (partial match, case insensitive)
     * 
     * @param name The name to search for
     * @return List of matching categories
     */
    public List<Category> findByNameContaining(String name) {
        return categories.stream()
                .filter(category -> category.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    /**
     * Saves a new category
     * 
     * @param category The category to save
     * @return The saved category
     */
    public Category save(Category category) {
        if (category.getId() == 0) {
            int maxId = categories.stream()
                    .mapToInt(Category::getId)
                    .max()
                    .orElse(0);
            category.setId(maxId + 1);
        }
        categories.add(category);
        return category;
    }
}