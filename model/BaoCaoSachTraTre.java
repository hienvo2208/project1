package com.javafx.librarian.model;

import javafx.beans.property.*;

import java.util.Date;

public class BaoCaoSachTraTre {
    private StringProperty MaBCSTT;
    private ObjectProperty<Date> Ngay;

    public String getMaBCSTT() {
        return MaBCSTT.get();
    }

    public StringProperty maBCSTTProperty() {return MaBCSTT;}

    public void setMaBCSTT(String maBCSTT) {
        this.MaBCSTT.set(maBCSTT);
    }

    public Date getNgay() { return Ngay.get();}

    public ObjectProperty<Date> ngayProperty() { return Ngay;}

    public void setNgay(Date ngay) { this.Ngay.set(ngay);}

    public BaoCaoSachTraTre(String maBCSTT, Date ngay) {
        MaBCSTT = new SimpleStringProperty(maBCSTT);
        Ngay = new SimpleObjectProperty<Date>(ngay);
    }
}
