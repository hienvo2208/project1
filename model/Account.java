package com.javafx.librarian.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {
    private StringProperty username;
    private StringProperty password;
    private IntegerProperty idper;
    private StringProperty name;
    private StringProperty email;

    public static Account currentUser;

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public Account(){};

    public Account(String userName, String password, int idPer, String name, String email){
        this.username = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.idper = new SimpleIntegerProperty(idPer);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
    }

    public Account(String userName, String password){
        this.username = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.idper = new SimpleIntegerProperty(1);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public int getIdper() {
        return idper.get();
    }

    public IntegerProperty idperProperty() {
        return idper;
    }

    public void setIdper(int idper) {
        this.idper.set(idper);
    }
}
