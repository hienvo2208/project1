package com.javafx.librarian.model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TacGia {
    private StringProperty MaTacGia;
    private StringProperty TenTacGia;

    public String getMaTacGia() {
        return MaTacGia.get();
    }

    public StringProperty maTacGiaProperty() {
        return MaTacGia;
    }

    public void setMaTacGia(String maTacGia) {
        this.MaTacGia.set(maTacGia);
    }

    public String getTenTacGia() {
        return TenTacGia.get();
    }

    public StringProperty tenTacGiaProperty() {
        return TenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.TenTacGia.set(tenTacGia);
    }

    public TacGia(){}

    public TacGia(String maTacGia, String tenTacGia) {
        MaTacGia = new SimpleStringProperty(maTacGia);
        TenTacGia = new SimpleStringProperty(tenTacGia);
    }

    @Override
    public String toString()
    {
        return this.getMaTacGia() + " - " + this.getTenTacGia();
    }
}
