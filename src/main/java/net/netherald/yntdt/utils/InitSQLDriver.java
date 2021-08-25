package net.netherald.yntdt.utils;

import net.netherald.yntdt.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitSQLDriver {

    static Connection sqlConnection;
    private String url;
    private int port;
    private String database;
    private String table;

    private String username;
    private String password;

    void loadSQLModule() {
        try {
            Main.LOGGER.info("Loading driver...");
            Class.forName("com.mysql.cj.cdbc.Driver");

            sqlConnection = DriverManager.getConnection("jdbc:mysql://" + url + ":" + port);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            Statement statement = sqlConnection.createStatement();
            statement.executeUpdate("create database if not exists " + database + " default character set utf8;");
            statement.executeUpdate("use " + database + ";");
            statement.executeUpdate("create table if not exists " + table + "(" +
                    "`uuid` varchar(36) not null," +
                    "`username` varchar(25)  not null," +
                    "`amount` int not null," +
                    "primary key (uuid)," +
                    "unique index (username)" +
                    ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Main.LOGGER.info("Connecting to SQL...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            sqlConnection = DriverManager.getConnection("jdbc:mysql://${url}:${port}/", username, password);
            Main.LOGGER.info("Connected to ${url}:${port}");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    void closeConnection() {
        try {
            if (!sqlConnection.isClosed()) {
                sqlConnection.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
