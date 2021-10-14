package com.javafx.librarian.dao;

import com.javafx.librarian.model.BaoCaoSachTraTre;
import com.javafx.librarian.model.BaoCaoTheoTheLoai;
import com.javafx.librarian.model.CTBaoCaoSTT;
import com.javafx.librarian.model.CTBaoCaoTheoTheLoai;
import com.javafx.librarian.utils.Util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BaoCaoSachTTDAO {
    private static BaoCaoSachTTDAO instance;

    private BaoCaoSachTTDAO() {
    }

    public static BaoCaoSachTTDAO getInstance() {
        if (instance == null) {
            instance = new BaoCaoSachTTDAO();
        }
        return instance;
    }

    public int addBaoCaoSachTT(BaoCaoSachTraTre bc) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into tbbaocaosachtratre(`mabcstt`, `ngay`) values(?,?)");
            ps.setString(1, bc.getMaBCSTT());
            ps.setDate(2, Date.valueOf(Util.convertDateToLocalDate(bc.getNgay())));
            res = ps.executeUpdate();
            System.out.println(res + "row is effected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public int addCTBaoCaoSachTT(CTBaoCaoSTT ctbc) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into tbctbaocaosachtratre (`mabcstt`, `masach`, `tensach`, `ngaymuon`, `songaytratre`) values(?,?,?,?,?)");
            ps.setString(1, ctbc.getMaBCSTT());
            ps.setString(2, ctbc.getMaSach());
            ps.setString(3,ctbc.getTenSach());
            ps.setDate(4, Date.valueOf(Util.convertDateToLocalDate(ctbc.getNgayMuon())));
            ps.setInt(5, ctbc.getSoNgayTraTre());
            res = ps.executeUpdate();
            System.out.println(res + "row is effected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public List<CTBaoCaoSTT> getAllCTBCSachTT(Date ngay)
    {
        List<CTBaoCaoSTT> list = new ArrayList<>();
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT s.masach, s.tensach, pm.ngaymuon, SUM(ctpt.songaymuon) as songaytratre FROM tbsach s, tbphieumuon pm, tbphieutra pt, tbctphieutra ctpt WHERE s.masach = ctpt.masach and ctpt.maphieutra = pt.maphieutra and pt.maphieumuon = pm.maphieumuon and pt.ngaytra = ? GROUP BY s.masach, s.tensach");
            ps.setDate(1, Date.valueOf(Util.convertDateToLocalDate(ngay)));
            ResultSet res = ps.executeQuery();
            int count = 0;
            while (res.next()) {
                String masach = res.getString(1);
                String tensach = res.getString(2);
                Date ngayMuon = res.getDate(3);
                int songaytratre = res.getInt(4);
                list.add(new CTBaoCaoSTT(masach, tensach, ngayMuon, songaytratre, ++count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int deleteBaoCaoSachTT(Date ngay) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("delete from tbbaocaosachtratre where ngay=?");
            ps.setDate(1, Date.valueOf(Util.convertDateToLocalDate(ngay)));
            res = ps.executeUpdate();
            System.out.println(res + "row is effected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
