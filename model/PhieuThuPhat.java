package com.javafx.librarian.model;

import javafx.beans.property.*;

import java.util.Date;

public class PhieuThuPhat {
    private StringProperty maPhieuThu;
    private StringProperty maDocGia;
    private StringProperty tenDocGia;
    private DoubleProperty conLai;
    private DoubleProperty soTienThu;
    private ObjectProperty<Date> ngayThu;

    public PhieuThuPhat() {
       maPhieuThu = new SimpleStringProperty();
       maDocGia = new SimpleStringProperty();
       tenDocGia = new SimpleStringProperty();
       conLai = new SimpleDoubleProperty();
       soTienThu = new SimpleDoubleProperty();
       ngayThu = new SimpleObjectProperty<>();
    }

    public String getMaPhieuThu() {
        return maPhieuThu.get();
    }

    public StringProperty maPhieuThuProperty() {
        return maPhieuThu;
    }

    public void setMaPhieuThu(String maPhieuThu) {
        this.maPhieuThu.set(maPhieuThu);
    }

    public String getMaDocGia() {
        return maDocGia.get();
    }

    public StringProperty maDocGiaProperty() {
        return maDocGia;
    }

    public void setMaDocGia(String maDocGia) {
        this.maDocGia.set(maDocGia);
    }

    public String getTenDocGia() {
        return tenDocGia.get();
    }

    public StringProperty tenDocGiaProperty() {
        return tenDocGia;
    }

    public void setTenDocGia(String tenDocGia) {
        this.tenDocGia.set(tenDocGia);
    }

    public double getConLai() {
        return conLai.get();
    }

    public DoubleProperty conLaiProperty() {
        return conLai;
    }

    public void setConLai(double conLai) {
        this.conLai.set(conLai);
    }

    public double getSoTienThu() {
        return soTienThu.get();
    }

    public DoubleProperty soTienThuProperty() {
        return soTienThu;
    }

    public void setSoTienThu(double soTienThu) {
        this.soTienThu.set(soTienThu);
    }

    public Date getNgayThu() {
        return ngayThu.get();
    }

    public ObjectProperty<Date> ngayThuProperty() {
        return ngayThu;
    }

    public void setNgayThu(Date ngayThu) {
        this.ngayThu.set(ngayThu);
    }
}
