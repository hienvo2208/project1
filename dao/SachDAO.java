package com.javafx.librarian.dao;

import com.javafx.librarian.model.Account;
import com.javafx.librarian.model.Sach;
import com.javafx.librarian.model.TheLoai;
import javafx.collections.ObservableList;
import com.javafx.librarian.utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private static SachDAO instance;

    private SachDAO() {
    }

    public static SachDAO getInstance() {
        if (instance == null) {
            instance = new SachDAO();
        }
        return instance;
    }

    public List<Sach> getAllSach() {
        List<Sach> ListSach = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            String query = null;
            if(Account.currentUser.getIdper() == 1)
                query = "select * from tbsach";
            else
                query = "select * from tbsach";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String maSach = res.getString(1);
                String tenSach = res.getString(2);
                String maTheLoai = res.getString(3);
                String maTacGia = res.getString(4);
                int namXb = res.getInt(5);
                String nxb = res.getString(6);
                Date ngayNhap = res.getDate(7);
                int triGia = res.getInt(8);
                int tinhTrang = res.getInt(9);
                Blob anhBiaBlob = res.getBlob(10);
                int soLuong = res.getInt(12);
                if (anhBiaBlob != null) {
                    InputStream anhBiaStream = anhBiaBlob.getBinaryStream();
                    File anhBia = File.createTempFile("temp", null);
                    org.apache.commons.io.FileUtils.copyInputStreamToFile(anhBiaStream, anhBia);
                    FileInputStream anhBiaDTO = new FileInputStream(anhBia);

                    ListSach.add(new Sach(maSach, tenSach, maTheLoai, maTacGia, namXb, nxb, ngayNhap, triGia, tinhTrang, anhBiaDTO, soLuong));
                } else
                    ListSach.add(new Sach(maSach, tenSach, maTheLoai, maTacGia, namXb, nxb, ngayNhap, triGia, tinhTrang, null, soLuong));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListSach;
    }

    public int addSach(Sach sach) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into tbsach(`masach`, `tensach`, `matheloai`, `matacgia`, `namxb`, `nxb`, `ngaynhap`, `trigia`, `tinhtrang`, `anhbia`, `soluong`) values(?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, sach.getMaSach());
            ps.setString(2, sach.getTenSach());
            ps.setString(3, sach.getMaTheLoai());
            ps.setString(4, sach.getMaTacGia());
            ps.setInt(5, sach.getNamXB());
            ps.setString(6, sach.getNXB());
            ps.setDate(7, Date.valueOf(Util.convertDateToLocalDate(sach.getNgayNhap())));
            ps.setInt(8, sach.getTriGia());
            ps.setInt(9, sach.getTinhTrang() == "Trống" ? 0 : 1);
            ps.setBinaryStream(10, sach.getAnhBia(), sach.getAnhBia().available());
            ps.setInt(11, sach.getSoLuong());
            res = ps.executeUpdate();
            System.out.println(res + "row is effected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public int editSach(Sach sach) {
        int res = 0;
        int tinhTrang = 0;
        if(sach.getTinhTrang() == "Trống")
            tinhTrang = 0;
        else if(sach.getTinhTrang() == "Đang mượn")
            tinhTrang = 1;
        else if(sach.getTinhTrang() == "Hư hỏng")
            tinhTrang = 2;
        else
            tinhTrang = 3;
        try (Connection conn = JDBCConnection.getJDBCConnection();) {
            PreparedStatement ps = conn.prepareStatement("update tbsach set tensach=?, matheloai=?, matacgia=?, namxb=?, nxb=?, ngaynhap=?, trigia=?, tinhtrang=?, anhbia=? where masach=?");
            ps.setString(1, sach.getTenSach());
            ps.setString(2, sach.getMaTheLoai());
            ps.setString(3, sach.getMaTacGia());
            ps.setInt(4, sach.getNamXB());
            ps.setString(5, sach.getNXB());
            ps.setDate(6, Date.valueOf(Util.convertDateToLocalDate(sach.getNgayNhap())));
            ps.setInt(7, sach.getTriGia());
            ps.setInt(8, tinhTrang);
            ps.setBinaryStream(9, sach.getAnhBia());
            ps.setString(10, sach.getMaSach());
            res = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public int deleteSach(String id) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection();) {
            PreparedStatement ps = conn.prepareStatement("update tbsach set record_status=0 where masach=?");
            ps.setString(1, id);
            res = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<Sach> getAllSachByDocGiaInPM(String maDocGia) {
        List<Sach> ListSach = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbsach where record_status = 1 and masach in (select masach from tbctphieumuon ct, tbphieumuon pm WHERE ct.maphieumuon = pm.maphieumuon and pm.madocgia = ? and pm.tinhtrang = 1 and ct.record_status = 1) ");
            ps.setString(1, maDocGia);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String maSach = res.getString(1);
                String tenSach = res.getString(2);
                String maTheLoai = res.getString(3);
                String maTacGia = res.getString(4);
                int namXb = res.getInt(5);
                String nxb = res.getString(6);
                Date ngayNhap = res.getDate(7);
                int triGia = res.getInt(8);
                int tinhTrang = res.getInt(9);
                String anhBia = res.getString(10);
                int soLuong = res.getInt(11);
                ListSach.add(new Sach(maSach, tenSach, maTheLoai, maTacGia, namXb, nxb, ngayNhap, triGia, tinhTrang, null, soLuong));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListSach;
    }

    public Sach getSachByID(String maSach) {
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbsach where record_status = 1 and masach = ?");
            ps.setString(1, maSach);
            ResultSet res = ps.executeQuery();
            res.next();
            String masach = res.getString(1);
            String tenSach = res.getString(2);
            String maTheLoai = res.getString(3);
            String maTacGia = res.getString(4);
            int namXb = res.getInt(5);
            String nxb = res.getString(6);
            Date ngayNhap = res.getDate(7);
            int triGia = res.getInt(8);
            int tinhTrang = res.getInt(9);
            String anhBia = res.getString(10);
            int soLuong = res.getInt(11);
            return new Sach(masach, tenSach, maTheLoai, maTacGia, namXb, nxb, ngayNhap, triGia, tinhTrang, null, soLuong);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getCount() {
        int ret = 0;

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select count(*) from tbsach where record_status = 1");
            ResultSet res = ps.executeQuery();
            res.next();
            ret = res.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public int getCountMuon() {
        int ret = 0;

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select count(*) from tbsach where record_status = 1 and masach in (select masach from tbctphieumuon where record_status = 1)");
            ResultSet res = ps.executeQuery();
            res.next();
            ret = res.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<Sach> searchSach(String find) {
        List<Sach> ListSach = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            //PreparedStatement ps = conn.prepareStatement("select * from tbsach where (tensach is null or tensach = '' or tensach LIKE ?) and record_status = 1");
            PreparedStatement ps = conn.prepareStatement("select * from tbsach");
            //ps.setString(1, "%" + find + "%");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String maSach = res.getString(1);
                String tenSach = res.getString(2);
                String maTheLoai = res.getString(3);
                String maTacGia = res.getString(4);
                int namXb = res.getInt(5);
                String nxb = res.getString(6);
                Date ngayNhap = res.getDate(7);
                int triGia = res.getInt(8);
                int tinhTrang = res.getInt(9);
                Blob anhBiaBlob = res.getBlob(10);
                int soLuong = res.getInt(11);
                if (anhBiaBlob != null) {
                    InputStream anhBiaStream = anhBiaBlob.getBinaryStream();
                    File anhBia = File.createTempFile("temp", null);
                    org.apache.commons.io.FileUtils.copyInputStreamToFile(anhBiaStream, anhBia);
                    FileInputStream anhBiaDTO = new FileInputStream(anhBia);

                    ListSach.add(new Sach(maSach, tenSach, maTheLoai, maTacGia, namXb, nxb, ngayNhap, triGia, tinhTrang, anhBiaDTO, soLuong));
                }

                else
                    ListSach.add(new Sach(maSach, tenSach, maTheLoai, maTacGia, namXb, nxb, ngayNhap, triGia, tinhTrang, null, soLuong));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListSach;
    }
}
