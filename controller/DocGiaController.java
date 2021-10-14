package com.javafx.librarian.controller;

import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.service.DocGiaService;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.javafx.librarian.utils.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DocGiaController implements Initializable {
    @FXML
    public TableView<DocGia> tableDocGia;
    @FXML
    public TableColumn<DocGia, String> colMaDocGia;
    @FXML
    public TableColumn<DocGia, String> colTenDocGia;
    @FXML
    public TableColumn<DocGia, String> colMaLoaiDocGia;
    @FXML
    public TableColumn<DocGia, String> colEmail;
    @FXML
    public TableColumn<DocGia, Date> colNgayLapThe;
    @FXML
    public TableColumn<DocGia, Date> colNgayHetHan;
    @FXML
    public TableColumn<DocGia, Byte> colTinhTrang;
    @FXML
    public TableColumn<DocGia, String> colSoDienThoai;
    @FXML
    public TableColumn<DocGia, Double> colTongNo;
    @FXML
    public TextField textMaDocGia;
    @FXML
    public TextField textTenDocGia;
    @FXML
    public TextField textMaLDG;
    @FXML
    public TextField textNgaySinh;
    @FXML
    public TextField textDiaChi;
    @FXML
    public TextField textEmail;
    @FXML
    public TextField textNgayLapThe;
    @FXML
    public TextField textNgayHetHan;
    @FXML
    public TextField textTinhTrangThe;
    @FXML
    public TextField textSoDienThoai;
    @FXML
    public TextField textTongNo;
    @FXML
    public TextField textTimKiemDG;
    @FXML
    public AnchorPane paneDocGia;

    ObservableList<DocGia> listDocGia = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMaDocGia.setCellValueFactory(new PropertyValueFactory<>("maDocGia"));
        colTenDocGia.setCellValueFactory(new PropertyValueFactory<>("tenDocGia"));
        colMaLoaiDocGia.setCellValueFactory(new PropertyValueFactory<>("maLoaiDocGia"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNgayLapThe.setCellValueFactory(new PropertyValueFactory<>("ngayLapThe"));
        colNgayHetHan.setCellValueFactory(new PropertyValueFactory<>("ngayHetHan"));
        colTinhTrang.setCellValueFactory(new PropertyValueFactory<>("tinhTrangThe"));
        colTongNo.setCellValueFactory(new PropertyValueFactory<>("tongNo"));
        colSoDienThoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));

        listDocGia.addAll(DocGiaService.getInstance().getListDocGia());
        tableDocGia.setItems(listDocGia);

        tableDocGia.getSelectionModel().selectedItemProperty().addListener((observableValue, docGiaTableViewSelectionModel, t1) -> {
            bindingTable();
        });

        textTimKiemDG.textProperty().addListener((observableValue, s, t1) -> {
            listDocGia.clear();
            listDocGia.addAll(DocGiaService.getInstance().searchDocGia(t1));
        });
    }

    private void bindingTable() {
        if (tableDocGia.getItems().size() > 0) {
            int index = tableDocGia.getSelectionModel().getSelectedIndex();
            DocGia dg = tableDocGia.getItems().get(index);
            textMaDocGia.setText(String.valueOf(dg.getMaDocGia()));
            textDiaChi.setText(dg.getDiaChi());
            textEmail.setText(dg.getEmail());
            textMaLDG.setText(dg.getTenLoaiDG());
            textNgayHetHan.setText(Util.dateFormat(dg.getNgayHetHan()));
            textNgayLapThe.setText(Util.dateFormat(dg.getNgayLapThe()));
            textNgaySinh.setText(Util.dateFormat(dg.getNgaySinh()));
            textSoDienThoai.setText(dg.getSoDienThoai());
            textTenDocGia.setText(dg.getTenDocGia());
            textTinhTrangThe.setText(String.valueOf(dg.getTinhTrangThe()));
            textTongNo.setText(String.valueOf(dg.getTongNo()));
        }
    }

    public void btnLamMoiClick(ActionEvent actionEvent) {
//        if (textTimKiemDG.getText() == null || textTimKiemDG.getText().isEmpty())
//            return;
        listDocGia.clear();
        listDocGia.addAll(DocGiaService.getInstance().getListDocGia());
        textTimKiemDG.setText("");
        tableDocGia.requestFocus();
    }

    public void btnThemDGClicked(ActionEvent actionEvent) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/docgia/AddDocGiaView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            AddDocGiaController controller = loader.getController();
            controller.setListDG(listDocGia);

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            Scene scene = new Scene(page);
            scene.setFill(Color.TRANSPARENT);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(paneDocGia.getScene().getWindow());
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void btnXoaDGClicked(ActionEvent actionEvent) {
        DocGia dg = tableDocGia.getSelectionModel().getSelectedItem();
        if (dg != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(paneDocGia.getScene().getWindow());
            alert.setTitle("Xoá Độc Giả");
            alert.setHeaderText("Xoá độc giả " + dg.getTenDocGia());
            alert.setContentText("Bạn chắc chắn chứ?");

            ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(btnYes, btnNo, btnCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == btnYes) {
                int resultDel = DocGiaService.getInstance().deleteDocGia(dg.getMaDocGia());
                if (resultDel == 1) {
                    listDocGia.remove(dg);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Chưa chọn đọc giả cần xóa!");
            alert.showAndWait();
        }
    }

    public void btnSuaDGClicked(ActionEvent actionEvent) {
        DocGia dg = tableDocGia.getSelectionModel().getSelectedItem();
        if (dg != null) {
            try {
                // Load the fxml file and create a new stage for the popup dialog.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/docgia/EditDocGiaView.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                Scene scene = new Scene(page);
                scene.setFill(Color.TRANSPARENT);
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(paneDocGia.getScene().getWindow());
                dialogStage.initStyle(StageStyle.TRANSPARENT);
                dialogStage.setScene(scene);

                EditDocGiaController controller = loader.getController();
                controller.setDocGia(dg);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Chưa chọn đọc giả cần chỉnh sửa!");
            alert.showAndWait();
        }
    }
}
