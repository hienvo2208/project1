package com.javafx.librarian.controller;

import com.javafx.librarian.model.Sach;
import com.javafx.librarian.model.TacGia;
import com.javafx.librarian.model.ThamSo;
import com.javafx.librarian.model.TheLoai;
import com.javafx.librarian.service.SachService;
import com.javafx.librarian.service.TacGiaService;
import com.javafx.librarian.service.ThamSoService;
import com.javafx.librarian.service.TheLoaiService;
import com.javafx.librarian.utils.Util;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddSachController implements Initializable {
    private SachController sachController;
    @FXML
    public TextField txtSoLuong;
    @FXML
    public Pane panelThemSach;
    @FXML
    public TextField txtMaSach;
    @FXML
    public TextField txtTenSach;
    @FXML
    public ComboBox cbTheLoai;
    @FXML
    public ComboBox cbTacGia;
    @FXML
    public TextField txtNXB;
    @FXML
    public TextField txtNamXB;
    @FXML
    public DatePicker dtNgayNhap;
    @FXML
    public TextField txtTriGia;
    @FXML
    public RadioButton rdbTrong;
    @FXML
    public RadioButton rdbDangMuon;
    @FXML
    public Button btnThem;
    @FXML
    public Button btnHuy;
    @FXML
    public Button btnChonAnh;
    @FXML
    public ImageView imgPreview;
    @FXML
    public JFXButton btnClose;
    @FXML
    public FontAwesomeIcon iconClose;
    private double mousepX = 0;
    private double mousepY = 0;

    private ObservableList<TheLoai> listTheLoai;
    private ObservableList<TacGia> listTacGia;
    File anhBia;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panelThemSach.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        panelThemSach.setOnMouseDragged(mouseEvent -> {
            panelThemSach.getScene().getWindow().setX(mouseEvent.getScreenX() - mousepX);
            panelThemSach.getScene().getWindow().setY(mouseEvent.getScreenY() - mousepY);
        });

        rdbTrong.setSelected(true);
        txtMaSach.setText(Util.generateID(Util.PREFIX_CODE.S));
        txtMaSach.setDisable(true);
        listTheLoai = FXCollections.observableArrayList(TheLoaiService.getInstance().getAllTheLoai());
        cbTheLoai.setTooltip(new Tooltip());
        cbTheLoai.setItems(listTheLoai);
        cbTheLoai.getSelectionModel().selectFirst();

        listTacGia = FXCollections.observableArrayList(TacGiaService.getInstance().getAllTacGia());
        cbTacGia.setTooltip(new Tooltip());
        cbTacGia.setItems(listTacGia);
        cbTacGia.getSelectionModel().selectFirst();
        dtNgayNhap.setValue(LocalDate.now());

//        txtNamXB.setTextFormatter(new TextFormatter<Integer>(change -> {
//            if (!change.getControlNewText().isEmpty()) {
//                if(change.getControlNewText().matches("^0\\d?+"))
//                    return null;
//                return change.getControlNewText().matches("\\d+") && change.getControlNewText().length() <= 4 ? change : null;
//            }
//
//            return change;
//        }));
//
//        txtTriGia.setTextFormatter(new TextFormatter<Integer>(change -> {
//            if (!change.getControlNewText().isEmpty()) {
//                if(!change.getControlNewText().matches("\\d+"))
//                    return null;
//            }
//
//            return change;
//        }));

        new AutoCompleteComboBoxListener<>(cbTheLoai);
        new AutoCompleteComboBoxListener<>(cbTacGia);

        ThamSo ts = ThamSoService.getInstance().getThamSo();
        imgPreview.setImage(new Image(ts.getAnhMacDinh()));

    }

    public void setSachController(SachController sach) {
        this.sachController = sach;
    }

    public void btnHuy_Click(ActionEvent event) {
        Stage stage = (Stage) btnHuy.getScene().getWindow();
        stage.close();
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

    public void btnThem_Click(ActionEvent event) throws FileNotFoundException {
        //VALIDATE
        int soLuong;
        if(txtTenSach.getText().trim().equals("") ||
                txtNamXB.getText().trim().equals("") ||
                txtNXB.getText().toString().trim().equals("") ||
                txtTriGia.getText().trim().equals("")
        ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Vui lòng nhập đầy đủ dữ liệu!");
            alert.showAndWait();
            return;
        }

//        ThamSo thamSo = ThamSoService.getInstance().getThamSo();
        int namXB = Integer.parseInt(txtNamXB.getText());
        if (/*LocalDate.now().getYear() - namXB <= thamSo.getKhoangCachXB()*/true) {
            String maSach = txtMaSach.getText();
            String tenSach = txtTenSach.getText();
            String NXB = txtNXB.getText();
            soLuong = Integer.parseInt(txtSoLuong.getText());
            Date ngayNhap = Date.valueOf(dtNgayNhap.getValue());
            int triGia = Integer.parseInt(txtTriGia.getText());
            String maTheLoai = (cbTheLoai.getSelectionModel().getSelectedItem().toString().split(" - "))[0].trim();
            String maTacGia = (cbTacGia.getSelectionModel().getSelectedItem().toString().split(" - "))[0].trim();
            int tinhTrang = rdbTrong.isSelected() ? 0 : 1;

            FileInputStream anhBiaBlob = null;

            if (anhBia == null) {
                anhBiaBlob = ThamSoService.getInstance().getThamSo().getAnhMacDinh();
            } else {
                anhBiaBlob = new FileInputStream(anhBia);
            }


            Sach sach = new Sach(maSach, tenSach, maTheLoai, maTacGia, namXB, NXB, ngayNhap, triGia, tinhTrang, anhBiaBlob, soLuong);

            int rs = SachService.getInstance().addSach(sach);
            sachController.refreshTable();
            txtMaSach.setText(Util.generateID(Util.PREFIX_CODE.S));
            txtTenSach.setText("");
            cbTheLoai.getSelectionModel().selectFirst();
            cbTacGia.getSelectionModel().selectFirst();
            txtNXB.setText("");
            txtNamXB.setText("");
            dtNgayNhap.setValue(LocalDate.now());
            rdbTrong.setSelected(true);
            rdbDangMuon.setSelected(false);
            txtTriGia.setText("");
            Util.showSuccess(rs, "Quản lý sách", "Thêm sách thành công!");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            //alert.setHeaderText("Sách quá cũ. Chỉ nhận sách trong vòng " + thamSo.getKhoangCachXB() + " năm trở lại đây!");
            alert.showAndWait();
        }
    }

    public void btnChonAnh_Click(ActionEvent actionEvent) throws FileNotFoundException {
        //
        FileChooser imgChooser = new FileChooser();
        Stage stage = (Stage) btnHuy.getScene().getWindow();
        anhBia = imgChooser.showOpenDialog(stage);
        //
        imgPreview.setImage(new Image(new FileInputStream(anhBia.getAbsolutePath())));
        //
    }
}
