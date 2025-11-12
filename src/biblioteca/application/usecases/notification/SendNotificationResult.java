package biblioteca.application.usecases.notification;

/**
 * Result DTO for notification sending
 */
public class SendNotificationResult {

    /**
     * Create success result
     * 
     * @param message        Success message
     * @param notificationId Notification ID
     * @return success result
     */
    public static SendNotificationResult success(String message, String notificationId) {
        return new SendNotificationResult(true, message, notificationId);
    }

    /**
     * Create error result
     * 
     * @param message Error message
     * @return error result
     */
    public static SendNotificationResult error(String message) {
        return new SendNotificationResult(false, message, null);
    }

    private final boolean success;

    private final String message;

    private final String notificationId;

    /**
     * Private constructor - use factory methods
     * 
     * @param success        Operation success status
     * @param message        Result message
     * @param notificationId Notification ID (if sent)
     */
    private SendNotificationResult(boolean success, String message, String notificationId) {
        this.success = success;
        this.message = message;
        this.notificationId = notificationId;
    }

    /**
     * Check if operation was successful
     * 
     * @return true if successful
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Get result message
     * 
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get notification ID
     * 
     * @return notification ID (null if failed)
     */
    public String getNotificationId() {
        return notificationId;
    }
}
