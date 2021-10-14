package com.javafx.librarian.model;

import javafx.beans.property.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class NhanVien {
    private StringProperty MaNV;
    private StringProperty TenNV;
    private StringProperty DiaChi;
    private ObjectProperty<Date> NgaySinh;
    private StringProperty Email;
    private StringProperty SDT;
    private StringProperty IdAccount;

    public NhanVien(String maNV, String tenNV, String diaChi,
                    Date ngaySinh, String email, String sdt, String idAccount) {
        MaNV = new SimpleStringProperty(maNV);
        TenNV = new SimpleStringProperty(tenNV);
        DiaChi = new SimpleStringProperty(diaChi);
        NgaySinh = new SimpleObjectProperty<Date>(ngaySinh);
        Email = new SimpleStringProperty(email);
        SDT = new SimpleStringProperty(sdt);
        IdAccount = new SimpleStringProperty(idAccount);
    }

    public NhanVien(String tenNV, String diaChi, java.sql.Date ngaySinh, String email, String sdt, String idAccount) {
        TenNV = new SimpleStringProperty(tenNV);
        DiaChi = new SimpleStringProperty(diaChi);
        NgaySinh = new SimpleObjectProperty<Date>(ngaySinh);
        Email = new SimpleStringProperty(email);
        SDT = new SimpleStringProperty(sdt);
        IdAccount = new SimpleStringProperty(idAccount);
    }

    public String getMaNV() {
        return MaNV.get();
    }

    public StringProperty maNVProperty() {
        return MaNV;
    }

    public String getTenNV() {
        return TenNV.get();
    }

    public StringProperty tenNVProperty() {
        return TenNV;
    }

    public String getDiaChi() {
        return DiaChi.get();
    }

    public StringProperty diaChiProperty() {
        return DiaChi;
    }

    public Date getNgaySinh() {
        return NgaySinh.get();
    }

    public ObjectProperty<Date> ngaySinhProperty() {
        return NgaySinh;
    }

    public String getEmail() {
        return Email.get();
    }

    public StringProperty emailProperty() {
        return Email;
    }

    public String getSDT() {
        return SDT.get();
    }

    public StringProperty SDTProperty() {
        return SDT;
    }

    public String getIdAccount() {
        return IdAccount.get();
    }

    public StringProperty idAccountProperty() {
        return IdAccount;
    }

    public void setIdAccount(String idAccount) {
        this.IdAccount.set(idAccount);
    }
}
