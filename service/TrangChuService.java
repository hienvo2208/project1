package com.javafx.librarian.service;

import com.javafx.librarian.dao.TrangChuDAO;
import com.javafx.librarian.model.Sach;
import com.javafx.librarian.model.SachDashboard;

import java.util.List;
import java.util.Map;

public class TrangChuService {
    private static TrangChuService instance;

    private TrangChuService() {
    }

    public static TrangChuService getInstance() {
        if (instance == null) {
            instance = new TrangChuService();
        }
        return instance;
    }

    public Map<String, Integer> getAllFeature() {
        return TrangChuDAO.getInstance().getAllFeature();
    }

    public List<SachDashboard> getNewSach() {
        return TrangChuDAO.getInstance().getNewSach();
    }

    public List<String> getActionMuonTra() {
        return TrangChuDAO.getInstance().getActionMuonTra();
    }
}
