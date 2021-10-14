package com.javafx.librarian.controller;

import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.model.PhieuThuPhat;
import com.javafx.librarian.service.DocGiaService;
import com.javafx.librarian.service.PhieuThuService;
import com.javafx.librarian.utils.Util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class AddPhieuThuPhatController {
    public TextField textMaPhieuThu;
//    public DatePicker dateNgayThu;
    public JFXComboBox cbbDocGia;
    public TextField textTongNo;
    public Spinner spnSoTienThu;
    public TextField textConLai;
    public JFXButton btnThem;
    public JFXButton btnHuy;
    public JFXButton btnClose;
    public Pane panelAddPhieuThu;
    public FontAwesomeIcon iconClose;
    public TextField textNgayThu;

    private double mousepX = 0;
    private double mousepY = 0;

    ObservableList<PhieuThuPhat> listPhieuThus;
    ObservableList<DocGia> listDG = FXCollections.observableArrayList();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    SpinnerValueFactory<Double> valueFactory;
    @FXML
    public void initialize(){
        panelAddPhieuThu.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelAddPhieuThu.setOnMouseDragged(mouseEvent -> {
            panelAddPhieuThu.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelAddPhieuThu.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });

        listDG.addAll(DocGiaService.getInstance().getListDGHasADebt());
        listDG.forEach(dg->{
            cbbDocGia.getItems().add(dg.getMaDocGia()+" - "+dg.getTenDocGia());
        });
        cbbDocGia.getSelectionModel().selectFirst();

//        dateNgayThu.setValue(LocalDate.parse(dtf.format(LocalDate.now()), dtf));
        textNgayThu.setText(dtf.format(LocalDate.now()));

        textMaPhieuThu.setText(Util.generateID(Util.PREFIX_CODE.PTT));

        textTongNo.textProperty().addListener((observableValue, s, t1) -> {
            if(Double.parseDouble(t1)>0){
                spnSoTienThu.setDisable(false);
                valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1000, Double.parseDouble(t1), Double.parseDouble(t1));
                spnSoTienThu.setValueFactory(valueFactory);
            }else{
                spnSoTienThu.setDisable(true);
            }
        });

        spnSoTienThu.getEditor().textProperty().addListener((observableValue, s, t1) -> {
           try{
               System.out.println(t1);
               if (Double.parseDouble(t1) > 0){
                   textConLai.setText(String.valueOf(Double.parseDouble(textTongNo.getText()) - Double.parseDouble(t1)));
               }
           }catch (Exception e){
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("THÔNG BÁO");
               alert.setHeaderText("Số tiền thu không hợp lệ!");
               alert.showAndWait();

               spnSoTienThu.getValueFactory().setValue(Double.parseDouble(textTongNo.getText()));
           }
        });
    }

    public void setListPhieuThu(ObservableList<PhieuThuPhat> listPhieuThu){
        this.listPhieuThus = listPhieuThu;
    }

    public void btnThemClicked(ActionEvent actionEvent) {
        PhieuThuPhat pt = new PhieuThuPhat();
        pt.setMaPhieuThu(textMaPhieuThu.getText());
        System.out.println(cbbDocGia.getValue().toString().split(" ", 2)[0]);
        pt.setMaDocGia( cbbDocGia.getValue().toString().split(" ", 2)[0]);
        pt.setConLai(Double.parseDouble(textConLai.getText()));
        pt.setNgayThu(java.sql.Date.valueOf(LocalDate.parse(textNgayThu.getText(), dtf)));
        pt.setSoTienThu(Double.parseDouble(spnSoTienThu.getValue().toString()));

        int rs = PhieuThuService.getInstance().addPhieuThuPhat(pt);
        Util.showSuccess(rs, "Quản lý phạt", "Thêm phiếu phạt thành công!");
        listPhieuThus.add(PhieuThuService.getInstance().getPhieuPhatById(pt.getMaPhieuThu()));

        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void btnHuyClicked(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
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

    public void cbbDocGiaAction(ActionEvent actionEvent) {
        String value = cbbDocGia.getValue().toString();
        String arrOfStr = value.split(" ", 2)[0];
        listDG.forEach(dg->{
            if(dg.getMaDocGia().equals(arrOfStr))
            {
                textTongNo.setText(String.valueOf(dg.getTongNo()));
                return;
            }
        });
    }
}
