package com.javafx.librarian.model;

import javafx.beans.property.*;

import java.util.Date;

public class DocGia {
    private StringProperty maDocGia;
    private StringProperty tenDocGia;
    private StringProperty maLoaiDocGia;
    private ObjectProperty<Date> ngaySinh;
    private StringProperty diaChi;
    private StringProperty email;
    private ObjectProperty<Date> ngayLapThe;
    private ObjectProperty<Date> ngayHetHan;
    private IntegerProperty tinhTrangThe;
    private DoubleProperty tongNo;
    private StringProperty idAccount;
    private StringProperty soDienThoai;
    private StringProperty tenLoaiDG;
    private StringProperty gioiThieu;

    public DocGia() {
        maDocGia = new SimpleStringProperty();
        tenDocGia = new SimpleStringProperty();
        maLoaiDocGia = new SimpleStringProperty();
        ngaySinh = new SimpleObjectProperty<>();
        diaChi = new SimpleStringProperty();
        email = new SimpleStringProperty();
        ngayLapThe = new SimpleObjectProperty<>();
        ngayHetHan = new SimpleObjectProperty<>();
        tinhTrangThe = new SimpleIntegerProperty();
        tongNo = new SimpleDoubleProperty();
        idAccount = new SimpleStringProperty();
        soDienThoai = new SimpleStringProperty();
        tenLoaiDG = new SimpleStringProperty();
        gioiThieu = new SimpleStringProperty();
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

    public Date getNgaySinh() {
        return ngaySinh.get();
    }

    public ObjectProperty<Date> ngaySinhProperty() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh.set(ngaySinh);
    }

    public String getDiaChi() {
        return diaChi.get();
    }

    public StringProperty diaChiProperty() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi.set(diaChi);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public Date getNgayLapThe() {
        return ngayLapThe.get();
    }

    public ObjectProperty<Date> ngayLapTheProperty() {
        return ngayLapThe;
    }

    public void setNgayLapThe(Date ngayLapThe) {
        this.ngayLapThe.set(ngayLapThe);
    }

    public Date getNgayHetHan() {
        return ngayHetHan.get();
    }

    public ObjectProperty<Date> ngayHetHanProperty() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan.set(ngayHetHan);
    }

    public int getTinhTrangThe() {
        return tinhTrangThe.get();
    }

    public IntegerProperty tinhTrangTheProperty() {
        return tinhTrangThe;
    }

    public void setTinhTrangThe(Byte tinhTrangThe) {
        this.tinhTrangThe.set(tinhTrangThe);
    }

    public double getTongNo() {
        return tongNo.get();
    }

    public DoubleProperty tongNoProperty() {
        return tongNo;
    }

    public void setTongNo(double tongNo) {
        this.tongNo.set(tongNo);
    }

    public String getIdAccount() {
        return idAccount.get();
    }

    public StringProperty idAccountProperty() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount.set(idAccount);
    }

    public String getSoDienThoai() {
        return soDienThoai.get();
    }

    public StringProperty soDienThoaiProperty() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai.set(soDienThoai);
    }

    public String getMaLoaiDocGia() {
        return maLoaiDocGia.get();
    }

    public StringProperty maLoaiDocGiaProperty() {
        return maLoaiDocGia;
    }

    public void setMaLoaiDocGia(String maLoaiDocGia) {
        this.maLoaiDocGia.set(maLoaiDocGia);
    }

    public void setTinhTrangThe(int tinhTrangThe) {
        this.tinhTrangThe.set(tinhTrangThe);
    }

    public String getTenLoaiDG() {
        return tenLoaiDG.get();
    }

    public StringProperty tenLoaiDGProperty() {
        return tenLoaiDG;
    }

    public void setTenLoaiDG(String tenLoaiDG) {
        this.tenLoaiDG.set(tenLoaiDG);
    }

    public String getGioiThieu() {
        return gioiThieu.get();
    }

    public StringProperty gioiThieuProperty() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu.set(gioiThieu);
    }

    @Override
    public String toString()
    {
        return this.getMaDocGia() + " - " + this.getTenDocGia();
    }
}
