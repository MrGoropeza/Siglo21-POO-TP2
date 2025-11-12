package biblioteca.domain.exceptions;

/**
 * Base exception for all library system exceptions.
 * Provides common functionality for all custom exceptions.
 */
public class LibraryException extends Exception {

    /**
     * Constructor with message
     * 
     * @param message Error message
     */
    public LibraryException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * 
     * @param message Error message
     * @param cause   Original exception
     */
    public LibraryException(String message, Throwable cause) {
        super(message, cause);
    }
}
