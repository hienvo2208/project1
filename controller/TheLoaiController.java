package com.javafx.librarian.controller;

import com.javafx.librarian.model.TacGia;
import com.javafx.librarian.model.TheLoai;
import com.javafx.librarian.service.DocGiaService;
import com.javafx.librarian.service.TheLoaiService;
import com.javafx.librarian.utils.Util;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TheLoaiController implements Initializable {
    //region khai báo biến controls
    @FXML
    public TableView<TheLoai> tableTheLoai;
    @FXML
    public TableColumn<TheLoai, String> colMaTheLoai;
    @FXML
    public TableColumn<TheLoai, String> colTenTheLoai;
    @FXML
    public TextField txtMaTheLoai;
    @FXML
    public TextField txtTenTheLoai;
    @FXML
    public Button btnThemTheLoai;
    @FXML
    public Button btnXoaTheLoai;
    @FXML
    public Button btnSuaTheLoai;
    @FXML
    public TextField textTimKiem;
    @FXML
    public AnchorPane panelTheLoai;
    //endregion

    //region controller
    private ObservableList<TheLoai> listTheLoai;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCell();
        loadData();

        txtMaTheLoai.setEditable(false);
        txtTenTheLoai.setEditable(false);

        textTimKiem.textProperty().addListener((observableValue, s, t1) -> {
            listTheLoai.clear();
            listTheLoai.addAll(TheLoaiService.getInstance().searchTL(t1));
        });
    }

    private void setCell() {
        colMaTheLoai.setCellValueFactory(cellData -> cellData.getValue().maTheLoaiProperty());
        colTenTheLoai.setCellValueFactory(cellData -> cellData.getValue().tenTheLoaiProperty());
    }

    private void loadData() {
        TheLoaiService.getInstance().getAllTheLoai();
        listTheLoai = FXCollections.observableArrayList(TheLoaiService.getInstance().getAllTheLoai());
        tableTheLoai.setItems(listTheLoai);
    }

    public void refreshTable() {
        listTheLoai.clear();
        loadData();
        clearInput();
    }

    private void clearInput() {
        txtMaTheLoai.setText("");
        txtTenTheLoai.setText("");
    }

    public void bindingData() {
        TheLoai temp = tableTheLoai.getSelectionModel().getSelectedItem();
        txtMaTheLoai.setText((String.valueOf(temp.getMaTheLoai())));
        txtTenTheLoai.setText(temp.getTenTheLoai());
        //System.out.println("OK");
    }

    public void btnXoa_Click(ActionEvent event) {

        if (tableTheLoai.getSelectionModel().getSelectedIndex() >= 0) {
            TheLoai temp = tableTheLoai.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xóa thể loại");
            alert.setHeaderText("Bạn muốn xóa thể loại này ra khỏi danh sách?");
            alert.setContentText("[" + temp.getMaTheLoai() + "] " + temp.getTenTheLoai());

            // option != null.
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null) {
            } else if (option.get() == ButtonType.OK) {
                int rs = TheLoaiService.getInstance().deleteTheLoai(temp.getMaTheLoai());
                Util.showSuccess(rs, "Quản lý thể loại", "Xóa thể loại thành công!");
                refreshTable();
            } else if (option.get() == ButtonType.CANCEL) {
            } else {
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Chưa chọn thể loại cần xóa!");
            alert.showAndWait();
        }

    }

    public void btnThemTheLoai_Click(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/AddTheLoaiDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //
            AddTheLoaiController addTheLoaiController = loader.getController();
            addTheLoaiController.setTheLoaiController(this);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSuaTheLoai_Click(ActionEvent event) {
        if (tableTheLoai.getSelectionModel().getSelectedIndex() >= 0) {
            TheLoai temp = tableTheLoai.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/EditTheLoaiDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                //
                EditTheLoaiController editTheLoaiController = loader.getController();
                editTheLoaiController.setTheLoaiController(this);
                editTheLoaiController.setEditTheLoai(temp);
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Chưa chọn thể loại cần chỉnh sửa!");
            alert.showAndWait();
        }
    }
}
