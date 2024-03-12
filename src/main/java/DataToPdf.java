import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class DataToPdf {

    public static void generatePdfFromResultSet(OutputStream outputStream) {
        // Database connection parameters
        String url = "jdbc:postgresql://192.168.50.142:5432/postgres"; // Update with your database URL
        String user = "postgres";
        String password = "budapest";

        // SQL query to retrieve data
        String query = "SELECT * FROM person";

        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(url, user, password);

            // Create a PDF document
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                // Create a new content stream for adding text
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Define font and font size
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);

                // Execute query and retrieve data
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Write data to the PDF
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700); // Set initial position for text
                int verticalOffset = 0; // Initialize vertical offset
                while (resultSet.next()) {
                    String data = resultSet.getInt("id") + " "
                            + resultSet.getString("name") + " "
                            + resultSet.getString("surname") + " "
                            + resultSet.getString("role") + " "
                            + resultSet.getString("email") + " "
                            + resultSet.getString("telegarm") + " "
                            + resultSet.getString("password");
                    contentStream.newLineAtOffset(0, -verticalOffset); // Move to the next line
                    contentStream.showText(data);
                    verticalOffset += 15; // Increment vertical offset
                }
                contentStream.endText();

                // Make sure to close the content stream
                contentStream.close();

                // Save the document to the provided output stream
                document.save(outputStream);
                System.out.println("PDF created successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
