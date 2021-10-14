package com.javafx.librarian.controller;

import javafx.librarian.model.Account;
import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.model.LoaiDocGia;
import com.javafx.librarian.service.AccountService;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.print.Doc;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.stream.Collectors;

public class AddDocGiaController {
    @FXML
    public Pane panelThemDocGia;
    @FXML
    public TextField textEmail;
    @FXML
    public TextField textTenDocGia;
    @FXML
    public JFXComboBox<String> cbbLoaiDG;
    @FXML
    public DatePicker dateNgaySinh;
    @FXML
    public TextField textDiaChi;
    @FXML
    public TextField textSoDienThoai;
    @FXML
    public JFXComboBox<String> cbbAccount;
    @FXML
    public JFXButton btnThemAccount;
    @FXML
    public JFXButton btnThem;
    @FXML
    public JFXButton btnHuy;
    @FXML
    public JFXButton btnClose;
    @FXML
    public FontAwesomeIcon iconClose;
    @FXML
    public TextField textMaDG;

    private double mousepX = 0;
    private double mousepY = 0;

    ObservableList<Account> listAccount = FXCollections.observableArrayList();
    ObservableList<LoaiDocGia> listLDG = FXCollections.observableArrayList();
    ObservableList<DocGia> listDocGia;
    int tuoimax;
    int tuoimin;
    int hanthe;

    @FXML
    public void initialize(){
        panelThemDocGia.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelThemDocGia.setOnMouseDragged(mouseEvent -> {
            panelThemDocGia.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelThemDocGia.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });

        listLDG.addAll(LoaiDocGiaService.getInstance().getListLoaiDocGia());
        listLDG.forEach(ldg ->{
            cbbLoaiDG.getItems().add(ldg.getTenLoaiDocGia());
        });

        listAccount.addAll(AccountService.getInstance().getUserNoOwner());
        listAccount.forEach(acc->{
            cbbAccount.getItems().add(acc.getUsername());
        });

        var ts = ThamSoService.getInstance().getThamSo();
        tuoimax = ts.getMaxTuoi();
        tuoimin = ts.getMinTuoi();
        hanthe = ts.getHanThe();

        textMaDG.setText(Util.generateID(Util.PREFIX_CODE.DG));

        dateNgaySinh.valueProperty().addListener((observableValue, localDate, t1) -> {
            int a = Period.between(t1, LocalDate.now()).getYears();
            if(!(a>=tuoimin&&a<=tuoimax)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(panelThemDocGia.getScene().getWindow());
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

        cbbLoaiDG.getSelectionModel().selectFirst();
    }

    public void setListDG(ObservableList<DocGia> listDG){
        this.listDocGia = listDG;
    }

    public void btnThemClicked(ActionEvent actionEvent) {
        //VALIDATE
        if(textTenDocGia.getText().trim().equals("") ||
                textEmail.getText().trim().equals("") ||
                dateNgaySinh.getValue().toString().trim().equals("") ||
                cbbAccount.getValue().trim().equals("")
        ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Vui lòng nhập đầy đủ dữ liệu!");
            alert.showAndWait();
            return;
        }
        //

        DocGia dg = new DocGia();
        dg.setMaDocGia(textMaDG.getText());
        dg.setTenDocGia(textTenDocGia.getText());
        dg.setMaLoaiDocGia(listLDG.stream().filter(ldg-> Objects.equals(ldg.getTenLoaiDocGia(), cbbLoaiDG.getValue())).collect(Collectors.toList()).get(0).getMaLoaiDocGia());
        dg.setEmail(textEmail.getText());
        dg.setNgaySinh(Date.valueOf(dateNgaySinh.getValue()));
        dg.setNgayLapThe(Date.valueOf(LocalDate.now()));
        dg.setNgayHetHan(Date.valueOf((LocalDate.now()).plusMonths(hanthe)));
        dg.setIdAccount(cbbAccount.getValue());
        dg.setDiaChi(textDiaChi.getText());
        dg.setSoDienThoai(textSoDienThoai.getText());


        int rs = DocGiaService.getInstance().addDocGia(dg);
        Util.showSuccess(rs, "Quản lý đọc giả", "Thêm đọc giả thành công!");
        listDocGia.add(DocGiaService.getInstance().getDocGia(dg.getIdAccount(), dg.getMaDocGia()));

//        boolean rs = DocGiaService.getInstance().updatecodedg();
//        System.out.println(rs);
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

    public void btnThemAccountClicked(ActionEvent actionEvent) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/AddAccountView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            AddAccountController controller = loader.getController();
            controller.setListAccount(listAccount);

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            Scene scene = new Scene(page);
            scene.setFill(Color.TRANSPARENT);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(panelThemDocGia.getScene().getWindow());
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            cbbAccount.getItems().clear();
            listAccount.forEach(acc->{
                cbbAccount.getItems().add(acc.getUsername());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnCloseMouseExit(MouseEvent mouseEvent) {
        btnClose.setStyle("-fx-background-color: #a6a6a6; -fx-background-radius: 15");
        iconClose.setVisible(false);
    }
}
