/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Главное меню");
        primaryStage.setMinHeight(350);
        primaryStage.setMinWidth(440);
        primaryStage.setScene(new Scene(root, 440, 350));
        primaryStage.show();
        System.setProperty("org.bytedeco.javacpp.maxphysicalbytes", "0");
        System.setProperty("org.bytedeco.javacpp.maxbytes", "0");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
