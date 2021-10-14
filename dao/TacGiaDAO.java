package com.javafx.librarian.dao;

import com.javafx.librarian.model.TacGia;
import com.javafx.librarian.model.TheLoai;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacGiaDAO {
    private static TacGiaDAO instance;

    private TacGiaDAO() {
    }

    public static TacGiaDAO getInstance() {
        if (instance == null) {
            instance = new TacGiaDAO();
        }
        return instance;
    }

    public List<TacGia> getAllTacGia() {
        List<TacGia> ListTacGia = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbtacgia where record_status = 1");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                ListTacGia.add(new TacGia(res.getString(1), res.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListTacGia;
    }

    public TacGia getTacGiaByID(String ID) {
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbtacgia where matacgia=?");
            ps.setString(1, ID);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                return new TacGia(res.getString(1), res.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addTacGia(String maTacGia, String tenTacGia) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into tbtacgia(matacgia, tentacgia) values(?,?)");
            ps.setString(1, maTacGia);
            ps.setString(2, tenTacGia);

            res = ps.executeUpdate();
            System.out.println(res + "row is effected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public int editTacGia(String maTacGia, String tenTacGia) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection();) {
            PreparedStatement ps = conn.prepareStatement("update tbtacgia set tentacgia=? where matacgia=?");
            ps.setString(1, tenTacGia);
            ps.setString(2, maTacGia);
            res = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public int deleteTacGia(String id) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection();) {
            PreparedStatement ps = conn.prepareStatement("update tbtacgia set record_status = 0 where matacgia=?");
            ps.setString(1, id);
            res = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<TacGia> searchTacGia(String find) {
        List<TacGia> ListTacGia = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbtacgia where (tentacgia is null or tentacgia = '' or tentacgia LIKE ?) and record_status = 1");
            ps.setString(1, "%" + find + "%");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                ListTacGia.add(new TacGia(res.getString(1), res.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListTacGia;
    }
}
