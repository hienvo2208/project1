package com.javafx.librarian.service;

import com.javafx.librarian.dao.PhieuMuonDAO;
import com.javafx.librarian.dao.TacGiaDAO;
import com.javafx.librarian.dao.TheLoaiDAO;
import com.javafx.librarian.model.PhieuMuon;
import com.javafx.librarian.model.TacGia;
import com.javafx.librarian.model.TheLoai;
import java.util.List;


public class TheLoaiService {
    private static TheLoaiService instance;

    private TheLoaiService() {
    }

    public static TheLoaiService getInstance() {
        if (instance == null)
            instance = new TheLoaiService();
        return instance;
    }

    public List<TheLoai> getAllTheLoai() {
        return TheLoaiDAO.getInstance().getAllTheLoai();
    }

    public TheLoai getTheLoaiByID(String ID) {
        return TheLoaiDAO.getInstance().getTheLoaiByID(ID);
    }

    public int addTheLoai(TheLoai theloai) {
        return TheLoaiDAO.getInstance().addTheloai(theloai);
    }

    public int editTheLoai(TheLoai theLoai) {
        return TheLoaiDAO.getInstance().editTheloai(theLoai);
    }

    public int deleteTheLoai(String id) {
        return TheLoaiDAO.getInstance().deleteTheLoai(id);
    }

    public List<TheLoai> searchTL(String find){return TheLoaiDAO.getInstance().searchTheLoai(find);}
}
