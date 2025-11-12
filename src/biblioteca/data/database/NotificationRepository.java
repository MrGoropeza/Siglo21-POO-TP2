package biblioteca.data.database;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import biblioteca.domain.notifications.Notification;

/**
 * Repository for managing notifications in memory.
 * Stores notification history for tracking and auditing.
 */
public class NotificationRepository {

    private final List<Notification> notifications;

    /**
     * Constructor - initializes empty notification list
     */
    public NotificationRepository() {
        this.notifications = new ArrayList<>();
    }

    /**
     * Save a notification to the repository
     * 
     * @param notification Notification to save
     */
    public void save(Notification notification) {
        notifications.add(notification);
    }

    /**
     * Find notification by ID
     * 
     * @param id Notification ID
     * @return Notification if found, null otherwise
     */
    public Notification findById(String id) {
        return notifications.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get all notifications
     * 
     * @return List of all notifications
     */
    public List<Notification> findAll() {
        return new ArrayList<>(notifications);
    }

    /**
     * Get all sent notifications
     * 
     * @return List of sent notifications
     */
    public List<Notification> findAllSent() {
        return notifications.stream()
                .filter(Notification::isSent)
                .collect(Collectors.toList());
    }

    /**
     * Get all pending notifications (not sent yet)
     * 
     * @return List of pending notifications
     */
    public List<Notification> findAllPending() {
        return notifications.stream()
                .filter(n -> !n.isSent())
                .collect(Collectors.toList());
    }

    /**
     * Get notifications by channel name
     * 
     * @param channelName Channel name (e.g., "Email", "SMS", "Consola")
     * @return List of notifications for that channel
     */
    public List<Notification> findByChannel(String channelName) {
        return notifications.stream()
                .filter(n -> n.getChannelName().equalsIgnoreCase(channelName))
                .collect(Collectors.toList());
    }

    /**
     * Get total count of notifications
     * 
     * @return total count
     */
    public int count() {
        return notifications.size();
    }

    /**
     * Get count of sent notifications
     * 
     * @return sent count
     */
    public int countSent() {
        return (int) notifications.stream()
                .filter(Notification::isSent)
                .count();
    }

    /**
     * Get count of pending notifications
     * 
     * @return pending count
     */
    public int countPending() {
        return (int) notifications.stream()
                .filter(n -> !n.isSent())
                .count();
    }

    /**
     * Clear all notifications (for testing purposes)
     */
    public void clear() {
        notifications.clear();
    }

    /**
     * Load dummy data for testing and initialization
     * 
     * @param dummyNotifications List of dummy notifications
     */
    public void loadDummyData(List<Notification> dummyNotifications) {
        notifications.addAll(dummyNotifications);
    }
}
