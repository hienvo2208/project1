package com.javafx.librarian.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoaiDocGia {
    private StringProperty maLoaiDocGia;
    private StringProperty tenLoaiDocGia;

    public LoaiDocGia() {
        maLoaiDocGia = new SimpleStringProperty();
        tenLoaiDocGia = new SimpleStringProperty();
    }

    public String getMaLoaiDocGia() {
        return maLoaiDocGia.get();
    }

    public StringProperty maLoaiDocGiaProperty() {
        return maLoaiDocGia;
    }

    public void setMaLoaiDocGia(String maLoaiDocGia) {
        this.maLoaiDocGia.set(maLoaiDocGia);
    }

    public String getTenLoaiDocGia() {
        return tenLoaiDocGia.get();
    }

    public StringProperty tenLoaiDocGiaProperty() {
        return tenLoaiDocGia;
    }

    public void setTenLoaiDocGia(String tenLoaiDocGia) {
        this.tenLoaiDocGia.set(tenLoaiDocGia);
    }
}
