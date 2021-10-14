package com.javafx.librarian.dao;

import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.model.Sach;
import com.javafx.librarian.model.SachDashboard;
import com.javafx.librarian.model.TheLoai;
import com.javafx.librarian.service.SachService;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TrangChuDAO {
    private static TrangChuDAO instance;

    private TrangChuDAO() {
    }

    public static TrangChuDAO getInstance() {
        if (instance == null) {
            instance = new TrangChuDAO();
        }
        return instance;
    }

    public Map<String, Integer> getAllFeature()
    {
        Map<String, Integer> rets = new HashMap<>();
        rets.put("sachs",SachDAO.getInstance().getCount());
        rets.put("nvs", NhanVienDAO.getInstance().getCount());
        rets.put("docgias", DocGiaDao.getInstance().getCount());
        rets.put("sachms", SachDAO.getInstance().getCountMuon());
         return rets;
    }

    public List<SachDashboard> getNewSach() {
        List<SachDashboard> ListSach = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select a.masach, a.tensach, b.tentheloai, c.tentacgia from tbsach as a join tbtheloai as b on a.matheloai = b.matheloai join tbtacgia as c on a.matacgia = c.matacgia  where a.record_status = 1 ORDER BY a.ngaynhap DESC LIMIT 10;");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String maSach = res.getString("masach");
                String tenSach = res.getString("tensach");
                String tenTheLoai = res.getString("tentheloai");
                String temTacgia = res.getString("tentacgia");

                ListSach.add(new SachDashboard(maSach, tenSach, tenTheLoai, temTacgia));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListSach;
    }

    public List<String> getActionMuonTra()
    {
        List<String> rets = new ArrayList<>();
        List<String> rets1 = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT d.tendocgia, s.tensach, pm.ngaymuon from tbdocgia d, tbphieumuon pm, tbctphieumuon ct, tbsach s WHERE ct.maphieumuon = pm.maphieumuon and pm.madocgia = d.madocgia AND ct.masach = s.masach");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                rets.add("Đọc giả " + res.getString(1) + " vừa mượn sách " + res.getString(2) + " vào lúc " + res.getDate(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.reverse(rets);
        for(int i = 3; i < rets.size(); i++)
        {
            rets.remove(i);
            i--;
        }

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT d.tendocgia, s.tensach, pt.ngaytra from tbdocgia d, tbphieutra pt, tbctphieutra ct, tbsach s WHERE ct.maphieutra = pt.maphieutra and pt.madocgia = d.madocgia AND ct.masach = s.masach");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                rets1.add("Đọc giả " + res.getString(1) + " vừa trả sách " + res.getString(2) + " vào lúc " + res.getDate(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.reverse(rets1);
        for(int i = 3; i < rets1.size(); i++)
        {
            rets1.remove(i);
            i--;
        }

        rets.addAll(rets1);

        Comparator<String> compareAction = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String v1 =  o1.split("lúc")[1].trim();
                String v2 =  o2.split("lúc")[1].trim();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate d1 = LocalDate.parse(v1);
                LocalDate d2 = LocalDate.parse(v2);
                return d2.compareTo(d1);
            }
        };

        rets.sort(compareAction);

        return rets;
    }
}
