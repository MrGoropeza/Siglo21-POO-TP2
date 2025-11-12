package biblioteca.application.usecases.report;

/**
 * Result DTO for report generation
 */
public class GenerateReportResult {

    /**
     * Create success result
     * 
     * @param message Success message
     * @return success result
     */
    public static GenerateReportResult success(String message) {
        return new GenerateReportResult(true, message);
    }

    /**
     * Create error result
     * 
     * @param message Error message
     * @return error result
     */
    public static GenerateReportResult error(String message) {
        return new GenerateReportResult(false, message);
    }

    private final boolean success;

    private final String message;

    /**
     * Private constructor - use factory methods
     * 
     * @param success Operation success status
     * @param message Result message
     */
    private GenerateReportResult(boolean success, String message) {
        this.success = success;
        this.message = message;
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
}
