import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TomcatReportBot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        TomcatReportBot bot = new TomcatReportBot();
        bot.run();
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Do nothing for now
    }

    @Override
    public String getBotUsername() {
        return "TomcatReportBot";
    }

    @Override
    public String getBotToken() {
        return "7073523400:AAFnwhrbWRB1axfjNYQM4vM-83T8IRkp9Yw";
    }

    public void run() {
        try {
            // Create a PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataToPdf.generatePdfFromResultSet(outputStream);

            // Send the PDF to Telegram
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId("1239004838"); // Change to your chat ID
            sendDocument.setDocument(new InputFile(new ByteArrayInputStream(outputStream.toByteArray()), "report.pdf"));
            execute(sendDocument);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
