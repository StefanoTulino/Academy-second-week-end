package com.corso.marketExercise;

import java.io.IOException;
import java.sql.*;

public class MarketCreateTable extends MarketMaster{

    static final LOG L=LOG.getInstance();

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;


    @Override
    public void run() {
        createTablePerson();
        createTableOrder();
    }


    public void createTablePerson(){
        String sqlCreateTable = "" +
                "CREATE TABLE IF NOT EXISTS market.person (\n" +
                "  id INT NOT NULL,\n" +
                "  name VARCHAR(45) NOT NULL,\n" +
                "  lastname VARCHAR(45) NOT NULL,\n" +
                "  age INT NOT NULL,\n" +
                "  PRIMARY KEY (id));" ;

        try {
            //abstract class
            connection=connect();

            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(sqlCreateTable);
            int res = 0;
            res = preparedStatement.executeUpdate();
            if(res==0)
                L.info("Tabella inserita o già esistente");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createTableOrder(){
        String sqlCreateTable = "" +
                "CREATE TABLE IF NOT EXISTS market.order (\n" +
                "  orderId INT NOT NULL,\n" +
                "  orderNumber INT NOT NULL,\n" +
                "  personId INT NOT NULL,\n" +
                "  PRIMARY KEY (orderId),\n" +
                "  FOREIGN KEY (personId)\n" +
                "  REFERENCES market.person (`id`)\n" +
                "  ON DELETE CASCADE\n" +
                "  ON UPDATE CASCADE);";

        try {
            //abstract class
            connection=connect();

            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(sqlCreateTable);
            int res = 0;
            res = preparedStatement.executeUpdate();
            if(res==0)
                L.info("Tabella inserita o già esistente");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
