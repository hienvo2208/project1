package com.javafx.librarian.controller;

import com.javafx.librarian.model.TacGia;
import com.javafx.librarian.model.TheLoai;
import com.javafx.librarian.service.TacGiaService;
import com.javafx.librarian.service.TheLoaiService;
import com.javafx.librarian.utils.Util;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class EditTheLoaiController implements Initializable{
    private TheLoaiController theLoaiController;
    private TheLoai theloai;
    @FXML
    public TextField txtMaTheLoai;
    @FXML
    public TextField txtTenTheLoai;
    @FXML
    public Button btnLuu;
    @FXML
    public Button btnHuy;
    @FXML
    public Pane panelSuaTheLoai;
    @FXML
    public JFXButton btnClose;
    @FXML
    public FontAwesomeIcon iconClose;
    private double mousepX = 0;
    private double mousepY = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtMaTheLoai.setDisable(true);
        panelSuaTheLoai.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelSuaTheLoai.setOnMouseDragged(mouseEvent -> {
            panelSuaTheLoai.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelSuaTheLoai.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });
    }

    public void setTheLoaiController(TheLoaiController theLoai) {
        this.theLoaiController = theLoai;
    }

    public void setEditTheLoai(TheLoai theLoai) {
        this.theloai = theLoai;
        bindingData();
    }

    public void btnHuy_Click(ActionEvent event) {
        Stage stage = (Stage) btnHuy.getScene().getWindow();
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

    public void btnLuu_Click(ActionEvent event) {
        //VALIDATE
        if(txtTenTheLoai.getText().trim().equals("")
        ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Vui lòng nhập đầy đủ dữ liệu!");
            alert.showAndWait();
            return;
        }
        //

        String maTheLoai = txtMaTheLoai.getText();
        String tenTheLoai = txtTenTheLoai.getText();
        TheLoai theloai = new TheLoai(maTheLoai, tenTheLoai);
        //
        int rs = TheLoaiService.getInstance().editTheLoai(theloai);
        Util.showSuccess(rs, "Quản lý thể loại", "Sửa thể loại thành công!");
        theLoaiController.refreshTable();
        txtMaTheLoai.setText("");
        txtTenTheLoai.setText("");
        Stage stage = (Stage) btnHuy.getScene().getWindow();
        stage.close();
    }

    private void bindingData() {
        txtMaTheLoai.setText(String.valueOf(theloai.getMaTheLoai()));
        txtTenTheLoai.setText(theloai.getTenTheLoai());
    }
}
