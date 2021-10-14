package com.javafx.librarian.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.swing.table.TableColumn;

public class CTPhieuMuon {
    private StringProperty MaPM;
    private StringProperty MaSach;
    public StringProperty TenSach;
    public StringProperty TheLoai;
    public StringProperty TacGia;

    public String getMaPM() {
        return MaPM.get();
    }

    public StringProperty maPMProperty() {
        return MaPM;
    }

    public void setMaPM(String maPM) {
        this.MaPM.set(maPM);
    }

    public String getMaSach() {
        return MaSach.get();
    }

    public StringProperty maSachProperty() {
        return MaSach;
    }

    public void setMaSach(String maSach) {
        this.MaSach.set(maSach);
    }

    public String getTenSach() {
        return TenSach.get();
    }

    public StringProperty tenSachProperty() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        this.TenSach.set(tenSach);
    }

    public String getTheLoai() {
        return TheLoai.get();
    }

    public StringProperty theLoaiProperty() {
        return TheLoai;
    }

    public void setTheLoai(String theLoai) {
        this.TheLoai.set(theLoai);
    }

    public String getTacGia() {
        return TacGia.get();
    }

    public StringProperty tacGiaProperty() {
        return TacGia;
    }

    public void setTacGia(String tacGia) {
        this.TacGia.set(tacGia);
    }

    public CTPhieuMuon(String maPM, String maSach, String tenSach, String theLoai, String tacGia) {
        MaPM = new SimpleStringProperty(maPM);
        MaSach = new SimpleStringProperty(maSach);
        TenSach = new SimpleStringProperty(tenSach);
        TheLoai = new SimpleStringProperty(theLoai);
        TacGia = new SimpleStringProperty(tacGia);
    }

    public CTPhieuMuon(String maPM, String maSach) {
        MaPM = new SimpleStringProperty(maPM);
        MaSach = new SimpleStringProperty(maSach);
    }
}
