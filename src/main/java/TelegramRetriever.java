import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelegramRetriever {
    private static final String DB_URL = "jdbc:postgresql://192.168.50.142:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "budapest";

    public static void main(String[] args) {
        String telegram = getTelegramForPersonId(1);
        System.out.println("Telegram value for person with id 1: " + telegram);
    }

    public static String getTelegramForPersonId(int id) {
        String telegram = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT person.telegram FROM person WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        telegram = resultSet.getString("telegram");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return telegram;
    }
}
