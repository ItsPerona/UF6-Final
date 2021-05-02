package com.iperona.Model;

public class Urgencies {

    private final String nomUrgencia;
    private final String DNIEspecialista;

    public Urgencies(String urg, String DNIEspecialista) {
        this.nomUrgencia = urg;
        this.DNIEspecialista = DNIEspecialista;
    }


    public String getNomUrgencia() {
        return nomUrgencia;
    }

    @Override
    public String toString() {
        return nomUrgencia;
    }

    public String getEspecialista() {
        return DNIEspecialista;
    }
}
