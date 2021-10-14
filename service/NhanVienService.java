package com.javafx.librarian.service;

import com.javafx.librarian.dao.LoaiDocGiaDao;
import com.javafx.librarian.dao.NhanVienDAO;
import com.javafx.librarian.dao.TacGiaDAO;
import com.javafx.librarian.model.LoaiDocGia;
import com.javafx.librarian.model.NhanVien;
import com.javafx.librarian.model.TacGia;

import java.util.List;

public class NhanVienService {
    private static NhanVienService instance;

    private NhanVienService() {
    }

    public static NhanVienService getInstance() {
        if (instance == null)
            instance = new NhanVienService();
        return instance;
    }

    public List<NhanVien> getAllNV() {
        return NhanVienDAO.getInstance().getAllNV();
    }

    public int addNV(NhanVien nv) {
        return NhanVienDAO.getInstance().addNV(nv);
    }

    public int editNV(NhanVien nv) {
        return NhanVienDAO.getInstance().editNV(nv);
    }

    public int deleteNV(String id) {
        return NhanVienDAO.getInstance().deleteNV(id);
    }

    public List<NhanVien> searchNV(String find){return NhanVienDAO.getInstance().searchNV(find);}
}
