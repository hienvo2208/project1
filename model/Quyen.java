package com.javafx.librarian.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Quyen {
    private IntegerProperty ID;
    private StringProperty Name;
    private StringProperty Code;
    private StringProperty Description;

    public Quyen(int id, String name, String code, String description) {
        ID = new SimpleIntegerProperty(id);
        Name = new SimpleStringProperty(name);
        Code = new SimpleStringProperty(code);
        Description = new SimpleStringProperty(description);
    }

    public int getID() {
        return ID.get();
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getName() {
        return Name.get();
    }

    public StringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public String getCode() {
        return Code.get();
    }

    public StringProperty codeProperty() {
        return Code;
    }

    public void setCode(String code) {
        this.Code.set(code);
    }

    public String getDescription() {
        return Description.get();
    }

    public StringProperty descriptionProperty() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description.set(description);
    }

    @Override
    public String toString() {
        return this.getID() + " - " + this.getName();
    }
}
