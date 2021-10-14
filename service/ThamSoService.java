package com.javafx.librarian.service;

import com.javafx.librarian.dao.ThamSoDAO;
import com.javafx.librarian.model.ThamSo;

public class ThamSoService {
    private static ThamSoService instance;
    private ThamSoService(){}

    public static ThamSoService getInstance(){
        if(instance==null)
            instance = new ThamSoService();
        return instance;
    }

    public ThamSo getThamSo() {
      return ThamSoDAO.getInstance().getThamSo();
    };
    public int suaThamSo(ThamSo thamSo) {
        return ThamSoDAO.getInstance().suaThamSo(thamSo);
    };
}
