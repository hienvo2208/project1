package com.javafx.librarian.model;

import javafx.beans.property.*;

import java.util.Date;

public class PhieuMuon {
    private StringProperty MaPM;
    private StringProperty MaDG;
    private ObjectProperty<Date> NgayMuon;
    private ObjectProperty<Date> HanTra;
    private StringProperty TinhTrang;

    public String getMaPM() {
        return MaPM.get();
    }

    public StringProperty maPMProperty() {
        return MaPM;
    }

    public void setMaPM(String maPM) {
        this.MaPM.set(maPM);
    }

    public String getMaDG() {
        return MaDG.get();
    }

    public StringProperty maDGProperty() {
        return MaDG;
    }

    public void setMaDG(String maDG) {
        this.MaDG.set(maDG);
    }

    public Date getNgayMuon() { return NgayMuon.get();}

    public ObjectProperty<Date> ngayMuonProperty() { return NgayMuon;}

    public void setNgayMuon(Date ngayMuon) { this.NgayMuon.set(ngayMuon);}

    public Date getHanTra() { return HanTra.get();}

    public ObjectProperty<Date> hanTraProperty() { return HanTra;}

    public void setHanTra(Date hanTra) { this.HanTra.set(hanTra);}

    public String getTinhTrang() {
        return TinhTrang.get();
    }

    public StringProperty tinhTrangProperty() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.TinhTrang.set(tinhTrang);
    }

    public PhieuMuon(String maPM, String maDG, Date ngayMuon, Date hanTra, int tinhTrang) {
        MaPM = new SimpleStringProperty(maPM);
        MaDG = new SimpleStringProperty(maDG);
        NgayMuon = new SimpleObjectProperty<Date>(ngayMuon);
        HanTra = new SimpleObjectProperty<Date>(hanTra);
        TinhTrang = new SimpleStringProperty(tinhTrang == 0? "Trả đủ" : "Chưa trả đủ");
    }

    @Override
    public String toString() {
        return this.getMaPM();
    }
}
