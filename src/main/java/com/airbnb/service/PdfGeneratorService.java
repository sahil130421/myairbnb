package com.airbnb.service;

import com.airbnb.entity.Booking;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGeneratorService {

    public void generateBookingPdf(Booking booking, String filePath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Booking Information");
                contentStream.endText();

                float tableTopY = 650;
                float tableBottomY = 50;
                float tableWidth = page.getMediaBox().getWidth() - 100;
                float yPosition = tableTopY;

                // Draw table header
                drawTableHeader(contentStream, yPosition);
                yPosition -= 20;

                // Draw booking information
                drawBookingInfo(contentStream, yPosition, booking);

                contentStream.close();
            }

            document.save(filePath);
        }
    }

    private void drawTableHeader(PDPageContentStream contentStream, float yPosition) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(60, yPosition);
        contentStream.showText("Booking ID");

        contentStream.newLine();


        contentStream.showText("Guest Name");
        contentStream.newLine();
        contentStream.showText("Check-in Date");
        contentStream.newLine();
        contentStream.showText("Check-out Date");
        contentStream.newLine();
        contentStream.showText("Total Price");
        contentStream.endText();
    }

    private void drawBookingInfo(PDPageContentStream contentStream, float yPosition, Booking booking) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(60, yPosition);
        contentStream.showText(booking.getId().toString());
        contentStream.newLine();
        contentStream.showText(booking.getGuestName());
        contentStream.newLine();
        contentStream.showText(booking.getCheckInDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        contentStream.newLine();
        contentStream.showText(booking.getCheckOutDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        contentStream.newLine();
        contentStream.showText("$" + booking.getTotalPrice());
        contentStream.endText();
    }
}