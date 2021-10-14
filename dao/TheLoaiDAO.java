package com.javafx.librarian.dao;

import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.model.TheLoai;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TheLoaiDAO {
    private static TheLoaiDAO instance;

    private TheLoaiDAO() {
    }

    public static TheLoaiDAO getInstance() {
        if (instance == null) {
            instance = new TheLoaiDAO();
        }
        return instance;
    }

    public List<TheLoai> getAllTheLoai() {
        List<TheLoai> ListTheLoai = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbtheloai where record_status = 1");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String maTheLoai = res.getString(1);
                String tenTheLoai = res.getString(2);
                ListTheLoai.add(new TheLoai(maTheLoai, tenTheLoai));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListTheLoai;
    }

    public TheLoai getTheLoaiByID(String ID) {
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbtheloai where matheloai=? and record_status = 1");
            ps.setString(1, ID);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                return new TheLoai(res.getString(1), res.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int addTheloai(TheLoai theloai) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into tbtheloai (matheloai, tentheloai) values(?,?)");
            ps.setString(1, theloai.getMaTheLoai());
            ps.setString(2, theloai.getTenTheLoai());

            res = ps.executeUpdate();
            System.out.println(res + "row is effected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public int editTheloai(TheLoai theloai) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection();) {
            PreparedStatement ps = conn.prepareStatement("update tbtheloai set tentheloai=? where matheloai=?");
            ps.setString(1, theloai.getTenTheLoai());
            ps.setString(2, theloai.getMaTheLoai());
            res = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public int deleteTheLoai(String id) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection();) {
            PreparedStatement ps = conn.prepareStatement("update tbtheloai set record_status = 0 where matheloai=?");
            ps.setString(1, id);
            res = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<TheLoai> searchTheLoai(String find) {
        List<TheLoai> ListTheLoai = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbtheloai where (tentheloai is null or tentheloai = '' or tentheloai LIKE ?) and record_status = 1");
            ps.setString(1, "%" + find + "%");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String maTheLoai = res.getString(1);
                String tenTheLoai = res.getString(2);
                ListTheLoai.add(new TheLoai(maTheLoai, tenTheLoai));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListTheLoai;
    }
}
