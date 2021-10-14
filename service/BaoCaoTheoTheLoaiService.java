package com.javafx.librarian.service;

import com.javafx.librarian.dao.BaoCaoTheoTheLoaiDAO;
import com.javafx.librarian.dao.SachDAO;
import com.javafx.librarian.model.BaoCaoTheoTheLoai;
import com.javafx.librarian.model.CTBaoCaoTheoTheLoai;
import com.javafx.librarian.model.Sach;

import java.util.List;

public class BaoCaoTheoTheLoaiService {
    private static BaoCaoTheoTheLoaiService instance;

    private BaoCaoTheoTheLoaiService() {
    }

    public static BaoCaoTheoTheLoaiService getInstance() {
        if (instance == null)
            instance = new BaoCaoTheoTheLoaiService();
        return instance;
    }

    public List<CTBaoCaoTheoTheLoai> getAllCTBCTheoTL(int thang, int nam) {
        return BaoCaoTheoTheLoaiDAO.getInstance().getAllCTBCTheoTL(thang, nam);
    }

    public int addCTBaoCaoTheoTheLoai(CTBaoCaoTheoTheLoai ctbc) {
        return BaoCaoTheoTheLoaiDAO.getInstance().addCTBaoCaoTheoTheLoai(ctbc);
    }

    public int addBaoCaoTheoTheLoai(BaoCaoTheoTheLoai bc) {
        return BaoCaoTheoTheLoaiDAO.getInstance().addBaoCaoTheoTheLoai(bc);
    }

    public int deleteBaoCaoTheoTheLoai(int thang, int nam) {
        return BaoCaoTheoTheLoaiDAO.getInstance().deleteBaoCaoTheoTheLoai(thang, nam);
    }

    public int getTongLuotMuon(int thang, int nam)
    {
        return BaoCaoTheoTheLoaiDAO.getInstance().getTongLuotMuon(thang, nam);
    }
}
