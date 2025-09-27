package biblioteca.data.database;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import biblioteca.data.dummy.MemberDummyData;
import biblioteca.domain.entities.Member;
import biblioteca.domain.enums.MemberType;

/**
 * Repository for Member entity operations
 */
public class MemberRepository {
    private final List<Member> members;

    public MemberRepository() {
        this.members = new ArrayList<>(MemberDummyData.getMembers());
    }

    /**
     * Saves a new member to the repository
     * 
     * @param member Member to save
     * @return The saved member with generated ID
     */
    public Member save(Member member) {
        if (member.getId() == null || member.getId().isEmpty()) {
            member.setId(MemberDummyData.getNextAvailableId());
        }
        members.add(member);
        return member;
    }

    /**
     * Updates an existing member
     * 
     * @param member Member to update
     * @return The updated member, null if not found
     */
    public Member update(Member member) {
        Member existingMember = findById(member.getId());
        if (existingMember != null) {
            existingMember.setName(member.getName());
            existingMember.setEmail(member.getEmail());
            existingMember.setPhone(member.getPhone());
            existingMember.setType(member.getType());
            return existingMember;
        }
        return null;
    }

    /**
     * Finds a member by ID
     * 
     * @param id Member ID
     * @return Member if found, null otherwise
     */
    public Member findById(String id) {
        return members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Searches members by name (case-insensitive partial match)
     * 
     * @param name Name to search for
     * @return List of matching members
     */
    public List<Member> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchTerm = name.toLowerCase().trim();
        return members.stream()
                .filter(member -> member.getName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Searches members by text (searches in name and email)
     * 
     * @param searchText Text to search for
     * @return List of matching members
     */
    public List<Member> searchByText(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchTerm = searchText.toLowerCase().trim();
        return members.stream()
                .filter(member -> member.getName().toLowerCase().contains(searchTerm) ||
                        member.getEmail().toLowerCase().contains(searchTerm) ||
                        member.getId().equals(searchText))
                .collect(Collectors.toList());
    }

    /**
     * Finds a member by email
     * 
     * @param email Email to search for
     * @return Member if found, null otherwise
     */
    public Member findByEmail(String email) {
        return members.stream()
                .filter(member -> member.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets all members
     * 
     * @return List of all members
     */
    public List<Member> findAll() {
        return new ArrayList<>(members);
    }

    /**
     * Gets members by type
     * 
     * @param type Member type to filter by
     * @return List of members of the specified type
     */
    public List<Member> findByType(MemberType type) {
        return members.stream()
                .filter(member -> member.getType() == type)
                .collect(Collectors.toList());
    }

    /**
     * Checks if a member ID already exists
     * 
     * @param id ID to check
     * @return true if ID exists, false otherwise
     */
    public boolean existsById(String id) {
        return findById(id) != null;
    }

    /**
     * Checks if an email already exists
     * 
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    /**
     * Deletes a member by ID
     * 
     * @param id Member ID to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteById(String id) {
        return members.removeIf(member -> member.getId().equals(id));
    }

    /**
     * Gets the total count of members
     * 
     * @return Total number of members
     */
    public int count() {
        return members.size();
    }

}