package com.javafx.librarian.controller;

import com.javafx.librarian.model.Sach;
import com.javafx.librarian.model.SachDashboard;
import com.javafx.librarian.service.TrangChuService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class TrangChuController implements Initializable {
    public ImageView imgFooterSach;
    public TableColumn<SachDashboard, String> colMS;
    public TableColumn<SachDashboard, String> colTS;
    public TableColumn<SachDashboard, String> colTL;
    public TableColumn<SachDashboard, String> colTG;
    public TableView<SachDashboard> tbSachMoi;
    @FXML
    private Label lbSach;
    @FXML
    private Label lbDocGia;
    @FXML
    private Label lbNhanVien;
    @FXML
    private Label lbSachMuon;
    @FXML
    private VBox boxSach;
    @FXML
    private VBox boxAction;

    ObservableList<SachDashboard> new10Sach = FXCollections.observableArrayList();

    String style = "-fx-background-color: #d2d2d2; -fx-background-radius: 30;" +
            " -fx-alignment: center; -fx-pref-height: 50; -fx-wrap-text: true;" +
            "-fx-padding: 0 10";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<String, Integer> features = TrangChuService.getInstance().getAllFeature();
        lbSach.setText(features.get("sachs").toString());
        lbDocGia.setText(features.get("docgias").toString());
        lbNhanVien.setText(features.get("nvs").toString());
        lbSachMuon.setText(features.get("sachms").toString());
        
        colMS.setCellValueFactory(new PropertyValueFactory<>("maSach"));
        colTS.setCellValueFactory(new PropertyValueFactory<>("tenSach"));
        colTG.setCellValueFactory(new PropertyValueFactory<>("tenTacGia"));
        colTL.setCellValueFactory(new PropertyValueFactory<>("tenTheLoai"));

        new10Sach.addAll(TrangChuService.getInstance().getNewSach());
        tbSachMoi.setItems(new10Sach);

        List<String> new10Action = TrangChuService.getInstance().getActionMuonTra();
        new10Action.forEach(e -> {
            Label row = new Label();
            row.setStyle(style);
            row.setText(e);
            boxAction.getChildren().add(row);
            Pane p = new Pane();
            p.setPrefHeight(8);
            boxAction.getChildren().add(p);
        });
    }
}
