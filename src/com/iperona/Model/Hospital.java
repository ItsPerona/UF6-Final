package com.iperona.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class Hospital {

    private final ObservableList<Pacient> llistaAlta = FXCollections.observableArrayList();
    private final ObservableList<Pacient> registres = FXCollections.observableArrayList();
    private final ObservableList<Urgencies> llistaServeis = FXCollections.observableArrayList();

    public Hospital() {
        llistaServeis.add(new Urgencies("Cirugia General"));
        llistaServeis.add(new Urgencies("Pediatria"));
        llistaServeis.add(new Urgencies("Traumatologia"));
        llistaServeis.add(new Urgencies("Cardiologia"));
        llistaServeis.add(new Urgencies("Dermatologia"));
        llistaServeis.add(new Urgencies("Neurologia"));
        llistaServeis.add(new Urgencies("Altres"));

        llistaAlta.add(new Pacient("Aida", "Barroso Dominguez", "11111111A", ResourceBundle.getBundle("args", Locale.getDefault()).getString("dona"), 23, llistaServeis.get(4), LocalDateTime.now()));
        llistaAlta.add(new Pacient("Pere", "Cliville Bazan", "22222222B", ResourceBundle.getBundle("args", Locale.getDefault()).getString("home"), 19, llistaServeis.get(5), LocalDateTime.now()));
        llistaAlta.add(new Pacient("Joan", "Hierro Mulet", "33333333C", ResourceBundle.getBundle("args", Locale.getDefault()).getString("home"), 36, llistaServeis.get(0), LocalDateTime.now()));
        llistaAlta.add(new Pacient("Lucia", "Muñoz Sanchez", "44444444D", ResourceBundle.getBundle("args", Locale.getDefault()).getString("dona"), 64, llistaServeis.get(1), LocalDateTime.now()));
        llistaAlta.add(new Pacient("Laura", "Cruz Garcia", "55555555E", ResourceBundle.getBundle("args", Locale.getDefault()).getString("dona"), 28, llistaServeis.get(3), LocalDateTime.now()));
        llistaAlta.add(new Pacient("Ismael", "Perona Martinez", "47842185T", ResourceBundle.getBundle("args", Locale.getDefault()).getString("home"), 19, llistaServeis.get(6), LocalDateTime.now()));
    }

    public ObservableList<Pacient> getLlistaAlta() {
        return llistaAlta;
    }

    public ObservableList<Pacient> getRegistres() {
        return registres;
    }

    public ObservableList<Urgencies> getLlistaServeis() {
        return llistaServeis;
    }
}
