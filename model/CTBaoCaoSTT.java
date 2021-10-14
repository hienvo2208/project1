package com.javafx.librarian.model;

import javafx.beans.property.*;

import java.util.Date;

public class CTBaoCaoSTT {
    private IntegerProperty Stt;
    private StringProperty MaBCSTT;
    private StringProperty MaSach;
    private StringProperty TenSach;
    private ObjectProperty<Date> NgayMuon;
    private IntegerProperty SoNgayTraTre;

    public String getMaBCSTT() {
        return MaBCSTT.get();
    }

    public StringProperty maBCSTTProperty() {return MaBCSTT;}

    public void setMaBCSTT(String maBCSTT) {
        this.MaBCSTT.set(maBCSTT);
    }

    public int getStt() {
        return Stt.get();
    }

    public IntegerProperty sttProperty() {
        return Stt;
    }

    public void setStt(int id) {
        this.Stt.set(id);
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

    public String getTenSach() { return TenSach.get();}

    public StringProperty tenSachProperty() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        this.TenSach.set(tenSach);
    }

    public Date getNgayMuon() { return NgayMuon.get();}

    public ObjectProperty<Date> ngayMuonProperty() { return NgayMuon;}

    public void setNgayMuon(Date ngayMuon) { this.NgayMuon.set(ngayMuon);}

    public int getSoNgayTraTre() {
        return SoNgayTraTre.get();
    }

    public IntegerProperty soNgayTraTreProperty() {
        return SoNgayTraTre;
    }

    public void setSoNgayTraTre(int soNM) {
        this.SoNgayTraTre.set(soNM);
    }

    public CTBaoCaoSTT(String mabcstt, String maSach, String tenSach, Date ngayMuon, int soNgayTT) {
        MaBCSTT = new SimpleStringProperty(mabcstt);
        MaSach = new SimpleStringProperty(maSach);
        TenSach = new SimpleStringProperty(tenSach);
        NgayMuon = new SimpleObjectProperty<Date>(ngayMuon);
        SoNgayTraTre = new SimpleIntegerProperty(soNgayTT);
    }

    public CTBaoCaoSTT(String maSach, String tenSach, Date ngayMuon, int soNgayTT, int stt) {
        MaSach = new SimpleStringProperty(maSach);
        TenSach = new SimpleStringProperty(tenSach);
        NgayMuon = new SimpleObjectProperty<Date>(ngayMuon);
        SoNgayTraTre = new SimpleIntegerProperty(soNgayTT);
        Stt = new SimpleIntegerProperty(stt);
    }
}
