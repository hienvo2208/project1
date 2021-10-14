package com.javafx.librarian.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class LSMuonSach {
    private StringProperty MaSach;
    private StringProperty TenSach;
    private ObjectProperty<Date> NgayMuon;
    private ObjectProperty<Date> HanTra;
    private StringProperty TinhTrang;

    public LSMuonSach(){
        MaSach = new SimpleStringProperty();
        TenSach = new SimpleStringProperty();
        NgayMuon = new SimpleObjectProperty<>();
        HanTra = new SimpleObjectProperty<>();
        TinhTrang = new SimpleStringProperty();
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

    public Date getNgayMuon() {
        return NgayMuon.get();
    }

    public ObjectProperty<Date> ngayMuonProperty() {
        return NgayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.NgayMuon.set(ngayMuon);
    }

    public Date getHanTra() {
        return HanTra.get();
    }

    public ObjectProperty<Date> hanTraProperty() {
        return HanTra;
    }

    public void setHanTra(Date hanTra) {
        this.HanTra.set(hanTra);
    }

    public String getTinhTrang() {
        return TinhTrang.get();
    }

    public StringProperty tinhTrangProperty() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.TinhTrang.set(tinhTrang);
    }
}
