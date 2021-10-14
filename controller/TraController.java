package com.javafx.librarian.controller;

import com.javafx.librarian.dao.DocGiaDao;
import com.javafx.librarian.dao.NhanVienDAO;
import com.javafx.librarian.dao.PhieuMuonDAO;
import com.javafx.librarian.dao.PhieuTraDAO;
import com.javafx.librarian.model.*;
import com.javafx.librarian.service.DocGiaService;
import com.javafx.librarian.service.PhieuTraService;
import com.javafx.librarian.service.TacGiaService;
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

public class TraController implements Initializable {
    @FXML
    public TableView<PhieuTra> tableTra;
    @FXML
    public TableView<CTPhieuTra> tableSachTra;
    @FXML
    public TableColumn<PhieuTra, String> colMaPM;
    @FXML
    public TableColumn<PhieuTra, String> colMaPT;
    @FXML
    public TableColumn<PhieuTra, String> colMaDG;
    @FXML
    public TableColumn<PhieuTra, Date> colNgayTra;
    @FXML
    public TableColumn<PhieuTra, Double> colTienPhatKyNay;
    @FXML
    public TableColumn<CTPhieuTra, String> colMaSach;
    @FXML
    public TableColumn<CTPhieuTra, String> colTenSach;
    @FXML
    public TableColumn<CTPhieuTra, Integer> colSoNM;
    @FXML
    public TableColumn<CTPhieuTra, Double> colTienPhat;
    @FXML
    public TableColumn<CTPhieuTra, String> colTinhTrangSach;
    @FXML
    public TextField txtMaPM;
    @FXML
    public ComboBox<DocGia> cbMaDG;
    @FXML
    public TextField txtMaPT;
    @FXML
    public TextField txtNgayTra;
    @FXML
    public TextField txtNgayMuon;
    @FXML
    public TextField txtTienPhatKyNay;
    @FXML
    public TextField textTimKiem;
    //endregion

    //region controller
    private ObservableList<PhieuTra> listPT;
    private ObservableList<CTPhieuTra> listCTPT;
    private ObservableList<DocGia> listDG;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCell();
        loadData();
        txtMaPM.setEditable(false);
        txtMaPT.setEditable(false);
        cbMaDG.setEditable(false);
        txtNgayTra.setEditable(false);
        txtNgayMuon.setEditable(false);
        txtTienPhatKyNay.setEditable(false);

        textTimKiem.textProperty().addListener((observableValue, s, t1) -> {
            listPT.clear();
            listPT.addAll(PhieuTraService.getInstance().searchPT(t1));
        });
    }


    private void setCell() {
        colMaPM.setCellValueFactory(cellData -> cellData.getValue().maPMProperty());
        colMaPT.setCellValueFactory(cellData -> cellData.getValue().maPTProperty());
        colNgayTra.setCellValueFactory(cellData -> cellData.getValue().ngayTraProperty());
        colMaDG.setCellValueFactory(cellData -> cellData.getValue().maDGProperty());
        colTienPhatKyNay.setCellValueFactory(cellData -> cellData.getValue().tienPhatKyNayProperty().asObject());

        colMaSach.setCellValueFactory(cellData -> cellData.getValue().maSachProperty());
        colTenSach.setCellValueFactory(cellData -> cellData.getValue().tenSachProperty());
        colTienPhat.setCellValueFactory(cellData -> cellData.getValue().tienPhatProperty().asObject());
        colSoNM.setCellValueFactory(cellData -> cellData.getValue().soNMProperty().asObject());
        colTinhTrangSach.setCellValueFactory(cellData -> cellData.getValue().tinhTrangProperty());
    }

    private void loadData() {
        listDG = FXCollections.observableArrayList(DocGiaDao.getInstance().getListDocGia());
        cbMaDG.setItems(listDG);
        cbMaDG.getSelectionModel().selectFirst();
        listPT = FXCollections.observableArrayList(PhieuTraDAO.getInstance().getAllPhieuTra());
        tableTra.setItems(listPT);
    }

    public void btnThemPT_Click(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/AddPhieuTraDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //
            AddPhieuTraController addPhieuTraController = loader.getController();
            addPhieuTraController.setTraController(this);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bindingData() {
        PhieuTra temp = tableTra.getSelectionModel().getSelectedItem();
        txtMaPT.setText((String.valueOf(temp.getMaPT())));
        txtMaPM.setText((String.valueOf(temp.getMaPM())));
        txtNgayMuon.setText(PhieuMuonDAO.getInstance().getPhieuMuonByID(temp.getMaPM()).getNgayMuon().toString());
        txtNgayTra.setText(temp.getNgayTra().toString());
        System.out.println(PhieuMuonDAO.getInstance().getPhieuMuonByID(temp.getMaPM()).getNgayMuon().toString());
        System.out.println(temp.getNgayTra().toString());
        txtTienPhatKyNay.setText(String.valueOf(temp.getTienPhatKyNay()));
        DocGia docgia = DocGiaService.getInstance().getDocGiaByID(temp.getMaDG());
        for(int i = 0; i < cbMaDG.getItems().size(); i++)
        {
            if(cbMaDG.getItems().get(i).toString().equals(docgia.toString()))
            {
                cbMaDG.getSelectionModel().select(i);
                break;
            }
        }

        listCTPT = FXCollections.observableArrayList(PhieuTraDAO.getInstance().getAllCTPhieuTraByMaPT(temp.getMaPT()));
        tableSachTra.setItems(listCTPT);
    }

    public void refreshTable() {
        listPT.clear();
        if(listCTPT != null)
            listCTPT.clear();
        loadData();
        clearInput();
    }

    public void clearInput() {
        txtMaPM.setText("");
        cbMaDG.setItems(listDG);
        cbMaDG.getSelectionModel().selectFirst();
        txtNgayMuon.setText("");
        txtNgayTra.setText("");
        txtTienPhatKyNay.setText("0");
    }
}
