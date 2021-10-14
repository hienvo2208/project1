package com.javafx.librarian.controller;

import com.javafx.librarian.dao.DocGiaDao;
import com.javafx.librarian.dao.SachDAO;
import com.javafx.librarian.model.*;
import com.javafx.librarian.service.*;
import com.javafx.librarian.utils.Util;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class GiaHanPMController implements Initializable {
    private MuonController muonController;
    private PhieuMuon phieuMuon;
    @FXML
    public Button btnLuu;
    @FXML
    public Button btnHuy;
    @FXML
    public TableView<CTPhieuMuon> tbSachMuon;
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
    public TableColumn<CTPhieuMuon, String> colMaSachMuon;
    @FXML
    public TableColumn<CTPhieuMuon, String> colTenSachMuon;
    @FXML
    public TableColumn<CTPhieuMuon, String> colTheLoaiMuon;
    @FXML
    public TableColumn<CTPhieuMuon, String> colTacGiaMuon;
    @FXML
    public TextField txtMaPM;
    @FXML
    public ComboBox cbMaDG;
    @FXML
    public DatePicker dtNgayMuon;
    @FXML
    public DatePicker dtHanTra;
    @FXML
    public TextField textTimKiem;
    @FXML
    public Pane panelGiaHanPM;
    @FXML
    public JFXButton btnClose;
    @FXML
    public FontAwesomeIcon iconClose;
    private double mousepX = 0;
    private double mousepY = 0;
    //endregion

    //region controller
    private ObservableList<Sach> listSach;
    private ObservableList<CTPhieuMuon> listCTPM;
    private ObservableList<DocGia> listDG;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panelGiaHanPM.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelGiaHanPM.setOnMouseDragged(mouseEvent -> {
            panelGiaHanPM.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelGiaHanPM.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });
        cbMaDG.setDisable(true);
        dtNgayMuon.setDisable(true);
        setCell();
        listDG = FXCollections.observableArrayList(DocGiaDao.getInstance().getListDocGia());
        cbMaDG.setItems(listDG);
        listSach = FXCollections.observableArrayList(SachDAO.getInstance().getAllSach());
        tableSach.setItems(listSach);

        dtHanTra.valueProperty().addListener((ov, oldValue, newValue) -> {
            if(newValue == null || oldValue == null)
                return;
            if(newValue.isBefore(oldValue))
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("THÔNG BÁO");
                alert.setHeaderText("Ngày gia hạn phải sau ngày trả hiện tại. Hãy chọn lại ngày trả phù hợp!");
                alert.showAndWait();
                dtHanTra.setValue(oldValue);
            }
        });
    }
    public void setMuonController(MuonController muon) {
        this.muonController = muon;
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
        colMaSachMuon.setCellValueFactory(cellData -> cellData.getValue().maSachProperty());
        colTenSachMuon.setCellValueFactory(cellData -> cellData.getValue().tenSachProperty());
        colTheLoaiMuon.setCellValueFactory(cellData -> cellData.getValue().theLoaiProperty());
        colTacGiaMuon.setCellValueFactory(cellData -> cellData.getValue().tacGiaProperty());
    }

    public void setGiaHanPM(PhieuMuon phieuMuon) {
        this.phieuMuon = phieuMuon;
        bindingData();
    }
    private void bindingData() {
        txtMaPM.setText(String.valueOf(phieuMuon.getMaPM()));
        dtNgayMuon.setValue(Util.convertDateToLocalDateUI(phieuMuon.getNgayMuon()));
        dtHanTra.setValue(Util.convertDateToLocalDateUI(phieuMuon.getHanTra()));
        DocGia docGia = DocGiaService.getInstance().getDocGiaByID(phieuMuon.getMaDG());
        for(int i = 0; i < cbMaDG.getItems().size(); i++)
        {
            if(cbMaDG.getItems().get(i).toString().equals(docGia.toString()))
            {
                cbMaDG.getSelectionModel().select(i);
                break;
            }
        }
        listCTPM = FXCollections.observableArrayList(PhieuMuonService.getInstance().getAllCTPhieuMuonByMaPM(phieuMuon.getMaPM()));
        tbSachMuon.setItems(listCTPM);
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
        //
        String maPM = txtMaPM.getText();
        Date hanTra = java.sql.Date.valueOf(dtHanTra.getValue());

        int rs = PhieuMuonService.getInstance().giaHanPhieuMuon(maPM, hanTra);

        Util.showSuccess(rs, "Quản lý mượn", "Gia hạn phiếu mượn thành công!");

        muonController.refreshTable();
        txtMaPM.setText("");
        cbMaDG.getSelectionModel().selectFirst();
        dtNgayMuon.setValue(LocalDate.now());
        dtHanTra.setValue(LocalDate.now());
        listCTPM.clear();
        tbSachMuon.setItems(listCTPM);
        Stage stage = (Stage) btnHuy.getScene().getWindow();
        stage.close();
    }
}

