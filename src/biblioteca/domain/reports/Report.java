package biblioteca.domain.reports;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Abstract base class for all report types.
 * Demonstrates inheritance, interfaces, and Template Method Pattern.
 * 
 * Each report generates a text file with structured information.
 * Subclasses must implement specific data preparation and content writing.
 */
public abstract class Report implements Reportable {
    protected String filename;
    protected LocalDateTime generatedAt;
    protected StringBuilder content;
    protected String reportTitle;

    /**
     * Constructor for report
     * 
     * @param reportTitle Title of the report
     */
    public Report(String reportTitle) {
        this.reportTitle = reportTitle;
        this.generatedAt = LocalDateTime.now();
        this.content = new StringBuilder();
        this.filename = generateFilename();
    }

    /**
     * Template method for report generation.
     * Defines the skeleton of the algorithm.
     * 
     * @throws IOException if file writing fails
     */
    public final void generate() throws IOException {
        prepareData();
        writeHeader();
        writeContent();
        writeFooter();
        saveToFile();
    }

    /**
     * Get the name of the report.
     * Must be implemented by subclasses.
     * 
     * @return report name
     */
    public abstract String getReportName();

    /**
     * Get the description of what the report contains.
     * Must be implemented by subclasses.
     * 
     * @return report description
     */
    public abstract String getDescription();

    /**
     * Get the generated filename
     * 
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Get the report title
     * 
     * @return report title
     */
    public String getReportTitle() {
        return reportTitle;
    }

    /**
     * Get generation timestamp
     * 
     * @return LocalDateTime when report was generated
     */
    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    /**
     * Prepare data for the report.
     * Must be implemented by subclasses.
     */
    protected abstract void prepareData();

    /**
     * Write specific content for the report.
     * Must be implemented by subclasses.
     */
    protected abstract void writeContent();

    /**
     * Write report header with title and generation date
     */
    protected void writeHeader() {
        content.append("═".repeat(80)).append("\n");
        content.append(centerText(reportTitle, 80)).append("\n");
        content.append("═".repeat(80)).append("\n");
        content.append("Fecha de generación: ").append(formatDateTime(generatedAt)).append("\n");
        content.append("─".repeat(80)).append("\n\n");
    }

    /**
     * Write report footer
     */
    protected void writeFooter() {
        content.append("\n");
        content.append("─".repeat(80)).append("\n");
        content.append("Fin del reporte\n");
        content.append("═".repeat(80)).append("\n");
    }

    /**
     * Save report content to file
     * 
     * @throws IOException if file writing fails
     */
    protected void saveToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content.toString());
        }
    }

    /**
     * Format date time for display
     * 
     * @param dateTime LocalDateTime to format
     * @return formatted string
     */
    protected String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    /**
     * Center text within specified width
     * 
     * @param text  Text to center
     * @param width Total width
     * @return centered text
     */
    protected String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }

    /**
     * Generate filename based on report title and timestamp
     * 
     * @return filename in format: reportname_YYYY-MM-DD_HH-mm-ss.txt
     */
    private String generateFilename() {
        String timestamp = generatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String sanitizedTitle = reportTitle.toLowerCase()
                .replace(" ", "_")
                .replaceAll("[^a-z0-9_]", "");
        return sanitizedTitle + "_" + timestamp + ".txt";
    }
}
