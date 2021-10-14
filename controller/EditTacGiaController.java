package com.javafx.librarian.controller;

import com.javafx.librarian.model.TacGia;
import com.javafx.librarian.service.TacGiaService;
import com.javafx.librarian.utils.Util;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditTacGiaController implements Initializable {
    private TacGiaController tacGiaController;
    private TacGia tacGia;
    @FXML
    TextField txtEditMaTacGia;
    @FXML
    TextField txtEditTenTacGia;
    @FXML
    Button btnEditThem;
    @FXML
    Button btnEditDong;
    @FXML
    public Pane panelSuaTacGia;
    @FXML
    public JFXButton btnClose;
    @FXML
    public FontAwesomeIcon iconClose;
    private double mousepX = 0;
    private double mousepY = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtEditMaTacGia.setDisable(true);
        panelSuaTacGia.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelSuaTacGia.setOnMouseDragged(mouseEvent -> {
            panelSuaTacGia.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelSuaTacGia.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });
    }

    public void setTacGia(TacGiaController tacGia) {
        this.tacGiaController = tacGia;
    }

    public void setEditTacGia(TacGia tacGia) {
        this.tacGia = tacGia;
        bindingData();
    }

    public void btnEditDong_Click(ActionEvent event) {
        Stage stage = (Stage) btnEditDong.getScene().getWindow();
        stage.close();
    }

    public void btnCloseAction(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void btnCloseMouseEnter(MouseEvent mouseEvent) {
        btnClose.setStyle("-fx-background-color: red; -fx-background-radius: 15");
        iconClose.setVisible(true);
    }

    public void btnCloseMouseExit(MouseEvent mouseEvent) {
        btnClose.setStyle("-fx-background-color: #a6a6a6; -fx-background-radius: 15");
        iconClose.setVisible(false);
    }

    public void btnEditThem_Click(ActionEvent event) {
        //VALIDATE
        if(txtEditTenTacGia.getText().trim().equals("")
        ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Vui lòng nhập đầy đủ dữ liệu!");
            alert.showAndWait();
            return;
        }
        //

        //
        String tenTacGiaMoi = txtEditTenTacGia.getText();
        //
        int rs = TacGiaService.getInstance().editTacGia(tacGia.getMaTacGia(), tenTacGiaMoi);
        Util.showSuccess(rs, "Quản lý tác giả", "Sửa tác giả thành công!");
        tacGiaController.refreshTable();
    }

    private void bindingData() {
        txtEditMaTacGia.setText(String.valueOf(tacGia.getMaTacGia()));
        txtEditTenTacGia.setText(tacGia.getTenTacGia());
    }
}
