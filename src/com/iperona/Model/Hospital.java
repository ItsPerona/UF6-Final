package com.iperona.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hospital {

    private final ObservableList<Pacient> llistaAlta = FXCollections.observableArrayList();
    private final ObservableList<Pacient> registres = FXCollections.observableArrayList();
    private final ObservableList<Urgencies> llistaServeis = FXCollections.observableArrayList();
    private final ObservableList<Especialista> llistaEspecialistes = FXCollections.observableArrayList();

    public Hospital() {
        loadUrgencies();
        loadEspecialistes();
        loadPacients();
    }

    private void loadPacients() {
        Connection connection = new DataBase().start();
        try {
            Statement ordre = connection.createStatement();
            ResultSet resultSet =  ordre.executeQuery("SELECT * FROM HOSPITAL.PACIENTS");
            while (resultSet.next()) {
                Urgencies urg = null;

                for (Urgencies llistaServei : llistaServeis) {
                    if (llistaServei.getNomUrgencia().equals(resultSet.getString(6))) {
                        urg = llistaServei;
                    }
                }

                if (resultSet.getString(8) != null) {
                    registres.add(
                            new Pacient(resultSet.getString(1)
                                    , resultSet.getString(2)
                                    , resultSet.getString(3)
                                    , resultSet.getString(4)
                                    , resultSet.getInt(5)
                                    , urg
                                    , LocalDateTime.parse(resultSet.getString(7), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                                    , LocalDateTime.parse(resultSet.getString(8), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                            ));
                } else {
                    llistaAlta.add(
                            new Pacient(resultSet.getString(1)
                                    , resultSet.getString(2)
                                    , resultSet.getString(3)
                                    , resultSet.getString(4)
                                    , resultSet.getInt(5)
                                    , urg
                                    , LocalDateTime.parse(resultSet.getString(7), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                            ));
                }
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadUrgencies() {
        Connection connection = new DataBase().start();
        try {
            Statement ordre = connection.createStatement();
            ResultSet resultSet =  ordre.executeQuery("SELECT * FROM HOSPITAL.URGENCIES");
            while (resultSet.next()) {
                llistaServeis.add(
                        new Urgencies(resultSet.getString(1)
                                , resultSet.getString(2)
                        ));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadEspecialistes() {
        Connection connection = new DataBase().start();
        try {
            Statement ordre = connection.createStatement();
            ResultSet resultSet =  ordre.executeQuery("SELECT * FROM HOSPITAL.ESPECIALISTES");
            while (resultSet.next()) {

                llistaEspecialistes.add(
                        new Especialista(resultSet.getString(1)
                                , resultSet.getString(2)
                        ));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean altaPacient(Pacient p){
        boolean ok = false;
        Connection connection = new DataBase().start();
        try {
            PreparedStatement ordre = connection.prepareStatement("INSERT INTO HOSPITAL.PACIENTS VALUES (?,?,?,?,?,?,?,?)");
            ordre.setString(1, p.getNom());
            ordre.setString(2, p.getCognom());
            ordre.setString(3, p.getDNI());
            ordre.setString(4, p.getSexe());
            ordre.setInt(5, p.getEdat());
            ordre.setString(6, p.getUrgencia().getNomUrgencia());
            ordre.setString(7, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            ordre.setString(8, null);
            ok = ordre.executeUpdate() > 0;
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ok;
    }

    public boolean baixaPacient(Pacient p){
        boolean ok = false;
        Connection connection = new DataBase().start();
        try {
            PreparedStatement addRegistres = connection.prepareStatement("UPDATE HOSPITAL.PACIENTS SET DATASORTIDA=? WHERE DNI=?");
            addRegistres.setString(1, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            addRegistres.setString(2, p.getDNI());
            ok = addRegistres.executeUpdate() > 0;
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ok;
    }

    public boolean altaEspecialista(Especialista e){
        boolean ok = false;
        Connection connection = new DataBase().start();
        try {
            PreparedStatement ordre = connection.prepareStatement("INSERT INTO HOSPITAL.ESPECIALISTES VALUES (?,?)");
            ordre.setString(1, e.getNom());
            ordre.setString(2, e.getDNI());
            ok = ordre.executeUpdate() > 0;
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ok;
    }

    public boolean baixaEspecialista(Especialista e){
        boolean ok = false;
        Connection connection = new DataBase().start();
        try {
            PreparedStatement ordre = connection.prepareStatement("DELETE FROM HOSPITAL.ESPECIALISTES WHERE DNI=?");
            ordre.setString(1, e.getDNI());
            ok = ordre.executeUpdate() > 0;
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ok;
    }

    public boolean addUrgencia(Urgencies u){
        boolean ok = false;
        Connection connection = new DataBase().start();
        try {
            PreparedStatement ordre = connection.prepareStatement("INSERT INTO HOSPITAL.URGENCIES VALUES (?,?)");
            ordre.setString(1, u.getNomUrgencia());
            ordre.setString(2, u.getEspecialista());
            ok = ordre.executeUpdate() > 0;
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ok;
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

    public ObservableList<Especialista> getLlistaEspecialistes() {
        return llistaEspecialistes;
    }
}
