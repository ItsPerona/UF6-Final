package com.iperona.Controlador;

import com.iperona.Main;
import com.iperona.Model.Especialista;
import com.iperona.Model.Hospital;
import com.iperona.Model.Pacient;
import com.iperona.Model.Urgencies;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    // Objectes dels Especialistes
    @FXML private TextField nomEsp;
    @FXML private TextField dniEsp;
    @FXML private ComboBox<Especialista> llistaEspecialistes;

    // Objectes de les urgencies
    @FXML private TextField nomUrg;
    @FXML private ComboBox<Especialista> llistaDisponibles;


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
                pacientsMenu.setScene(new Scene(root, 700, 430));
                loadStage(pacientsMenu);
                break;
            case "viewMore":
                if (llistaPacients.getSelectionModel().getSelectedItem() != null) {
                    Controller controller4 = this;

                    FXMLLoader loader4 = new FXMLLoader(getClass().getResource("../Vista/viewMore.fxml"), ResourceBundle.getBundle("args", Locale.getDefault()));
                    loader4.setControllerFactory(c -> controller4);
                    Parent root4 = loader4.load();

                    Pacient p = llistaPacients.getSelectionModel().getSelectedItem();

                    // Introdueix als Labels els atributs del Pacient
                    campNom.setText(p.getNom());
                    campCognom.setText(p.getCognom());
                    campDNI.setText(p.getDNI());
                    campEdat.setText(String.valueOf(p.getEdat()));
                    campSexe.setText(p.getSexe());
                    campUrgencia.setText(p.getUrgencia().getNomUrgencia());

                    Stage viewMore = new Stage();
                    viewMore.setScene(new Scene(root4, 550, 200));
                    loadStage(viewMore);
                    viewMore.setTitle(p.getCognom() + ", " + p.getNom());
                } else {
                    // Si no hi ha cap pacient seleccionat
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
                altaMenu.setScene(new Scene(root2, 650, 430));
                loadStage(altaMenu);
                servei.setItems(hospital.getLlistaServeis());
                break;
            case "baixaPacients":
                // Si existeixen pacients
                if (!llistaPacients.getItems().isEmpty()) {
                    // Si hi ha un pacient seleccionat
                    if (llistaPacients.getSelectionModel().getSelectedItem() != null) {
                        Pacient temp = llistaPacients.getSelectionModel().getSelectedItem();
                        // Actualitza la hora de sortida
                        temp.setDataSortida(LocalDateTime.now());
                        hospital.getRegistres().add(temp);
                        hospital.getLlistaAlta().remove(llistaPacients.getSelectionModel().getSelectedItem());
                        hospital.baixaPacient(temp);
                    } else {
                        // Si no hi ha cap seleccionat, selecciona automaticament el primer
                        Pacient temp = hospital.getLlistaAlta().get(0);
                        temp.setDataSortida(LocalDateTime.now());
                        hospital.getRegistres().add(temp);
                        hospital.getLlistaAlta().remove(0);
                        hospital.baixaPacient(temp);
                    }
                } else {
                    // Si no existeixen pacients
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

                // Omple la combobox de serveis
                servei.setItems(hospital.getLlistaServeis());
                // Si existeixen registres carregals
                if (hospital.getRegistres() != null) {
                    registres.setItems(hospital.getRegistres());
                }
                // Si es selecciona un objecte de la combobox executa el mètode mostraRegistreVoid
                servei.getSelectionModel().selectedItemProperty().addListener((observableValue, nothing, urgencia) -> mostraRegistreVoid(urgencia));

                Stage logsMenu = new Stage();
                logsMenu.setScene(new Scene(root3, 650, 430));
                loadStage(logsMenu);
                break;
            case "gUrgencies":
                Controller e1 = this;

                FXMLLoader l1 = new FXMLLoader(getClass().getResource("../Vista/gestorUrg.fxml"), ResourceBundle.getBundle("args", Locale.getDefault()));
                l1.setControllerFactory(c -> e1);
                Parent r1 = l1.load();

                // Nomes es podrá crear una urgencia si existeix un especialista que no tingui cap urgencia ja assignada
                boolean done = false;
                for (int i = 0; i < hospital.getLlistaServeis().size(); i++) {
                    for (int j = 0; j < hospital.getLlistaEspecialistes().size(); j++) {
                        if (!hospital.getLlistaServeis().get(i).getEspecialista().equals(hospital.getLlistaEspecialistes().get(j).getDNI()) && !done) {
                            llistaDisponibles.getItems().add(hospital.getLlistaEspecialistes().get(j));
                            done = true;
                        }
                    }
                }

                // Boolean per veure si hi han especialistes disponibles
                boolean disp = true;
                if (llistaDisponibles.getItems().size() == 0) {
                    disp = false;
                }

                Stage menuUrg = new Stage();
                menuUrg.setScene(new Scene(r1, 350, 400));
                loadStage(menuUrg);
                // Alerta al obrir el menu si no hi han especialistes disponibles
                if (!disp) {
                    alert.setContentText("Alerta: No hi han Especialistes Disponibles");
                    alert.show();
                }
                break;
            case "gEspecialistes":
                Controller e2 = this;

                FXMLLoader l2 = new FXMLLoader(getClass().getResource("../Vista/gestorEsp.fxml"), ResourceBundle.getBundle("args", Locale.getDefault()));
                l2.setControllerFactory(c -> e2);
                Parent r2 = l2.load();

                llistaEspecialistes.setItems(hospital.getLlistaEspecialistes());

                Stage menuEsp = new Stage();
                menuEsp.setScene(new Scene(r2, 650, 250));
                loadStage(menuEsp);
                break;
            case "donarAlta":
                // Si tots els camps están complerts:
                if (progress.getProgress() >= 1) {
                    String sexe;
                    LocalDate edatLocal = edat.getValue();
                    int edatInt; boolean existeix = false;

                    // Pasa el sexe del Pacient a String depenent del idioma
                    if (home.isSelected()) {
                        sexe = ResourceBundle.getBundle("args", Locale.getDefault()).getString("home");
                    } else {
                        sexe = ResourceBundle.getBundle("args", Locale.getDefault()).getString("dona");
                    }
                    // Pasa la data de naixement a la edat actual
                    Period periode = Period.between(edatLocal, LocalDate.now());
                    edatInt = periode.getYears();
                    Pacient p = new Pacient(nom.getText(), cognom.getText(), dni.getText(), sexe, edatInt, servei.getValue(), LocalDateTime.now());

                    // Comprova que no existeixi ja aquest pacient (mateix dni)
                    for (int i = 0; i < hospital.getLlistaAlta().size(); i++) {
                        if (hospital.getLlistaAlta().get(i).getDNI().equals(p.getDNI())) {
                            existeix = true;
                            alert.setContentText("Ja hi ha un pacient a l'hospital amb aquest DNI");
                            alert.show();
                        }
                    }
                    if (!existeix) { // Si no existeix afegeix-lo a la basedades
                        hospital.getLlistaAlta().add(p);
                        llistaPacients.setItems(hospital.getLlistaAlta());
                        hospital.altaPacient(p);
                    }
                } else {
                    alert.setContentText(ResourceBundle.getBundle("args", Locale.getDefault()).getString("camps_buits"));
                    alert.show();
                }
                break;
            case "donarAltaEsp":
                boolean existent = false;
                // Comprova que els camps no estàn buits
                if (!nomEsp.getText().equals("") && !dniEsp.getText().equals("")) {
                    // Comprova que no existeix un Especialista amb el mateix DNI
                    for (int i = 0; i < hospital.getLlistaEspecialistes().size(); i++) {
                        if (hospital.getLlistaEspecialistes().get(i).getDNI().equals(dniEsp.getText())) {
                            existent = true;
                        }
                    }
                    // Si no existeix, crea-ho
                    if (!existent) {
                        Especialista esp = new Especialista(nomEsp.getText(), dniEsp.getText());
                        hospital.getLlistaEspecialistes().add(esp);
                        hospital.altaEspecialista(esp);
                    }
                }
                break;
            case "bajaEsp":
                // Dona de baixa el Especialista seleccionat
                if (llistaEspecialistes.getSelectionModel().getSelectedItem() != null) {
                    Especialista esp = llistaEspecialistes.getSelectionModel().getSelectedItem();
                    hospital.getLlistaEspecialistes().remove(esp);
                    hospital.baixaEspecialista(esp);
                }
                break;
            default:
                existent = false;
                // Comprova que els camps no son buits
                if (llistaDisponibles.getSelectionModel().getSelectedItem() != null && !nomUrg.getText().equals("")) {
                    // Comprova que no existeixi una urgencia amb el mateix nom
                    for (int i = 0; i < hospital.getLlistaServeis().size(); i++) {
                        if (hospital.getLlistaServeis().get(i).getNomUrgencia().equals(nomUrg.getText())) {
                            existent = true;
                        }
                    }
                    // Si no existeix, crea-ho
                    if (!existent) {
                        Urgencies novaUrgencia = new Urgencies(nomUrg.getText(), llistaDisponibles.getSelectionModel().getSelectedItem().getDNI());
                        hospital.getLlistaServeis().add(novaUrgencia);
                        hospital.addUrgencia(novaUrgencia);
                    }
                }
        }
    }

    /**
     * Parametres per defecte de tots els Stages (Titol, Icona...)
     * @param stage el stage a crear
     */

    private void loadStage(Stage stage) {
        // Parametres per defecte de tots els Stages
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("Vista/logo.png")));
        stage.setTitle(ResourceBundle.getBundle("args", Locale.getDefault()).getString("hospital"));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Els Button amb ID 'exit' i event 'menus' es
     * tancarán quan siguin pulsats.
     * @param e un event
     */
    public void menus(Event e) {
        // Sortir del stage actual ( on s'ha clicat el botó)
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    /**
     * Crea una ListView temporal i insereix dins als pacients que
     * tinguin la urgencia seleccionada.
     * @param urgencia la urgencia seleccionada
     */
    private void mostraRegistreVoid(Urgencies urgencia) {
        // Introdueix tots els pacients a la ListView
        registres.setItems(hospital.getRegistres());
        // Si hi ha una urgencia seleccionada:
        if (urgencia != null) {
            // Crea una ObservableList temporal
            ObservableList<Pacient> temp = FXCollections.observableArrayList();
            // Comrpova els pacients que tenen la urgencia seleccionada
            for (int i = 0; i < hospital.getRegistres().size(); i++) {
                Pacient p = registres.getItems().get(i);
                // Introdueix-los a la llista temporal
                if (p.getUrgencia().equals(urgencia)) {
                    temp.add(p);
                }
            }
            // Actualitza la ListView amb els Pacients que tenen la urgencia seleccionada
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

        // Canvia el color de la progress bar depenent del progress
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