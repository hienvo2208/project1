package com.javafx.librarian.controller;

import com.javafx.librarian.dao.DocGiaDao;
import com.javafx.librarian.dao.PhieuMuonDAO;
import com.javafx.librarian.dao.SachDAO;
import com.javafx.librarian.model.*;
import com.javafx.librarian.service.*;
import com.javafx.librarian.utils.Util;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.util.*;

public class AddPhieuMuonController implements Initializable {
    private MuonController muonController;
    @FXML
    public Button btnThem;
    @FXML
    public Button btnHuy;
    @FXML
    public Button btnThemSach;
    @FXML
    public Button btnXoaSach;
    @FXML
    public TableView<Sach> tbSachMuon;
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
    public TableColumn<Sach, String> colMaSachMuon;
    @FXML
    public TableColumn<Sach, String> colTenSachMuon;
    @FXML
    public TableColumn<Sach, String> colTheLoaiMuon;
    @FXML
    public TableColumn<Sach, String> colTacGiaMuon;
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
    public Pane panelThemPM;
    @FXML
    public JFXButton btnClose;
    @FXML
    public FontAwesomeIcon iconClose;
    private double mousepX = 0;
    private double mousepY = 0;
    //endregion

    //region controller
    private ObservableList<Sach> listSach;
    private List<Sach> listSelectionSach;
    private ObservableList<DocGia> listDG;
    private ObservableList<Sach> list;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panelThemPM.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelThemPM.setOnMouseDragged(mouseEvent -> {
            panelThemPM.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelThemPM.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });

        dtHanTra.setDisable(true);
        dtHanTra.setEditable(false);
        txtMaPM.setText(Util.generateID(Util.PREFIX_CODE.PM));
        txtMaPM.setDisable(true);
        setCell();
        loadData();
        dtNgayMuon.setValue(LocalDate.now());
        dtHanTra.setValue(dtNgayMuon.getValue().plusDays(7));
        dtNgayMuon.valueProperty().addListener((ov, oldValue, newValue) -> {
            dtHanTra.setValue(newValue.plusDays(7));
        });

        tableSach.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tableSach.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            }
        });

        tbSachMuon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tbSachMuon.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            }
        });
        new AutoCompleteComboBoxListener<>(cbMaDG);


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
        colTheLoaiMuon.setCellValueFactory(cellData -> cellData.getValue().maTheLoaiProperty());
        colTacGiaMuon.setCellValueFactory(cellData -> cellData.getValue().maTacGiaProperty());
    }

    private void loadData() {
        cbMaDG.getItems().clear();
        listDG = FXCollections.observableArrayList(DocGiaDao.getInstance().getListDocGiaToCB());
        cbMaDG.setTooltip(new Tooltip());
        cbMaDG.getItems().addAll(listDG);
        cbMaDG.getSelectionModel().selectFirst();
        listSach = FXCollections.observableArrayList(SachDAO.getInstance().getAllSach());
        tableSach.setItems(listSach);
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

    public void changeHanTra()
    {
        dtHanTra.setValue(dtNgayMuon.getValue().plusDays(7));
    }

    public void btnThem_Click(ActionEvent event) {
        //
        ThamSo thamSo = ThamSoService.getInstance().getThamSo();
        String maPM = txtMaPM.getText();
        System.out.println(cbMaDG.getSelectionModel().getSelectedItem().toString());
        String[] info = cbMaDG.getSelectionModel().getSelectedItem().toString().split(" - ");
        String maDG = info[0].trim();
        Date ngayMuon = java.sql.Date.valueOf(dtNgayMuon.getValue());
        Date hanTra = java.sql.Date.valueOf(dtHanTra.getValue());
        int tinhTrang = 1;
        ObservableList<Sach> temp = tbSachMuon.getItems();
        if(temp.size() > 5)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Chỉ được mượn tối đa " + thamSo.getMaxSachMuon() + " quyển sách!");
            alert.showAndWait();
        }
        else if (temp.size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Không có quyển sách nào được mượn!");
            alert.showAndWait();
        }
        else
        {
            PhieuMuon phieuMuon = new PhieuMuon(maPM, maDG, ngayMuon, hanTra, tinhTrang);
            int rs = PhieuMuonService.getInstance().addPhieuMuon(phieuMuon);

            for(int i = 0; i < temp.size(); i++)
            {
                String maSach = temp.get(i).getMaSach();
                CTPhieuMuon ctphieumuon = new CTPhieuMuon(maPM, maSach);
                PhieuMuonService.getInstance().addCTPhieuMuon(ctphieumuon);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Util.showSuccess(rs, "Quản lý mượn", "Thêm phiếu mượn thành công!");

            muonController.refreshTable();
            txtMaPM.setText(Util.generateID(Util.PREFIX_CODE.PM));
            dtNgayMuon.setValue(LocalDate.now());
            dtHanTra.setValue(dtNgayMuon.getValue().plusDays(7));
            list.clear();
            tbSachMuon.setItems(list);
            loadData();
        }
    }

    public void btnThemSach_Click(ActionEvent event) {
        //
        ObservableList<Sach> temp = tableSach.getSelectionModel().getSelectedItems();
        listSelectionSach = tbSachMuon.getItems();
        List<Sach> unavailable = new ArrayList<>();
        if(listSelectionSach == null)
            listSelectionSach = new ArrayList<>();
        for(int i = 0; i < temp.size(); i++){
           if(!listSelectionSach.contains(temp.get(i)))
           {
               if(temp.get(i).getTinhTrang().equals("Trống"))
               {
                   listSelectionSach.add(temp.get(i));
               }
               else {
                   unavailable.add(temp.get(i));
               }
           }
        }
        list = FXCollections.observableArrayList(listSelectionSach);
        tbSachMuon.setItems(list);
        if(unavailable.size() > 0)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Sách không có sẵn. Mời chọn sách khác!");
            for(int i = 0; i < unavailable.size(); i++)
            {
                alert.setContentText(alert.getContentText() + "[" + unavailable.get(i).getMaSach() + "] " + unavailable.get(i).getTenSach() + "\n");
            }
            alert.showAndWait();
        }
    }

    public void btnXoaSach_Click(ActionEvent event) {
        //
        ObservableList<Sach> temp = tbSachMuon.getSelectionModel().getSelectedItems();
        System.out.println(listSelectionSach.size());
        for(int i = 0; i < temp.size(); i++){
            if(listSelectionSach.contains(temp.get(i)))
            {
                listSelectionSach.remove(temp.get(i));
            }
        }
        list = FXCollections.observableArrayList(listSelectionSach);
        tbSachMuon.setItems(list);
    }
}
