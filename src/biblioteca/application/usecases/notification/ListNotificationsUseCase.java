package biblioteca.application.usecases.notification;

import java.util.List;

import biblioteca.data.database.NotificationRepository;
import biblioteca.domain.notifications.Notification;

/**
 * Use case for listing notification history
 */
public class ListNotificationsUseCase {

    /**
     * Notification filter enum
     */
    public enum NotificationFilter {
        ALL("Todas"),
        SENT("Enviadas"),
        PENDING("Pendientes");

        private final String displayName;

        NotificationFilter(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * Notification statistics record
     */
    public record NotificationStats(int total, int sent, int pending) {
    }

    private final NotificationRepository notificationRepository;

    /**
     * Constructor with dependency injection
     * 
     * @param notificationRepository Notification repository
     */
    public ListNotificationsUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * List all notifications with optional filtering
     * 
     * @param filter Filter type (ALL, SENT, PENDING)
     * @return List of notifications
     */
    public List<Notification> execute(NotificationFilter filter) {
        return switch (filter) {
            case ALL -> notificationRepository.findAll();
            case SENT -> notificationRepository.findAllSent();
            case PENDING -> notificationRepository.findAllPending();
        };
    }

    /**
     * Get notification statistics
     * 
     * @return Statistics summary
     */
    public NotificationStats getStats() {
        int total = notificationRepository.count();
        int sent = notificationRepository.countSent();
        int pending = notificationRepository.countPending();

        return new NotificationStats(total, sent, pending);
    }
}
