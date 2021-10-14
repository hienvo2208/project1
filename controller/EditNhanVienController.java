package com.javafx.librarian.controller;

import com.javafx.librarian.dao.QuyenDAO;
import com.javafx.librarian.model.Account;
import com.javafx.librarian.model.NhanVien;
import com.javafx.librarian.model.Quyen;
import com.javafx.librarian.model.TacGia;
import com.javafx.librarian.service.AccountService;
import com.javafx.librarian.service.NhanVienService;
import com.javafx.librarian.service.TacGiaService;
import com.javafx.librarian.utils.Util;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;

public class EditNhanVienController implements Initializable {
    NhanVienController nhanVienController;
    NhanVien nv;
    //@FXML
    //public JFXButton btnDong;
    @FXML
    public TextField txtMaNV;
    @FXML
    public JFXButton btnCapNhat;
    @FXML
    public TextField txtTenNV;
    @FXML
    public DatePicker dpNgaySinh;
    @FXML
    public TextField txtDiaChi;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtSDT;
    @FXML
    public TextField txtUsername;
    @FXML
    public TextField txtPassword;
    @FXML
    public ComboBox<Quyen> cbLoai;
    @FXML
    public Button btnHuy;
    @FXML
    public Pane panelSuaNV;
    @FXML
    public JFXButton btnClose;
    @FXML
    public FontAwesomeIcon iconClose;
    private double mousepX = 0;
    private double mousepY = 0;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtMaNV.setDisable(true);
        panelSuaNV.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelSuaNV.setOnMouseDragged(mouseEvent -> {
            panelSuaNV.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelSuaNV.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });

        txtSDT.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(!change.getControlNewText().matches("\\d+"))
                    return null;
            }

            return change;
        }));

        List<Quyen> role = QuyenDAO.getInstance().getAllQuyen();
        role.remove(0);
        cbLoai.setItems(FXCollections.observableList(role));
    }

    public void setNhanVienController(NhanVienController nv) {
        this.nhanVienController = nv;
    }

    public void setEditNV(NhanVien nv) {
        this.nv = nv;
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

    private void bindingData() {
        Account acc = AccountService.getInstance().getUserById(nv.getIdAccount());
        txtMaNV.setText(String.valueOf(nv.getMaNV()));
        txtTenNV.setText(String.valueOf(nv.getTenNV()));
        txtDiaChi.setText(nv.getDiaChi());
        txtEmail.setText(String.valueOf(nv.getEmail()));
        dpNgaySinh.setValue(Util.convertDateToLocalDateUI(nv.getNgaySinh()));
        txtSDT.setText(String.valueOf(nv.getSDT()));
        txtUsername.setText(acc.getUsername());
        txtPassword.setText(acc.getPassword());
        cbLoai.getSelectionModel().select(QuyenDAO.getInstance().getQuyenByIDAcc(nv.getIdAccount()));
    }

    public void btnCapNhat_Click(ActionEvent event) {
        //VALIDATE
        if(txtTenNV.getText().trim().equals("") ||
                txtEmail.getText().trim().equals("") ||
                txtSDT.getText().trim().equals("") ||
                txtUsername.getText().trim().equals("") ||
                txtPassword.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Vui lòng nhập đầy đủ dữ liệu!");
            alert.showAndWait();
            return;
        }
        int age = Period.between(dpNgaySinh.getValue(), LocalDate.now()).getYears();
        if(!(age >= 20 && age <= 60)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(panelSuaNV.getScene().getWindow());
            alert.setHeaderText("Lỗi");
            alert.setContentText("Tuổi của nhân viên phải từ 20 đến 60!");
            alert.show();
        };
        //
        //
        // Update account
        Account acc = new Account(txtUsername.getText(), txtPassword.getText(), cbLoai.getSelectionModel().getSelectedItem().getID(), "", "");
        AccountService.getInstance().editUser(acc);
        //
        NhanVien upNv = new NhanVien(
                nv.getMaNV(),
                txtTenNV.getText(),
                txtDiaChi.getText(),
                Date.valueOf(dpNgaySinh.getValue()),
                txtEmail.getText(),
                txtSDT.getText(),
                nv.getIdAccount()
        );
        //
        int rs = NhanVienService.getInstance().editNV(upNv);
        Util.showSuccess(rs, "Quản lý nhân viên", "Sửa nhân viên thành công!");
        nhanVienController.refreshTable();
    }
}
