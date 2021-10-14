package com.javafx.librarian.service;


import com.javafx.librarian.dao.DocGiaDao;
import com.javafx.librarian.model.DocGia;

import java.sql.Date;
import java.util.List;

public class DocGiaService {
    private static DocGiaService instance;
    private DocGiaService(){}

    public static DocGiaService getInstance(){
        if(instance==null)
            instance = new DocGiaService();
        return instance;
    }

    public List<DocGia> getListDocGia(){
        return DocGiaDao.getInstance().getListDocGia();
    }
    public List<DocGia> searchDocGia(String find){return DocGiaDao.getInstance().searchDocGia(find);}
    public int deleteDocGia(String madg){return DocGiaDao.getInstance().deleteDocGia(madg);}
    public int updateDocGia(DocGia dg){return DocGiaDao.getInstance().updateDocGia(dg);}
    public int addDocGia(DocGia dg){return DocGiaDao.getInstance().addDocGia(dg);}
    public DocGia getDocGia(String idaccount, String madg){return DocGiaDao.getInstance().getDocGia(idaccount, madg);}
    public DocGia getDocGiaByID(String madg){return DocGiaDao.getInstance().getDocGiaByID(madg);}
    public List<DocGia> getListDocGiaToCB(){
        return DocGiaDao.getInstance().getListDocGiaToCB();
    }
//    public boolean updatecodedg(){return DocGiaDao.getInstance().updatecodedg();}
    public List<DocGia> getListDGHasADebt(){return DocGiaDao.getInstance().getListDGHasADebt();}
    public int updateInfoDocDG(String madg, String ten, String diachi, String sdt, Date ngaysinh, String idacc, String pass, String gt){
        return DocGiaDao.getInstance().updateInfoDocDG(madg, ten, diachi, sdt, ngaysinh, idacc, pass, gt);
    }
}
