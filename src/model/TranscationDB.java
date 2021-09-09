package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TranscationDB {
    Connection connection=null;
    /**+
     * Establishing an connection between the Application and SQL Database
     * @throws SQLException - Error within SQL handling
     */
    public  TranscationDB() throws SQLException {
        String url = "jdbc:sqlite:C:\\Milestone3.db";;
        connection = DriverManager.getConnection(url);
    }


    public void insert(Transaction transaction) throws SQLException {

        String insertString = "INSERT INTO libTransactions (bookID, userID, issueDate, status) VALUES (?, ?, ?, ?);";

        PreparedStatement ps = connection.prepareStatement(insertString);

        LocalDate localDate =  transaction.getIssueDate();

        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

        ps.setInt(1, transaction.getBookID());
        ps.setInt(2, transaction.getUserID());
        ps.setDate(3, sqlDate);
        ps.setBoolean(4,false);
        ps.executeUpdate();

    }

    public void delete(Transaction transaction) throws SQLException {

        String deleteString = "DELETE FROM libTransactions WHERE bookID = ? AND userID = ?";

        PreparedStatement ps = connection.prepareStatement(deleteString);

        ps.setInt(1, transaction.getBookID());
        ps.setInt(2, transaction.getUserID());
        ps.executeUpdate();
    }

    public void update(Transaction transaction) throws SQLException {

        String insertString = "UPDATE libTransactions" +
                "SET issueDate = ?, status = ?" +
                "WHERE bookID = ? AND userID = ?;";

        PreparedStatement ps = connection.prepareStatement(insertString);

        LocalDate localDate = transaction.getIssueDate();

        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

        ps.setDate(1, sqlDate);
        ps.setBoolean(2,false);
        ps.setInt(3, transaction.getBookID());
        ps.setInt(4, transaction.getUserID());
        ps.executeUpdate();

    }


    public ArrayList<Transaction> getByUser(int id) throws SQLException {

        Transaction transaction;

        ArrayList<Transaction> result = new ArrayList<>();

        String deleteString = "SELECT userID" +
                "FROM libTransaction" +
                "WHERE userID = ?;";

        PreparedStatement ps = connection.prepareStatement(deleteString);

        ps.setInt(1, id);
        ps.executeUpdate();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            result.add(new Transaction(rs.getInt("bookID"), rs.getInt("userID")));
        }
        return result;
    }

    public ArrayList<Transaction> getByBook(int id) throws SQLException {

        Transaction transaction;

        ArrayList<Transaction> result = new ArrayList<>();

        String deleteString = "SELECT bookID" +
                "FROM libTransaction" +
                "WHERE bookID = ?;";

        PreparedStatement ps = connection.prepareStatement(deleteString);

        ps.setInt(1, id);
        ps.executeUpdate();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            result.add(new Transaction(rs.getInt("bookID"), rs.getInt("userID")));
        }
        return result;
    }

    public ArrayList<Transaction> getAll() throws SQLException {

        ArrayList<Transaction> result = new ArrayList<>();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM libTransactions");

        while (rs.next()) {

            result.add(new Transaction(rs.getInt("bookID"), rs.getInt("userID")));
        }
        return result;
    }

    public ArrayList<Transaction> getCurrent() throws SQLException {

        ArrayList<Transaction> result = new ArrayList<>();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM libTransactions WHERE status = false");

        while (rs.next()) {

            result.add(new Transaction(rs.getInt("bookID"), rs.getInt("userID")));
        }
        return result;
    }

}
