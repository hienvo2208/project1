package com.javafx.librarian.controller;

import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.model.LoaiDocGia;
import com.javafx.librarian.service.DocGiaService;
import com.javafx.librarian.service.LoaiDocGiaService;
import com.javafx.librarian.service.ThamSoService;
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
import java.time.Period;
import java.util.Calendar;
import java.util.Objects;
import java.util.stream.Collectors;

public class EditDocGiaController{
    @FXML
    public JFXButton btnClose;
    @FXML
    public FontAwesomeIcon iconClose;
    @FXML
    public Pane panelSuaDocGia;
    @FXML
    public TextField textMaDocGia;
    @FXML
    public JFXButton btnCapNhat;
    @FXML
    public JFXButton btnHuy;
    @FXML
    public JFXComboBox<String> cbbLoaiDG;
    @FXML
    public TextField textTenDocGia;
    @FXML
    public TextField textTinhTrangThe;
    @FXML
    public TextField textTongNo;
    @FXML
    public TextField textSoDienThoai;
    @FXML
    public TextField textDiaChi;
    @FXML
    public DatePicker dateNgayHetHan;
    @FXML
    public DatePicker dateNgaySinh;

    private double mousepX = 0;
    private double mousepY = 0;

    DocGia dg;
    ObservableList<LoaiDocGia> listLDG = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        panelSuaDocGia.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelSuaDocGia.setOnMouseDragged(mouseEvent -> {
            panelSuaDocGia.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelSuaDocGia.getScene().getWindow().setY(mouseEvent.getScreenY()- mousepY);
        });

        listLDG.addAll(LoaiDocGiaService.getInstance().getListLoaiDocGia());

        textTongNo.setDisable(true);

        var ts = ThamSoService.getInstance().getThamSo();
        int tuoimax = ts.getMaxTuoi();
        int tuoimin = ts.getMinTuoi();
        int hanthe = ts.getHanThe();

        dateNgaySinh.valueProperty().addListener((observableValue, localDate, t1) -> {
            int a = Period.between(t1, LocalDate.now()).getYears();
            if(!(a>=tuoimin&&a<=tuoimax)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(textTongNo.getScene().getWindow());
                alert.setTitle("Lỗi");
                alert.setContentText("Tuổi không thoả lớn hơn 18 và nhỏ hơn 55");
                ButtonType btnYes = new ButtonType("OK", ButtonBar.ButtonData.YES);
                alert.getButtonTypes().setAll(btnYes);
                alert.showAndWait();

                LocalDate dateDefault = LocalDate.now().minusYears(20);
                dateNgaySinh.setValue(dateDefault);
            }
        });

        textSoDienThoai.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(!change.getControlNewText().matches("\\d+"))
                    return null;
            }

            return change;
        }));
    }

    public void setDocGia(DocGia dg){
        this.dg = dg;
        textMaDocGia.setText(dg.getMaDocGia());
        textTenDocGia.setText(dg.getTenDocGia());
        textTinhTrangThe.setText(String.valueOf(dg.getTinhTrangThe()));
        textTongNo.setText(String.valueOf(dg.getTongNo()));
        textDiaChi.setText(dg.getDiaChi());
        textSoDienThoai.setText(dg.getSoDienThoai());
        listLDG.forEach(ldg ->{
            cbbLoaiDG.getItems().add(ldg.getTenLoaiDocGia());
        });

        cbbLoaiDG.setValue(listLDG.stream().filter(ldg->ldg.getMaLoaiDocGia().equals(dg.getMaLoaiDocGia())).collect(Collectors.toList()).get(0).getTenLoaiDocGia());
        dateNgaySinh.setValue(Util.convertDateToLocalDateUI(dg.getNgaySinh()));
        dateNgayHetHan.setValue(Util.convertDateToLocalDateUI(dg.getNgayHetHan()));
    }

    public void btnCloseMouseEnter(MouseEvent mouseEvent) {
        btnClose.setStyle("-fx-background-color: red; -fx-background-radius: 15");
        iconClose.setVisible(true);
    }

    public void btnCloseMouseExit(MouseEvent mouseEvent) {
        btnClose.setStyle("-fx-background-color: #a6a6a6; -fx-background-radius: 15");
        iconClose.setVisible(false);
    }

    public void btnCloseAction(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void btnCapNhatClicked(ActionEvent actionEvent) {
        //VALIDATE
        if(textTenDocGia.getText().trim().equals("") ||
                dateNgaySinh.getValue().toString().trim().equals("")
        ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Vui lòng nhập đầy đủ dữ liệu!");
            alert.showAndWait();
            return;
        }
        //

        this.dg.setTenDocGia(textTenDocGia.getText());
        this.dg.setTinhTrangThe(Byte.valueOf(textTinhTrangThe.getText()));
        this.dg.setTongNo(Double.parseDouble(textTongNo.getText()));
        this.dg.setDiaChi(textDiaChi.getText());
        this.dg.setSoDienThoai(textSoDienThoai.getText());
        this.dg.setNgaySinh(Date.valueOf(dateNgaySinh.getValue()));
        this.dg.setNgayHetHan(Date.valueOf(dateNgayHetHan.getValue()));
        this.dg.setMaLoaiDocGia(listLDG.stream().filter(ldg-> Objects.equals(ldg.getTenLoaiDocGia(), cbbLoaiDG.getValue())).collect(Collectors.toList()).get(0).getMaLoaiDocGia());

        int rs = DocGiaService.getInstance().updateDocGia(dg);
        Util.showSuccess(rs, "Quản lý đọc giả", "Sửa đọc giả thành công!");
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void btnHuyClicked(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }
}
