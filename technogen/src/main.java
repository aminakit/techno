import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class main {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test4";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public static void main(String[] args) throws SQLException {
        // Подключение к базе данных
        java.util.Properties info = new java.util.Properties();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            System.out.println("Успешное подключение к базе данных.");

            System.out.println("Нажмите 1 для вывода техногенных месторождений");
            System.out.println("Нажмите 2 для вывода состава отходов техногенных месторождений");
            System.out.println("Нажмите 3 для вывода колличества техногенных месторождений в РК");


            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();

            switch (input) {
                case "1":
                    deposit(connection);
                    break;
                case "2":
                    depositwaste(connection);
                    break;
                case "3":
                    technogenicdeposits(connection);
                    break;
                default:
                    throw new SQLException();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void deposit(Connection connection) {
        String queryDepositTypes = "SELECT id, type_name, description FROM DepositTypes";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(queryDepositTypes)) {
            while (resultSet.next()) {
                System.out.printf("ID: %d, Тип: %s, Описание: %s%n",
                        resultSet.getInt("id"),
                        resultSet.getString("type_name"),
                        resultSet.getString("description"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void depositwaste(Connection connection) {
        String querydepositWasteComposition = "SELECT id, deposit_id, component, percentage FROM public.depositwastecomposition";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querydepositWasteComposition)) {
            while (resultSet.next()) {
                System.out.printf("ID: %d, Тип: %s, Описание: %s%n",
                        resultSet.getInt("id"),
                        resultSet.getInt("deposit_id"),
                        resultSet.getString("component"),
                        resultSet.getString("percentage"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void technogenicdeposits (Connection connection) {
        String querytechnogenicdeposits = "SELECT id, name, location, type_id, waste_composition_id, deposit_size FROM public.technogenicdeposits;";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querytechnogenicdeposits)) {
            while (resultSet.next()) {
                System.out.printf("ID: %d, Тип: %s, Описание: %s%n",
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("location"),
                        resultSet.getInt("type_id"),
                        resultSet.getInt("waste_composition_id"),
                        resultSet.getDouble("deposit_size"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
