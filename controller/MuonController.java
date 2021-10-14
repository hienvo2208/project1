package com.javafx.librarian.controller;

import com.javafx.librarian.dao.DocGiaDao;
import com.javafx.librarian.dao.PhieuMuonDAO;
import com.javafx.librarian.model.*;
import com.javafx.librarian.service.*;
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
import java.util.ResourceBundle;

public class MuonController implements Initializable {
    @FXML
    public TableView<PhieuMuon> tableMuon;
    @FXML
    public TableView<CTPhieuMuon> tbSachMuon;
    @FXML
    public TableColumn<PhieuMuon, String> colMaPM;
    @FXML
    public TableColumn<PhieuMuon, String> colMaDocGia;
    @FXML
    public TableColumn<PhieuMuon, Date> colNgayMuon;
    @FXML
    public TableColumn<PhieuMuon, Date> colHanTra;
    @FXML
    public TableColumn<PhieuMuon, String> colTinhTrang;
    @FXML
    public TableColumn<CTPhieuMuon, String> colMaSach;
    @FXML
    public TableColumn<CTPhieuMuon, String> colTenSach;
    @FXML
    public TableColumn<CTPhieuMuon, String> colTheLoai;
    @FXML
    public TableColumn<CTPhieuMuon, String> colTacGia;
    @FXML
    public TextField txtMaPM;
    @FXML
    public ComboBox cbMaDG;
    @FXML
    public TextField txtNgayMuon;
    @FXML
    public TextField txtHanTra;
    @FXML
    public RadioButton rdbChuaTraDu;
    @FXML
    public RadioButton rdbTraDu;
    @FXML
    public TextField textTimKiem;
    @FXML
    public Button btnLamMoi;
    //endregion

    //region controller
    private ObservableList<PhieuMuon> listPM;
    private ObservableList<CTPhieuMuon> listCTPM;
    private ObservableList<DocGia> listDG;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCell();
        loadData();

        txtMaPM.setEditable(false);
        cbMaDG.setEditable(false);
        txtNgayMuon.setEditable(false);
        txtHanTra.setEditable(false);
        rdbChuaTraDu.setDisable(true);
        rdbTraDu.setDisable(true);
        rdbChuaTraDu.setSelected(false);
        rdbTraDu.setSelected(false);

        textTimKiem.textProperty().addListener((observableValue, s, t1) -> {
            listPM.clear();
            listPM.addAll(PhieuMuonService.getInstance().searchPM(t1));
        });
    }

    private void setCell() {
        colMaPM.setCellValueFactory(cellData -> cellData.getValue().maPMProperty());
        colMaDocGia.setCellValueFactory(cellData -> cellData.getValue().maDGProperty());
        colNgayMuon.setCellValueFactory(cellData -> cellData.getValue().ngayMuonProperty());
        colHanTra.setCellValueFactory(cellData -> cellData.getValue().hanTraProperty());
        colTinhTrang.setCellValueFactory(cellData -> cellData.getValue().tinhTrangProperty());
        colMaSach.setCellValueFactory(cellData -> cellData.getValue().maSachProperty());
        colTenSach.setCellValueFactory(cellData -> cellData.getValue().tenSachProperty());
        colTheLoai.setCellValueFactory(cellData -> cellData.getValue().theLoaiProperty());
        colTacGia.setCellValueFactory(cellData -> cellData.getValue().tacGiaProperty());
    }

    private void loadData() {
        listDG = FXCollections.observableArrayList(DocGiaDao.getInstance().getListDocGia());
        cbMaDG.setItems(listDG);
        cbMaDG.getSelectionModel().selectFirst();
        listPM = FXCollections.observableArrayList(PhieuMuonDAO.getInstance().getAllPhieuMuon());
        tableMuon.setItems(listPM);
    }

    public void refreshTable() {
        listPM.clear();
        if(listCTPM != null)
            listCTPM.clear();
        loadData();
        clearInput();
    }

    public void clearInput() {
        txtMaPM.setText("");
        cbMaDG.setItems(listDG);
        cbMaDG.getSelectionModel().selectFirst();
        txtNgayMuon.setText("");
        txtHanTra.setText("");
        rdbTraDu.setSelected(true);
    }

    public void bindingData() {
        PhieuMuon temp = tableMuon.getSelectionModel().getSelectedItem();
        txtMaPM.setText((String.valueOf(temp.getMaPM())));
        txtNgayMuon.setText((String.valueOf(temp.getNgayMuon())));
        txtHanTra.setText((String.valueOf(temp.getHanTra())));
        if (String.valueOf(temp.getTinhTrang()).equals("Trả đủ"))
        {
            rdbTraDu.setSelected(true);
            rdbChuaTraDu.setSelected(false);
        }
        else
        {
            rdbChuaTraDu.setSelected(true);
            rdbTraDu.setSelected(false);
        }

        DocGia docgia = DocGiaService.getInstance().getDocGiaByID(temp.getMaDG());
        for(int i = 0; i < cbMaDG.getItems().size(); i++)
        {
            if(cbMaDG.getItems().get(i).toString().equals(docgia.toString()))
            {
                cbMaDG.getSelectionModel().select(i);
                break;
            }
        }

        listCTPM = FXCollections.observableArrayList(PhieuMuonDAO.getInstance().getAllCTPhieuMuonByMaPM(temp.getMaPM()));
        tbSachMuon.setItems(listCTPM);
    }

    public void btnThemPM_Click(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/AddPhieuMuonDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //
            AddPhieuMuonController addPhieuMuonController = loader.getController();
            addPhieuMuonController.setMuonController(this);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnGiaHan_Click(ActionEvent event) {
        if (tableMuon.getSelectionModel().getSelectedIndex() >= 0) {
            PhieuMuon temp = tableMuon.getSelectionModel().getSelectedItem();
            if(temp.getTinhTrang().equals("Trả đủ"))
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("THÔNG BÁO");
                alert.setHeaderText("Các đầu sách trong phiếu mượn đã được trả đầy đủ. Không thể gia hạn phiếu mượn này!");
                alert.showAndWait();
            }
            else
            {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../view/GiaHanPMDialog.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();

                    // Create the dialog Stage.
                    Stage dialogStage = new Stage();
                    dialogStage.initStyle(StageStyle.UNDECORATED);
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);
                    //
                    GiaHanPMController giaHanPMController = loader.getController();
                    giaHanPMController.setMuonController(this);
                    giaHanPMController.setGiaHanPM(temp);
                    dialogStage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Chưa chọn phiếu mượn cần gia hạn!");
            alert.showAndWait();
        }
    }

    public void btnLamMoiClick(ActionEvent actionEvent) {
//        if (textTimKiemDG.getText() == null || textTimKiemDG.getText().isEmpty())
//            return;
        refreshTable();
    }
}
