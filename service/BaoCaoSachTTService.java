package com.javafx.librarian.service;

import com.javafx.librarian.dao.BaoCaoSachTTDAO;
import com.javafx.librarian.dao.BaoCaoTheoTheLoaiDAO;
import com.javafx.librarian.model.BaoCaoSachTraTre;
import com.javafx.librarian.model.BaoCaoTheoTheLoai;
import com.javafx.librarian.model.CTBaoCaoSTT;
import com.javafx.librarian.model.CTBaoCaoTheoTheLoai;

import java.sql.Date;
import java.util.List;

public class BaoCaoSachTTService {
    private static BaoCaoSachTTService instance;

    private BaoCaoSachTTService() {
    }

    public static BaoCaoSachTTService getInstance() {
        if (instance == null)
            instance = new BaoCaoSachTTService();
        return instance;
    }

    public List<CTBaoCaoSTT> getAllCTBCSachTT(Date ngay) {
        return BaoCaoSachTTDAO.getInstance().getAllCTBCSachTT(ngay);
    }

    public int addCTBaoCaoSachTT(CTBaoCaoSTT ctbc) {
        return BaoCaoSachTTDAO.getInstance().addCTBaoCaoSachTT(ctbc);
    }

    public int addBaoCaoSachTT(BaoCaoSachTraTre bc) {
        return BaoCaoSachTTDAO.getInstance().addBaoCaoSachTT(bc);
    }

    public int deleteBaoCaoSachTT(Date ngay) {
        return BaoCaoSachTTDAO.getInstance().deleteBaoCaoSachTT(ngay);
    }
}
