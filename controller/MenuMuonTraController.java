package com.javafx.librarian.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuMuonTraController implements Initializable {
    @FXML
    public BorderPane borderPaneMuon;
    @FXML
    public BorderPane borderPaneTra;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //.setCenter(getPage("../view/docgia/DocGiaView.fxml"));
        borderPaneMuon.setCenter(getPage("../view/MuonView.fxml"));
        borderPaneTra.setCenter(getPage("../view/TraView.fxml"));
    }

    private <T> T getPage(String url) {
        T menuMuonTraView = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(url));
            menuMuonTraView = (T) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menuMuonTraView;
    }
}
