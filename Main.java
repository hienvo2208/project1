package com.javafx.librarian;

import com.javafx.librarian.controller.*;
//import com.javafx.librarian.controller.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        initRootLayout();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/login/LoginView.fxml"));
          // loader.setLocation(getClass().getResource("view/MainView.fxml"));
            rootLayout = loader.load();

         //   LoginViewController controller = loader.getController();
           // controller.setMainStage(primaryStage);

         // MainViewController controller = loader.getController();
          //  controller.setMainStage(primaryStage);

            Scene scene = new Scene(rootLayout);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}