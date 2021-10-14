package com.javafx.librarian.service;

import com.javafx.librarian.dao.PhieuMuonDAO;
import com.javafx.librarian.dao.TacGiaDAO;
import com.javafx.librarian.model.PhieuMuon;
import com.javafx.librarian.model.TacGia;

import java.util.List;

public class TacGiaService {
    private static TacGiaService instance;

    private TacGiaService() {
    }

    public static TacGiaService getInstance() {
        if (instance == null)
            instance = new TacGiaService();
        return instance;
    }

    public List<TacGia> getAllTacGia() {
        return TacGiaDAO.getInstance().getAllTacGia();
    }

    public TacGia getTacGiaByID(String ID) {
        return TacGiaDAO.getInstance().getTacGiaByID(ID);
    }

    public int addTacGia(String maTacGia, String tenTacGia) {
        return TacGiaDAO.getInstance().addTacGia(maTacGia, tenTacGia);
    }

    public int editTacGia(String maTacGia, String tenTacGia) {
        return TacGiaDAO.getInstance().editTacGia(maTacGia, tenTacGia);
    }

    public int deleteTacGia(String id) {
        return TacGiaDAO.getInstance().deleteTacGia(id);
    }

    public List<TacGia> searchTG(String find){return TacGiaDAO.getInstance().searchTacGia(find);}

}
