package com.javafx.librarian.service;

import com.javafx.librarian.dao.PhieuMuonDAO;
import com.javafx.librarian.dao.SachDAO;
import com.javafx.librarian.model.PhieuMuon;
import com.javafx.librarian.model.Sach;

import java.util.List;

public class SachService {
    private static SachService instance;

    private SachService() {
    }

    public static SachService getInstance() {
        if (instance == null)
            instance = new SachService();
        return instance;
    }

    public List<Sach> getAllSach() {
        return SachDAO.getInstance().getAllSach();
    }

    public int addSach(Sach sach) {
        return SachDAO.getInstance().addSach(sach);
    }

    public int editSach(Sach sach) {
        return SachDAO.getInstance().editSach(sach);
    }

    public int deleteSach(String id) {
        return SachDAO.getInstance().deleteSach(id);
    }

    public Sach getSachByID(String maSach) {return SachDAO.getInstance().getSachByID(maSach);}

    public List<Sach> searchSach(String find){return SachDAO.getInstance().searchSach(find);}
}
