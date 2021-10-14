package com.javafx.librarian.dao;

import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.model.PhieuThuPhat;
import com.javafx.librarian.utils.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuThuDao {
    private static PhieuThuDao instance;

    private PhieuThuDao() {
    }

    public static PhieuThuDao getInstance() {
        if (instance == null) {
            instance = new PhieuThuDao();
        }
        return instance;
    }

    public List<PhieuThuPhat> getListPhieuThu() {
        List<PhieuThuPhat> phieuThusPhats = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select a.maphieuphat, a.madocgia, a.sotienthu, a.conlai, a.ngaythu, b.tendocgia from tbphieuphat as a inner join tbdocgia as b on a.madocgia = b.madocgia where a.record_status = 1 order by a.ngaythu desc";

        try {
            assert connection != null;
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
               PhieuThuPhat pt = new PhieuThuPhat();
               pt.setMaPhieuThu(rs.getString("maphieuphat"));
               pt.setMaDocGia(rs.getString("madocgia"));
               pt.setConLai(rs.getDouble("conlai"));
               pt.setNgayThu(rs.getDate("ngaythu"));
               pt.setSoTienThu(rs.getDouble("sotienthu"));
               pt.setTenDocGia(rs.getString("tendocgia"));

               phieuThusPhats.add(pt);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return phieuThusPhats;
    }

    public int addPhieuThuPhat(PhieuThuPhat pt) {
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "INSERT INTO tbphieuphat(maphieuphat, madocgia, sotienthu, conlai, ngaythu, record_status)" +
                " VALUES (?,?,?,?,?,1)";

        String sql1 = "update tbdocgia set tongno=? where madocgia=?";

        int rs = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pt.getMaPhieuThu());
            preparedStatement.setString(2, pt.getMaDocGia());
            preparedStatement.setDouble(3, pt.getSoTienThu());
            preparedStatement.setDouble(4, pt.getConLai());
            preparedStatement.setDate(5, Date.valueOf(Util.convertDateToLocalDate(pt.getNgayThu())));

            rs = preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setDouble(1, pt.getConLai());
            preparedStatement1.setString(2, pt.getMaDocGia());

            rs += preparedStatement1.executeUpdate();

            System.out.println(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public int deletePhieuThuPhat(String maphieu) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "update tbphieuphat set record_status=0 WHERE maphieuphat=?";
        int rs = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maphieu);

            rs = preparedStatement.executeUpdate();
            System.out.println(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public int updatePhieuPhat(PhieuThuPhat pt) {
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "Update tbphieuphat a inner join tbdocgia b on a.madocgia = b.madocgia set a.sotienthu=?, a.conlai=?, a.ngaythu=? where a.maphieuthu=?";
        int rs = 0;
        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, pt.getSoTienThu());
            preparedStatement.setDouble(2, pt.getConLai());
            preparedStatement.setDate(3, Date.valueOf(Util.convertDateToLocalDate(pt.getNgayThu())));
            preparedStatement.setString(4, pt.getMaPhieuThu());

            rs = preparedStatement.executeUpdate();
            System.out.println(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public PhieuThuPhat getPhieuPhatById(String mapt) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "select a.maphieuphat, a.madocgia, a.sotienthu, a.conlai, a.ngaythu, b.tendocgia from tbphieuphat as a inner join tbdocgia as b on a.madocgia = b.madocgia where a.record_status = 1 and a.maphieuphat=?";
        PhieuThuPhat pt = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mapt);

            ResultSet rsPT = preparedStatement.executeQuery();
            rsPT.last();

            pt = new PhieuThuPhat();
            pt.setMaDocGia(rsPT.getString("madocgia"));
            pt.setTenDocGia(rsPT.getString("tendocgia"));
            pt.setSoTienThu(rsPT.getDouble("sotienthu"));
            pt.setConLai(rsPT.getDouble("conlai"));
            pt.setMaPhieuThu(rsPT.getString("maphieuphat"));
            pt.setNgayThu(rsPT.getDate("ngaythu"));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pt;
    }

    public List<PhieuThuPhat> searchDocGia(String find) {
        List<PhieuThuPhat> phieuThusPhats = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select a.maphieuphat, a.madocgia, a.sotienthu, a.conlai, a.ngaythu, b.tendocgia from tbphieuphat as a inner join tbdocgia as b on a.madocgia = b.madocgia where a.record_status = 1 and (b.tendocgia is null or b.tendocgia = '' or b.tendocgia LIKE ?) order by a.ngaythu desc";

        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%"+find+"%");
//            preparedStatement.setString(2, find);
//            preparedStatement.setString(3, find);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                PhieuThuPhat pt = new PhieuThuPhat();
                pt.setMaPhieuThu(rs.getString("maphieuphat"));
                pt.setMaDocGia(rs.getString("madocgia"));
                pt.setConLai(rs.getDouble("conlai"));
                pt.setNgayThu(rs.getDate("ngaythu"));
                pt.setSoTienThu(rs.getDouble("sotienthu"));
                pt.setTenDocGia(rs.getString("tendocgia"));

                phieuThusPhats.add(pt);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return phieuThusPhats;
    }
}
