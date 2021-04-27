package com.iperona.Model;

public class Urgencies {

    private final String nomUrgencia;
    private final String especialista;

    public Urgencies(String urg, String especialista) {
        this.nomUrgencia = urg;
        this.especialista = especialista;
    }


    public String getNomUrgencia() {
        return nomUrgencia;
    }

    @Override
    public String toString() {
        return nomUrgencia;
    }

    public String getEspecialista() {
        return especialista;
    }
}
