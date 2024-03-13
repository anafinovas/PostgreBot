package services;

import services.DatabaseConnector;

import java.sql.*;

public class TelegramRetriever {
    public static void main(String[] args) {
        String telegram = getTelegramForPersonId(1);
        System.out.println("Telegram value for person with id 1: " + telegram);
    }

    public static String getTelegramForPersonId(int id) {
        String telegram = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnector.getConnection();
            String sql = "SELECT person.telegram FROM person WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                telegram = resultSet.getString("telegram");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnector.close(resultSet);
            DatabaseConnector.close(statement);
            DatabaseConnector.close(connection);
        }
        return telegram;
    }
}
