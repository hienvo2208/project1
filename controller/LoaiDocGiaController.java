package com.javafx.librarian.controller;

import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.model.LoaiDocGia;
import com.javafx.librarian.service.DocGiaService;
import com.javafx.librarian.service.LoaiDocGiaService;
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

public class LoaiDocGiaController implements Initializable {

    @FXML
    public TableView<LoaiDocGia> tableLoaiDocGia;
    @FXML
    public TableColumn<LoaiDocGia, String> colMaLoaiDocGia;
    @FXML
    public TableColumn<LoaiDocGia, String> colTenLoaiDocGia;
    @FXML
    public TextField textMaLoaiDocGia;
    @FXML
    public TextField textTenDocGia;
    @FXML
    public JFXButton btnThemLDG;
    @FXML
    public JFXButton btnXoaLDG;
    @FXML
    public JFXButton btnLamMoi;
    @FXML
    public JFXButton btnSuaLDG;
    @FXML
    public TextField textTimKiem;
    @FXML
    public AnchorPane panelLDG;


    ObservableList<LoaiDocGia> listLoaiDocGia = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMaLoaiDocGia.setCellValueFactory(new PropertyValueFactory<>("maLoaiDocGia"));
        colTenLoaiDocGia.setCellValueFactory(new PropertyValueFactory<>("tenLoaiDocGia"));

        listLoaiDocGia.addAll(LoaiDocGiaService.getInstance().getListLoaiDocGia());
        tableLoaiDocGia.setItems(listLoaiDocGia);

        tableLoaiDocGia.getSelectionModel().selectedItemProperty().addListener((observableValue, docGiaTableViewSelectionModel, t1) -> {
            bindingTable();
        });

        textTimKiem.textProperty().addListener((observableValue, s, t1) -> {
            listLoaiDocGia.clear();
            listLoaiDocGia.addAll(LoaiDocGiaService.getInstance().searchLoaiDocGia(t1));
        });
    }

    private void bindingTable() {
        if (tableLoaiDocGia.getItems().size() > 0) {
            int index = tableLoaiDocGia.getSelectionModel().getSelectedIndex();
            LoaiDocGia ldg = tableLoaiDocGia.getItems().get(index);
            textMaLoaiDocGia.setText(String.valueOf(ldg.getMaLoaiDocGia()));
            textTenDocGia.setText(ldg.getTenLoaiDocGia());
        }

    }

    public void btnThemLDGClicked(ActionEvent actionEvent) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/docgia/CULoaiDocGia.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            CULoaiDocGiaController controller = loader.getController();
            controller.setLabelLDG("Th??m lo???i ?????c gi???");
            controller.setListLDG(this.listLoaiDocGia);

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            Scene scene = new Scene(page);
            scene.setFill(Color.TRANSPARENT);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(panelLDG.getScene().getWindow());
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnXoaLDGClicked(ActionEvent actionEvent) {
        LoaiDocGia ldg = tableLoaiDocGia.getSelectionModel().getSelectedItem();
        if (ldg != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(panelLDG.getScene().getWindow());
            alert.setTitle("Xo?? Lo???i ?????c Gi???");
            alert.setHeaderText("Xo?? lo???i ?????c gi??? " + ldg.getTenLoaiDocGia());
            alert.setContentText("B???n ch???c ch???n ch????");

            ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(btnYes, btnNo, btnCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == btnYes) {
                int resultDel = LoaiDocGiaService.getInstance().deleteLDG(ldg.getMaLoaiDocGia());
                if (resultDel == 1) {
                    listLoaiDocGia.remove(ldg);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(panelLDG.getScene().getWindow());
            //alert.setTitle("");
            alert.setHeaderText("L???i");
            alert.setContentText("Vui l??ng ch???n lo???i ?????c gi??? mu???n xo??!");
            alert.show();
        }
    }

    public void btnSuaLDGClicked(ActionEvent actionEvent) {
        LoaiDocGia ldg = tableLoaiDocGia.getSelectionModel().getSelectedItem();
        if (ldg != null) {
            try {
                // Load the fxml file and create a new stage for the popup dialog.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/docgia/CULoaiDocGia.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                CULoaiDocGiaController controller = loader.getController();
                controller.setLabelLDG("S???a lo???i ?????c gi???");

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                Scene scene = new Scene(page);
                scene.setFill(Color.TRANSPARENT);
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(panelLDG.getScene().getWindow());
                dialogStage.initStyle(StageStyle.TRANSPARENT);
                dialogStage.setScene(scene);

                CULoaiDocGiaController controllerCU = loader.getController();
                controllerCU.setLoaiDocGia(ldg);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(panelLDG.getScene().getWindow());
            //alert.setTitle("");
            alert.setHeaderText("L???i");
            alert.setContentText("Vui l??ng ch???n lo???i ?????c gi??? mu???n s???a!");
            alert.show();
        }
    }

    public void btnLamMoiClicked(ActionEvent actionEvent) {
        listLoaiDocGia.clear();
        listLoaiDocGia.addAll(LoaiDocGiaService.getInstance().getListLoaiDocGia());
        textTimKiem.setText("");
        tableLoaiDocGia.requestFocus();
    }
}
