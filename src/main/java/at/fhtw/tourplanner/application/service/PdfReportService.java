package at.fhtw.tourplanner.application.service;

public interface PdfReportService {
    byte[] generateTourReport(String tourId);
    byte[] generateSummaryReport();
}
