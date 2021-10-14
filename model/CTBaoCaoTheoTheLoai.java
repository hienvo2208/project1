package com.javafx.librarian.model;

import javafx.beans.property.*;

public class CTBaoCaoTheoTheLoai {
    private IntegerProperty Stt;
    private StringProperty MaBaoCaoTheoTheLoai;
    private StringProperty MaTheLoai;
    private StringProperty TenTheLoai;
    private IntegerProperty SoLuotMuon;
    private DoubleProperty TiLe;

    public String getMaBaoCaoTheoTheLoai() {
        return MaBaoCaoTheoTheLoai.get();
    }

    public StringProperty maBaoCaoTheoTheLoaiProperty() {return MaBaoCaoTheoTheLoai;}

    public void setMaBaoCaoTheoTheLoai(String maBaoCaoTheoTheLoai) {
        this.MaBaoCaoTheoTheLoai.set(maBaoCaoTheoTheLoai);
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

    public int getSoLuotMuon() {
        return SoLuotMuon.get();
    }

    public IntegerProperty soLuotMuonProperty() {
        return SoLuotMuon;
    }

    public void setSoLuotMuon(int soLuotMuon) {
        this.SoLuotMuon.set(soLuotMuon);
    }

    public double getTiLe() {
        return TiLe.get();
    }

    public DoubleProperty tiLeProperty() {
        return TiLe;
    }

    public void setTiLe(double tiLe) {
        this.TiLe.set(tiLe);
    }

    public CTBaoCaoTheoTheLoai(String maBaoCaoTheoTheLoai, String maTheLoai, String tenTheLoai, int soLuotMuon, double tiLe) {
        MaBaoCaoTheoTheLoai = new SimpleStringProperty(maBaoCaoTheoTheLoai);
        MaTheLoai = new SimpleStringProperty(maTheLoai);
        TenTheLoai = new SimpleStringProperty(tenTheLoai);
        SoLuotMuon = new SimpleIntegerProperty(soLuotMuon);
        TiLe = new SimpleDoubleProperty(tiLe);
    }

    public CTBaoCaoTheoTheLoai(String maTheLoai, String tenTheLoai, int soLuotMuon, double tiLe, int stt) {
        MaTheLoai = new SimpleStringProperty(maTheLoai);
        TenTheLoai = new SimpleStringProperty(tenTheLoai);
        SoLuotMuon = new SimpleIntegerProperty(soLuotMuon);
        TiLe = new SimpleDoubleProperty(tiLe);
        Stt = new SimpleIntegerProperty(stt);
    }
}
