package com.javafx.librarian.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TheLoai {
    private StringProperty MaTheLoai;
    private StringProperty TenTheLoai;

    public String getMaTheLoai() {
        return MaTheLoai.get();
    }

    public StringProperty maTheLoaiProperty() {
        return MaTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.MaTheLoai.set(maTheLoai);
    }

    public String getTenTheLoai() {
        return TenTheLoai.get();
    }

    public StringProperty tenTheLoaiProperty() {
        return TenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.TenTheLoai.set(tenTheLoai);
    }

    public TheLoai(){}

    public TheLoai(String maTheLoai, String tenTheLoai) {
        MaTheLoai = new SimpleStringProperty(maTheLoai);
        TenTheLoai = new SimpleStringProperty(tenTheLoai);
    }

    @Override
    public String toString()
    {
        return this.getMaTheLoai() + " - " + this.getTenTheLoai();
    }
}
