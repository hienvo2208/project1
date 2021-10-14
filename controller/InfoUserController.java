package com.javafx.librarian.controller;

import com.javafx.librarian.model.Account;
import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.model.LSMuonSach;
import com.javafx.librarian.service.AccountService;
import com.javafx.librarian.service.DocGiaService;
import com.javafx.librarian.service.LSMuonSachService;
import com.javafx.librarian.utils.Util;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InfoUserController {
    public TextField textMa;
    public TextField textDiaChi;
    public TextField textEmail;
    public TextField textSDT;
    public TextField textUsername;
//    public TextField textPassword;
//    public ComboBox<String> cbbLDG;
    public TextField textNgaylap;
    public TextField textNgayhet;
    public TextField textTongNo;
    public TextArea textAGioiThieu;
    public Label lbName;
    public PasswordField textPassword;
    public DatePicker dateNgaySinh;
    public TextField textLDG;
    public TableView<LSMuonSach> tableLSMuon;
    public TableColumn<LSMuonSach, String> colMaSach;
    public TableColumn<LSMuonSach, String> colTenSach;
    public TableColumn<LSMuonSach, String> colHanTra;
    public TableColumn<LSMuonSach, String> colTinhTrang;
    public TableColumn<LSMuonSach, String> colNgayMuon;
    public JFXButton btnChinhSua;
    public JFXButton btnLuu;
    public JFXButton btnHuy;
    public JFXButton btnDoiPass;
    public AnchorPane paneInfor;
    public TextField textName;

    DocGia dg;
    Account account;
//    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    ObservableList<LSMuonSach> listLSMuonSaches = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        colMaSach.setCellValueFactory(new PropertyValueFactory<>("MaSach"));
        colTenSach.setCellValueFactory(new PropertyValueFactory<>("TenSach"));
        colTinhTrang.setCellValueFactory(c->{
            if(c.getValue().getTinhTrang().equals("0")){
                return new SimpleStringProperty("Đã trả");
            }else{
                if(Util.convertDateToLocalDate(c.getValue().getHanTra()).compareTo(LocalDate.now())>0){
                    return new SimpleStringProperty("Còn hạn");
                }else return new SimpleStringProperty("Quá hạn");
            }
        });
        colNgayMuon.setCellValueFactory(c-> new SimpleStringProperty(Util.dateFormat(c.getValue().getNgayMuon())));
        colHanTra.setCellValueFactory(c-> new SimpleStringProperty(Util.dateFormat(c.getValue().getHanTra())));
    }

    public void setAccount(Account ac){
        BindingData(ac);
    }

    private void BindingData(Account ac){
        this.account = ac;
        dg = DocGiaService.getInstance().getDocGia(this.account.getUsername(), "");
        textUsername.setText(this.account.getUsername());
        textPassword.setText(this.account.getPassword());
//        lbName.setText(dg.getTenDocGia().toUpperCase());
        textMa.setText(dg.getMaDocGia());
        textEmail.setText(dg.getEmail());
        textDiaChi.setText(dg.getDiaChi());
        textNgaylap.setText(Util.dateFormat(dg.getNgayLapThe()));
        textNgayhet.setText(Util.dateFormat(dg.getNgayHetHan()));
        textSDT.setText(dg.getSoDienThoai());
        dateNgaySinh.setValue(Util.convertDateToLocalDateUI(dg.getNgaySinh()));
        textTongNo.setText(String.valueOf(dg.getTongNo()));
        textLDG.setText(dg.getTenLoaiDG());
        textName.setText(dg.getTenDocGia());
        textAGioiThieu.setText(dg.getGioiThieu());

        if(listLSMuonSaches.size()>0) listLSMuonSaches.clear();
        listLSMuonSaches.addAll(LSMuonSachService.getInstance().getListLSMuonSach(dg.getMaDocGia()));
        tableLSMuon.setItems(listLSMuonSaches);
    }

    public void btnChinhSuaClicked(ActionEvent actionEvent) {
        btnLuu.setVisible(true);
        btnHuy.setVisible(true);
        btnChinhSua.setVisible(false);

        dateNgaySinh.setDisable(false);
        textDiaChi.setEditable(true);
        textSDT.setEditable(true);
        textName.setEditable(true);
        btnDoiPass.setVisible(true);
        textAGioiThieu.setEditable(true);
    }

    public void btnLuuClicked(ActionEvent actionEvent) {
        int rs = DocGiaService.getInstance().updateInfoDocDG(textMa.getText(), textName.getText(), textDiaChi.getText(), textSDT.getText(), Date.valueOf(dateNgaySinh.getValue()), textUsername.getText(), textPassword.getText(), textAGioiThieu.getText());
        System.out.println(rs);

        btnChinhSua.setVisible(true);
        btnHuy.setVisible(false);
        btnLuu.setVisible(false);

        dateNgaySinh.setDisable(true);
        textDiaChi.setEditable(false);
        textSDT.setEditable(false);
        textName.setEditable(false);
        btnDoiPass.setVisible(false);
        textAGioiThieu.setEditable(false);

        BindingData(AccountService.getInstance().getUserById(account.getUsername()));
    }

    public void btnHuyClicked(ActionEvent actionEvent) {
        btnChinhSua.setVisible(true);
        btnHuy.setVisible(false);
        btnLuu.setVisible(false);

        dateNgaySinh.setDisable(true);
        textDiaChi.setEditable(false);
        textSDT.setEditable(false);
        textName.setEditable(false);
        btnDoiPass.setVisible(false);
        textAGioiThieu.setEditable(false);
        BindingData(AccountService.getInstance().getUserById(account.getUsername()));
    }

    public void btnDoiPassClicked(ActionEvent actionEvent) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/DoiMatKhauView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            DoiMatKhauController controller = loader.getController();
            controller.setMK(account);

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            Scene scene = new Scene(page);
            scene.setFill(Color.TRANSPARENT);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(paneInfor.getScene().getWindow());
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            textPassword.setText(account.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
