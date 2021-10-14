package com.javafx.librarian.dao;

import com.javafx.librarian.model.Account;
import com.javafx.librarian.model.Quyen;
import com.javafx.librarian.model.Sach;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuyenDAO {
    private static QuyenDAO instance;

    private QuyenDAO() {
    }

    public static QuyenDAO getInstance() {
        if (instance == null) {
            instance = new QuyenDAO();
        }
        return instance;
    }

    public enum QUYEN {
        DG,
        QL,
        TT
    }

    public List<Quyen> getAllQuyen() {
        List<Quyen> list = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbphanquyen where record_status = 1");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int ID = res.getInt(1);
                String name = res.getString(2);
                String code = res.getString(3);
                String des = res.getString(4);
                list.add(new Quyen(ID, name, code, des));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Quyen getQuyenByIDAcc(String idAccount) {
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select r.* from tbphanquyen r, tbaccount a where r.idper = a.idper and a.idaccount = ?");
            ps.setString(1, idAccount);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int ID = res.getInt(1);
                String name = res.getString(2);
                String code = res.getString(3);
                String des = res.getString(4);
                return new Quyen(ID, name, code, des);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
