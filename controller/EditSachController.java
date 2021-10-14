package com.javafx.librarian.controller;

import com.javafx.librarian.model.Sach;
import com.javafx.librarian.model.TacGia;
import com.javafx.librarian.model.TheLoai;
import com.javafx.librarian.service.SachService;
import com.javafx.librarian.service.TacGiaService;
import com.javafx.librarian.service.TheLoaiService;
import com.javafx.librarian.utils.Util;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EditSachController implements Initializable {
    private SachController sachController;
    private Sach sach;
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
    public RadioButton rdbMat;
    @FXML
    public RadioButton rdbHuHong;
    @FXML
    public Button btnLuu;
    @FXML
    public Button btnHuy;
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

        txtMaSach.setDisable(true);
        listTheLoai = FXCollections.observableArrayList(TheLoaiService.getInstance().getAllTheLoai());
        cbTheLoai.setTooltip(new Tooltip());
        cbTheLoai.setItems(listTheLoai);
        cbTheLoai.getSelectionModel().selectFirst();

        listTacGia = FXCollections.observableArrayList(TacGiaService.getInstance().getAllTacGia());
        cbTacGia.setTooltip(new Tooltip());
        cbTacGia.setItems(listTacGia);
        cbTacGia.getSelectionModel().selectFirst();

        txtNamXB.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(change.getControlNewText().matches("^0\\d?+"))
                    return null;
                return change.getControlNewText().matches("\\d+") && change.getControlNewText().length() <= 4 ? change : null;
            }

            return change;
        }));

        txtTriGia.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (!change.getControlNewText().isEmpty()) {
                if(!change.getControlNewText().matches("\\d+"))
                    return null;
            }

            return change;
        }));

        new AutoCompleteComboBoxListener<>(cbTheLoai);
        new AutoCompleteComboBoxListener<>(cbTacGia);

        rdbTrong.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    rdbDangMuon.setSelected(false);
                    rdbHuHong.setSelected(false);
                    rdbMat.setSelected(false);
                }
            }
        });

        rdbDangMuon.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    rdbTrong.setSelected(false);
                    rdbHuHong.setSelected(false);
                    rdbMat.setSelected(false);
                }
            }
        });

        rdbHuHong.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    rdbDangMuon.setSelected(false);
                    rdbTrong.setSelected(false);
                    rdbMat.setSelected(false);
                }
            }
        });

        rdbMat.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    rdbDangMuon.setSelected(false);
                    rdbHuHong.setSelected(false);
                    rdbTrong.setSelected(false);
                }
            }
        });
    }

    public void setSachController(SachController sach) {
        this.sachController = sach;
    }

    public void setEditSach(Sach sach) {
        this.sach = sach;
        bindingData();
    }
    private void bindingData() {
        txtMaSach.setText(String.valueOf(sach.getMaSach()));
        txtTenSach.setText(sach.getTenSach());
        txtNXB.setText(sach.getNXB());
        txtNamXB.setText(String.valueOf(sach.getNamXB()));
        if (sach.getTinhTrang() == "Trống")
            rdbTrong.setSelected(true);
        else if(sach.getTinhTrang().equals("Đang mượn"))
            rdbDangMuon.setSelected(true);
        else if(sach.getTinhTrang().equals("Mất"))
            rdbMat.setSelected(true);
        else
            rdbHuHong.setSelected(true);
        dtNgayNhap.setValue(Util.convertDateToLocalDateUI(sach.getNgayNhap()));
        txtTriGia.setText(String.valueOf(sach.getTriGia()));
        TheLoai theLoai = TheLoaiService.getInstance().getTheLoaiByID(sach.getMaTheLoai());
        TacGia tacGia = TacGiaService.getInstance().getTacGiaByID(sach.getMaTacGia());
        System.out.println(cbTheLoai.getItems());
        System.out.println(theLoai.toString());
        for(int i = 0; i < cbTheLoai.getItems().size(); i++)
        {
            if(cbTheLoai.getItems().get(i).toString().equals(theLoai.toString()))
            {
                cbTheLoai.getSelectionModel().select(i);
                break;
            }
        }
        for(int i = 0; i < cbTacGia.getItems().size(); i++)
        {
            if(cbTacGia.getItems().get(i).toString().equals(tacGia.toString()))
            {
                cbTacGia.getSelectionModel().select(i);
                break;
            }
        }

        imgPreview.setImage(sach.getImage());
        imgPreview.setCache(true);
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

    public void btnLuu_Click(ActionEvent event) throws FileNotFoundException {
        //VALIDATE
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
        //
        int soLuong = Integer.parseInt(txtSoLuong.getText());

        String maSach = txtMaSach.getText();
        String tenSach = txtTenSach.getText();
        String NXB = txtNXB.getText();
        int namXB = Integer.parseInt(txtNamXB.getText());
        Date ngayNhap = Date.valueOf(dtNgayNhap.getValue());
        int triGia = Integer.parseInt(txtTriGia.getText());
        String maTheLoai = (cbTheLoai.getSelectionModel().getSelectedItem().toString().split(" - "))[0].trim();
        String maTacGia = (cbTacGia.getSelectionModel().getSelectedItem().toString().split(" - "))[0].trim();
        int tinhTrang = 0;
        if(rdbHuHong.isSelected())
            tinhTrang = 2;
        else if(rdbDangMuon.isSelected())
            tinhTrang = 1;
        else if(rdbMat.isSelected())
            tinhTrang = 3;
        FileInputStream anhBiaBlob = null;
        if(anhBia == null)
            anhBiaBlob = sach.getAnhBia();
        else
            anhBiaBlob = new FileInputStream(anhBia);

        Sach sach = new Sach(maSach, tenSach, maTheLoai, maTacGia, namXB, NXB, ngayNhap, triGia, tinhTrang, anhBiaBlob,soLuong );

        int rs = SachService.getInstance().editSach(sach);
        Util.showSuccess(rs, "Quản lý sách", "Sửa sách thành công!");
        sachController.refreshTable();
        txtTenSach.setText("");
        cbTheLoai.getSelectionModel().selectFirst();
        cbTacGia.getSelectionModel().selectFirst();
        txtNXB.setText("");
        txtNamXB.setText("");
        dtNgayNhap.setValue(LocalDate.now());
        rdbTrong.setSelected(false);
        rdbDangMuon.setSelected(false);
        txtTriGia.setText("");
        Stage stage = (Stage) btnHuy.getScene().getWindow();
        stage.close();
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
