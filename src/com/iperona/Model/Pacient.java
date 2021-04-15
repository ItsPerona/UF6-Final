package com.iperona.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pacient {

    private final String nom;
    private final String cognom;
    private final String DNI;
    private final String sexe;
    private final int edat;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSortida;

    public Pacient(String nom, String cognom, String DNI, String sexe, int edat, LocalDateTime dataEntrada) {
        this.nom = nom;
        this.cognom = cognom;
        this.DNI = DNI;
        this.sexe = sexe;
        this.edat = edat;
        this.dataEntrada = dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setDataSortida(LocalDateTime dataSortida) {
        this.dataSortida = dataSortida;
    }

    @Override
    public String toString() {
        if (dataSortida != null) {
            return nom + ", " + cognom + ", " + DNI + ", " + sexe + ", " + edat + ", " + dataEntrada.format(DateTimeFormatter.ofPattern("dd/MM HH:mm:ss"));
        } else {
            return cognom + ", " + nom + ", " + DNI + ", " + sexe + ", " + edat + ", " + dataEntrada.format(DateTimeFormatter.ofPattern("dd/MM HH:mm:ss")) + ", " + dataSortida.format(DateTimeFormatter.ofPattern("dd/MM HH:mm:ss"));
        }
    }
}
