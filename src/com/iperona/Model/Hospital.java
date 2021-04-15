package com.iperona.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Hospital {

    private final ObservableList<Pacient> llistaAlta = FXCollections.observableArrayList();
    private final ObservableList<Pacient> registres = FXCollections.observableArrayList();

    public ObservableList<Pacient> getLlistaAlta() {
        return llistaAlta;
    }

    public ObservableList<Pacient> getRegistres() {
        return registres;
    }
}
