package com.javafx.librarian.service;

import com.javafx.librarian.dao.NhanVienDAO;
import com.javafx.librarian.dao.PhieuMuonDAO;
import com.javafx.librarian.dao.SachDAO;
import com.javafx.librarian.model.CTPhieuMuon;
import com.javafx.librarian.model.NhanVien;
import com.javafx.librarian.model.PhieuMuon;
import com.javafx.librarian.model.Sach;

import java.util.Date;
import java.util.List;

public class PhieuMuonService {
    private static PhieuMuonService instance;

    private PhieuMuonService() {
    }

    public static PhieuMuonService getInstance() {
        if (instance == null)
            instance = new PhieuMuonService();
        return instance;
    }

    public List<PhieuMuon> getAllPhieuMuon() {
        return PhieuMuonDAO.getInstance().getAllPhieuMuon();
    }

    public List<CTPhieuMuon> getAllCTPhieuMuonByMaPM(String maPM) {
        return PhieuMuonDAO.getInstance().getAllCTPhieuMuonByMaPM(maPM);
    }

    public int addPhieuMuon(PhieuMuon phieuMuon) {return PhieuMuonDAO.getInstance().addPhieuMuon(phieuMuon);}

    public int addCTPhieuMuon(CTPhieuMuon ctphieuMuon) {return PhieuMuonDAO.getInstance().addCTPhieuMuon(ctphieuMuon);}

    public int giaHanPhieuMuon(String maPM, Date hanTra){return PhieuMuonDAO.getInstance().giaHanPhieuMuon(maPM, hanTra);}

    public List<PhieuMuon> searchPM(String find){return PhieuMuonDAO.getInstance().searchPM(find);}
}
