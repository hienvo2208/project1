package com.javafx.librarian.dao;

import com.javafx.librarian.model.NhanVien;
import com.javafx.librarian.model.TacGia;
import com.javafx.librarian.utils.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NhanVienDAO {
    private static NhanVienDAO instance;

    private NhanVienDAO() {
    }

    ;

    public static NhanVienDAO getInstance() {
        if (instance == null) {
            instance = new NhanVienDAO();
        }
        return instance;
    }

    public List<NhanVien> getAllNV() {
        List<NhanVien> ListNV = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbadmin where record_status = 1");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String maNV = res.getString(1);
                String idAccount = res.getString(2);
                String tenNV = res.getString(3);
                Date ngaySinh = res.getDate(4);
                String diaChi = res.getString(5);
                String email = res.getString(6);
                String sdt = res.getString(7);
                ListNV.add(new NhanVien(maNV, tenNV, diaChi, ngaySinh, email, sdt, idAccount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListNV;
    }

    public int addNV(NhanVien nv) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into tbadmin(idadmin,idaccount, hoten, ngaysinh, diachi, email, sdt) values(?,?,?,?,?,?,?)");
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getIdAccount());
            ps.setString(3, nv.getTenNV());
            ps.setDate(4, java.sql.Date.valueOf(Util.convertDateToLocalDate(nv.getNgaySinh())));
            ps.setString(5, nv.getDiaChi());
            ps.setString(6, nv.getEmail());
            ps.setString(7, nv.getSDT());
            res = ps.executeUpdate();
            System.out.println(res + "row is effected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public int editNV(NhanVien nv) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection();) {
            PreparedStatement ps = conn.prepareStatement("update tbadmin set hoten=?,ngaysinh=?,diachi=?,email=?,sdt=? where idadmin=?");
            ps.setString(1, nv.getTenNV());
            ps.setDate(2, java.sql.Date.valueOf(Util.convertDateToLocalDate(nv.getNgaySinh())));
            ps.setString(3,nv.getDiaChi());
            ps.setString(4, nv.getEmail());
            ps.setString(5, nv.getSDT());
            ps.setString(6, nv.getMaNV());
            res = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public int deleteNV(String id) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection();) {
            PreparedStatement ps = conn.prepareStatement("update tbadmin set record_status = 0 where idadmin=?");
            ps.setString(1, id);
            res = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public int getCount() {
        int ret = 0;

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select count(*) from tbadmin where record_status = 1");
            ResultSet res = ps.executeQuery();
            res.next();
            ret = res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public List<NhanVien> searchNV(String find){
        List<NhanVien> ListNV = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbadmin where (hoten is null or hoten = '' or hoten LIKE ?) and record_status = 1");
            ps.setString(1, "%" + find + "%");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String maNV = res.getString(1);
                String idAccount = res.getString(2);
                String tenNV = res.getString(3);
                Date ngaySinh = res.getDate(4);
                String diaChi = res.getString(5);
                String email = res.getString(6);
                String sdt = res.getString(7);
                ListNV.add(new NhanVien(maNV, tenNV, diaChi, ngaySinh, email, sdt, idAccount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListNV;
    }
}

