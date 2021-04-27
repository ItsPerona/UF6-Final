package com.iperona.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Hospital {

    private final ObservableList<Pacient> llistaAlta = FXCollections.observableArrayList();
    private final ObservableList<Pacient> registres = FXCollections.observableArrayList();
    private final ObservableList<Urgencies> llistaServeis = FXCollections.observableArrayList();

    public Hospital() {
        loadUrgencies();
        loadPacients();
    }

    private void loadPacients() {
        Connection connection = new DataBase().start();
        try {
            Statement ordre = connection.createStatement();
            ResultSet resultSet =  ordre.executeQuery("SELECT * FROM HOSPITAL.PACIENTS");
            while (resultSet.next()) {
                Urgencies urg = null;

                for (int i = 0; i < llistaServeis.size(); i++) {
                    if (llistaServeis.get(i).getNomUrgencia().equals(resultSet.getString(6))) {
                        urg = llistaServeis.get(i);
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
