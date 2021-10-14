package com.javafx.librarian.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ReportMuonTheLoai {
    private int stt;
    private String maBaoCaoTheLoai;
    private String maTheLoai;
    private String tenTheLoai;
    private int soLuotMuon;
    private double tiLe;

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getMaBaoCaoTheLoai() {
        return maBaoCaoTheLoai;
    }

    public void setMaBaoCaoTheLoai(String maBaoCaoTheLoai) {
        this.maBaoCaoTheLoai = maBaoCaoTheLoai;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public int getSoLuotMuon() {
        return soLuotMuon;
    }

    public void setSoLuotMuon(int soLuotMuon) {
        this.soLuotMuon = soLuotMuon;
    }

    public ReportMuonTheLoai() {}

    public ReportMuonTheLoai(String maTheLoai, String tenTheLoai, int soLuotMuon, double tiLe) {
        this.maTheLoai = maTheLoai;
        this.tenTheLoai = tenTheLoai;
        this.soLuotMuon = soLuotMuon;
        this.tiLe = tiLe;
    }

    public double getTiLe() {
        return tiLe;
    }

    public void setTiLe(double tiLe) {
        this.tiLe = tiLe;
    }
}
