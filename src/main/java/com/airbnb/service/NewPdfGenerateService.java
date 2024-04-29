package com.airbnb.service;

import com.airbnb.entity.Booking;
import com.airbnb.entity.Location;
import com.airbnb.entity.Property;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;


import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
@Service

public class NewPdfGenerateService {
    public boolean generatePDF(Booking booking, String filePath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            addContent(document,booking);
            document.close();
            System.out.println("PDF created successfully at: " + filePath);
            return true;
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;

    }


    private void addContent(Document document, Booking booking) throws DocumentException {
        PdfPTable table = new PdfPTable(2); // 2 columns
        table.setWidthPercentage(100);

        // Add "Booking Details" header row
        PdfPCell headerCell = new PdfPCell(new Phrase("Booking Details:"));
        headerCell.setColspan(2); // Set colspan to span across both columns
        headerCell.setBackgroundColor(BaseColor.GRAY);
        table.addCell(headerCell);

        // Add booking details
        addRow(table, "Booking-Id:", String.valueOf(booking.getId()));
        addRow(table,"First Name:",booking.getGuestName());
        addRow(table, "Total Price:", String.valueOf(booking.getTotalPrice()));
        addRow(table, "Check-in Date:", String.valueOf(booking.getCheckInDate()));
        addRow(table, "Check-out Date:", String.valueOf(booking.getCheckOutDate()));
        Property property = booking.getProperty();
        if (property != null) {
            addRow(table, "Property Name:", property.getPropertyName());
            if (property.getLocation() != null) {
                addRow(table, "Location:", property.getLocation().getLocationName());
            }
            addRow(table, "Property Country:", property.getCountry().getCountryName());
            addRow(table, "Total Guests:", property.getGuests().toString());
            // Add more property details as needed
        }

        document.add(table);
    }

    private void addRow(PdfPTable table, String field, String value) {
        table.addCell(field);
        table.addCell(value);


}
}
