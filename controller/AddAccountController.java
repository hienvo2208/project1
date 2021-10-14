package com.javafx.librarian.controller;

import com.javafx.librarian.dao.AccountDao;
import com.javafx.librarian.model.Account;
import com.javafx.librarian.service.AccountService;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class AddAccountController {
    public AnchorPane paneAddAccount;
    public Pane panelAddAcc;
    public PasswordField textMatKhau;
    public JFXButton btnThem;
    public JFXButton btnHuy;
    public JFXButton btnClose;
    public FontAwesomeIcon iconClose;
    public TextField textTenTaiKhoan;

    private double mousepX = 0;
    private double mousepY = 0;
    ObservableList<Account> listAcc;

    @FXML
    public void initialize(){
        panelAddAcc.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelAddAcc.setOnMouseDragged(mouseEvent -> {
            panelAddAcc.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelAddAcc.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });
    }

    public void setListAccount(ObservableList<Account> listAcc){
        this.listAcc = listAcc;
    }

    public void btnThemClicked(ActionEvent actionEvent) {
        //
        //
        Account acc = new Account(textTenTaiKhoan.getText(), textMatKhau.getText(), 1, "", "");
//        acc.setUsername(textTenTaiKhoan.getText());
//        acc.setPassword(textMatKhau.getText());
//        acc.setIdper(1);

        List<String> all = AccountDao.getInstance().getAllUserKeys();
        if(all.contains(acc.getUsername())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Tên tài khoản đã tồn tại trong hệ thống!");
            alert.showAndWait();
            return;
        }

        AccountService.getInstance().addUser(acc);
        listAcc.add(AccountService.getInstance().getUserById(acc.getUsername()));
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void btnHuyClicked(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void btnCloseAction(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void btnCloseMouseExit(MouseEvent mouseEvent) {
        btnClose.setStyle("-fx-background-color: #a6a6a6; -fx-background-radius: 15");
        iconClose.setVisible(false);
    }

    public void btnCloseMouseEnter(MouseEvent mouseEvent) {
        btnClose.setStyle("-fx-background-color: red; -fx-background-radius: 15");
        iconClose.setVisible(true);
    }
}
