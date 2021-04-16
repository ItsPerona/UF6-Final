package com.iperona.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pacient {

    private final String nom;
    private final String cognom;
    private final String DNI;
    private final String sexe;
    private final int edat;
    private final Urgencies urgencia;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSortida;

    public Pacient(String nom, String cognom, String DNI, String sexe, int edat, Urgencies urgencia, LocalDateTime dataEntrada) {
        this.nom = nom;
        this.cognom = cognom;
        this.DNI = DNI;
        this.sexe = sexe;
        this.edat = edat;
        this.urgencia = urgencia;
        this.dataEntrada = dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setDataSortida(LocalDateTime dataSortida) {
        this.dataSortida = dataSortida;
    }

    public Urgencies getUrgencia() {
        return urgencia;
    }

    public String getNom() {
        return nom;
    }

    public String getCognom() {
        return cognom;
    }

    public String getDNI() {
        return DNI;
    }

    public String getSexe() {
        return sexe;
    }

    public int getEdat() {
        return edat;
    }

    @Override
    public String toString() {
        if (dataSortida == null) {
            return nom + " " + cognom + ", " + DNI + ", " + sexe + ", " + edat + ", " + urgencia + ", " + dataEntrada.format(DateTimeFormatter.ofPattern("dd/MM HH:mm:ss"));
        } else {
            return cognom + " " + nom + ", " + DNI + ", " + sexe + ", " + edat + ", " + urgencia + ", " + dataEntrada.format(DateTimeFormatter.ofPattern("dd/MM HH:mm:ss")) + ", " + dataSortida.format(DateTimeFormatter.ofPattern("dd/MM HH:mm:ss"));
        }
    }
}
