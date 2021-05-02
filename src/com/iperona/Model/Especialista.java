package com.iperona.Model;

public class Especialista {

    private final String nom;
    private final String DNI;

    public Especialista(String nom, String DNI) {
        this.nom = nom;
        this.DNI = DNI;
    }

    public String getNom() {
        return nom;
    }

    public String getDNI() {
        return DNI;
    }

    @Override
    public String toString() {
        return "[" + DNI + "] " + nom;
    }
}
