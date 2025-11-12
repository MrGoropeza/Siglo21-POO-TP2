package biblioteca.domain.exceptions;

/**
 * Exception thrown when notification sending fails.
 * Extends LibraryException to maintain exception hierarchy.
 * 
 * Demonstrates exception inheritance.
 */
public class NotificationSendException extends LibraryException {

    /**
     * Constructor with message
     * 
     * @param message Error message
     */
    public NotificationSendException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * 
     * @param message Error message
     * @param cause   Root cause exception
     */
    public NotificationSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
