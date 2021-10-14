package com.javafx.librarian.controller;

import com.javafx.librarian.model.Account;
import com.javafx.librarian.model.DocGia;
import com.javafx.librarian.model.LoaiDocGia;
import com.javafx.librarian.service.AccountService;
import com.javafx.librarian.service.DocGiaService;
import com.javafx.librarian.service.LoaiDocGiaService;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class LoginViewController implements Initializable {

    private double mousepX = 0;
    private double mousepY = 0;
    private Stage stage;
    private AnchorPane root;

    @FXML
    public TextField textFullName;
    @FXML
    public DatePicker dateBirthday;
    @FXML
    public ComboBox<String> cbbType;
    //region khai báo biến controls
    @FXML
    public AnchorPane layoutParent;
    @FXML
    public AnchorPane layersignup;
    @FXML
    public AnchorPane layer1;

    @FXML
    public JFXButton btnClose;
    @FXML
    public Label lbCreateAccount;
    @FXML
    public Label lbSignIn;
    @FXML
    public TextField textUserCreate;
    @FXML
    public TextField textEmail;
    @FXML
    public TextField passCreate;
    @FXML
    public JFXButton btnSignup;
    @FXML
    public JFXButton btnSignin;
    @FXML
    public TextField textUserLogin;
    @FXML
    public Label lbForget;
    @FXML
    public PasswordField passUserLogin;
    @FXML
    public AnchorPane layer2;
    @FXML
    public Label lbCreate1;
    @FXML
    public Label lbCreate2;
    @FXML
    public Label lbCreate3;
    @FXML
    public JFXButton btnSignInMove;
    @FXML
    public JFXButton btnSignupMove;
    @FXML
    public Label lbLogin1;
    @FXML
    public Label lbLogin2;
    @FXML
    public Label lbLogin3;
    @FXML
    public FontAwesomeIcon iconClose;
    //endregion

    ObservableList<LoaiDocGia> listLDG = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSignupMove.setVisible(false);
        lbCreateAccount.setVisible(false);
        textUserCreate.setVisible(false);
        textEmail.setVisible(false);
        passCreate.setVisible(false);
        btnSignup.setVisible(false);
        lbCreate1.setVisible(false);
        lbCreate2.setVisible(false);
        lbCreate3.setVisible(false);
        textFullName.setVisible(false);
        cbbType.setVisible(false);
        dateBirthday.setVisible(false);
        btnSignInMove.setVisible(false);
        textUserLogin.requestFocus();

        layer1.setOnMousePressed(mouseEvent -> {
            mousepX = mouseEvent.getSceneX();
            mousepY = mouseEvent.getSceneY();
        });

        layer1.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - mousepX);
            stage.setY(mouseEvent.getScreenY()- mousepY);
        });

        listLDG.addAll(LoaiDocGiaService.getInstance().getListLoaiDocGia());
    }

    @FXML
    public void btnSignIn_Click(ActionEvent event) {
        Account user = AccountService.getInstance().getUser(textUserLogin.getText(), passUserLogin.getText());
        if(user!=null){
            String tilte = "Sign In";
            String message = textUserLogin.getText();
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            tray.setAnimationType(type);
            tray.setTitle(tilte);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(2000));

            try {
                Stage stageMain = new Stage();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/MainView.fxml"));
                root = loader.load();

                MainViewController controller = loader.getController();
                controller.setMainStage(stageMain, user);

                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stageMain.setScene(scene);
                stageMain.initStyle(StageStyle.TRANSPARENT);
                stageMain.show();
                stage.hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            String tilte = "Sign In";
            String message = "Error Username/Password Wrong";
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            tray.setAnimationType(type);
            tray.setTitle(tilte);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.millis(2000));
        }
    }

    @FXML
    public void btnSignUpMove_Click(MouseEvent mouseEvent) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.7));
        slide.setNode(layer2);

        slide.setToX(491);
        slide.play();

        layer1.setTranslateX(-309);
        btnSignin.setVisible(false);
        btnSignup.setVisible(true);

        lbCreateAccount.setVisible(true);
        textUserCreate.setVisible(true);
        textUserCreate.requestFocus();
        textEmail.setVisible(true);
        passCreate.setVisible(true);
        lbCreate1.setVisible(true);
        lbCreate2.setVisible(true);
        lbCreate3.setVisible(true);
        textFullName.setVisible(true);
        cbbType.setVisible(true);
        dateBirthday.setVisible(true);
        btnSignInMove.setVisible(true);

        lbSignIn.setVisible(false);
        textUserLogin.setVisible(false);
        passUserLogin.setVisible(false);
        lbLogin1.setVisible(false);
        lbLogin2.setVisible(false);
        lbLogin3.setVisible(false);
        btnSignupMove.setVisible(false);
        lbForget.setVisible(false);


        slide.setOnFinished((e -> {
            System.out.println("Finish Right!");
        }));

        if(cbbType.getItems().size()==0){
            listLDG.forEach(ldg ->{
                cbbType.getItems().add(ldg.getTenLoaiDocGia());
            });
        }
    }

    @FXML
    public void btnSignInMove_Click(MouseEvent mouseEvent) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.millis(700));
        slide.setNode(layer2);

        slide.setToX(0);
        slide.play();

        layer1.setTranslateX(0);
        btnSignup.setVisible(false);
        btnSignin.setVisible(true);

        lbCreateAccount.setVisible(false);
        textUserCreate.setVisible(false);
        textEmail.setVisible(false);
        passCreate.setVisible(false);
        lbCreate1.setVisible(false);
        lbCreate2.setVisible(false);
        lbCreate3.setVisible(false);
        textFullName.setVisible(false);
        cbbType.setVisible(false);
        dateBirthday.setVisible(false);
        btnSignInMove.setVisible(false);

        lbSignIn.setVisible(true);
        textUserLogin.setVisible(true);
        textUserLogin.requestFocus();
        passUserLogin.setVisible(true);
        lbLogin1.setVisible(true);
        lbLogin2.setVisible(true);
        lbLogin3.setVisible(true);
        btnSignupMove.setVisible(true);
        lbForget.setVisible(true);

        slide.setOnFinished(e -> System.out.println("Finish Left!"));
    }

    public void closeClick(ActionEvent event) {
        btnClose.setStyle("-fx-background-color: red");
        System.exit(0);
    }

    @FXML
    public void btnSignUp_Click(ActionEvent event) {
        boolean check = AccountService.getInstance().checkCreateUser(textUserCreate.getText(), textEmail.getText());

        if(check){
            String tilte = "Sign Up";
            String message = "User: "+textUserCreate.getText() + " đã đăng kí thành công!!!";
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            tray.setAnimationType(type);
            tray.setTitle(tilte);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(2000));

            AccountService.getInstance().addUser(new Account(textUserCreate.getText(), passCreate.getText() ));

            DocGia dg = new DocGia();
            dg.setTenDocGia(textFullName.getText());
            dg.setMaLoaiDocGia(listLDG.stream().filter(ldg-> Objects.equals(ldg.getTenLoaiDocGia(), cbbType.getValue())).collect(Collectors.toList()).get(0).getMaLoaiDocGia());
            dg.setEmail(textEmail.getText());
            dg.setNgaySinh(Date.valueOf(dateBirthday.getValue()));
            dg.setNgayLapThe(Date.valueOf(LocalDate.now()));
            dg.setNgayHetHan(Date.valueOf((LocalDate.now()).plusMonths(6)));
            dg.setIdAccount(textUserCreate.getText());

            DocGiaService.getInstance().addDocGia(dg);
            textUserCreate.setText("");
            passCreate.setText("");
            textEmail.setText("");
            cbbType.setValue("");
            dateBirthday.setValue(null);
            textFullName.setText("");
            textUserCreate.requestFocus();
        }else{
            String tilte = "Sign Up";
            String message = "Lỗi trùng username/email. Xin vui lòng đăng ký lại!";
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;

            tray.setAnimationType(type);
            tray.setTitle(tilte);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.millis(2000));
        }
    }

    public void setMainStage(Stage stage){
        this.stage = stage;
    }

    public void btnCloseMouseEnter(MouseEvent mouseEvent) {
        btnClose.setStyle("-fx-background-color: red; -fx-background-radius: 15");
        iconClose.setVisible(true);
    }

    public void btnCloseMouseExit(MouseEvent mouseEvent) {
        btnClose.setStyle("-fx-background-color: #d6d6d6; -fx-background-radius: 15");
        iconClose.setVisible(false);
    }
}
