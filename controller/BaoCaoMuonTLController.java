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
import java.util.*;

public class BaoCaoMuonTLController implements Initializable {
    @FXML
    public TableView<CTBaoCaoTheoTheLoai> tbCTBCTheLoai;
    @FXML
    public TableColumn<CTBaoCaoTheoTheLoai, Integer> colStt;
    @FXML
    public TableColumn<CTBaoCaoTheoTheLoai, String> colMaTL;
    @FXML
    public TableColumn<CTBaoCaoTheoTheLoai, String> colTenTL;
    @FXML
    public TableColumn<CTBaoCaoTheoTheLoai, Integer> colSoLuotMuon;
    @FXML
    public TableColumn<CTBaoCaoTheoTheLoai, Double> colTiLe;
    @FXML
    public ComboBox<Integer> cbThang;
    @FXML
    public TextField txtNam;
    @FXML
    public Button btnThongKe;
    @FXML
    public Button btnLapBaoCao;
    //endregion

    //region controller
    private String mabc;
    private ObservableList<CTBaoCaoTheoTheLoai> listBCTTL;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCell();
        loadData();
    }

    private void setCell() {
        colStt.setCellValueFactory(cellData -> cellData.getValue().sttProperty().asObject());
        colMaTL.setCellValueFactory(cellData -> cellData.getValue().maTheLoaiProperty());
        colTenTL.setCellValueFactory(cellData -> cellData.getValue().tenTheLoaiProperty());
        colSoLuotMuon.setCellValueFactory(cellData -> cellData.getValue().soLuotMuonProperty().asObject());
        colTiLe.setCellValueFactory(cellData -> cellData.getValue().tiLeProperty().asObject());
    }

    private void loadData() {
        List<Integer> dataThang  = new ArrayList<Integer>();
        dataThang.add(1);
        dataThang.add(2);
        dataThang.add(3);
        dataThang.add(4);
        dataThang.add(5);
        dataThang.add(6);
        dataThang.add(7);
        dataThang.add(8);
        dataThang.add(9);
        dataThang.add(10);
        dataThang.add(11);
        dataThang.add(12);
        cbThang.setItems(FXCollections.observableList(dataThang));
        cbThang.getSelectionModel().selectFirst();
        txtNam.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(change.getControlNewText().matches("^0\\d?+"))
                    return null;
                return change.getControlNewText().matches("\\d+") && change.getControlNewText().length() <= 4 ? change : null;
            }

            return change;
        }));
    }

    public void btnThongKe_Click()
    {
        if(txtNam.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Vui lòng nhập năm!");
            alert.showAndWait();
        }
        else
        {
            mabc = Util.generateID(Util.PREFIX_CODE.BCTL);
            int thang = cbThang.getValue();
            int nam = Integer.parseInt(txtNam.getText());
            BaoCaoTheoTheLoaiService.getInstance().deleteBaoCaoTheoTheLoai(thang, nam);
            int tongluotmuon = BaoCaoTheoTheLoaiService.getInstance().getTongLuotMuon(thang, nam);
            listBCTTL = FXCollections.observableArrayList(BaoCaoTheoTheLoaiService.getInstance().getAllCTBCTheoTL(thang, nam));
            tbCTBCTheLoai.setItems(listBCTTL);
            BaoCaoTheoTheLoai bc = new BaoCaoTheoTheLoai(mabc, thang, nam, tongluotmuon);
            BaoCaoTheoTheLoaiService.getInstance().addBaoCaoTheoTheLoai(bc);
            for(int i = 0; i < listBCTTL.size(); i++)
            {
                CTBaoCaoTheoTheLoai ctbc = new CTBaoCaoTheoTheLoai(mabc, listBCTTL.get(i).getMaTheLoai(), listBCTTL.get(i).getTenTheLoai(), listBCTTL.get(i).getSoLuotMuon(), listBCTTL.get(i).getTiLe());
                BaoCaoTheoTheLoaiService.getInstance().addCTBaoCaoTheoTheLoai(ctbc);
            }
        }
    }

    public void btnlapBaoCao_Click() throws JRException {
        if(txtNam.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Vui lòng nhập năm!");
            alert.showAndWait();
        }
        else {
            int thang = cbThang.getValue();
            int nam = Integer.parseInt(txtNam.getText());
            int tongluotmuon = BaoCaoTheoTheLoaiService.getInstance().getTongLuotMuon(thang, nam);
            String reportSrcFile = "../view/report/ReportSachTheoTheLoai.jrxml";
            try {
                // --- Show Jasper Report on click-----
                new PrintReport().showReport(reportSrcFile, thang, nam, mabc, tongluotmuon);
            } catch (ClassNotFoundException | JRException | SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
