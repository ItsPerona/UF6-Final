package com.iperona.Controlador;

import com.iperona.Main;
import com.iperona.Model.Hospital;
import com.iperona.Model.Pacient;
import com.iperona.Model.Urgencies;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller {

    // Objectes Generals
    @FXML private Hospital hospital;
    @FXML private ListView<Pacient> llistaPacients;
    @FXML private ListView<Pacient> registres;
    @FXML private Button exit;

    // Objectes de creació de Pacient
    @FXML private ProgressBar progress;
    @FXML private TextField nom;
    @FXML private TextField cognom;
    @FXML private TextField dni;
    @FXML private DatePicker edat;
    @FXML private RadioButton home;
    @FXML private RadioButton dona;
    @FXML private CheckBox terms;
    @FXML private ComboBox<Urgencies> servei;

    // Objectes d'informacio de pacients
    @FXML private Label campNom;
    @FXML private Label campCognom;
    @FXML private Label campDNI;
    @FXML private Label campEdat;
    @FXML private Label campSexe;
    @FXML private Label campUrgencia;


    /**
     * Els botóns obren noves finestres mitjançant altres .fxml
     * @param e un event
     * @throws IOException finestra d'alerta
     */
    public void onClick(Event e) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        switch (((Button) e.getSource()).getId()) {
            case "pacientsBtn":
                Controller controller = this;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Vista/gestorPacients.fxml"), ResourceBundle.getBundle("args", Locale.getDefault()));
                loader.setControllerFactory(c -> controller);
                Parent root = loader.load();

                llistaPacients.setItems(hospital.getLlistaAlta());

                Stage pacientsMenu = new Stage();
                pacientsMenu.getIcons().add(new Image(Main.class.getResourceAsStream("Vista/logo.png")));
                pacientsMenu.setTitle(ResourceBundle.getBundle("args", Locale.getDefault()).getString("hospital"));
                pacientsMenu.setScene(new Scene(root, 700, 430));
                pacientsMenu.setResizable(false);
                pacientsMenu.show();
                break;
            case "viewMore":
                if (llistaPacients.getSelectionModel().getSelectedItem() != null) {
                    Controller controller4 = this;

                    FXMLLoader loader4 = new FXMLLoader(getClass().getResource("../Vista/viewMore.fxml"), ResourceBundle.getBundle("args", Locale.getDefault()));
                    loader4.setControllerFactory(c -> controller4);
                    Parent root4 = loader4.load();

                    Pacient p = llistaPacients.getSelectionModel().getSelectedItem();

                    campNom.setText(p.getNom());
                    campCognom.setText(p.getCognom());
                    campDNI.setText(p.getDNI());
                    campEdat.setText(String.valueOf(p.getEdat()));
                    campSexe.setText(p.getSexe());
                    campUrgencia.setText(p.getUrgencia().getNomUrgencia());

                    Stage viewMore = new Stage();
                    viewMore.getIcons().add(new Image(Main.class.getResourceAsStream("Vista/logo.png")));
                    viewMore.setTitle(p.getCognom() + ", " + p.getNom());
                    viewMore.setScene(new Scene(root4, 550, 200));
                    viewMore.setResizable(false);
                    viewMore.show();
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText(ResourceBundle.getBundle("args", Locale.getDefault()).getString("errorPacient"));
                    alert.show();
                }
                break;
            case "altaPacients":
                Controller controller2 = this;

                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../Vista/altaPacients.fxml"), ResourceBundle.getBundle("args", Locale.getDefault()));
                loader2.setControllerFactory(c -> controller2);
                Parent root2 = loader2.load();

                Stage altaMenu = new Stage();
                altaMenu.getIcons().add(new Image(Main.class.getResourceAsStream("Vista/logo.png")));
                altaMenu.setTitle(ResourceBundle.getBundle("args", Locale.getDefault()).getString("hospital"));
                altaMenu.setScene(new Scene(root2, 650, 430));
                altaMenu.setResizable(false);
                altaMenu.show();
                servei.setItems(hospital.getLlistaServeis());
                break;
            case "baixaPacients":
                if (!llistaPacients.getItems().isEmpty()) {
                    if (llistaPacients.getSelectionModel().getSelectedItem() != null) {
                        Pacient temp = llistaPacients.getSelectionModel().getSelectedItem();
                        temp.setDataSortida(LocalDateTime.now());
                        hospital.getRegistres().add(temp);
                        hospital.getLlistaAlta().remove(llistaPacients.getSelectionModel().getSelectedItem());
                    } else {
                        Pacient temp = hospital.getLlistaAlta().get(0);
                        temp.setDataSortida(LocalDateTime.now());
                        hospital.getRegistres().add(temp);
                        hospital.getLlistaAlta().remove(0);
                    }
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText(ResourceBundle.getBundle("args", Locale.getDefault()).getString("empty"));
                    alert.show();
                }
                break;
            case "registresBtn":
                Controller controller3 = this;

                FXMLLoader loader3 = new FXMLLoader(getClass().getResource("../Vista/logsMenu.fxml"), ResourceBundle.getBundle("args", Locale.getDefault()));
                loader3.setControllerFactory(c -> controller3);
                Parent root3 = loader3.load();

                servei.setItems(hospital.getLlistaServeis());
                if (hospital.getRegistres() != null) {
                    registres.setItems(hospital.getRegistres());
                }
                servei.getSelectionModel().selectedItemProperty().addListener((observableValue, nothing, urgencia) -> mostraRegistreVoid(urgencia));

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
                    } else {
                        sexe = ResourceBundle.getBundle("args", Locale.getDefault()).getString("dona");
                    }
                    Period periode = Period.between(edatLocal, LocalDate.now());
                    edatInt = periode.getYears();
                    Pacient p = new Pacient(nom.getText(), cognom.getText(), dni.getText(), sexe, edatInt, servei.getValue(), LocalDateTime.now());
                    hospital.getLlistaAlta().add(p);
                    llistaPacients.setItems(hospital.getLlistaAlta());
                } else {
                    alert.setContentText(ResourceBundle.getBundle("args", Locale.getDefault()).getString("camps_buits"));
                    alert.show();
                }
                break;
        }
    }

    /**
     * Els Button amb ID 'exit' i event 'menus' es
     * tancarán quan siguin pulsats.
     * @param e un event
     */
    public void menus(Event e) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    /**
     * Crea una ListView temporal i insereix dins als pacients que
     * tinguin la urgencia seleccionada.
     * @param urgencia la urgencia seleccionada
     */
    private void mostraRegistreVoid(Urgencies urgencia) {
        registres.setItems(hospital.getRegistres());
        if (urgencia != null) {
            ObservableList<Pacient> temp = FXCollections.observableArrayList();
            for (int i = 0; i < hospital.getRegistres().size(); i++) {
                Pacient p = registres.getItems().get(i);
                if (p.getUrgencia().equals(urgencia)) {
                    temp.add(p);
                }
            }
            registres.setItems(temp);
        }
    }

    /**
     * Cada cop que s'actualitza un camp del menu "donar d'alta" fica la barra de progress a 0,
     * comprova quins requisits es cumpleixen i al final actualitza la barra de progress.
     * @param e un event
     */
    public void changePB(Event e) {
        // Vars
        boolean name = false, surname = false, dniBoolean = false, edad = false, sexe = false, comboboxUrg = false, termsBln = false;

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
        if (servei.getValue() != null) {
            comboboxUrg = true;
        }
        termsBln = terms.isSelected();


        // Actualitza la barra
        progress.setProgress(0);
        if (name) {
            progress.setProgress(progress.getProgress() + 0.15);
        }
        if (surname) {
            progress.setProgress(progress.getProgress() + 0.15);
        }
        if (dniBoolean) {
            progress.setProgress(progress.getProgress() + 0.15);
        }
        if (sexe) {
            progress.setProgress(progress.getProgress() + 0.15);
        }
        if (edad) {
            progress.setProgress(progress.getProgress() + 0.15);
        }
        if (comboboxUrg) {
            progress.setProgress(progress.getProgress() + 0.15);
        }
        if (termsBln) {
            progress.setProgress(progress.getProgress() + 0.1);
        }

        if (progress.getProgress() < 0.3) {
            progress.setStyle("-fx-accent: red");
        } else if (progress.getProgress() < 0.45) {
            progress.setStyle("-fx-accent: orange");
        } else if (progress.getProgress() < 0.65) {
            progress.setStyle("-fx-accent: yellow");
        } else if (progress.getProgress() < 0.85) {
            progress.setStyle("-fx-accent: lightgreen");
        } else {
            progress.setStyle("-fx-accent: green");
        }
    }

    public void Hospital(Hospital hospital) {
        this.hospital = hospital;
    }
}
