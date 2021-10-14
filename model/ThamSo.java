package com.javafx.librarian.model;

import javafx.beans.property.*;

import java.io.FileInputStream;
import java.util.Date;

public class ThamSo {
    private IntegerProperty MaxTuoi;
    private IntegerProperty MinTuoi;
    private IntegerProperty HanThe;
    private IntegerProperty MaxTG;
    private IntegerProperty KhoangCachXB;
    private IntegerProperty MaxSachMuon;
    private IntegerProperty HanMuon;
    private IntegerProperty TienPhat;
    private IntegerProperty TienPhatSach;
    private FileInputStream AnhMacDinh;

    public ThamSo(int maxTuoi, int minTuoi, int hanThe, int maxTG, int khoangCachXB, int maxSachMuon, int hanMuon, int tienPhat, int tienPhatSach, FileInputStream anhMacDinh) {
        MaxTuoi = new SimpleIntegerProperty(maxTuoi);
        MinTuoi = new SimpleIntegerProperty(minTuoi);
        HanThe = new SimpleIntegerProperty(hanThe);
        MaxTG = new SimpleIntegerProperty(maxTG);
        KhoangCachXB = new SimpleIntegerProperty(khoangCachXB);
        MaxSachMuon = new SimpleIntegerProperty(maxSachMuon);
        HanMuon = new SimpleIntegerProperty(hanMuon);
        TienPhat = new SimpleIntegerProperty(tienPhat);
        TienPhatSach = new SimpleIntegerProperty(tienPhatSach);
        AnhMacDinh = anhMacDinh;
    }

    public FileInputStream getAnhMacDinh() {
        return AnhMacDinh;
    }

    public void setAnhMacDinh(FileInputStream anhMacDinh) {
        AnhMacDinh = anhMacDinh;
    }

    public ThamSo(int maxTuoi, int minTuoi, int hanThe, int khoangCachXB, int maxSachMuon, int hanMuon, int tienPhat, int tienPhatSach) {
        MaxTuoi = new SimpleIntegerProperty(maxTuoi);
        MinTuoi = new SimpleIntegerProperty(minTuoi);
        HanThe = new SimpleIntegerProperty(hanThe);
        KhoangCachXB = new SimpleIntegerProperty(khoangCachXB);
        MaxSachMuon = new SimpleIntegerProperty(maxSachMuon);
        HanMuon = new SimpleIntegerProperty(hanMuon);
        TienPhat = new SimpleIntegerProperty(tienPhat);
        TienPhatSach = new SimpleIntegerProperty(tienPhatSach);
    }

    public int getMaxTuoi() {
        return MaxTuoi.get();
    }

    public IntegerProperty maxTuoiProperty() {
        return MaxTuoi;
    }

    public void setMaxTuoi(int maxTuoi) {
        this.MaxTuoi.set(maxTuoi);
    }

    public int getMinTuoi() {
        return MinTuoi.get();
    }

    public IntegerProperty minTuoiProperty() {
        return MinTuoi;
    }

    public void setMinTuoi(int minTuoi) {
        this.MinTuoi.set(minTuoi);
    }

    public int getHanThe() {
        return HanThe.get();
    }

    public IntegerProperty hanTheProperty() {
        return HanThe;
    }

    public void setHanThe(int hanThe) {
        this.HanThe.set(hanThe);
    }

    public int getMaxTG() {
        return MaxTG.get();
    }

    public IntegerProperty maxTGProperty() {
        return MaxTG;
    }

    public void setMaxTG(int maxTG) {
        this.MaxTG.set(maxTG);
    }

    public int getKhoangCachXB() {
        return KhoangCachXB.get();
    }

    public IntegerProperty khoangCachXBProperty() {
        return KhoangCachXB;
    }

    public void setKhoangCachXB(int khoangCachXB) {
        this.KhoangCachXB.set(khoangCachXB);
    }

    public int getMaxSachMuon() {
        return MaxSachMuon.get();
    }

    public IntegerProperty maxSachMuonProperty() {
        return MaxSachMuon;
    }

    public void setMaxSachMuon(int maxSachMuon) {
        this.MaxSachMuon.set(maxSachMuon);
    }

    public int getHanMuon() {
        return HanMuon.get();
    }

    public IntegerProperty hanMuonProperty() {
        return HanMuon;
    }

    public void setHanMuon(int hanMuon) {
        this.HanMuon.set(hanMuon);
    }

    public int getTienPhat() {
        return TienPhat.get();
    }

    public IntegerProperty tienPhatProperty() {
        return TienPhat;
    }

    public void setTienPhat(int tienPhat) {
        this.TienPhat.set(tienPhat);
    }

    public int getTienPhatSach() {
        return TienPhatSach.get();
    }

    public IntegerProperty tienPhatSachProperty() {
        return TienPhatSach;
    }

    public void setTienPhatSach(int tienPhat) {
        this.TienPhatSach.set(tienPhat);
    }
}
