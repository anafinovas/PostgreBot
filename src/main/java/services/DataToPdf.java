package services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import services.DatabaseConnector;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class DataToPdf {
    public static void generatePdfFromResultSet(OutputStream outputStream) {
        String query = "SELECT * FROM person";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnector.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                int verticalOffset = 0;
                while (resultSet.next()) {
                    String data = resultSet.getInt("id") + " "
                            + resultSet.getString("name") + " "
                            + resultSet.getString("surname") + " "
                            + resultSet.getString("role") + " "
                            + resultSet.getString("email") + " "
                            + resultSet.getString("telegram") + " "
                            + resultSet.getString("password");
                    contentStream.newLineAtOffset(0, -verticalOffset);
                    contentStream.showText(data);
                    verticalOffset += 15;
                }
                contentStream.endText();
                contentStream.close();
                document.save(outputStream);
                System.out.println("PDF created successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnector.close(resultSet);
            DatabaseConnector.close(preparedStatement);
            DatabaseConnector.close(connection);
        }
    }
}
