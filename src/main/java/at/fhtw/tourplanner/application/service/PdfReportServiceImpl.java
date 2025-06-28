package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.application.service.dto.TourLogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfReportServiceImpl implements PdfReportService {

    private final TourService tourService;
    private final TourLogService tourLogService;

    @Override
    public byte[] generateTourReport(String tourId) {
        TourDto tour = tourService.getTour(new at.fhtw.tourplanner.domain.model.TourId(java.util.UUID.fromString(tourId)))
                .orElseThrow(() -> new IllegalArgumentException("Tour not found"));

        List<TourLogDto> logs = tourLogService.getTourLogsOfTour(
                new at.fhtw.tourplanner.domain.model.TourId(java.util.UUID.fromString(tourId)), 0, 1000
        ).getContent();

        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            float y = page.getMediaBox().getHeight() - 50;

            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 18);
            content.newLineAtOffset(50, y);
            content.showText("Tour Report: " + tour.name());
            content.endText();

            y -= 30;

            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.newLineAtOffset(50, y);
            content.showText("Description: " + tour.description());
            content.endText();

            y -= 20;
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("From: " + tour.from().city() + ", To: " + tour.to().city());
            content.endText();

            y -= 20;
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("Transport: " + tour.transportType() +
                    ", Distance: " + tour.distance() +
                    ", Estimated Time: " + tour.estimatedTime());
            content.endText();

            y -= 40;
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 14);
            content.newLineAtOffset(50, y);
            content.showText("Tour Logs:");
            content.endText();

            for (TourLogDto log : logs) {
                y -= 20;
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(50, y);
                content.showText("[" + log.duration().startTime() + "] "
                        + "Comment: " + log.comment()
                        + ", Difficulty: " + log.difficulty()
                        + ", Total Time: " + log.duration().duration()
                        + ", Rating: " + log.rating());
                content.endText();
            }

            content.close();
            document.save(baos);
            return baos.toByteArray();

        } catch (IOException e) {
            log.error("Failed to generate PDF", e);
            throw new RuntimeException(e);
        }
    }


    public byte[] generateSummaryReport() {
        int page = 0;
        int size = 1000;
        var tours = tourService.getTours(page, size).getContent();

        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PDPage pageDoc = new PDPage(PDRectangle.A4);
            document.addPage(pageDoc);

            PDPageContentStream content = new PDPageContentStream(document, pageDoc);
            float y = pageDoc.getMediaBox().getHeight() - 50;

            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 18);
            content.newLineAtOffset(50, y);
            content.showText("Summary Report for all Tours");
            content.endText();

            y -= 40;

            for (TourDto tour : tours) {
                List<TourLogDto> logs = tourLogService
                        .getTourLogsOfTour(
                                new at.fhtw.tourplanner.domain.model.TourId(java.util.UUID.
                                        fromString(String.valueOf(tour.id()))),
                                0, 1000)
                        .getContent();

                double avgTime = logs.stream()
                        .mapToLong(log -> log.duration().duration())
                        .average()
                        .orElse(0);

                double avgDistance = logs.stream()
                        .mapToDouble(TourLogDto::distance)
                        .average().orElse(0);

                double avgRating = logs.stream()
                        .mapToDouble(TourLogDto::rating)
                        .average().orElse(0);

                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 12);
                content.newLineAtOffset(50, y);
                content.showText("Tour: " + tour.name());
                content.endText();

                y -= 15;
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(50, y);
                content.showText("Avg Time: " + avgTime +
                        " | Avg Distance: " + avgDistance +
                        " | Avg Rating: " + avgRating);
                content.endText();

                y -= 25;
                if (y < 100) {
                    content.close();
                    pageDoc = new PDPage(PDRectangle.A4);
                    document.addPage(pageDoc);
                    content = new PDPageContentStream(document, pageDoc);
                    y = pageDoc.getMediaBox().getHeight() - 50;
                }
            }

            content.close();
            document.save(baos);
            return baos.toByteArray();

        } catch (IOException e) {
            log.error("Failed to generate summary PDF", e);
            throw new RuntimeException(e);
        }
    }
}