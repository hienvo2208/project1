package com.javafx.librarian.service;

import com.javafx.librarian.dao.LSMuonSachDao;
import com.javafx.librarian.model.LSMuonSach;

import java.util.List;

public class LSMuonSachService {

    private static LSMuonSachService instance;

    private LSMuonSachService(){}

    public static LSMuonSachService getInstance(){
        if(instance == null){
            instance = new LSMuonSachService();
        }
        return instance;
    }

    public List<LSMuonSach> getListLSMuonSach(String madocgia) {return LSMuonSachDao.getInstance().getListLSMuonSach(madocgia);}
}
