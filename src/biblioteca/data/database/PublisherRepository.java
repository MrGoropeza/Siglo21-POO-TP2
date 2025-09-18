package biblioteca.data.database;

import java.util.ArrayList;
import java.util.List;

import biblioteca.domain.entities.Publisher;

/**
 * Repository for Publisher entities - handles CRUD operations
 */
public class PublisherRepository {
    private List<Publisher> publishers = new ArrayList<>();

    public PublisherRepository() {
    }

    public void loadDummyData(List<Publisher> initialData) {
        this.publishers.clear();
        this.publishers.addAll(initialData);
    }

    /**
     * Finds a publisher by ID
     * 
     * @param id The publisher ID
     * @return The publisher if found, null otherwise
     */
    public Publisher findById(int id) {
        return publishers.stream()
                .filter(publisher -> publisher.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all publishers
     * 
     * @return List of all publishers
     */
    public List<Publisher> findAll() {
        return new ArrayList<>(publishers);
    }

    /**
     * Checks if a publisher with the given ID exists
     * 
     * @param id The ID to check
     * @return true if exists, false otherwise
     */
    public boolean existsById(int id) {
        return publishers.stream()
                .anyMatch(publisher -> publisher.getId() == id);
    }

    /**
     * Finds publishers by name (partial match, case insensitive)
     * 
     * @param name The name to search for
     * @return List of matching publishers
     */
    public List<Publisher> findByNameContaining(String name) {
        return publishers.stream()
                .filter(publisher -> publisher.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    /**
     * Saves a new publisher
     * 
     * @param publisher The publisher to save
     * @return The saved publisher
     */
    public Publisher save(Publisher publisher) {
        if (publisher.getId() == 0) {
            int maxId = publishers.stream()
                    .mapToInt(Publisher::getId)
                    .max()
                    .orElse(0);
            publisher.setId(maxId + 1);
        }
        publishers.add(publisher);
        return publisher;
    }
}