package com.javafx.librarian.controller;

import com.javafx.librarian.dao.DocGiaDao;
import com.javafx.librarian.dao.PhieuMuonDAO;
import com.javafx.librarian.model.*;
import com.javafx.librarian.service.*;
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
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class BaoCaoSachTTController implements Initializable {
    @FXML
    public TableView<CTBaoCaoSTT> tbCTBCTheLoai;
    @FXML
    public TableColumn<CTBaoCaoSTT, Integer> colStt;
    @FXML
    public TableColumn<CTBaoCaoSTT, String> colMaSach;
    @FXML
    public TableColumn<CTBaoCaoSTT, String> colTenSach;
    @FXML
    public TableColumn<CTBaoCaoSTT, java.util.Date> colNgayMuon;
    @FXML
    public TableColumn<CTBaoCaoSTT, Integer> colSoNgayTT;
    @FXML
    public DatePicker dtNgay;
    @FXML
    public Button btnThongKe;
    @FXML
    public Button btnLapBaoCao;
    //endregion

    //region controller
    private String mabc;
    private ObservableList<CTBaoCaoSTT> listBCTTL;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCell();
        loadData();
    }

    private void setCell() {
        colStt.setCellValueFactory(cellData -> cellData.getValue().sttProperty().asObject());
        colMaSach.setCellValueFactory(cellData -> cellData.getValue().maSachProperty());
        colTenSach.setCellValueFactory(cellData -> cellData.getValue().tenSachProperty());
        colNgayMuon.setCellValueFactory(cellData -> cellData.getValue().ngayMuonProperty());
        colSoNgayTT.setCellValueFactory(cellData -> cellData.getValue().soNgayTraTreProperty().asObject());
    }

    private void loadData() {
        dtNgay.setValue(LocalDate.now());
    }

    public void btnThongKe_Click()
    {
        mabc = Util.generateID(Util.PREFIX_CODE.BCTT);
        Date ngay = Date.valueOf(dtNgay.getValue());
        BaoCaoSachTTService.getInstance().deleteBaoCaoSachTT(ngay);
        listBCTTL = FXCollections.observableArrayList(BaoCaoSachTTService.getInstance().getAllCTBCSachTT(ngay));
        tbCTBCTheLoai.setItems(listBCTTL);
        BaoCaoSachTraTre bc = new BaoCaoSachTraTre(mabc, ngay);
        BaoCaoSachTTService.getInstance().addBaoCaoSachTT(bc);
        for(int i = 0; i < listBCTTL.size(); i++)
        {
            CTBaoCaoSTT ctbc = new CTBaoCaoSTT(mabc, listBCTTL.get(i).getMaSach(), listBCTTL.get(i).getTenSach(), listBCTTL.get(i).getNgayMuon(), listBCTTL.get(i).getSoNgayTraTre());
            BaoCaoSachTTService.getInstance().addCTBaoCaoSachTT(ctbc);
        }
    }

    public void btnlapBaoCao_Click() throws JRException {
        Date ngay = Date.valueOf(dtNgay.getValue());
        String reportSrcFile = "../view/report/ReportSachTheoTheLoai.jrxml";
        try {
            // --- Show Jasper Report on click-----
            new PrintReport().showReport2(reportSrcFile, ngay);
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }
}
