package com.javafx.librarian.controller;

import com.javafx.librarian.model.Account;
import com.javafx.librarian.model.Sach;
import com.javafx.librarian.model.TheLoai;
import com.javafx.librarian.service.PhieuTraService;
import com.javafx.librarian.service.SachService;
import com.javafx.librarian.service.TacGiaService;
import com.javafx.librarian.service.TheLoaiService;
import com.javafx.librarian.utils.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class SachController implements Initializable {
    //region khai báo biến controls
    @FXML
    public TableView<Sach> tableSach;
    @FXML
    public TableColumn<Sach, String> colMaSach;
    @FXML
    public TableColumn<Sach, String> colTenSach;
    @FXML
    public TableColumn<Sach, String> colMaTheLoai;
    @FXML
    public TableColumn<Sach, String> colMaTacGia;
    @FXML
    public TableColumn<Sach, Integer> colNamXB;
    @FXML
    public TableColumn<Sach, String> colNXB;
    @FXML
    public TableColumn<Sach, Date> colNgayNhap;
    @FXML
    public TableColumn<Sach, Integer> colTriGia;
    @FXML
    public TableColumn<Sach, String> colTinhTrang;
    @FXML
    public TableColumn<Sach, Integer> colSoLuong;
    @FXML
    public TextField txtMaSach;
    @FXML
    public TextField txtTenSach;
    @FXML
    public TextField txtMaTheLoai;
    @FXML
    public TextField txtMaTacGia;
    @FXML
    public TextField txtNamXB;
    @FXML
    public TextField txtNXB;
    @FXML
    public TextField txtNgayNhap;
    @FXML
    public TextField txtTriGia;
    @FXML
    public TextField txtSoLuong;
    @FXML
    public RadioButton rdbTrong;
    @FXML
    public RadioButton rdbDangMuon;
    @FXML
    public RadioButton rdbMat;
    @FXML
    public RadioButton rdbHuHong;
    @FXML
    public Button btnThemSach;
    @FXML
    public Button btnXoaSach;
    @FXML
    public Button btnSuaSach;
    @FXML
    public TextField textTimKiem;
    @FXML
    public AnchorPane panelSach;
    @FXML
    public ImageView imgAnhBia;
    //endregion

    //region controller
    private ObservableList<Sach> listSach;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCell();
        loadData();

        //check permission
        if(Account.currentUser.getIdper() == 1) {
            btnThemSach.setVisible(false);
            btnSuaSach.setVisible(false);
            btnXoaSach.setVisible(false);
        }

        txtMaSach.setEditable(false);
        txtTenSach.setEditable(false);
        txtMaTacGia.setEditable(false);
        txtMaTheLoai.setEditable(false);
        txtNamXB.setEditable(false);
        txtNXB.setEditable(false);
        txtNgayNhap.setEditable(false);
        txtTriGia.setEditable(false);
        rdbTrong.setDisable(true);
        rdbDangMuon.setDisable(true);
        rdbMat.setDisable(true);
        rdbHuHong.setDisable(true);

        textTimKiem.textProperty().addListener((observableValue, s, t1) -> {
            listSach.clear();
            listSach.addAll(SachService.getInstance().searchSach(t1));
        });
    }

    private void setCell() {
        colMaSach.setCellValueFactory(cellData -> cellData.getValue().maSachProperty());
        colTenSach.setCellValueFactory(cellData -> cellData.getValue().tenSachProperty());
        colMaTheLoai.setCellValueFactory(cellData -> cellData.getValue().maTheLoaiProperty());
        colMaTacGia.setCellValueFactory(cellData -> cellData.getValue().maTacGiaProperty());
        colNamXB.setCellValueFactory(cellData -> cellData.getValue().namXBProperty().asObject());
        colNXB.setCellValueFactory(cellData -> cellData.getValue().nxbProperty());
        colNgayNhap.setCellValueFactory(cellData -> cellData.getValue().ngayNhapProperty());
        colTriGia.setCellValueFactory(cellData -> cellData.getValue().triGiaProperty().asObject());
        colTinhTrang.setCellValueFactory(cellData -> cellData.getValue().tinhTrangProperty() );
        colSoLuong.setCellValueFactory(cellData -> cellData.getValue().soLuongProperty().asObject());
    }

    private void loadData() {
        listSach = FXCollections.observableArrayList(SachService.getInstance().getAllSach());
        tableSach.setItems(listSach);
    }

    public void refreshTable() {
        listSach.clear();
        loadData();
        clearInput();
    }

    public void clearInput() {
        txtMaSach.setText("");
        txtTenSach.setText("");
        txtMaTacGia.setText("");
        txtMaTheLoai.setText("");
        txtNamXB.setText("");
        txtNXB.setText("");
        txtNgayNhap.setText("");
        txtTriGia.setText("");
        txtSoLuong.setText("");
        rdbTrong.setSelected(true);
    }

    public void bindingData() {
        Sach temp = tableSach.getSelectionModel().getSelectedItem();
        System.out.println(TacGiaService.getInstance().getTacGiaByID(temp.getMaTacGia()));
        txtMaSach.setText(temp.getMaSach());
        txtTenSach.setText(temp.getTenSach());
        txtMaTacGia.setText((String.valueOf(temp.getMaTacGia())) + " - " + TacGiaService.getInstance().getTacGiaByID(temp.getMaTacGia()).getTenTacGia());
        txtMaTheLoai.setText((String.valueOf(temp.getMaTheLoai())) + " - " + TheLoaiService.getInstance().getTheLoaiByID(temp.getMaTheLoai()).getTenTheLoai());
        txtNamXB.setText((String.valueOf(temp.getNamXB())));;
        txtNXB.setText(temp.getNXB());
        txtNgayNhap.setText((String.valueOf(temp.getNgayNhap())));
        txtTriGia.setText((String.valueOf(temp.getTriGia())));
        txtSoLuong.setText((String.valueOf(temp.getSoLuong())));
        if (String.valueOf(temp.getTinhTrang()).equals("Trống"))
        {
            rdbTrong.setSelected(true);
            rdbDangMuon.setSelected(false);
            rdbMat.setSelected(false);
            rdbHuHong.setSelected(false);
        }
        else if(String.valueOf(temp.getTinhTrang()).equals("Đang mượn"))
        {
            rdbDangMuon.setSelected(true);
            rdbTrong.setSelected(false);
            rdbMat.setSelected(false);
            rdbHuHong.setSelected(false);
        }
        else if(String.valueOf(temp.getTinhTrang()).equals("Mất"))
        {
            rdbMat.setSelected(true);
            rdbTrong.setSelected(false);
            rdbDangMuon.setSelected(false);
            rdbHuHong.setSelected(false);
        }
        else
        {
            rdbHuHong.setSelected(true);
            rdbTrong.setSelected(false);
            rdbDangMuon.setSelected(false);
            rdbMat.setSelected(false);
        }

        System.out.println(temp.getImage());
        imgAnhBia.setImage(temp.getImage());
        imgAnhBia.setCache(true);

    }

    public void btnThemSach_Click(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/AddSachDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //
            AddSachController addSachController = loader.getController();
            addSachController.setSachController(this);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSuaSach_Click(ActionEvent event) {
        if (tableSach.getSelectionModel().getSelectedIndex() >= 0) {
            Sach temp = tableSach.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/EditSachDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                //
                EditSachController editSachController = loader.getController();
                editSachController.setSachController(this);
                editSachController.setEditSach(temp);
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Chưa chọn sách cần chỉnh sửa!");
            alert.showAndWait();
        }
    }

    public void btnXoaSach_Click(ActionEvent event) {

        if (tableSach.getSelectionModel().getSelectedIndex() >= 0) {
            Sach temp = tableSach.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xóa sách");
            alert.setHeaderText("Bạn muốn xóa sách này ra khỏi danh sách?");
            alert.setContentText("[" + temp.getMaSach() + "] " + temp.getTenSach());

            // option != null.
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null) {
            } else if (option.get() == ButtonType.OK) {
                int rs = SachService.getInstance().deleteSach(temp.getMaSach());
                Util.showSuccess(rs, "Quản lý sách", "Xóa sách thành công!");
                refreshTable();
            } else if (option.get() == ButtonType.CANCEL) {
            } else {
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Chưa chọn sách cần xóa!");
            alert.showAndWait();
        }

    }
}
