package com.javafx.librarian.dao;

import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.model.LSMuonSach;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LSMuonSachDao {
    private static LSMuonSachDao instance;

    private LSMuonSachDao() {
    }

    public static LSMuonSachDao getInstance() {
        if (instance == null) {
            instance = new LSMuonSachDao();
        }
        return instance;
    }

    public List<LSMuonSach> getListLSMuonSach(String madocgia) {
        List<LSMuonSach> lsMuonSaches = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "select a.ngaymuon, a.hantra, b.masach, b.record_status, c.tensach from tbphieumuon as a inner join tbctphieumuon as b inner join tbsach as c on a.maphieumuon = b.maphieumuon and b.masach = c.masach where a.record_status = 1 and a.madocgia=? order by a.ngaymuon desc";

        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, madocgia);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                LSMuonSach lsMuonSach = new LSMuonSach();
                lsMuonSach.setMaSach(rs.getString("masach"));
                lsMuonSach.setTenSach(rs.getString("tensach"));
                lsMuonSach.setTinhTrang(rs.getString("record_status"));
                lsMuonSach.setNgayMuon(rs.getDate("ngaymuon"));
                lsMuonSach.setHanTra(rs.getDate("hantra"));

                lsMuonSaches.add(lsMuonSach);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lsMuonSaches;
    }
}
