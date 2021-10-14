package com.javafx.librarian.service;

import com.javafx.librarian.dao.PhieuMuonDAO;
import com.javafx.librarian.dao.PhieuTraDAO;
import com.javafx.librarian.model.CTPhieuTra;
import com.javafx.librarian.model.PhieuMuon;
import com.javafx.librarian.model.PhieuTra;

import java.util.List;

public class PhieuTraService {
    private static PhieuTraService instance;

    private PhieuTraService() {
    }

    public static PhieuTraService getInstance() {
        if (instance == null)
            instance = new PhieuTraService();
        return instance;
    }

    public int addPhieuTra(PhieuTra phieuTra) {
        return PhieuTraDAO.getInstance().addPhieuTra(phieuTra);
    }

    public int addCTPhieuTra(CTPhieuTra ctPhieuTra) {
        return PhieuTraDAO.getInstance().addCTPhieuTra(ctPhieuTra);
    }

    public List<PhieuTra> searchPT(String find){return PhieuTraDAO.getInstance().searchPT(find);}

}
