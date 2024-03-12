public class Main {

    public static void main(String[] args) {
        // Generate PDF report and send via email
        PdfReportSender.main(args);

        // Start Telegram bot to send PDF report
        TomcatReportBot bot = new TomcatReportBot();
        bot.run();
    }
}
