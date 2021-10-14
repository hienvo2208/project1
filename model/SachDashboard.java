package com.javafx.librarian.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SachDashboard {
    private StringProperty maSach;
    private StringProperty tenSach;
    private StringProperty tenTheLoai;
    private StringProperty tenTacGia;

    public SachDashboard(String maSach, String tenSach, String tenTheLoai, String tenTacGia) {
        this.maSach = new SimpleStringProperty(maSach);
        this.tenSach = new SimpleStringProperty(tenSach);
        this.tenTheLoai = new SimpleStringProperty(tenTheLoai);
        this.tenTacGia = new SimpleStringProperty(tenTacGia);
    }

    public String getMaSach() {
        return maSach.get();
    }

    public StringProperty maSachProperty() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach.set(maSach);
    }

    public String getTenSach() {
        return tenSach.get();
    }

    public StringProperty tenSachProperty() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach.set(tenSach);
    }

    public String getTenTheLoai() {
        return tenTheLoai.get();
    }

    public StringProperty tenTheLoaiProperty() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai.set(tenTheLoai);
    }

    public String getTenTacGia() {
        return tenTacGia.get();
    }

    public StringProperty tenTacGiaProperty() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia.set(tenTacGia);
    }
}
