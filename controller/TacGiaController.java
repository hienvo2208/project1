package com.javafx.librarian.controller;

import com.javafx.librarian.model.TacGia;
import com.javafx.librarian.service.TacGiaService;
import com.javafx.librarian.service.TheLoaiService;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TacGiaController implements Initializable {
    //region khai báo biến controls
    @FXML
    public TableView<TacGia> tbhienthi;
    @FXML
    public TableColumn<TacGia, String> tbcMaTacGia;
    @FXML
    public TableColumn<TacGia, String> tbcTenTacGia;
    @FXML
    public TextField txtTenTacGia;
    @FXML
    public TextField txtMaTacGia;
    @FXML
    public Button btnThem;
    @FXML
    public Button btnXoa;
    @FXML
    public Button btnSua;
    @FXML
    public Tab tpTheLoai;
    @FXML
    public TextField textTimKiem;
    //endregion

    //region controller
    private ObservableList<TacGia> listTacGia;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCell();
        loadData();

        textTimKiem.textProperty().addListener((observableValue, s, t1) -> {
            listTacGia.clear();
            listTacGia.addAll(TacGiaService.getInstance().searchTG(t1));
        });
    }

    private void setCell() {
        tbcMaTacGia.setCellValueFactory(cellData -> cellData.getValue().maTacGiaProperty());
        tbcTenTacGia.setCellValueFactory(cellData -> cellData.getValue().tenTacGiaProperty());
    }

    private void loadData() {
        TacGiaService.getInstance().getAllTacGia();
        listTacGia = FXCollections.observableArrayList(TacGiaService.getInstance().getAllTacGia());
        tbhienthi.setItems(listTacGia);
    }

    public void refreshTable() {
        listTacGia.clear();
        loadData();
    }

    private void clearInput() {
        txtTenTacGia.setText("");
        txtMaTacGia.setText("");
    }

    public void bindingData() {
        TacGia temp = tbhienthi.getSelectionModel().getSelectedItem();
        txtMaTacGia.setText((String.valueOf(temp.getMaTacGia())));
        txtTenTacGia.setText(temp.getTenTacGia());
        System.out.println("KOKO");
    }

    public void btnXoa_Click(ActionEvent event) {

        if (tbhienthi.getSelectionModel().getSelectedIndex() >= 0) {
            TacGia temp = tbhienthi.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xóa tác giả");
            alert.setHeaderText("Bạn muốn xóa tác giả này ra khỏi danh sách?");
            alert.setContentText("[" + temp.getMaTacGia() + "] " + temp.getTenTacGia());

            // option != null.
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null) {
            } else if (option.get() == ButtonType.OK) {
                int rs = TacGiaService.getInstance().deleteTacGia(temp.getMaTacGia());
                Util.showSuccess(rs, "Quản lý tác giả", "Xóa tác giả thành công!");
                refreshTable();
                clearInput();
            } else if (option.get() == ButtonType.CANCEL) {
            } else {
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Chưa chọn tác giả cần xóa!");
            alert.showAndWait();
        }

    }

    public void btnThem_Click(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/AddTacGiaDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Thêm sách");
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //
            AddTacGiaController addTacGia = loader.getController();
            addTacGia.setTacGia(this);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSua_Click(ActionEvent event) {
        if (tbhienthi.getSelectionModel().getSelectedIndex() >= 0) {
            TacGia temp = tbhienthi.getSelectionModel().getSelectedItem();
            //
            //refreshTable();
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/EditTacGiaDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Thêm sách");
                dialogStage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                //
                EditTacGiaController editTacGia = loader.getController();
                editTacGia.setTacGia(this);
                editTacGia.setEditTacGia(temp);
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Chưa chọn tác giả cần chỉnh sửa!");
            alert.showAndWait();
        }
    }


}
