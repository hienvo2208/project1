package com.javafx.librarian.dao;

import com.javafx.librarian.model.BaoCaoTheoTheLoai;
import com.javafx.librarian.model.CTBaoCaoTheoTheLoai;
import com.javafx.librarian.model.Sach;
import com.javafx.librarian.model.TheLoai;
import com.javafx.librarian.utils.Util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BaoCaoTheoTheLoaiDAO {
    private static BaoCaoTheoTheLoaiDAO instance;

    private BaoCaoTheoTheLoaiDAO() {
    }

    public static BaoCaoTheoTheLoaiDAO getInstance() {
        if (instance == null) {
            instance = new BaoCaoTheoTheLoaiDAO();
        }
        return instance;
    }

    public int addBaoCaoTheoTheLoai(BaoCaoTheoTheLoai bc) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into tbbaocaotheotheloai(`mabaocaotheloai`, `thang`, `nam`, `tongluotmuon`) values(?,?,?,?)");
            ps.setString(1, bc.getMaBaoCaoTheoTheLoai());
            ps.setInt(2, bc.getThang());
            ps.setInt(3,bc.getNam());
            ps.setInt(4, bc.getTongLuotMuon());
            res = ps.executeUpdate();
            System.out.println(res + "row is effected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public int addCTBaoCaoTheoTheLoai(CTBaoCaoTheoTheLoai ctbc) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into tbctbaocaotheloai(`mabaocaotheloai`, `matheloai`, `tentheloai`, `soluotmuon`, `tile`) values(?,?,?,?,?)");
            ps.setString(1, ctbc.getMaBaoCaoTheoTheLoai());
            ps.setString(2, ctbc.getMaTheLoai());
            ps.setString(3,ctbc.getTenTheLoai());
            ps.setInt(4, ctbc.getSoLuotMuon());
            ps.setDouble(5, ctbc.getTiLe());
            res = ps.executeUpdate();
            System.out.println(res + "row is effected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public List<CTBaoCaoTheoTheLoai> getAllCTBCTheoTL(int thang, int nam)
    {
        int tongluotmuon = 0;
        List<CTBaoCaoTheoTheLoai> list = new ArrayList<>();
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select count(ct.maphieumuon) from tbctphieumuon ct, tbphieumuon pm where ct.maphieumuon=pm.maphieumuon and MONTH(pm.NgayMuon) = ? and YEAR(pm.NgayMuon) = ?");
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ResultSet res = ps.executeQuery();
            res.next();
            tongluotmuon = res.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT tl.matheloai, tl.tentheloai, COUNT(ct.maphieumuon) as soluotmuon from ((tbtheloai tl LEFT JOIN tbsach s ON tl.matheloai = s.matheloai) LEFT JOIN (select ct.* FROM tbctphieumuon ct, tbphieumuon pm WHERE ct.maphieumuon = pm.maphieumuon and YEAR(pm.ngaymuon) = ? and MONTH(pm.ngaymuon) = ?) as ct ON s.masach = ct.masach) GROUP BY tl.matheloai, tl.tentheloai");
            ps.setInt(1, nam);
            ps.setInt(2, thang);
            ResultSet res = ps.executeQuery();
            int count = 0;
            while (res.next()) {
                String maTheLoai = res.getString(1);
                String tenTheLoai = res.getString(2);
                int soLuotMuon = res.getInt(3);
                double tiLe = tongluotmuon == 0 ? 0 : (soLuotMuon * 100 * 1.0) / tongluotmuon;
                list.add(new CTBaoCaoTheoTheLoai(maTheLoai, tenTheLoai, soLuotMuon, tiLe, ++count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int deleteBaoCaoTheoTheLoai(int thang, int nam) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("delete from tbbaocaotheotheloai where thang=? and nam=?");
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            res = ps.executeUpdate();
            System.out.println(res + "row is effected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public int getTongLuotMuon(int thang, int nam)
    {
        int tongluotmuon = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select count(ct.maphieumuon) from tbctphieumuon ct, tbphieumuon pm where ct.maphieumuon=pm.maphieumuon and MONTH(pm.NgayMuon) = ? and YEAR(pm.NgayMuon) = ?");
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ResultSet res = ps.executeQuery();
            res.next();
            tongluotmuon = res.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongluotmuon;
    }
}
