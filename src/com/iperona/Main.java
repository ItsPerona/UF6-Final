package com.iperona;

import com.iperona.Controlador.Controller;
import com.iperona.Model.Hospital;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Projecte M3 UF5 - Hospital
 * @author ItsPerona (Ismael Perona)
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Controller controller = new Controller();
        controller.Hospital(new Hospital());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista/inici.fxml"), ResourceBundle.getBundle("args", Locale.getDefault()));
        loader.setControllerFactory(c -> controller);
        Parent root = loader.load();

        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("Vista/logo.png")));
        primaryStage.setTitle(ResourceBundle.getBundle("args", Locale.getDefault()).getString("hospital"));
        primaryStage.setScene(new Scene(root, 650, 430));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
