package model;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Library {
    private int MAX_BOOK_LIMIT=3;
    private int MAX_LOAN_DAYS=14;
    private boolean DEBUG_DATE=true;

   private TranscationDB transactions;
    private UserDB users;
    private BookDB books;
    public ArrayList<String> msgLog;

    public ArrayList<Transaction> getTransactions() throws SQLException {

        return transactions.getAll();
    }

    public ArrayList<User> getUsers() throws SQLException {
        return users.getAll();
    }

    public ArrayList<Book> getBooks() throws SQLException {
        return books.getAll();
    }

    public Library() throws SQLException {
        transactions = new TranscationDB();
        users = new UserDB();
        books = new BookDB();
        msgLog =new ArrayList<String>();

    }

    public void addBook(Book b) throws SQLException {
        books.insert(b);
        msgLog.add("Book ID "+b.getID()+" added to Library");
    }
    public void addUser(User u) throws SQLException {
        users.insert(u);
        msgLog.add("User ID "+u.getID()+" added to Library");
    }
    public void addTransaction(Transaction t) throws SQLException {

        transactions.insert(t);
    }
    public boolean isAvailable(int bookID) throws SQLException {
        for(Transaction t:transactions.getAll()){
            if (t.getBookID() == bookID && t.isStatus())
                return false;
        }
        return true;

    }
    public int getBorrowCount(int userID) throws SQLException {
        int count=0;
        for(Transaction t: transactions.getAll()){
            if (t.getUserID()== userID && t.isStatus())
                count++;
        }
        return count;
    }

    public LocalDate getDueDate(LocalDate issueDate) {

        return issueDate.plusDays(MAX_LOAN_DAYS);
    }


    public boolean issueBook(int userID, int bookID) throws SQLException {
        User u = getUser(userID);
        Book b = getBook(bookID);

        //check if book is unavailable
        if (!isAvailable(bookID)){
            msgLog.add("This book is currently unavailable!");
            return false;
        }

        //check if max books limit has been reached or outstanding fines
        if (getBorrowCount(userID) >= MAX_BOOK_LIMIT) {
            msgLog.add("User has reached the maximum limit of borrowed books!");
            return false;
        }
        //check if user has outstanding balance
        if (u.getBalance() > 0){
            msgLog.add("User has an outstanding balance of $"+u.getBalance()+"!");
            return false;
        }

        // ready to issue book.
        Transaction trans = new Transaction(bookID,userID);
        addTransaction(trans);
        msgLog.add(b.getName()+" has been issued to "+u.getName()+"." );
        msgLog.add("The due date is "+getDueDate(trans.getIssueDate()));
        return true;
    }

    public boolean returnBook(int bookID) throws SQLException {
        Transaction trans=null;
        for (Transaction t: transactions.getAll())
            if (t.getBookID()==bookID && t.isStatus()){
                trans = t;
                break;
            }
        if (trans==null){
            msgLog.add("Book currently not borrowed");
            return false;
        }

        //compute Fines
        int userID = trans.getUserID();
        User u = getUser(userID);
        Book b = getBook(bookID);
        double fine = computeFine(trans);
        //System.out.println(fi)
        u.setBalance(u.getBalance()+fine);
        trans.setStatus(false);
        if(fine==0)
            msgLog.add("Thanks for returning "+b.getName()+"!");
        else {
                msgLog.add("You returned " + b.getName() + " " + fine + " days late!");
                msgLog.add("Your outstanding balance is $" + u.getBalance());
        }
        return true;

    }

    public void collectFine(User u){
        if (u.getBalance()<=0){
            msgLog.add("User has no outstanding balances..");
        }
        else{
            msgLog.add("User dues collected....");
            u.setBalance(0);
        }

    }

    private double computeFine(Transaction t)
    {
        if (DEBUG_DATE)
            return new Random().nextInt(20);

        Period interval = Period.between(LocalDate.now() ,t.getIssueDate());
        int ms = interval.getDays();
        if (ms<=14) //change in final
            return 0;
        return ms*1.0;


    }

    public ArrayList<Book> searchBook(String name) throws SQLException {
        if(name.equals(""))
            return books.getAll();

        ArrayList<Book> results= new ArrayList<>();
        for (Book b: books.getAll()) {
            if (b.getName().toLowerCase().contains(name.toLowerCase()))
                results.add(b);
        }

        return results;
    }

    public ArrayList<User> searchUser(String name) throws SQLException {
        if(name.equals(""))
            return users.getAll();

        ArrayList<User> results= new ArrayList<>();
        for (User b: users.getAll()) {
            if (b.getName().toLowerCase().contains(name.toLowerCase()))
                results.add(b);
        }

        return results;
    }


    public void printBooks(ArrayList<Book> results)
    {
        if (results.size()==0)
            msgLog.add("No books matched the search criteria");
        for (Book b: results) {
            msgLog.add("Book Id : "+b.getID()+" Book Name : "+b.getName());

        }

    }

    public void printUsers(ArrayList<User> results)
    {
        if (results.size()==0)
            msgLog.add("No users matched the search criteria");
        for (User b: results) {
            msgLog.add("User Id : "+b.getID()+" User Name : "+b.getName());

        }

    }


    public Book getBook(int bookID) throws SQLException {
        for (Book temp : books.getAll()){
            if (temp.getID()==bookID){
                return temp;
            }
        }
        return null;
    }

    public User getUser(int userID) throws SQLException {
        for (User temp : users.getAll()){
            if (temp.getID()==userID){
                return temp;
            }
        }
        return null;
    }

    public String getLog(){
        StringBuilder sb= new StringBuilder();

        for(String msg :msgLog){
            sb.append(msg+"\n");
        }
        msgLog.clear();
        return sb.toString();
    }

    public ArrayList<Book> getCheckedOutBooks(User u) throws SQLException {
        ArrayList<Book> res= new ArrayList<>();

        for(Transaction t: transactions.getAll()){
            if(t.getUserID() == u.getID() && t.isStatus()){
                res.add(getBook(t.getBookID()));
            }
        }

        return res;
    }

    public ArrayList<Transaction> getActiveTransactions() throws SQLException {
        ArrayList<Transaction> res= new ArrayList<>();

        for(Transaction t: transactions.getAll()){
            if(t.isStatus()){
                res.add(t);
            }
        }

        return res;
    }



}
