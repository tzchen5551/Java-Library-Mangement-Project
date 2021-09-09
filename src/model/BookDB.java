package model;

import java.sql.*;
import java.util.ArrayList;

public class BookDB {
    Connection connection=null;
    /**+
     * Establishing an connection between the Application and SQL Database
     * @throws SQLException - Error within SQL handling
     */
    public  BookDB() throws SQLException {
        String url = "jdbc:sqlite:C:\\Milestone3.db";;
        connection = DriverManager.getConnection(url);
    }


    public void insert(Book book) throws SQLException {

        String insertString = "INSERT INTO books (name, author, publisher, genre, ISBN, year) VALUES ( ?, ?, ?, ?, ?, ?);";

        PreparedStatement ps = connection.prepareStatement(insertString);

        ps.setString(1, book.getName());
        ps.setString(2, book.getAuthor());
        ps.setString(3, book.getPublisher());
        ps.setString(4, book.getGenre());
        ps.setString(5, book.getISBN());
        ps.setLong(6, book.getYear());
        ps.executeUpdate();

    }

    public void delete(Book book) throws SQLException {

        String deleteString = "DELETE FROM books WHERE ID = ?";

        PreparedStatement ps = connection.prepareStatement(deleteString);

        ps.setInt(1, book.getID());
        ps.executeUpdate();
    }

    public void update(Book book) throws SQLException {

        String insertString = "UPDATE books" +
                "SET name = ?, author = ?, publisher = ?, genre = ?, ISBN = ?, year = ?" +
                "WHERE ID = ?;";

        PreparedStatement ps = connection.prepareStatement(insertString);

        ps.setString(1, book.getName());
        ps.setString(2, book.getAuthor());
        ps.setString(3, book.getPublisher());
        ps.setString(4, book.getGenre());
        ps.setString(5, book.getISBN());
        ps.setLong(6, book.getYear());
        ps.setInt(7, book.getID());

        ps.executeUpdate();

    }

    public ArrayList<Book> getByQuery(String name) throws SQLException {

        ArrayList<Book> result = new ArrayList<>();

        String deleteString = "SELECT name" +
                "FROM books" +
                "WHERE name LIKE %?%;";

        PreparedStatement ps = connection.prepareStatement(deleteString);

        ps.setString(1, name);
        ps.executeUpdate();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            result.add(new Book(rs.getString("name"), rs.getString("author"), rs.getString("publisher"), rs.getString("genre"), rs.getString("ISBN"), rs.getLong("year")));
        }
        return result;
    }

    public Book getBook(int id) throws SQLException {

        Book book;

        String deleteString = "SELECT ID" +
                "FROM books" +
                "WHERE ID = ?;";

        PreparedStatement ps = connection.prepareStatement(deleteString);

        ps.setInt(1, id);
        ps.executeUpdate();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

           book = new Book(rs.getString("name"), rs.getString("author"),rs.getString("publisher"), rs.getString("genre"), rs.getString("ISBN"), rs.getLong("year"));
           return book;
        }

        return null;
    }

    public ArrayList<Book> getAll() throws SQLException {

        ArrayList<Book> result = new ArrayList<>();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM books");

        while (rs.next()) {

            result.add(new Book(rs.getString("name"), rs.getString("author"), rs.getString("publisher"), rs.getString("genre"), rs.getString("ISBN"), rs.getLong("year")));
        }
        return result;
    }

}
