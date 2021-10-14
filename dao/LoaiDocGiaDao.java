package com.javafx.librarian.dao;

import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.model.LoaiDocGia;
import com.javafx.librarian.utils.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiDocGiaDao {
    private static LoaiDocGiaDao instance;

    private LoaiDocGiaDao(){}

    public static LoaiDocGiaDao getInstance(){
        if(instance == null){
            instance = new LoaiDocGiaDao();
        }
        return instance;
    }

    public List<LoaiDocGia> getListLoaiDocGia(){
        List<LoaiDocGia> LoaiDocGias = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select * from tbloaidocgia where record_status=1";

        try {
            assert connection != null;
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                LoaiDocGia loaiDocGia = new LoaiDocGia();
                loaiDocGia.setMaLoaiDocGia(rs.getString("maLoaiDocGia"));
                loaiDocGia.setTenLoaiDocGia(rs.getString("tenLoaiDocGia"));

                LoaiDocGias.add(loaiDocGia);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return LoaiDocGias;
    }

    public List<LoaiDocGia> searchLoaiDocGia(String find){
        List<LoaiDocGia> loaidocGias = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select * from tbloaidocgia where tenloaidocgia is null or tenloaidocgia = '' or tenloaidocgia LIKE ?";

        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%"+find+"%");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                LoaiDocGia loaiDocGia = new LoaiDocGia();
                loaiDocGia.setMaLoaiDocGia(rs.getString("maloaidocgia"));
                loaiDocGia.setTenLoaiDocGia(rs.getString("tenloaidocgia"));

                loaidocGias.add(loaiDocGia);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return loaidocGias;
    }

    public int updateLoaiDocGia(LoaiDocGia ldg){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "Update tbloaidocgia set tenloaidocgia=? where maloaidocgia=?";

        int rs =  0;
        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ldg.getTenLoaiDocGia());
            preparedStatement.setString(2, ldg.getMaLoaiDocGia());

            rs = preparedStatement.executeUpdate();
            System.out.println(rs);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public int addLoaiDocGia(LoaiDocGia ldg){
        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "INSERT INTO tbloaidocgia(maloaidocgia, tenloaidocgia) VALUES (?, ?)";

        int rs = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ldg.getMaLoaiDocGia());
            preparedStatement.setString(2, ldg.getTenLoaiDocGia());

            rs = preparedStatement.executeUpdate();
            System.out.println(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public LoaiDocGia getLoaiDocGia(String maldg){
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM tbloaidocgia WHERE (maloaidocgia=?)";

        LoaiDocGia loaiDocGia= null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maldg);

            ResultSet rsLDG = preparedStatement.executeQuery();
            rsLDG.last();
            loaiDocGia = new LoaiDocGia();
            loaiDocGia.setMaLoaiDocGia(rsLDG.getString("maloaidocgia"));
            loaiDocGia.setTenLoaiDocGia(rsLDG.getString("tenloaidocgia"));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return loaiDocGia;
    }

    public int deleteLDG(String maldg) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "update tbloaidocgia set record_status=0 WHERE maloaidocgia=?";
        int rs = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maldg);

            rs = preparedStatement.executeUpdate();
            System.out.println(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }
}
