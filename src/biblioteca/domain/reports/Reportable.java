package biblioteca.domain.reports;

import java.io.IOException;

/**
 * Interface that defines the contract for reportable entities.
 * Any class that can generate a report must implement this interface.
 * 
 * Demonstrates the use of interfaces in object-oriented programming.
 */
public interface Reportable {

    /**
     * Generate the report and save it to a file.
     * 
     * @throws IOException if there's an error writing the file
     */
    void generate() throws IOException;

    /**
     * Get the name of the report.
     * 
     * @return report name
     */
    String getReportName();

    /**
     * Get the description of what the report contains.
     * 
     * @return report description
     */
    String getDescription();
}
