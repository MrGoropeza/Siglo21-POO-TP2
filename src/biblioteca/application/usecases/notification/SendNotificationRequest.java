package biblioteca.application.usecases.notification;

import biblioteca.application.usecases.notification.SendNotificationUseCase.NotificationChannel;

/**
 * Request DTO for sending notifications
 */
public class SendNotificationRequest {

    private final NotificationChannel channel;
    private final String message;
    private final String recipientInfo;

    /**
     * Constructor
     * 
     * @param channel       Notification channel
     * @param message       Message content
     * @param recipientInfo Recipient information
     */
    public SendNotificationRequest(
            NotificationChannel channel,
            String message,
            String recipientInfo) {
        this.channel = channel;
        this.message = message;
        this.recipientInfo = recipientInfo;
    }

    /**
     * Get notification channel
     * 
     * @return channel
     */
    public NotificationChannel getChannel() {
        return channel;
    }

    /**
     * Get message content
     * 
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get recipient information
     * 
     * @return recipient info
     */
    public String getRecipientInfo() {
        return recipientInfo;
    }
}
