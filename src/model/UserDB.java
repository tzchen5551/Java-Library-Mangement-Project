package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserDB {
    Connection connection=null;
    /**+
     * Establishing an connection between the Application and SQL Database
     * @throws SQLException - Error within SQL handling
     */
    public  UserDB() throws SQLException {
        String url = "jdbc:sqlite:C:\\Milestone3.db";;
        connection = DriverManager.getConnection(url);
    }

    public void insert(User user) throws SQLException {

        String insertString = "INSERT INTO users (name, email, address, dateOfBirth, isStudent, balance) VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement ps = connection.prepareStatement(insertString);

        LocalDate localDate = user.getDateOfBirth();

        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

        ps.setString(1, user.getName());
        ps.setString(2, user.getAddress());
        ps.setString(3, user.getEmail());
        ps.setDate(4, sqlDate);
        ps.setBoolean(5, user.getStudent());
        ps.setDouble(6, user.getBalance());
        ps.executeUpdate();

    }

    public void delete(User user) throws SQLException {

        String deleteString = "DELETE FROM users WHERE ID = ?";

        PreparedStatement ps = connection.prepareStatement(deleteString);

        ps.setInt(1, user.getID());
        ps.executeUpdate();
    }

    public void update(User user) throws SQLException {

        String insertString = "UPDATE users" +
                "SET name = ?, email = ?, address = ?, dateOfBirth = ?, isStudent = ?, balance = ?" +
                "WHERE ID = ?;";

        PreparedStatement ps = connection.prepareStatement(insertString);

        LocalDate localDate = user.getDateOfBirth();

        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

        ps.setString(1, user.getName());
        ps.setString(2, user.getAddress());
        ps.setString(3, user.getEmail());
        ps.setDate(4, sqlDate);
        ps.setBoolean(5, user.getStudent());
        ps.setDouble(6, user.getBalance());
        ps.setInt(7, user.getID());

        ps.executeUpdate();

    }

    public ArrayList<User> getByQuery(String name) throws SQLException {

        ArrayList<User> result = new ArrayList<>();

        String deleteString = "SELECT name" +
                "FROM users" +
                "WHERE name LIKE %?%;";

        PreparedStatement ps = connection.prepareStatement(deleteString);

        ps.setString(1, name);
        ps.executeUpdate();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            //Get SQL date instance
            java.sql.Date sqlDate = new java.sql.Date(rs.getLong("dateOfBirth"));

            //Get LocalDate from SQL date
            LocalDate localDate = sqlDate.toLocalDate();

            result.add(new User(rs.getString("name"), rs.getString("email"), rs.getString("address"), localDate, rs.getBoolean("isStudent")));
        }
        return result;
    }

    public User getUser(int id) throws SQLException {

        User user;

        String deleteString = "SELECT ID" +
                "FROM users" +
                "WHERE ID = ?;";

        PreparedStatement ps = connection.prepareStatement(deleteString);

        ps.setInt(1, id);
        ps.executeUpdate();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            //Get SQL date instance
            java.sql.Date sqlDate = new java.sql.Date(rs.getLong("dateOfBirth"));

            //Get LocalDate from SQL date
            LocalDate localDate = sqlDate.toLocalDate();

            user = new User(rs.getString("name"), rs.getString("email"), rs.getString("address"), localDate, rs.getBoolean("isStudent"));
            return user;
        }

        return null;
    }

    public ArrayList<User> getAll() throws SQLException {

        ArrayList<User> result = new ArrayList<>();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        while (rs.next()) {

            //Get SQL date instance
            java.sql.Date sqlDate = new java.sql.Date(rs.getLong("dateOfBirth"));

            //Get LocalDate from SQL date
            LocalDate localDate = sqlDate.toLocalDate();

            result.add(new User(rs.getString("name"), rs.getString("email"), rs.getString("address"), localDate, rs.getBoolean("isStudent")));
        }
        return result;
    }

}
