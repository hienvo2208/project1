package com.javafx.librarian.model;

import javafx.beans.property.*;

import java.util.Date;

public class BaoCaoTheoTheLoai {
    private StringProperty MaBaoCaoTheoTheLoai;
    private IntegerProperty Thang;
    private IntegerProperty Nam;
    private IntegerProperty TongLuotMuon;

    public String getMaBaoCaoTheoTheLoai() {
        return MaBaoCaoTheoTheLoai.get();
    }

    public StringProperty maBaoCaoTheoTheLoaiProperty() {return MaBaoCaoTheoTheLoai;}

    public void setMaBaoCaoTheoTheLoai(String maBaoCaoTheoTheLoai) {
        this.MaBaoCaoTheoTheLoai.set(maBaoCaoTheoTheLoai);
    }

    public int getThang() {
        return Thang.get();
    }

    public IntegerProperty thangProperty() {
        return Thang;
    }

    public void setThang(int thang) {
        this.Thang.set(thang);
    }

    public int getNam() {
        return Nam.get();
    }

    public IntegerProperty namProperty() {
        return Nam;
    }

    public void setNam(int nam) {
        this.Nam.set(nam);
    }

    public int getTongLuotMuon() {
        return TongLuotMuon.get();
    }

    public IntegerProperty tongLuotMuonProperty() {
        return TongLuotMuon;
    }

    public void setTongLuotMuon(int tongLuotMuon) {
        this.TongLuotMuon.set(tongLuotMuon);
    }

    public BaoCaoTheoTheLoai(String maBaoCaoTheoTheLoai, int thang, int nam, int tongluotmuon) {
        MaBaoCaoTheoTheLoai = new SimpleStringProperty(maBaoCaoTheoTheLoai);
        Thang = new SimpleIntegerProperty(thang);
        Nam = new SimpleIntegerProperty(nam);
        TongLuotMuon = new SimpleIntegerProperty(tongluotmuon);
    }
}
