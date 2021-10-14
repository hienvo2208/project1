package com.javafx.librarian.dao;

import com.javafx.librarian.model.Account;
import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.service.AccountService;
import com.javafx.librarian.utils.Util;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DocGiaDao {
    private static DocGiaDao instance;

    private DocGiaDao() {
    }

    public static DocGiaDao getInstance() {
        if (instance == null) {
            instance = new DocGiaDao();
        }
        return instance;
    }

    public List<DocGia> getListDocGia() {
        List<DocGia> docGias = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select a.*, b.* from tbdocgia as a inner join tbloaidocgia  as b on a.maloaidocgia = b.maloaidocgia where a.record_status = 1 order by a.ngaylapthe desc";

        try {
            assert connection != null;
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                DocGia docgia = new DocGia();
                docgia.setMaDocGia(rs.getString("madocgia"));
                docgia.setTenDocGia(rs.getString("tendocgia"));
                docgia.setMaLoaiDocGia(rs.getString("maloaidocgia"));
                docgia.setNgaySinh(rs.getDate("ngaysinh"));
                docgia.setDiaChi(rs.getString("diachi"));
                docgia.setEmail(rs.getString("email"));
                docgia.setNgayLapThe(rs.getDate("ngaylapthe"));
                docgia.setNgayHetHan(rs.getDate("ngayhethan"));
                docgia.setTinhTrangThe(rs.getByte("tinhtrangthe"));
                docgia.setTongNo(rs.getDouble("tongno"));
                docgia.setIdAccount((rs.getString("idaccount")));
                docgia.setSoDienThoai(rs.getString("sdt"));
                docgia.setTenLoaiDG(rs.getString("tenloaidocgia"));

                docGias.add(docgia);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return docGias;
    }

    public List<DocGia> searchDocGia(String find) {
        List<DocGia> docGias = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select * from tbdocgia where (tendocgia is null or tendocgia = '' or tendocgia LIKE ?) and record_status = 1";

        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + find + "%");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                DocGia docgia = new DocGia();
                docgia.setMaDocGia(rs.getString("madocgia"));
                docgia.setTenDocGia(rs.getString("tendocgia"));
                docgia.setMaLoaiDocGia(rs.getString("maloaidocgia"));
                docgia.setNgaySinh(rs.getDate("ngaysinh"));
                docgia.setDiaChi(rs.getString("diachi"));
                docgia.setEmail(rs.getString("email"));
                docgia.setNgayLapThe(rs.getDate("ngaylapthe"));
                docgia.setNgayHetHan(rs.getDate("ngayhethan"));
                docgia.setTinhTrangThe(rs.getByte("tinhtrangthe"));
                docgia.setTongNo(rs.getDouble("tongno"));
                docgia.setIdAccount((rs.getString("idaccount")));
                docgia.setSoDienThoai(rs.getString("sdt"));

                docGias.add(docgia);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return docGias;
    }

    public int deleteDocGia(String madg) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "update tbdocgia set record_status=0 WHERE madocgia=?";
        int rs = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, madg);

            rs = preparedStatement.executeUpdate();
            System.out.println(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public int updateDocGia(DocGia dg) {
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "Update tbdocgia set tendocgia=?, maloaidocgia=?, ngaysinh=?, ngayhethan=?, " +
                "tinhtrangthe=?, tongno=?, sdt=?, diachi=? where madocgia=?";
        int rs = 0;
        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dg.getTenDocGia());
            preparedStatement.setString(2, dg.getMaLoaiDocGia());
            preparedStatement.setDate(3, Date.valueOf(Util.convertDateToLocalDate(dg.getNgaySinh())));
            preparedStatement.setDate(4, Date.valueOf(Util.convertDateToLocalDate(dg.getNgayHetHan())));
            preparedStatement.setInt(5, dg.getTinhTrangThe());
            preparedStatement.setDouble(6, dg.getTongNo());
            preparedStatement.setString(7, dg.getSoDienThoai());
            preparedStatement.setString(8, dg.getDiaChi());
            preparedStatement.setString(9, dg.getMaDocGia());

            rs = preparedStatement.executeUpdate();
            System.out.println(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public int addDocGia(DocGia dg) {
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "INSERT INTO tbdocgia(tendocgia, maloaidocgia, ngaysinh, diachi, email, ngaylapthe, ngayhethan, tinhtrangthe, tongno, idaccount, sdt, madocgia, record_status)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?, 1)";

        int rs = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dg.getTenDocGia());
            preparedStatement.setString(2, dg.getMaLoaiDocGia());
            preparedStatement.setDate(3, Date.valueOf(Util.convertDateToLocalDate(dg.getNgaySinh())));
            preparedStatement.setString(4, dg.getDiaChi());
            preparedStatement.setString(5, dg.getEmail());
            preparedStatement.setDate(6, Date.valueOf(Util.convertDateToLocalDate(dg.getNgayLapThe())));
            preparedStatement.setDate(7, Date.valueOf(Util.convertDateToLocalDate(dg.getNgayHetHan())));
            preparedStatement.setInt(8, 1);
            preparedStatement.setDouble(9, dg.getTongNo());
            preparedStatement.setString(10, dg.getIdAccount());
            preparedStatement.setString(11, dg.getSoDienThoai());
            preparedStatement.setString(12, dg.getMaDocGia());

            rs = preparedStatement.executeUpdate();
            System.out.println(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public DocGia getDocGia(String idaccount, String madg) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT a.*, b.* FROM tbdocgia as a inner join tbloaidocgia as b on a.maloaidocgia = b.maloaidocgia WHERE (a.madocgia=? or a.idaccount=?) LIMIT 1";

        DocGia docgia = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, madg);
            preparedStatement.setString(2, idaccount);

            ResultSet rsDG = preparedStatement.executeQuery();
            rsDG.last();

            docgia = new DocGia();
            docgia.setMaDocGia(rsDG.getString("madocgia"));
            docgia.setTenDocGia(rsDG.getString("tendocgia"));
            docgia.setMaLoaiDocGia(rsDG.getString("maloaidocgia"));
            docgia.setNgaySinh(rsDG.getDate("ngaysinh"));
            docgia.setDiaChi(rsDG.getString("diachi"));
            docgia.setEmail(rsDG.getString("email"));
            docgia.setNgayLapThe(rsDG.getDate("ngaylapthe"));
            docgia.setNgayHetHan(rsDG.getDate("ngayhethan"));
            docgia.setTinhTrangThe(rsDG.getByte("tinhtrangthe"));
            docgia.setTongNo(rsDG.getDouble("tongno"));
            docgia.setIdAccount((rsDG.getString("idaccount")));
            docgia.setSoDienThoai(rsDG.getString("sdt"));
            docgia.setTenLoaiDG(rsDG.getString("tenloaidocgia"));
            docgia.setGioiThieu(rsDG.getString("introduction"));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return docgia;
    }

    public DocGia getDocGiaByID(String madg) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM tbdocgia WHERE madocgia=? and record_status = 1";
        DocGia docgia = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, madg);

            ResultSet rsDG = preparedStatement.executeQuery();
            rsDG.last();

            docgia = new DocGia();
            docgia.setMaDocGia(rsDG.getString("madocgia"));
            docgia.setTenDocGia(rsDG.getString("tendocgia"));
            docgia.setMaLoaiDocGia(rsDG.getString("maloaidocgia"));
            docgia.setNgaySinh(rsDG.getDate("ngaysinh"));
            docgia.setDiaChi(rsDG.getString("diachi"));
            docgia.setEmail(rsDG.getString("email"));
            docgia.setNgayLapThe(rsDG.getDate("ngaylapthe"));
            docgia.setNgayHetHan(rsDG.getDate("ngayhethan"));
            docgia.setTinhTrangThe(rsDG.getByte("tinhtrangthe"));
            docgia.setTongNo(rsDG.getDouble("tongno"));
            docgia.setIdAccount((rsDG.getString("idaccount")));
            docgia.setSoDienThoai(rsDG.getString("sdt"));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return docgia;
    }

    public List<DocGia> getListDocGiaToCB() {
        List<DocGia> docGias = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select * from tbdocgia where tinhtrangthe = 1 and record_status = 1 and madocgia NOT IN (SELECT madocgia FROM tbphieumuon WHERE tinhtrang = 1)";

        try {
            assert connection != null;
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                DocGia docgia = new DocGia();
                docgia.setMaDocGia(rs.getString("madocgia"));
                docgia.setTenDocGia(rs.getString("tendocgia"));
                docgia.setMaLoaiDocGia(rs.getString("maloaidocgia"));
                docgia.setNgaySinh(rs.getDate("ngaysinh"));
                docgia.setDiaChi(rs.getString("diachi"));
                docgia.setEmail(rs.getString("email"));
                docgia.setNgayLapThe(rs.getDate("ngaylapthe"));
                docgia.setNgayHetHan(rs.getDate("ngayhethan"));
                docgia.setTinhTrangThe(rs.getByte("tinhtrangthe"));
                docgia.setTongNo(rs.getDouble("tongno"));
                docgia.setIdAccount((rs.getString("idaccount")));
                docgia.setSoDienThoai(rs.getString("sdt"));

                docGias.add(docgia);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return docGias;
    }

    public List<DocGia> getListDocGiaToPhieuTraCB() {
        List<DocGia> docGias = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select * from tbdocgia where tinhtrangthe = '1' and record_status = '1' and madocgia IN (SELECT madocgia FROM tbphieumuon WHERE tinhtrang = 1)";

        try {
            assert connection != null;
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                DocGia docgia = new DocGia();
                docgia.setMaDocGia(rs.getString("madocgia"));
                docgia.setTenDocGia(rs.getString("tendocgia"));
                docgia.setMaLoaiDocGia(rs.getString("maloaidocgia"));
                docgia.setNgaySinh(rs.getDate("ngaysinh"));
                docgia.setDiaChi(rs.getString("diachi"));
                docgia.setEmail(rs.getString("email"));
                docgia.setNgayLapThe(rs.getDate("ngaylapthe"));
                docgia.setNgayHetHan(rs.getDate("ngayhethan"));
                docgia.setTinhTrangThe(rs.getByte("tinhtrangthe"));
                docgia.setTongNo(rs.getDouble("tongno"));
                docgia.setIdAccount((rs.getString("idaccount")));
                docgia.setSoDienThoai(rs.getString("sdt"));

                docGias.add(docgia);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return docGias;
    }
    public List<DocGia> getListDGHasADebt(){
        List<DocGia> docGias = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select madocgia, tendocgia, tongno from tbdocgia where record_status = 1 and tongno>0";

        try {
            assert connection != null;
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                DocGia docgia = new DocGia();
                docgia.setMaDocGia(rs.getString("madocgia"));
                docgia.setTenDocGia(rs.getString("tendocgia"));
                docgia.setTongNo(rs.getDouble("tongno"));

                docGias.add(docgia);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return docGias;
    }

    public int updateInfoDocDG(String madg, String ten, String diachi, String sdt, Date ngaysinh, String idacc, String pass, String gt){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "Update tbdocgia set tendocgia=?, diachi=?, sdt=?, ngaysinh=?, introduction=? where madocgia=?";
        int rs = 0;
        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ten);
            preparedStatement.setString(2, diachi);
            preparedStatement.setString(3, sdt);
            preparedStatement.setDate(4, Date.valueOf(Util.convertDateToLocalDate(ngaysinh)));
            preparedStatement.setString(5, gt);
            preparedStatement.setString(6, madg);

            rs = preparedStatement.executeUpdate();

            rs += AccountDao.getInstance().editUser(new Account(idacc, pass, 1, "",""));
            System.out.println(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public int getCount() {
        int ret = 0;

        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select count(*) from tbdocgia where record_status = 1");
            ResultSet res = ps.executeQuery();
            res.next();
            ret = res.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

//    public boolean updatecodedg(){
//        Connection connection = JDBCConnection.getJDBCConnection();
////        String sql = "update tbdocgia set madocgia=? where madocgia=?";
////
////        String sql1 = "select * from tbdocgia where record_status = 1";
//        String sql = "insert into tbloaidocgia(maloaidocgia, tenloaidocgia, record_status) values(?, 'Sinh Viên', 1)";
//
//        try {
//            assert connection != null;
////            Statement statement = connection.createStatement();
////
////            ResultSet rs = statement.executeQuery(sql1);
//
////            while (rs.next()) {
////                System.out.println("1");
//                PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setString(1, Util.generateID((Util.PREFIX_CODE.LDG)));
////                preparedStatement.setString(2, rs.getString("madocgia"));
//
//                preparedStatement.executeUpdate();
////            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return true;
//    }
}