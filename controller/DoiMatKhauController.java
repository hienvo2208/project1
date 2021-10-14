package com.javafx.librarian.controller;

import com.javafx.librarian.model.Account;
import com.javafx.librarian.utils.Util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DoiMatKhauController {

    
    public Pane panelDMK;
    public PasswordField textMatKhauHT;
    public PasswordField textMatKhauMoi;
    public PasswordField textNhapLaiMK;
    Account acc;
    private double mousepX = 0;
    private double mousepY = 0;

    public AnchorPane paneDoiMatKhau;
   
    public JFXButton btnDoi;
    public JFXButton btnHuy;
    public JFXButton btnClose;
    public FontAwesomeIcon iconClose;

    @FXML
    public void initialize(){
        panelDMK.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelDMK.setOnMouseDragged(mouseEvent -> {
            paneDoiMatKhau.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            paneDoiMatKhau.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });

        textMatKhauHT.textProperty().addListener((observableValue, s, t1) -> {
            if(t1.equals(acc.getPassword())){
                textMatKhauMoi.setEditable(true);
                textNhapLaiMK.setEditable(true);
                if(textNhapLaiMK.getText().equals(textMatKhauMoi.getText())&& !textNhapLaiMK.getText().isEmpty()){
                    btnDoi.setDisable(false);
                }
            }else{
                textMatKhauMoi.setEditable(false);
                textNhapLaiMK.setEditable(false);
                btnDoi.setDisable(true);
            }
        });
        textMatKhauMoi.textProperty().addListener((observableValue, s, t1) -> {
            if(t1.equals(textNhapLaiMK.getText())&&(!t1.isEmpty() || !t1.equals(""))&&acc.getPassword().equals(textMatKhauHT.getText())){
                btnDoi.setDisable(false);
            }else btnDoi.setDisable(true);
        });
        textNhapLaiMK.textProperty().addListener((observableValue, s, t1) -> {
            if(t1.equals(textMatKhauMoi.getText())&&(!t1.isEmpty() || !t1.equals(""))&&acc.getPassword().equals(textMatKhauHT.getText())){
                btnDoi.setDisable(false);
            }else btnDoi.setDisable(true);
        });
    }

    public void setMK(Account ac){
        this.acc = ac;
        System.out.println(acc.getPassword());
    }

    public void btnDoiClicked(ActionEvent actionEvent) {
        this.acc.setPassword(textMatKhauMoi.getText());
        Util.showSuccess(1, "Đổi mật khẩu", "Đổi mật khẩu thành công!");
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void btnHuyClicked(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
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
}
