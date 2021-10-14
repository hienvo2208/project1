package com.javafx.librarian.controller;

import com.javafx.librarian.model.PhieuThuPhat;
import com.javafx.librarian.service.DocGiaService;
import com.javafx.librarian.service.PhieuThuService;
import com.javafx.librarian.utils.Util;
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
import java.util.Optional;

public class ThuTienPhatController {
    public TextField textTenDocGia;
    public TextField textMaPhieuThu;
    public TextField textTongNo;
    public TextField textSoTienThu;
    public TextField textConLai;
    public TableColumn<PhieuThuPhat, String> colMaPhieuThu;
    public TableColumn<PhieuThuPhat, String> colMaDocGia;
    public TableColumn<PhieuThuPhat, Double> colSoTienThu;
    public TableColumn<PhieuThuPhat, Double> colConLai;
    public TableView<PhieuThuPhat> tablePhieuThu;
    public AnchorPane panePhieuThu;
    public TextField textNgayThu;
    public TableColumn colNgayThu;
    public TextField textTimKiemDG;

    ObservableList<PhieuThuPhat> listPhieuThuPhat = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        colMaDocGia.setCellValueFactory(new PropertyValueFactory<>("tenDocGia"));
        colMaPhieuThu.setCellValueFactory(new PropertyValueFactory<>("maPhieuThu"));
        colConLai.setCellValueFactory(new PropertyValueFactory<>("conLai"));
        colSoTienThu.setCellValueFactory(new PropertyValueFactory<>("soTienThu"));
        colNgayThu.setCellValueFactory(new PropertyValueFactory<>("ngayThu") );

        listPhieuThuPhat.addAll(PhieuThuService.getInstance().getListPhieuThu());
        tablePhieuThu.setItems(listPhieuThuPhat);

        tablePhieuThu.getSelectionModel().selectedItemProperty().addListener(((observableValue, phieuThuPhat, t1) -> {
            bindingTable();
        }));

        textTimKiemDG.textProperty().addListener((observableValue, s, t1) -> {
            listPhieuThuPhat.clear();
            listPhieuThuPhat.addAll(PhieuThuService.getInstance().searchPhieuPhat(t1));
        });
    }

    private void bindingTable() {
        if (tablePhieuThu.getItems().size() > 0) {
            int index = tablePhieuThu.getSelectionModel().getSelectedIndex();
            PhieuThuPhat pt = tablePhieuThu.getItems().get(index);
            textMaPhieuThu.setText(pt.getMaPhieuThu());
//            System.out.println(pt.getTenDocGia());
            textTenDocGia.setText(pt.getTenDocGia());
            textConLai.setText(String.valueOf(pt.getConLai()));
            textSoTienThu.setText(String.valueOf(pt.getSoTienThu()));
            textNgayThu.setText(Util.dateFormat(pt.getNgayThu()));
        }
    }

    public void btnThemClicked(ActionEvent actionEvent) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/docgia/AddPhieuThuPhat.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            AddPhieuThuPhatController controller = loader.getController();
            controller.setListPhieuThu(listPhieuThuPhat);

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            Scene scene = new Scene(page);
            scene.setFill(Color.TRANSPARENT);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(panePhieuThu.getScene().getWindow());
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnXoaClicked(ActionEvent actionEvent) {
        PhieuThuPhat pt = tablePhieuThu.getSelectionModel().getSelectedItem();
        if (pt != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(panePhieuThu.getScene().getWindow());
            alert.setTitle("Xoá Phiếu Thu");
            alert.setHeaderText("Xoá phiếu thu " + pt.getMaPhieuThu());
            alert.setContentText("Bạn chắc chắn chứ?");

            ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(btnYes, btnNo, btnCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == btnYes) {
                int resultDel = PhieuThuService.getInstance().deletePhieuThuPhat(pt.getMaPhieuThu());
                if (resultDel == 1) {
                    listPhieuThuPhat.remove(pt);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(panePhieuThu.getScene().getWindow());
            //alert.setTitle("");
            alert.setHeaderText("Lỗi");
            alert.setContentText("Vui lòng chọn phiếu thu muốn xoá!");
            alert.show();
        }
    }

    public void btnSuaClicked(ActionEvent actionEvent) {
    }

    public void btnLamMoiClicked(ActionEvent actionEvent) {
        listPhieuThuPhat.clear();
        listPhieuThuPhat.addAll(PhieuThuService.getInstance().getListPhieuThu());
//        textTimKiemDG.setText("");
        tablePhieuThu.requestFocus();
    }
}
