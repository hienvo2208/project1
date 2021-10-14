package com.javafx.librarian.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class ReportSachTT {
    private int stt;
    //private String maBCSTT;
    private String maSach;
    private String tenSach;
    private Date ngayMuon;
    private int soNgayTraTre;

    public ReportSachTT() {

    }

    public ReportSachTT(String maSach, String tenSach, Date ngayMuon, int soNgayTraTre) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.ngayMuon = ngayMuon;
        this.soNgayTraTre = soNgayTraTre;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    /*public String getMaBCSTT() {
        return maBCSTT;
    }

    public void setMaBCSTT(String maBCSTT) {
        this.maBCSTT = maBCSTT;
    }*/

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public int getSoNgayTraTre() {
        return soNgayTraTre;
    }

    public void setSoNgayTraTre(int soNgayTraTre) {
        this.soNgayTraTre = soNgayTraTre;
    }
}
