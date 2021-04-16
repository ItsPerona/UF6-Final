package com.iperona.Model;

public class Urgencies {

    private final String nomUrgencia;

    public Urgencies(String urg) {
        this.nomUrgencia = urg;
    }


    public String getNomUrgencia() {
        return nomUrgencia;
    }

    @Override
    public String toString() {
        return nomUrgencia;
    }
}
