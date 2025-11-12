package biblioteca.domain.notifications;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Abstract base class for all notification types.
 * Demonstrates inheritance, interfaces, and Template Method Pattern.
 * 
 * Each notification has a message and is sent through a specific channel.
 * Subclasses must implement the send() method for their specific channel.
 */
public abstract class Notification implements Notifiable {

    protected String id;
    protected String message;
    protected LocalDateTime createdAt;
    protected LocalDateTime sentAt;
    protected boolean sent;
    protected String recipientInfo;

    /**
     * Constructor for notification
     * 
     * @param id            Unique notification ID
     * @param message       Notification message
     * @param recipientInfo Recipient information (email, phone, user ID, etc.)
     */
    public Notification(String id, String message, String recipientInfo) {
        this.id = id;
        this.message = message;
        this.recipientInfo = recipientInfo;
        this.createdAt = LocalDateTime.now();
        this.sent = false;
    }

    /**
     * Send the notification through the specific channel.
     * Template Method - orchestrates the sending process.
     * 
     * @return true if sent successfully
     */
    public final boolean deliver() {
        if (sent) {
            return false; // Already sent
        }

        try {
            boolean success = send();
            if (success) {
                this.sent = true;
                this.sentAt = LocalDateTime.now();
            }
            return success;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the name of the notification channel.
     * Must be implemented by subclasses.
     * 
     * @return channel name (e.g., "Consola", "Email", "SMS")
     */
    public abstract String getChannelName();

    /**
     * Get notification ID
     * 
     * @return notification ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get notification message
     * 
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get creation timestamp
     * 
     * @return created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Get sent timestamp
     * 
     * @return sent at (null if not sent)
     */
    public LocalDateTime getSentAt() {
        return sentAt;
    }

    /**
     * Check if notification was sent
     * 
     * @return true if sent
     */
    public boolean isSent() {
        return sent;
    }

    /**
     * Get recipient information
     * 
     * @return recipient info
     */
    public String getRecipientInfo() {
        return recipientInfo;
    }

    /**
     * Format date time for display
     * 
     * @param dateTime Date time to format
     * @return formatted string
     */
    public String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%s) - Enviado: %s",
                getChannelName(),
                id,
                message.substring(0, Math.min(50, message.length())) + "...",
                recipientInfo,
                sent ? "Sí" : "No");
    }

    /**
     * Abstract method to send notification through specific channel.
     * Must be implemented by concrete notification classes.
     * 
     * @return true if sent successfully
     */
    protected abstract boolean send();
}
