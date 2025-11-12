package biblioteca.domain.notifications;

/**
 * Interface that defines the contract for notifiable entities.
 * Any class that can deliver notifications must implement this interface.
 * 
 * Demonstrates the use of interfaces in object-oriented programming.
 */
public interface Notifiable {

    /**
     * Deliver the notification through the specific channel.
     * 
     * @return true if delivered successfully, false otherwise
     */
    boolean deliver();

    /**
     * Get the name of the notification channel.
     * 
     * @return channel name (e.g., "Consola", "Email", "SMS")
     */
    String getChannelName();

    /**
     * Check if the notification has been sent.
     * 
     * @return true if sent, false if pending
     */
    boolean isSent();
}
