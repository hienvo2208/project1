package com.javafx.librarian.controller;

import com.javafx.librarian.dao.QuyenDAO;
import com.javafx.librarian.model.Quyen;
import com.javafx.librarian.service.ThamSoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.net.URL;
import java.util.ResourceBundle;

public class QuyenController implements Initializable {
    @FXML
    public TableView<Quyen> tbQuyen;
    @FXML
    public TableColumn<Quyen, Integer> colID;
    @FXML
    public TableColumn<Quyen, String> colName;
    @FXML
    public TableColumn<Quyen, String> colCode;
    @FXML
    public TableColumn<Quyen, String> colDes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCell();
        loadData();
    }

    private void setCell() {
        colID.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colCode.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        colDes.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    }

    private void loadData() {
        ObservableList<Quyen> list = FXCollections.observableArrayList(QuyenDAO.getInstance().getAllQuyen());
        tbQuyen.setItems(list);
    }
}
