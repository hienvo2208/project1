package com.javafx.librarian.model;

import javafx.beans.property.*;

import java.util.Date;

public class PhieuTra {
    private StringProperty MaPT;
    private StringProperty MaPM;
    private StringProperty MaDG;
    private ObjectProperty<Date> NgayTra;
    private DoubleProperty TienPhatKyNay;

    public PhieuTra(String maPT, String maPM, String maDG, Date ngayTra, double tienPhatKyNay) {
        MaPT = new SimpleStringProperty(maPT);
        MaPM =  new SimpleStringProperty(maPM);
        MaDG =  new SimpleStringProperty(maDG);
        NgayTra = new SimpleObjectProperty<Date>(ngayTra);
        TienPhatKyNay =  new SimpleDoubleProperty(tienPhatKyNay);
    }

    public String getMaPT() {
        return MaPT.get();
    }

    public StringProperty maPTProperty() {
        return MaPT;
    }

    public void setMaPT(String maPT) {
        this.MaPT.set(maPT);
    }

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

    public Date getNgayTra() {
        return NgayTra.get();
    }

    public ObjectProperty<Date> ngayTraProperty() {
        return NgayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.NgayTra.set(ngayTra);
    }

    public double getTienPhatKyNay() {
        return TienPhatKyNay.get();
    }

    public DoubleProperty tienPhatKyNayProperty() {
        return TienPhatKyNay;
    }

    public void setTienPhatKyNay(double tienPhatKyNay) {
        this.TienPhatKyNay.set(tienPhatKyNay);
    }
}
