package com.iperona.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    private final String DB = "jdbc:derby:base1;create=true";
    private final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

    public Connection start() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
