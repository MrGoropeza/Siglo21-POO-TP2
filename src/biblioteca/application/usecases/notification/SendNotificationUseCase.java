package biblioteca.application.usecases.notification;

import biblioteca.data.database.NotificationRepository;
import biblioteca.domain.notifications.ConsoleNotification;
import biblioteca.domain.notifications.EmailNotification;
import biblioteca.domain.notifications.Notification;
import biblioteca.domain.notifications.SMSNotification;

/**
 * Use case for sending notifications through different channels.
 * Demonstrates polymorphism and exception handling.
 */
public class SendNotificationUseCase {

    /**
     * Notification channel enum
     */
    public enum NotificationChannel {
        CONSOLE("Consola"),
        EMAIL("Email"),
        SMS("SMS");

        private final String displayName;

        NotificationChannel(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private final NotificationRepository notificationRepository;

    private int notificationCounter = 1;

    /**
     * Constructor with dependency injection
     * 
     * @param notificationRepository Notification repository
     */
    public SendNotificationUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Send a notification through specified channel
     * 
     * @param request Send notification request
     * @return Result of operation
     */
    public SendNotificationResult execute(SendNotificationRequest request) {
        try {
            // Validate input
            if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
                return SendNotificationResult.error("El mensaje no puede estar vacío");
            }

            if (request.getRecipientInfo() == null || request.getRecipientInfo().trim().isEmpty()) {
                return SendNotificationResult.error("La información del destinatario no puede estar vacía");
            }

            // Generate unique ID
            String notificationId = generateNotificationId();

            // Create notification instance based on channel (Polymorphism)
            Notification notification = createNotification(
                    request.getChannel(),
                    notificationId,
                    request.getMessage(),
                    request.getRecipientInfo());

            // Save notification to repository
            notificationRepository.save(notification);

            // Send notification (calls abstract method)
            boolean sent = notification.deliver();

            if (sent) {
                return SendNotificationResult.success(
                        "Notificación enviada exitosamente por " + notification.getChannelName(),
                        notificationId);
            } else {
                return SendNotificationResult.error(
                        "Error al enviar la notificación por " + notification.getChannelName());
            }

        } catch (IllegalArgumentException e) {
            return SendNotificationResult.error("Canal de notificación inválido: " + e.getMessage());

        } catch (Exception e) {
            return SendNotificationResult.error(
                    "Error inesperado al enviar notificación: " + e.getMessage());
        }
    }

    /**
     * Create notification instance based on channel.
     * Demonstrates polymorphism - returns Notification base type.
     * 
     * @param channel       Notification channel
     * @param id            Notification ID
     * @param message       Message content
     * @param recipientInfo Recipient information
     * @return Notification instance
     * @throws IllegalArgumentException if channel is invalid
     */
    private Notification createNotification(
            NotificationChannel channel,
            String id,
            String message,
            String recipientInfo) {

        return switch (channel) {
            case CONSOLE -> new ConsoleNotification(id, message, recipientInfo);
            case EMAIL -> new EmailNotification(id, message, recipientInfo);
            case SMS -> new SMSNotification(id, message, recipientInfo);
        };
    }

    /**
     * Generate unique notification ID
     * 
     * @return notification ID
     */
    private String generateNotificationId() {
        return "NOTIF-" + String.format("%05d", notificationCounter++);
    }
}
