package biblioteca.domain.exceptions;

/**
 * Exception thrown when report generation fails.
 * Used in the reports module to handle generation errors.
 */
public class ReportGenerationException extends LibraryException {

    /**
     * Constructor with message
     * 
     * @param message Error message describing why generation failed
     */
    public ReportGenerationException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * 
     * @param message Error message
     * @param cause   Original exception that caused the failure
     */
    public ReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
