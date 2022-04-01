package com.corso.marketExercise;

import java.io.IOException;
import java.sql.*;

public abstract class MarketMaster extends Thread{

    private Connection connection;
    private ReadProperties readProperties=new ReadProperties();


    public Connection connect() throws IOException {
        readProperties.read("applicationMarket.properties");

        String url = readProperties.getProperties().getProperty("db.url");
        String user = readProperties.getProperties().getProperty("db.user");
        String passw = readProperties.getProperties().getProperty("db.passw");

        try {
            connection = DriverManager.getConnection(url, user, passw);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
