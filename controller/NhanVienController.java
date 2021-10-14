package com.javafx.librarian.controller;

import com.javafx.librarian.dao.QuyenDAO;
import com.javafx.librarian.model.Account;
import com.javafx.librarian.model.NhanVien;
import com.javafx.librarian.model.TacGia;
import com.javafx.librarian.service.AccountService;
import com.javafx.librarian.service.NhanVienService;
import com.javafx.librarian.service.PhieuTraService;
import com.javafx.librarian.service.TacGiaService;
import com.javafx.librarian.utils.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class NhanVienController implements Initializable {

    @FXML
    public TableView<NhanVien> tbhienthi;
    @FXML
    public TableColumn<NhanVien, String> tbcMaNV;
    @FXML
    public TableColumn<NhanVien, String> tbcTenNV;
    @FXML
    public TableColumn<NhanVien, String> tbcDiaChi;
    @FXML
    public TableColumn<NhanVien, Date> tbcNgaySinh;
    @FXML
    public TableColumn<NhanVien, String> tbcEmail;
    @FXML
    public TableColumn<NhanVien, String> tbcSDT;
    @FXML
    public TextField txtMaNV;
    @FXML
    public TextField txtTenNV;
    @FXML
    public TextField txtDiaChi;
    @FXML
    public TextField txtNgaySinh;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtSDT;
    @FXML
    public Button btnThem;
    @FXML
    public TextField txtUsername;
    @FXML
    public TextField txtChucVu;
    @FXML
    public TextField textTimKiem;

    //region controller
    private ObservableList<NhanVien> listNV;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCell();
        loadData();

        textTimKiem.textProperty().addListener((observableValue, s, t1) -> {
            listNV.clear();
            listNV.addAll(NhanVienService.getInstance().searchNV(t1));
        });
    }

    private void setCell() {
        tbcMaNV.setCellValueFactory(cellData -> cellData.getValue().maNVProperty());
        tbcTenNV.setCellValueFactory(cellData -> cellData.getValue().tenNVProperty());
        tbcDiaChi.setCellValueFactory(cellData -> cellData.getValue().diaChiProperty());
        tbcNgaySinh.setCellValueFactory(cellData -> cellData.getValue().ngaySinhProperty());
        tbcEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        tbcSDT.setCellValueFactory(cellData -> cellData.getValue().SDTProperty());
    }

    private void loadData() {
        listNV = FXCollections.observableArrayList(NhanVienService.getInstance().getAllNV());
        tbhienthi.setItems(listNV);
    }

    public void bindingData() {
        NhanVien temp = tbhienthi.getSelectionModel().getSelectedItem();
        Account acc = AccountService.getInstance().getUserById(temp.getIdAccount());
        txtMaNV.setText((String.valueOf(temp.getMaNV())));
        txtTenNV.setText(temp.getTenNV());
        txtDiaChi.setText((String.valueOf(temp.getDiaChi())));
        txtNgaySinh.setText(temp.getNgaySinh().toString());
        txtEmail.setText(temp.getEmail());
        txtSDT.setText(temp.getSDT());
        txtUsername.setText(acc.getUsername());
        txtChucVu.setText(QuyenDAO.getInstance().getQuyenByIDAcc(temp.getIdAccount()).getName());
    }

    public void refreshTable() {
        listNV.clear();
        loadData();
    }

    private void clearInput() {
        txtMaNV.setText("");
        txtTenNV.setText("");
        txtDiaChi.setText("");
        txtNgaySinh.setText("");
        txtEmail.setText("");
        txtSDT.setText("");
    }

    public void btnThem_Click(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/AddNhanVienDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //
            AddNhanVienController addNhanVienController = loader.getController();
            addNhanVienController.setNhanVienController(this);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSua_Click(ActionEvent event) {
        if (tbhienthi.getSelectionModel().getSelectedIndex() >= 0) {
            NhanVien temp = tbhienthi.getSelectionModel().getSelectedItem();
            //
            //refreshTable();
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/EditNhanVienDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                //
                EditNhanVienController editNhanVienController = loader.getController();
                editNhanVienController.setNhanVienController(this);
                editNhanVienController.setEditNV(temp);
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Chưa chọn nhân viên cần chỉnh sửa!");
            alert.showAndWait();
        }
    }

    public void btnXoa_Click(ActionEvent event) {

        if (tbhienthi.getSelectionModel().getSelectedIndex() >= 0) {
            NhanVien temp = tbhienthi.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xóa nhân viên");
            alert.setHeaderText("Bạn muốn xóa nhân viên này ra khỏi danh sách?");
            alert.setContentText("[" + temp.getMaNV() + "] " + temp.getTenNV());

            // option != null.
            Optional<ButtonType> option = alert.showAndWait();

             if (option.get() == ButtonType.OK) {
                int rs = NhanVienService.getInstance().deleteNV(temp.getMaNV());
                Util.showSuccess(rs, "Quản lý nhân viên", "Xóa nhân viên thành công!");
                refreshTable();
                clearInput();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Chưa chọn nhân viên cần chỉnh sửa!");
            alert.showAndWait();
        }

    }
}
