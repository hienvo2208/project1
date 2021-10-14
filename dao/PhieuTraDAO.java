package com.javafx.librarian.dao;

import com.javafx.librarian.model.CTPhieuTra;
import com.javafx.librarian.model.PhieuTra;
import com.javafx.librarian.utils.Util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhieuTraDAO {
    private static PhieuTraDAO instance;

    private PhieuTraDAO() {
    }

    public static PhieuTraDAO getInstance() {
        if (instance == null) {
            instance = new PhieuTraDAO();
        }
        return instance;
    }

    public List<PhieuTra> getAllPhieuTra() {
        List<PhieuTra> ListPT = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbphieutra where record_status = 1 order by ngaytra desc");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String maPT = res.getString(1);
                String maPM = res.getString(2);
                String maDG = res.getString(3);
                Date ngayTra = res.getDate(4);
                Double tienPhat = res.getDouble(5);
                ListPT.add(new PhieuTra(maPT ,maPM, maDG, ngayTra, tienPhat));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListPT;
    }

    public List<CTPhieuTra> getAllCTPhieuTraByMaPT(String maPT) {
        List<CTPhieuTra> ListCTPT = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select ct.masach, s.tensach, ct.songaymuon, ct.tienphat, ct.tinhtrang from tbsach s, tbctphieutra ct where ct.masach = s.masach and s.record_status = 1 and ct.maphieutra = ?");
            ps.setString(1, maPT);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String maSach = res.getString(1);
                String tenSach = res.getString(2);
                int soNgayMuon = res.getInt(3);
                int tienPhat = res.getInt(4);
                String tinhTrang = "";
                if(res.getInt(5) == 0)
                {
                    tinhTrang = "Bình thường";
                }
                else if (res.getInt(5) == 2)
                {
                    tinhTrang = "Hư hỏng";
                }
                else if (res.getInt(5) == 3)
                {
                    tinhTrang = "Mất";
                }
                ListCTPT.add(new CTPhieuTra(maPT, maSach, tenSach,null, soNgayMuon, tienPhat, tinhTrang));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListCTPT;
    }

    public int addPhieuTra(PhieuTra phieuTra) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into tbphieutra(`maphieutra`, `maphieumuon`, `madocgia`, `ngaytra`, `tienphatkynay`) values(?,?,?,?,?)");
            ps.setString(1, phieuTra.getMaPT());
            ps.setString(2, phieuTra.getMaPM());
            ps.setString(3,phieuTra.getMaDG());
            ps.setDate(4,Date.valueOf(Util.convertDateToLocalDateUI(phieuTra.getNgayTra()).plusDays(1)));
            ps.setDouble(5,phieuTra.getTienPhatKyNay());
            res = ps.executeUpdate();
            System.out.println(res + "row is effected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        double tongno = DocGiaDao.getInstance().getDocGiaByID(phieuTra.getMaDG()).getTongNo() + phieuTra.getTienPhatKyNay();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps1 = conn.prepareStatement("update tbdocgia set tongno=? where madocgia=?");
            ps1.setDouble(1, tongno);
            ps1.setString(2, phieuTra.getMaDG());
            res = ps1.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public int addCTPhieuTra(CTPhieuTra ctPhieuTra) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into tbctphieutra(`maphieutra`, `masach`, `tinhtrang`, `songaymuon`, `tienphat`) values(?,?,?,?,?)");
            ps.setString(1, ctPhieuTra.getMaPT());
            ps.setString(2, ctPhieuTra.getMaSach());
            if(ctPhieuTra.getTinhTrang().equals("Bình thường"))
            {
                ps.setInt(3, 0);
            }
            else if (ctPhieuTra.getTinhTrang().equals("Hư hỏng"))
            {
                ps.setInt(3, 2);
            }
            else if (ctPhieuTra.getTinhTrang().equals("Mất"))
            {
                ps.setInt(3, 3);
            }
            ps.setInt(4, ctPhieuTra.getSoNM());
            ps.setDouble(5, ctPhieuTra.getTienPhat());

            res = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps1 = conn.prepareStatement("update tbsach set tinhtrang=? where masach=?");
            if(ctPhieuTra.getTinhTrang().equals("Bình thường"))
            {
                ps1.setInt(1, 0);
            }
            else if (ctPhieuTra.getTinhTrang().equals("Hư hỏng"))
            {
                ps1.setInt(1, 2);
            }
            else if (ctPhieuTra.getTinhTrang().equals("Mất"))
            {
                ps1.setInt(1, 3);
            }
            ps1.setString(2, ctPhieuTra.getMaSach());
            res = ps1.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<PhieuTra> searchPT(String find) {
        List<PhieuTra> ListPT = new ArrayList<>();

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select pt.* from tbphieutra pt, tbdocgia dg where dg.madocgia = pt.madocgia and (dg.tendocgia is null or dg.tendocgia = '' or dg.tendocgia LIKE ?) and pt.record_status = 1");
            ps.setString(1, "%" + find + "%");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String maPT = res.getString(1);
                String maPM = res.getString(2);
                String maDG = res.getString(3);
                Date ngayTra = res.getDate(4);
                Double tienPhat = res.getDouble(5);
                ListPT.add(new PhieuTra(maPT ,maPM, maDG, ngayTra, tienPhat));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListPT;
    }
}
