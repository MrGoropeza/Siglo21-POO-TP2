package biblioteca.data.database;

import java.util.ArrayList;
import java.util.List;

import biblioteca.domain.entities.Book;
import biblioteca.domain.entities.Copy;
import biblioteca.domain.enums.CopyOrigin;
import biblioteca.domain.enums.CopyState;

/**
 * Repository for Copy entities - handles CRUD operations for book copies
 */
public class CopyRepository {
    private List<Copy> copies = new ArrayList<>();
    private int nextCodeNumber = 1;

    public CopyRepository() {
    }

    public void loadDummyData(List<Copy> initialData) {
        this.copies.clear();
        this.copies.addAll(initialData);
        updateNextCodeNumber();
    }

    /**
     * Generates multiple copies for a book
     * 
     * @param book     The book to create copies for
     * @param quantity Number of copies to create
     * @param origin   Origin of the copies (PURCHASE or DONATION)
     * @return List of created copies
     */
    public List<Copy> createCopies(Book book, int quantity, CopyOrigin origin) {
        List<Copy> newCopies = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            String code = generateUniqueCode(book);
            Copy copy = new Copy(code, CopyState.AVAILABLE, origin, book);
            copies.add(copy);
            newCopies.add(copy);
        }

        return newCopies;
    }

    /**
     * Finds a copy by its unique code
     * 
     * @param code The copy code
     * @return The copy if found, null otherwise
     */
    public Copy findByCode(String code) {
        return copies.stream()
                .filter(copy -> copy.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds all copies of a specific book
     * 
     * @param book The book to find copies for
     * @return List of copies for the book
     */
    public List<Copy> findByBook(Book book) {
        return copies.stream()
                .filter(copy -> copy.getBook().getId() == book.getId())
                .toList();
    }

    /**
     * Finds copies by state
     * 
     * @param state The state to filter by
     * @return List of copies with the specified state
     */
    public List<Copy> findByState(CopyState state) {
        return copies.stream()
                .filter(copy -> copy.getState() == state)
                .toList();
    }

    /**
     * Finds available copies of a specific book
     * 
     * @param book The book to find available copies for
     * @return List of available copies
     */
    public List<Copy> findAvailableCopiesByBook(Book book) {
        return copies.stream()
                .filter(copy -> copy.getBook().getId() == book.getId())
                .filter(Copy::isAvailable)
                .toList();
    }

    /**
     * Counts copies by book and state
     * 
     * @param book  The book to count copies for
     * @param state The state to count
     * @return Number of copies in the specified state
     */
    public long countByBookAndState(Book book, CopyState state) {
        return copies.stream()
                .filter(copy -> copy.getBook().getId() == book.getId())
                .filter(copy -> copy.getState() == state)
                .count();
    }

    /**
     * Updates a copy
     * 
     * @param copy The copy to update
     * @return The updated copy if found, null otherwise
     */
    public Copy update(Copy copy) {
        for (int i = 0; i < copies.size(); i++) {
            if (copies.get(i).getCode().equals(copy.getCode())) {
                copies.set(i, copy);
                return copy;
            }
        }
        return null;
    }

    /**
     * Retrieves all copies
     * 
     * @return List of all copies
     */
    public List<Copy> findAll() {
        return new ArrayList<>(copies);
    }

    /**
     * Checks if any copies exist for a book with loaned or reserved state
     * 
     * @param book The book to check
     * @return true if there are active copies (loaned or reserved)
     */
    public boolean hasActiveCopies(Book book) {
        return copies.stream()
                .filter(copy -> copy.getBook().getId() == book.getId())
                .anyMatch(copy -> copy.getState() == CopyState.LOANED || copy.getState() == CopyState.RESERVED);
    }

    /**
     * Deletes all copies of a book
     * 
     * @param book The book to delete copies for
     * @return Number of copies deleted
     */
    public int deleteByBook(Book book) {
        int initialSize = copies.size();
        copies.removeIf(copy -> copy.getBook().getId() == book.getId());
        return initialSize - copies.size();
    }

    private String generateUniqueCode(Book book) {
        String prefix = "LIB";
        String bookId = String.format("%04d", book.getId());
        String copyNumber = String.format("%03d", nextCodeNumber++);
        return prefix + bookId + copyNumber;
    }

    private void updateNextCodeNumber() {
        if (!copies.isEmpty()) {
            // Find the highest copy number from existing codes
            int maxNumber = copies.stream()
                    .mapToInt(copy -> extractCopyNumber(copy.getCode()))
                    .max()
                    .orElse(0);
            nextCodeNumber = maxNumber + 1;
        }
    }

    private int extractCopyNumber(String code) {
        try {
            // Extract the last 3 digits from codes like "LIB0001001"
            if (code.length() >= 3) {
                String numberPart = code.substring(code.length() - 3);
                return Integer.parseInt(numberPart);
            }
        } catch (NumberFormatException e) {
            // Ignore invalid codes
        }
        return 0;
    }
}