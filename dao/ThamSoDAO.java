package com.javafx.librarian.dao;

import com.javafx.librarian.model.Sach;
import com.javafx.librarian.model.ThamSo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThamSoDAO {
    private static ThamSoDAO instance;

    private ThamSoDAO() {
    }

    public static ThamSoDAO getInstance() {
        if (instance == null) {
            instance = new ThamSoDAO();
        }
        return instance;
    }

    public ThamSo getThamSo() {
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from tbthamso where record_status = 1");
            ResultSet res = ps.executeQuery();
            res.next();
            int maxTuoi = res.getInt(1);
            int minTuoi = res.getInt(2);
            int hanThe = res.getInt(3);
            int soTacGia = res.getInt(4);
            int KhoangCachXB = res.getInt(5);
            int MaxSachMuon = res.getInt(6);
            int HanMuon = res.getInt(7);
            int TienPhat = res.getInt(8);
            int TienPhatSach = res.getInt(9);
            Blob anhMacDinhBlob = res.getBlob(10);

            InputStream anhBiaStream = anhMacDinhBlob.getBinaryStream();
            File anhBia = File.createTempFile("temp", null);
            org.apache.commons.io.FileUtils.copyInputStreamToFile(anhBiaStream, anhBia);
            FileInputStream anhBiaDTO = new FileInputStream(anhBia);

            return new ThamSo(maxTuoi, minTuoi, hanThe, soTacGia, KhoangCachXB, MaxSachMuon, HanMuon, TienPhat, TienPhatSach, anhBiaDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int suaThamSo(ThamSo thamSo) {
        int res = 0;
        try (Connection conn = JDBCConnection.getJDBCConnection()) {
            PreparedStatement ps = conn.prepareStatement("update tbthamso set tuoitoida=?, tuoitoithieu=?, thoihanthe=?, khoangcachxb=?, sosachmuontoida=?, thoigianmuontoida=?, tienphatmotngay=?, tienphatsachhu=? where record_status = 1");
            ps.setInt(1, thamSo.getMaxTuoi());
            ps.setInt(2, thamSo.getMinTuoi());
            ps.setInt(3, thamSo.getHanThe());
            ps.setInt(4, thamSo.getKhoangCachXB());
            ps.setInt(5, thamSo.getMaxSachMuon());
            ps.setInt(6, thamSo.getHanMuon());
            ps.setInt(7, thamSo.getTienPhat());
            ps.setInt(8, thamSo.getTienPhatSach());
            res = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
