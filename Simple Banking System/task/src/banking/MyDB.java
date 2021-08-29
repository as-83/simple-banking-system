package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class MyDB {
    SQLiteDataSource dataSource;
    String url;
    public MyDB(String dbName) {
        this.url  = "jdbc:sqlite:" + dbName;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        this.createTable();
    }

    private void createTable() {
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean save(String cardNumber, String pin){
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()){
            statement.executeUpdate("INSERT INTO card(number, pin) " +
                    "VALUES(\"" + cardNumber + "\", \"" +
                    pin + "\")");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean get(String cardNumber, String pin){
        boolean exists = false;
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM card where number=\"" +
                    cardNumber + "\" and pin=\"" +
                    pin + "\" LIMIT 1");
            exists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;

    }

    public void addIncome(int income, String currentCardNum) {
        String updateBalance = "UPDATE card SET balance = balance + ? WHERE number = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateBalance)){
            preparedStatement.setInt(1, income);
            preparedStatement.setString(2, currentCardNum);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getBalance(String currentCardNum) {
        int balance = 0;
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT balance FROM card where number=\"" +
                    currentCardNum  + "\" LIMIT 1");
            balance = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public void deleteCard(String currentCardNum) {
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()){
            statement.execute("DELETE FROM card where number=" + currentCardNum);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean cardExists(String transferCard) {
        boolean exists = false;
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM card where number=" +
                    transferCard +  " LIMIT 1");
            exists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public void transfer(String currentCardNum, String transferCard, int transferSum) {
        String updateBalance = "UPDATE card SET balance = balance+? WHERE number = ?";
        String downBalance = "UPDATE card SET balance = balance-? WHERE number = ?";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement plusMoneyStatement = connection.prepareStatement(updateBalance);
                 PreparedStatement minusMoneyStatement = connection.prepareStatement(downBalance)) {
                plusMoneyStatement.setInt(1, transferSum);
                plusMoneyStatement.setString(2, transferCard);
                minusMoneyStatement.setInt(1, transferSum);
                minusMoneyStatement.setString(2, currentCardNum);
                minusMoneyStatement.executeUpdate();
                plusMoneyStatement.executeUpdate();
                connection.commit();
            }

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                    e.printStackTrace();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
