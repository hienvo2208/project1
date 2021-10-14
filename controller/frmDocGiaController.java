package com.javafx.librarian.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class frmDocGiaController implements Initializable {
    @FXML
    public ImageView imgQLDG;
    @FXML
    public BorderPane borderPaneDocGia;
    @FXML
    public BorderPane borderPaneLDG;
    public BorderPane borderPaneThuTienPhat;
    public AnchorPane paneHeaderQLDG;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borderPaneDocGia.setCenter(getPage("../view/docgia/DocGiaView.fxml"));
        borderPaneLDG.setCenter(getPage("../view/docgia/LoaiDocGiaView.fxml"));
        borderPaneThuTienPhat.setCenter(getPage("../view/docgia/ThuTienPhatView.fxml"));

//        paneHeaderQLDG.setStyle("-fx-bac");
    }

    private <T> T getPage(String url){
        T frmDocgiaView = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(url));
            frmDocgiaView = (T) loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return frmDocgiaView;
    }
}
