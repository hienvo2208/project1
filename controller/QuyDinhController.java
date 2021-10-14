package com.javafx.librarian.controller;

import com.javafx.librarian.model.Account;
import com.javafx.librarian.model.ThamSo;
import com.javafx.librarian.model.TheLoai;
import com.javafx.librarian.service.ThamSoService;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class QuyDinhController implements Initializable {
    //region khai báo biến controls
    @FXML
    public TextField txtMaxTuoi;
    @FXML
    public TextField txtMinTuoi;
    @FXML
    public TextField txtHanThe;
    @FXML
    public TextField txtXB;
    @FXML
    public TextField txtMaxMuon;
    @FXML
    public TextField txtTimeMuon;
    @FXML
    public TextField txtTienPhat;
    @FXML
    public TextField txtTienHu;
    @FXML
    public Button btnCapNhat;
    //endregion

    //region controller
    private ThamSo thamSo;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();

        //check permission
        if(Account.currentUser.getIdper() == 3)
            btnCapNhat.setVisible(false);
        //
        txtMaxTuoi.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(change.getControlNewText().matches("^0\\d?+"))
                    return null;
                return change.getControlNewText().matches("\\d+") && change.getControlNewText().length() <= 4 ? change : null;
            }

            return change;
        }));

        txtMinTuoi.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(change.getControlNewText().matches("^0\\d?+"))
                    return null;
                return change.getControlNewText().matches("\\d+") && change.getControlNewText().length() <= 4 ? change : null;
            }

            return change;
        }));

        txtHanThe.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(change.getControlNewText().matches("^0\\d?+"))
                    return null;
                return change.getControlNewText().matches("\\d+") && change.getControlNewText().length() <= 4 ? change : null;
            }

            return change;
        }));

        txtXB.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(change.getControlNewText().matches("^0\\d?+"))
                    return null;
                return change.getControlNewText().matches("\\d+") && change.getControlNewText().length() <= 4 ? change : null;
            }

            return change;
        }));

        txtTienHu.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(change.getControlNewText().matches("^0\\d?+"))
                    return null;
                return change.getControlNewText().matches("\\d+") && change.getControlNewText().length() <= 4 ? change : null;
            }

            return change;
        }));

        txtTienPhat.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(change.getControlNewText().matches("^0\\d?+"))
                    return null;
                return change.getControlNewText().matches("\\d+") && change.getControlNewText().length() <= 4 ? change : null;
            }

            return change;
        }));

        txtTimeMuon.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(change.getControlNewText().matches("^0\\d?+"))
                    return null;
                return change.getControlNewText().matches("\\d+") && change.getControlNewText().length() <= 4 ? change : null;
            }

            return change;
        }));

        txtMaxMuon.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(change.getControlNewText().matches("^0\\d?+"))
                    return null;
                return change.getControlNewText().matches("\\d+") && change.getControlNewText().length() <= 4 ? change : null;
            }

            return change;
        }));
    }

    private void loadData() {
        thamSo = ThamSoService.getInstance().getThamSo();
        txtTienPhat.setText(thamSo.getTienPhat() + "");
        txtHanThe.setText(thamSo.getHanThe() + "");
        txtMaxMuon.setText(thamSo.getMaxSachMuon() + "");
        txtMaxTuoi.setText(thamSo.getMaxTuoi() + "");
        txtMinTuoi.setText(thamSo.getMinTuoi() + "");
        txtTienHu.setText(thamSo.getTienPhatSach() + "");
        txtTimeMuon.setText(thamSo.getHanMuon() + "");
        txtXB.setText(thamSo.getKhoangCachXB() + "");
    }

    public void btnCapNhat_Click(ActionEvent event) {
        if (txtXB.getText().equals("") ||
                txtTimeMuon.getText().equals("") ||
                txtTienHu.getText().equals("") ||
                txtMinTuoi.getText().equals("") ||
                txtMaxTuoi.getText().equals("") ||
                txtMaxMuon.getText().equals("") ||
                txtHanThe.getText().equals("") ||
                txtTienPhat.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Có dữ liệu trống. Vui lòng kiểm tra lại!");
            alert.showAndWait();
        }
        else
        {
            ThamSo temp = new ThamSo(Integer.parseInt(txtMaxTuoi.getText()),
                    Integer.parseInt(txtMinTuoi.getText()),
                    Integer.parseInt(txtHanThe.getText()),
                    Integer.parseInt(txtXB.getText()),
                    Integer.parseInt(txtMaxMuon.getText()),
                    Integer.parseInt(txtTimeMuon.getText()),
                    Integer.parseInt(txtTienPhat.getText()),
                    Integer.parseInt(txtTienHu.getText()));
            int rs = ThamSoService.getInstance().suaThamSo(temp);
            Util.showSuccess(rs, "Quản lý hệ thống", "Cập nhật quy định thành công!");
            loadData();
        }
    }
}
