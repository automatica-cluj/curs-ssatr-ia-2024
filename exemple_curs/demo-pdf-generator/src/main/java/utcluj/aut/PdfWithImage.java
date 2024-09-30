package utcluj.aut;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;

public class PdfWithImage {
    public static void main(String[] args) throws Exception{
        // Define the PDF output file path
        String pdfPath = "pdf_with_image.pdf";
        // Define the image file path
        String imagePath = "img.png";  // Replace with your image path

        try {
            // Create a PdfWriter object
            PdfWriter writer = new PdfWriter(pdfPath);

            // Initialize the PDF document
            PdfDocument pdfDoc = new PdfDocument(writer);

            // Create a document to work with layout
            Document document = new Document(pdfDoc);

            // Add a paragraph to the document
            document.add(new Paragraph("This is a PDF document with an image."));

            // Load the image
            ImageData imageData = ImageDataFactory.create(imagePath);
            Image image = new Image(imageData);

            // Optionally, scale the image to fit the page or set specific dimensions
            image.scaleToFit(200, 200);  // Example: scaling the image

            // Add the image to the document
            document.add(image);

            // Add more content (e.g., another paragraph)
            document.add(new Paragraph("Below the image, we add more text."));

            // Close the document
            document.close();

            System.out.println("PDF created with image successfully at: " + pdfPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
