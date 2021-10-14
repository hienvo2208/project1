package com.javafx.librarian.controller;

import com.javafx.librarian.dao.DocGiaDao;
import com.javafx.librarian.dao.PhieuMuonDAO;
import com.javafx.librarian.dao.SachDAO;
import com.javafx.librarian.model.*;
import com.javafx.librarian.service.PhieuMuonService;
import com.javafx.librarian.service.PhieuTraService;
import com.javafx.librarian.service.SachService;
import com.javafx.librarian.service.ThamSoService;
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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class AddPhieuTraController implements Initializable {
    private TraController traController;
    @FXML
    public Button btnThem;
    @FXML
    public Button btnHuy;
    @FXML
    public Button btnThemSach;
    @FXML
    public Button btnXoaSach;
    @FXML
    public TableView<CTPhieuTra> tbSachTra;
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
    public TableColumn<CTPhieuTra, String> colMaSachTra;
    @FXML
    public TableColumn<CTPhieuTra, String> colTenSachTra;
    @FXML
    public TableColumn<CTPhieuTra, Integer> colSoNM;
    @FXML
    public TableColumn<CTPhieuTra, Double> colTienPhat;
    @FXML
    public TableColumn<CTPhieuTra, String> colTinhTrangSach;
    @FXML
    public TextField txtMaPT;
    @FXML
    public ComboBox cbMaDG;
    @FXML
    public ComboBox<PhieuMuon> cbMaPM;
    @FXML
    public DatePicker dtNgayTra;
    @FXML
    public TextField txtTienPhat;
    @FXML
    public Pane panelThemPT;
    @FXML
    public JFXButton btnClose;
    @FXML
    public FontAwesomeIcon iconClose;
    private double mousepX = 0;
    private double mousepY = 0;

    private ObservableList<Sach> listSach;
    private List<Sach> listSelectionSach = new ArrayList<>();
    private ObservableList<DocGia> listDG;
    private ObservableList<PhieuMuon> listPM;
    private ObservableList<CTPhieuTra> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panelThemPT.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelThemPT.setOnMouseDragged(mouseEvent -> {
            panelThemPT.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelThemPT.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });

        txtTienPhat.setText("0");
        txtTienPhat.setDisable(true);
        txtMaPT.setText(Util.generateID(Util.PREFIX_CODE.PT));
        txtMaPT.setDisable(true);
        setCell();
        loadData();
        dtNgayTra.setValue(LocalDate.now());

        tableSach.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tableSach.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            }
        });

        tbSachTra.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tbSachTra.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            }
        });
        new AutoCompleteComboBoxListener<>(cbMaDG);

        dtNgayTra.valueProperty().addListener((ov, oldValue, newValue) -> {
            ThamSo thamSo = ThamSoService.getInstance().getThamSo();
            Date ngayMuon = ((PhieuMuon) cbMaPM.getSelectionModel().getSelectedItem()).getNgayMuon();
            Date hanTra = ((PhieuMuon) cbMaPM.getSelectionModel().getSelectedItem()).getHanTra();
            if(newValue.isBefore(Util.convertDateToLocalDateUI(ngayMuon)))
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("THÔNG BÁO");
                alert.setHeaderText("Ngày trả không được sau ngày mượn. Hãy chọn lại ngày trả phù hợp!");
                alert.showAndWait();
                dtNgayTra.setValue(oldValue);
            }
            else
            {
                List<CTPhieuTra> listCTPT = tbSachTra.getItems();
                for(int i = 0; i < listCTPT.size(); i++){
                    System.out.println(listCTPT.get(i).getTinhTrang());
                    long soNM = 0;
                    Date dateAfter = java.sql.Date.valueOf(dtNgayTra.getValue());
                    long difference = dateAfter.getTime() - ngayMuon.getTime();
                    long difference1 = hanTra.getTime() - ngayMuon.getTime();
                    long daysBetween = (difference / (1000*60*60*24));
                    long daysBetween1 = (difference1 / (1000*60*60*24));
                    if(daysBetween > daysBetween1) {
                        soNM = daysBetween - daysBetween1;
                    }
                    listCTPT.get(i).setSoNM((int) soNM);
                }
                refreshTienPhat();
                /*tbSachTra.getItems().clear();
                ObservableList<Sach> temp = tableSach.getSelectionModel().getSelectedItems();
                //System.out.println(temp.si);
                if(listSelectionSach == null)
                    listSelectionSach = new ArrayList<>();
                for(int i = 0; i < temp.size(); i++){
                    if(!listSelectionSach.contains(temp.get(i)))
                    {
                        listSelectionSach.add(temp.get(i));
                    }
                }
                list = FXCollections.observableArrayList();
                for(int i = 0; i < listSelectionSach.size(); i++){
                    long soNM = 0;
                    double tienPhat = 0;
                    Date dateAfter = java.sql.Date.valueOf(dtNgayTra.getValue());
                    long difference = dateAfter.getTime() - ngayMuon.getTime();
                    long difference1 = hanTra.getTime() - ngayMuon.getTime();
                    long daysBetween = (difference / (1000*60*60*24));
                    long daysBetween1 = (difference1 / (1000*60*60*24));
                    if(daysBetween > daysBetween1) {
                        tienPhat = (daysBetween - daysBetween1) * thamSo.getTienPhat();
                        soNM = daysBetween - daysBetween1;
                        CTPhieuTra ctPhieuTra = new CTPhieuTra(txtMaPT.getText(), listSelectionSach.get(i).getMaSach(), listSelectionSach.get(i).getTenSach(), ngayMuon, (int)soNM, tienPhat, "Bình thường");
                        list.add(ctPhieuTra);
                        tongtien += tienPhat;
                    }
                    else
                    {
                        CTPhieuTra ctPhieuTra = new CTPhieuTra(txtMaPT.getText(), listSelectionSach.get(i).getMaSach(), listSelectionSach.get(i).getTenSach(), ngayMuon, (int)soNM, tienPhat, "Bình thường");
                        list.add(ctPhieuTra);
                        tongtien += tienPhat;
                    }
                }
                tbSachTra.setItems(list);
                txtTienPhat.setText(tongtien + "");*/
            }

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

        colMaSachTra.setCellValueFactory(cellData -> cellData.getValue().maSachProperty());
        colTenSachTra.setCellValueFactory(cellData -> cellData.getValue().tenSachProperty());
        colSoNM.setCellValueFactory(cellData -> cellData.getValue().soNMProperty().asObject());
        colTienPhat.setCellValueFactory(cellData -> cellData.getValue().tienPhatProperty().asObject());
        colTinhTrangSach.setCellValueFactory(cellData -> cellData.getValue().tinhTrangProperty());

        colTinhTrangSach.setCellFactory(ComboBoxTableCell.forTableColumn("Bình thường", "Hư hỏng", "Mất"));

        colTinhTrangSach.setOnEditCommit(event -> {
            event.getRowValue().setTinhTrang(event.getNewValue());
            refreshTienPhat();
        });
    }

    private void loadData() {
        cbMaDG.getItems().clear();
        listDG = FXCollections.observableArrayList(DocGiaDao.getInstance().getListDocGiaToPhieuTraCB());
        cbMaDG.setTooltip(new Tooltip());
        cbMaDG.getItems().addAll(listDG);
        cbMaDG.getSelectionModel().selectFirst();
        String maDocGia = getListPMByDocGia();
        dtNgayTra.setValue(LocalDate.now());
        listSach = FXCollections.observableArrayList(SachDAO.getInstance().getAllSachByDocGiaInPM(maDocGia == null ? null : maDocGia.trim()));
        tableSach.setItems(listSach);
        listSelectionSach.clear();
    }

    public void setTraController(TraController tra) {
        this.traController = tra;
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

    public void setCbMaDGAction(ActionEvent event) {
        getListPMByDocGia();
    }

    public void setCbMaPMAction(ActionEvent event) {
        refreshTable();
    }

    public void refreshTable() {
        if(cbMaPM.getValue() != null) {
            String maDocGia = (cbMaDG.getValue().toString().split(" - "))[0].trim();
            listSach = FXCollections.observableArrayList(SachDAO.getInstance().getAllSachByDocGiaInPM(maDocGia.trim()));
            tableSach.setItems(listSach);
        }
        tbSachTra.getItems().clear();
        listSelectionSach.clear();
    }

    public String getListPMByDocGia() {
        if(cbMaDG.getValue() != null) {
            String[] data = cbMaDG.getValue().toString().split(" - ");
            listPM = FXCollections.observableArrayList(PhieuMuonDAO.getInstance().getListPMByDGToCB(data[0].trim()));
            cbMaPM.setItems(listPM);
            cbMaPM.getSelectionModel().selectFirst();
            //return DocGia ID for reuse code
            return data[0].trim();
        }
        return null;
    }

    public void btnThemSach_Click(ActionEvent event) {
        //
        double tongtien = 0;
        ThamSo thamSo = ThamSoService.getInstance().getThamSo();
        Date ngayMuon = ((PhieuMuon) cbMaPM.getSelectionModel().getSelectedItem()).getNgayMuon();
        Date hanTra = ((PhieuMuon) cbMaPM.getSelectionModel().getSelectedItem()).getHanTra();
       // TimeS soNM = ngayMuon
        /*Date nw = java.util.Calendar.getInstance().getTime();
        Date diff =new Date(nw - ngayMuon.getTime());
        long days = TimeUnit.MILLISECONDS.toDays(diff);*/
        ObservableList<Sach> temp = tableSach.getSelectionModel().getSelectedItems();
        //System.out.println(temp.si);
        if(listSelectionSach == null)
            listSelectionSach = new ArrayList<>();
        for(int i = 0; i < temp.size(); i++){
            if(!listSelectionSach.contains(temp.get(i)))
            {
                listSelectionSach.add(temp.get(i));
            }
        }
        list = FXCollections.observableArrayList(tbSachTra.getItems());
        //get list of tbsachtra
        List<String> tbSachTras = new ArrayList<String>();
        tbSachTra.getItems().forEach(e -> {
            tbSachTras.add(e.getMaSach());
        });
        //end
        for(int i = 0; i < listSelectionSach.size(); i++){
            long soNM = 0;
            double tienPhat = 0;
            Date dateAfter = java.sql.Date.valueOf(dtNgayTra.getValue());
            long difference = dateAfter.getTime() - ngayMuon.getTime();
            long difference1 = hanTra.getTime() - ngayMuon.getTime();
            long daysBetween = (difference / (1000*60*60*24));
            long daysBetween1 = (difference1 / (1000*60*60*24));
            if(daysBetween > daysBetween1) {
                tienPhat = (daysBetween - daysBetween1) * thamSo.getTienPhat();
                soNM = daysBetween - daysBetween1;
                CTPhieuTra ctPhieuTra = new CTPhieuTra(txtMaPT.getText(), listSelectionSach.get(i).getMaSach(), listSelectionSach.get(i).getTenSach(), ngayMuon, (int)soNM, tienPhat, "Bình thường");
                if (!tbSachTras.contains(ctPhieuTra.getMaSach())) {
                    list.add(ctPhieuTra);
                }
            }
            else
            {
                CTPhieuTra ctPhieuTra = new CTPhieuTra(txtMaPT.getText(), listSelectionSach.get(i).getMaSach(), listSelectionSach.get(i).getTenSach(), ngayMuon, (int)soNM, tienPhat, "Bình thường");
                if (!tbSachTras.contains(ctPhieuTra.getMaSach())) {
                    list.add(ctPhieuTra);
                }
            }
            tongtien += tienPhat;
        }
        tbSachTra.setItems(list);
        txtTienPhat.setText(tongtien + "");
    }

    public void btnXoaSach_Click(ActionEvent event) {
        //
        double tongtien = 0;
        ThamSo thamSo = ThamSoService.getInstance().getThamSo();
        Date ngayMuon = ((PhieuMuon) cbMaPM.getSelectionModel().getSelectedItem()).getNgayMuon();
        Date hanTra = ((PhieuMuon) cbMaPM.getSelectionModel().getSelectedItem()).getHanTra();
        ObservableList<CTPhieuTra> temp = tbSachTra.getSelectionModel().getSelectedItems();
        ObservableList<CTPhieuTra> getall = tbSachTra.getItems();
        for(int i = 0; i < temp.size(); i++){
           getall.remove(temp.get(i));
           //System.out.println(tongtien);
        }
        for(int i = 0; i < getall.size(); i++){
            tongtien += getall.get(i).getTienPhat();
            //System.out.println(tongtien);
        }
        tbSachTra.setItems(getall);
        txtTienPhat.setText(tongtien + "");
        listSelectionSach.clear();
    }

    public void ThemPhieuTra_Click(ActionEvent event) {
        if(cbMaPM.getValue() == null || cbMaDG.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Không có phiếu mượn hoặc đọc giả!");
            alert.showAndWait();
            return;
        }
        ObservableList<CTPhieuTra> temp = tbSachTra.getItems();
        if(temp.size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Không có quyển sách nào được mượn!");
            alert.showAndWait();
        }
        else {
            PhieuTra phieuTra = new PhieuTra(txtMaPT.getText(),cbMaPM.getValue().toString(),(cbMaDG.getValue().toString().split(" - "))[0].trim(), java.sql.Date.valueOf(dtNgayTra.getValue()), Double.parseDouble(txtTienPhat.getText()));
            int rs = PhieuTraService.getInstance().addPhieuTra(phieuTra);
            for(int i = 0; i < temp.size(); i++)
            {
                PhieuTraService.getInstance().addCTPhieuTra(temp.get(i));
                //Update CTPM -> delete all CTPM had returned
                PhieuMuonDAO.getInstance().deleteCTPhieuMuon(phieuTra.getMaPM(), temp.get(i).getMaSach());
            }

            Util.showSuccess(rs, "Quản lý trả", "Thêm phiếu trả thành công!");

            //Check if user has return all books -> PM is inactive
            finishPM(phieuTra.getMaPM());

            traController.refreshTable();
            txtMaPT.setText(Util.generateID(Util.PREFIX_CODE.PM));
            dtNgayTra.setValue(LocalDate.now());
            list.clear();
            txtTienPhat.setText("0");
            tbSachTra.setItems(list);
            loadData();
        }
    }

    public void finishPM(String maPM) {
        List<Integer> records = PhieuMuonDAO.getInstance().getAllRecordStatusCTPMByMaPM(maPM);
        if(!records.contains(1)) {
            PhieuMuonDAO.getInstance().finishPhieuMuon(maPM);
        }
    }

    public void refreshTienPhat()
    {
        double tongtien = 0;
        int tienPhat = ThamSoService.getInstance().getThamSo().getTienPhat();
        List<CTPhieuTra> listct = tbSachTra.getItems();
        for(int i = 0; i < listct.size(); i++)
        {
            int tienPhatSach = SachService.getInstance().getSachByID(listct.get(i).getMaSach()).getTriGia();
            if(listct.get(i).getTinhTrang().equals("Hư hỏng") || listct.get(i).getTinhTrang().equals("Mất"))
            {
                listct.get(i).setTienPhat(tienPhatSach + listct.get(i).getSoNM() * tienPhat);
            }
            else if(listct.get(i).getTinhTrang().equals("Bình thường"))
            {
                listct.get(i).setTienPhat(listct.get(i).getSoNM() * tienPhat);
            }
            tongtien += listct.get(i).getTienPhat();
        }
        tbSachTra.setItems(list);
        txtTienPhat.setText(tongtien + "");
    }
}
