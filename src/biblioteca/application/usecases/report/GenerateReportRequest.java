package biblioteca.application.usecases.report;

import biblioteca.application.usecases.report.GenerateReportUseCase.ReportType;

/**
 * Request DTO for report generation
 */
public class GenerateReportRequest {

    private final ReportType reportType;

    /**
     * Constructor
     * 
     * @param reportType Type of report to generate
     */
    public GenerateReportRequest(ReportType reportType) {
        this.reportType = reportType;
    }

    /**
     * Get report type
     * 
     * @return report type
     */
    public ReportType getReportType() {
        return reportType;
    }
}
