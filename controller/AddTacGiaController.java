package com.javafx.librarian.controller;

import com.javafx.librarian.service.TacGiaService;
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

public class AddTacGiaController implements Initializable {
    private TacGiaController TacGia;
    @FXML
    public TextField txtAddMaTacGia;
    @FXML
    public TextField txtAddTenTacGia;
    @FXML
    public Button btnAddThem;
    @FXML
    public Button btnAddDong;
    @FXML
    public Pane panelThemTacGia;
    @FXML
    public JFXButton btnClose;
    @FXML
    public FontAwesomeIcon iconClose;
    private double mousepX = 0;
    private double mousepY = 0;

    public TacGiaController tacGiaController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panelThemTacGia.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelThemTacGia.setOnMouseDragged(mouseEvent -> {
            panelThemTacGia.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelThemTacGia.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });
        txtAddMaTacGia.setText(Util.generateID(Util.PREFIX_CODE.TG));
        txtAddMaTacGia.setDisable(true);
    }

    public void setTacGia(TacGiaController tacGia) {
        this.tacGiaController = tacGia;
    }

    public void btnAddDong_Click(ActionEvent event) {
        Stage stage = (Stage) btnAddDong.getScene().getWindow();
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

    public void btnAddThem_Click(ActionEvent event) {
        //VALIDATE
        if(txtAddTenTacGia.getText().trim().equals("")
        ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Vui lòng nhập đầy đủ dữ liệu!");
            alert.showAndWait();
            return;
        }
        //

        //
        String maTacGia = txtAddMaTacGia.getText();
        String tenTacGia = txtAddTenTacGia.getText();
        //
        int rs = TacGiaService.getInstance().addTacGia(maTacGia, tenTacGia);
        Util.showSuccess(rs, "Quản lý tác giả", "Thêm tác giả thành công!");
        tacGiaController.refreshTable();
        txtAddTenTacGia.setText("");
        txtAddMaTacGia.setText(Util.generateID(Util.PREFIX_CODE.TG));
    }
}
