package model;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private int ID;
    private String name;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
    private boolean isStudent;
    private double balance=0.0;


    public User(String name, String email, String address, LocalDate dateOfBirth, boolean isStudent) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.isStudent = isStudent;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }

    public boolean getStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        this.isStudent = student;
    }

    @Override
    public String toString() {
        return "ID: "+getID()+" Name: "+ getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.ID == user.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, email, address, dateOfBirth, isStudent, balance);
    }
}