package com.javafx.librarian.controller;

import com.javafx.librarian.dao.AccountDao;
import com.javafx.librarian.dao.QuyenDAO;
import com.javafx.librarian.model.Account;
import com.javafx.librarian.model.NhanVien;
import com.javafx.librarian.model.Quyen;
import com.javafx.librarian.service.AccountService;
import com.javafx.librarian.service.NhanVienService;
import com.javafx.librarian.service.TacGiaService;
import com.javafx.librarian.utils.Util;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;

public class AddNhanVienController implements Initializable {
    NhanVienController nhanVienController;

    @FXML
    public Button btnThem;
    @FXML
    public TextField txtMaNV;
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
    public PasswordField txtPassword;
    @FXML
    public ComboBox<Quyen> cbLoai;
    @FXML
    public Pane panelThemNV;
    @FXML
    public JFXButton btnDong;
    @FXML
    public FontAwesomeIcon iconClose;
    private double mousepX = 0;
    private double mousepY = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panelThemNV.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelThemNV.setOnMouseDragged(mouseEvent -> {
            panelThemNV.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelThemNV.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });
        txtMaNV.setText(Util.generateID(Util.PREFIX_CODE.NV));

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
        cbLoai.getSelectionModel().selectFirst();

    }

    public void setNhanVienController(NhanVienController nv) {
        this.nhanVienController = nv;
    }

    public void btnHuy_Click(ActionEvent event) {
        Stage stage = (Stage) btnDong.getScene().getWindow();
        stage.close();
    }

    public void btnAddThem_Click(ActionEvent event) {
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
            alert.initOwner(cbLoai.getScene().getWindow());
            alert.setContentText("Tuổi của nhân viên phải từ 20 đến 60!");
            alert.show();
            return;
        };

        List<String> all = AccountDao.getInstance().getAllUserKeys();
        if(all.contains(txtUsername.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Tên tài khoản đã tồn tại trong hệ thống!");
            alert.showAndWait();
            return;
        }

        //

        String maNV = txtMaNV.getText();
        String tenNV = txtTenNV.getText();
        String diaChi = txtDiaChi.getText();
        String email = txtEmail.getText();
        String sdt = txtSDT.getText();
        Date ngaySinh = Date.valueOf(dpNgaySinh.getValue());
        String userName = txtUsername.getText();
        String passWord = txtPassword.getText();
        int role = cbLoai.getSelectionModel().getSelectedItem().getID();

        //create account according to new NhanVien
        Account acc = new Account(userName, passWord, role, "", "");
        AccountService.getInstance().addUser(acc);

        //
        NhanVien nv = new NhanVien(maNV, tenNV, diaChi, ngaySinh, email, sdt, acc.getUsername());
        //
        int rs = NhanVienService.getInstance().addNV(nv);
        Util.showSuccess(rs, "Quản lý nhân viên", "Thêm nhân viên thành công!");
        nhanVienController.refreshTable();
    }

    public void btnCloseMouseEnter(MouseEvent mouseEvent) {
        btnDong.setStyle("-fx-background-color: red; -fx-background-radius: 15");
        iconClose.setVisible(true);
    }

    public void btnCloseMouseExit(MouseEvent mouseEvent) {
        btnDong.setStyle("-fx-background-color: #a6a6a6; -fx-background-radius: 15");
        iconClose.setVisible(false);
    }
}
