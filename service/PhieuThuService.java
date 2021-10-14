package com.javafx.librarian.service;

import com.javafx.librarian.dao.PhieuThuDao;
import com.javafx.librarian.model.PhieuThuPhat;

import java.util.List;

public class PhieuThuService {
    private static PhieuThuService instance;

    private PhieuThuService() {
    }

    public static PhieuThuService getInstance() {
        if (instance == null)
            instance = new PhieuThuService();
        return instance;
    }

    public List<PhieuThuPhat> getListPhieuThu() {return PhieuThuDao.getInstance().getListPhieuThu(); }
    public int addPhieuThuPhat(PhieuThuPhat pt) {return PhieuThuDao.getInstance().addPhieuThuPhat(pt);}
    public int deletePhieuThuPhat(String maphieu) {return  PhieuThuDao.getInstance().deletePhieuThuPhat(maphieu);}
    public int updatePhieuPhat(PhieuThuPhat pt) {return PhieuThuDao.getInstance().updatePhieuPhat(pt);}
    public PhieuThuPhat getPhieuPhatById(String pt){return PhieuThuDao.getInstance().getPhieuPhatById(pt);}
    public List<PhieuThuPhat> searchPhieuPhat(String find) {return PhieuThuDao.getInstance().searchDocGia(find);}
}
