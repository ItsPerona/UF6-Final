package com.iperona.Controlador;

import com.iperona.Main;
import com.iperona.Model.Hospital;
import com.iperona.Model.Pacient;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private static Hospital hospital;
    @FXML private static ListView<Pacient> llistaPacients;
    @FXML private static ListView<Pacient> registres;
    @FXML private ProgressBar progress;
    @FXML private TextField nom;
    @FXML private TextField cognom;
    @FXML private TextField dni;
    @FXML private DatePicker edat;
    @FXML private RadioButton home;
    @FXML private RadioButton dona;
    @FXML private CheckBox terms;
    //@FXML private ComboBox<>

    public void Hospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public static void addDefaultPacients() {
        //Pacient p = new Pacient("Marc", "Cliville Bazan", "22222222B", "H", 18, LocalDateTime.now());
        //hospital.getLlistaAlta().add(p);
        //p = new Pacient("Aida", "Barroso Dominguez", "11111111A", "M", 26, LocalDateTime.now());
        //hospital.getLlistaAlta().add(p);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //llistaPacients.setItems(FXCollections.observableList(hospital.getLlistaAlta()));
    }

    public void onClick(Event e) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        switch (((Button) e.getSource()).getId()) {
            case "pacientsBtn":
                Controller controller = new Controller();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Vista/gestorPacients.fxml"), ResourceBundle.getBundle("args", Locale.getDefault()));
                loader.setControllerFactory(c -> controller);
                Parent root = loader.load();

                Stage pacientsMenu = new Stage();
                pacientsMenu.getIcons().add(new Image(Main.class.getResourceAsStream("Vista/logo.png")));
                pacientsMenu.setTitle(ResourceBundle.getBundle("args", Locale.getDefault()).getString("hospital"));
                pacientsMenu.setScene(new Scene(root, 700, 430));
                pacientsMenu.setResizable(false);
                pacientsMenu.show();
                break;
            case "altaPacients":
                Controller controller2 = new Controller();

                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../Vista/altaPacients.fxml"), ResourceBundle.getBundle("args", Locale.getDefault()));
                loader2.setControllerFactory(c -> controller2);
                Parent root2 = loader2.load();

                Stage altaMenu = new Stage();
                altaMenu.getIcons().add(new Image(Main.class.getResourceAsStream("Vista/logo.png")));
                altaMenu.setTitle(ResourceBundle.getBundle("args", Locale.getDefault()).getString("hospital"));
                altaMenu.setScene(new Scene(root2, 650, 430));
                altaMenu.setResizable(false);
                altaMenu.show();
                break;
            case "baixaPacients":

                break;
            case "registresBtn":
                Controller controller3 = new Controller();

                FXMLLoader loader3 = new FXMLLoader(getClass().getResource("../Vista/logsMenu.fxml"), ResourceBundle.getBundle("args", Locale.getDefault()));
                loader3.setControllerFactory(c -> controller3);
                Parent root3 = loader3.load();

                Stage logsMenu = new Stage();
                logsMenu.getIcons().add(new Image(Main.class.getResourceAsStream("Vista/logo.png")));
                logsMenu.setTitle(ResourceBundle.getBundle("args", Locale.getDefault()).getString("hospital"));
                logsMenu.setScene(new Scene(root3, 650, 430));
                logsMenu.setResizable(false);
                logsMenu.show();
                break;
            case "donarAlta":
                if (progress.getProgress() >= 1) {
                    String sexe;
                    LocalDate edatLocal = edat.getValue();
                    int edatInt;

                    if (home.isSelected()) {
                        sexe = ResourceBundle.getBundle("args", Locale.getDefault()).getString("home");
                    }
                    else {
                        sexe = ResourceBundle.getBundle("args", Locale.getDefault()).getString("dona");
                    }
                    Period periode = Period.between(edatLocal, LocalDate.now());
                    edatInt = periode.getYears();

                    Pacient p = new Pacient(nom.getText(), cognom.getText(), dni.getText(), sexe, edatInt, LocalDateTime.now());
                    hospital.getLlistaAlta().add(p);
                    llistaPacients.setItems(hospital.getLlistaAlta());
                } else {
                    alert.setContentText(ResourceBundle.getBundle("args", Locale.getDefault()).getString("camps_buits"));
                    alert.show();
                }
                break;
            default:
        }
    }

    /**
     * Cada cop que s'actualitza un camp del menu "donar d'alta" fica la barra de progress a 0,
     * comprova quins requisits es cumpleixen i al final actualitza la barra de progress.
     * @param e un event
     */
    public void changePB(Event e) {
        // Vars
        boolean name = false, surname = false, dniBoolean = false, edad = false, sexe = false, termsBln = false;

        // Comprovació dels camps
        name = !nom.getText().equalsIgnoreCase("");
        surname = !cognom.getText().equalsIgnoreCase("");
        dniBoolean = !dni.getText().equalsIgnoreCase("");
        if (home.isSelected() || dona.isSelected()) {
            sexe = true;
        }
        if (edat.getValue() != null) {
            edad = true;
        }
        termsBln = terms.isSelected();


        // Actualitza la barra
        progress.setProgress(0);
        if (name) {
            progress.setProgress(progress.getProgress() + 0.2);
        }
        if (surname) {
            progress.setProgress(progress.getProgress() + 0.2);
        }
        if (dniBoolean) {
            progress.setProgress(progress.getProgress() + 0.2);
        }
        if (sexe) {
            progress.setProgress(progress.getProgress() + 0.15);
        }
        if (edad) {
            progress.setProgress(progress.getProgress() + 0.15);
        }
        if (termsBln) {
            progress.setProgress(progress.getProgress() + 0.1);
        }
    }
}
