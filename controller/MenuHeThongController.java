package com.javafx.librarian.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuHeThongController implements Initializable {
    @FXML
    public BorderPane paneHeThong;
    @FXML
    public Pane pnCenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //.setCenter(getPage("../view/docgia/DocGiaView.fxml"));
        paneHeThong.setCenter(getPage("../view/QuyDinhDialog.fxml"));
    }

    private <T> T getPage(String url) {
        T menuHeThong = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(url));
            menuHeThong = (T) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menuHeThong;
    }

    public void btnQuyDinh_Click()
    {
        paneHeThong.setCenter(getPage("../view/QuyDinhDialog.fxml"));
    }

    public void btnGioiThieu_Click()
    {
        paneHeThong.setCenter(getPage("../view/GioiThieuDialog.fxml"));
    }

    public void btnPhanQuyen_Click()
    {
        paneHeThong.setCenter(getPage("../view/VaiTroDialog.fxml"));
    }
}
