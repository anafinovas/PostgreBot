import java.io.ByteArrayOutputStream;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

public class PdfReportSender {

    public static void main(String[] args) {
        // Generate PDF report
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataToPdf.generatePdfFromResultSet(outputStream);

        // Send PDF report via email
        sendEmailWithAttachment("saule.anafinova@gmail.com", "PDF Report", "Please find attached the PDF report.", "report.pdf", outputStream);
        // Send PDF report via email to the second email address
        //sendEmailWithAttachment("alenachzhen1999@gmail.com", "PDF Report", "Please find attached the PDF report.", "report.pdf", outputStream);
    }

    public static void sendEmailWithAttachment(String to, String subject, String body, String attachmentFilename, ByteArrayOutputStream outputStream) {
        // Sender's email ID needs to be mentioned
        String from = "saule.anafinova@gmail.com"; // Update with your email

        // Assuming you are sending email from Gmail
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");

        // Get the default Session object.
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("saule.anafinova@gmail.com", "ypdu pvjj ifib hkfq"); // Update with your email and password
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText(body);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(outputStream.toByteArray(), "application/pdf");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachmentFilename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
