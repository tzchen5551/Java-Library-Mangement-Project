package controller;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Book;
import model.Library;
import model.User;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main extends Application {
    Library library = new Library();
    public static Stage primaryStage;

    public Main() throws SQLException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        User u1 = new User("John Stones","jstones@gmail.com","445 Conradi St. Tallahassee, FL 32304", LocalDate.of(1989,06,13), true);
        User u2 = new User("Jack Bauer","jack24@gmail.com","7400 Bay Rd. University Center, MI 48710",LocalDate.of(1988,11,15),false);
        User u3 = new User("Harry Kane","hkane@gmail.com","123 James Boyd Rd. Scranton, PA 28410",LocalDate.of(1988,2,1), false);
        User u4 = new User("Tim Arnold","ta123@gmail.com","3412 Dinsmore Ave, MA 01710",LocalDate.of(1999,1,15), true);

        Book b1 = new Book("Programming with Java","Daniel Liang","Pearson","Education","1234568924",2020);
        Book b2 = new Book("Data Structures and Algorithms","Robert Lafore","Pearson","Education","98726213",2001);
        Book b3 = new Book("Harry Potter and The Chamber of Secrets","J.K. Rowling","Scholastic","Adventure","343255323",1998);
        Book b4 = new Book("Lord of the Rings - The Two Towers","Tolkien","Wiley","Thriller","989636362",1945);

        Library library = new Library();

        library.getUsers().add(u1);
        library.getUsers().add(u2);
        library.getUsers().add(u3);
        library.getUsers().add(u4);


        library.getBooks().add(b1);
        library.getBooks().add(b2);
        library.getBooks().add(b3);
        library.getBooks().add(b4);


        Main.primaryStage =primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainMenuPage.fxml"));
        Parent root = loader.load();

        MainMenuPage loginController = loader.getController();
        loginController.initData(library);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 800   , 600));
        primaryStage.show();
    }
    /*
    public void launchLoginScene(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PersonView.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        //access the controller and call a method
        PersonViewController controller = loader.getController();
        controller.initData(tableView.getSelectionModel().getSelectedItem());

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    */

    public static void main(String[] args) {
        launch(args);
    }
}
