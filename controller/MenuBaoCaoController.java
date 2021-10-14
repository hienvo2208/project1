package com.javafx.librarian.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuBaoCaoController implements Initializable {
    @FXML
    public BorderPane borderPaneBCMuon;
    @FXML
    public BorderPane borderPaneBCTra;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //.setCenter(getPage("../view/docgia/DocGiaView.fxml"));
        borderPaneBCMuon.setCenter(getPage("../view/BaoCaoMuonTL.fxml"));
        borderPaneBCTra.setCenter(getPage("../view/BaoCaoSachTT.fxml"));
    }

    private <T> T getPage(String url) {
        T menuBaoCaoView = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(url));
            menuBaoCaoView = (T) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menuBaoCaoView;
    }
}
