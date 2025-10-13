package biblioteca.data.database;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import biblioteca.domain.entities.Fine;
import biblioteca.domain.entities.Member;

/**
 * Repository for managing fines
 */
public class FineRepository {
    private final List<Fine> fines;
    private int nextId = 1;

    public FineRepository() {
        this.fines = new ArrayList<>();
    }

    /**
     * Save a new fine
     */
    public Fine save(Fine fine) {
        if (fine.getId() == null) {
            // Create a new fine with generated ID
            String newId = generateId();
            Fine fineWithId = new Fine(newId, fine.getMember(), fine.getAmount(), fine.getIssueDate());
            // Preserve payment state if the fine was already paid
            if (fine.isPaid() && fine.getPaidDate() != null) {
                fineWithId.pay(fine.getPaidDate());
            }
            fines.add(fineWithId);
            return fineWithId;
        }
        fines.add(fine);
        return fine;
    }

    /**
     * Find all fines
     */
    public List<Fine> findAll() {
        return new ArrayList<>(fines);
    }

    /**
     * Find fine by ID
     */
    public Fine findById(String id) {
        return fines.stream()
                .filter(fine -> fine.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Find all fines for a specific member
     */
    public List<Fine> findByMember(Member member) {
        return fines.stream()
                .filter(fine -> fine.getMember().equals(member))
                .collect(Collectors.toList());
    }

    /**
     * Find unpaid fines for a specific member
     */
    public List<Fine> findUnpaidByMember(Member member) {
        return fines.stream()
                .filter(fine -> fine.getMember().equals(member))
                .filter(fine -> !fine.isPaid())
                .collect(Collectors.toList());
    }

    /**
     * Find all unpaid fines
     */
    public List<Fine> findAllUnpaid() {
        return fines.stream()
                .filter(fine -> !fine.isPaid())
                .collect(Collectors.toList());
    }

    /**
     * Mark a fine as paid
     */
    public boolean markAsPaid(String fineId) {
        Fine fine = findById(fineId);
        if (fine != null && !fine.isPaid()) {
            fine.pay(java.time.LocalDate.now());
            return true;
        }
        return false;
    }

    /**
     * Calculate total unpaid fines for a member
     */
    public double getTotalUnpaidAmount(Member member) {
        return findUnpaidByMember(member).stream()
                .mapToDouble(Fine::getAmount)
                .sum();
    }

    /**
     * Get count of fines
     */
    public int count() {
        return fines.size();
    }

    /**
     * Clear all fines (for testing)
     */
    public void clear() {
        fines.clear();
        nextId = 1;
    }

    /**
     * Generate unique fine ID
     */
    private String generateId() {
        String id = String.format("FINE%04d", nextId++);
        // Ensure uniqueness
        while (findById(id) != null) {
            id = String.format("FINE%04d", nextId++);
        }
        return id;
    }
}
