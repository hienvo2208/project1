package com.javafx.librarian.service;

import com.javafx.librarian.dao.LoaiDocGiaDao;
import com.javafx.librarian.model.LoaiDocGia;

import java.util.List;

public class LoaiDocGiaService {
    private static LoaiDocGiaService instance;

    private LoaiDocGiaService(){}

    public static LoaiDocGiaService getInstance(){
        if(instance == null){
            instance = new LoaiDocGiaService();
        }
        return instance;
    }

    public List<LoaiDocGia> getListLoaiDocGia(){
        return LoaiDocGiaDao.getInstance().getListLoaiDocGia();
    }
    public List<LoaiDocGia> searchLoaiDocGia(String find){return LoaiDocGiaDao.getInstance().searchLoaiDocGia(find);}
    public int updateLoaiDocGia(LoaiDocGia ldg){return LoaiDocGiaDao.getInstance().updateLoaiDocGia(ldg);}
    public int addLoaiDocGia(LoaiDocGia ldg){return LoaiDocGiaDao.getInstance().addLoaiDocGia(ldg);}
    public LoaiDocGia getLoaiDocGia(String maldg){return LoaiDocGiaDao.getInstance().getLoaiDocGia(maldg);}
    public int deleteLDG(String maldg){return  LoaiDocGiaDao.getInstance().deleteLDG(maldg);}
}
