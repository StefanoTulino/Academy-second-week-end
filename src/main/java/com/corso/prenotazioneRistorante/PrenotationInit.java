package com.corso.prenotazioneRistorante;


import java.io.IOException;
import java.sql.*;

public abstract class PrenotationInit {

    static final LOG L=LOG.getInstance();

    private Connection connection;
    private ReadProperties readProperties=new ReadProperties();

    private Statement statement;
    private PreparedStatement preparedStatement;


    /**
     *
     * @return
     * @throws IOException
     */
    public Connection connect() throws IOException {;
        readProperties.read("applicationPrenotation.properties");
        String url = readProperties.getProperties().getProperty("url");

        try {
            connection=DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void createSchema() throws IOException {
        String sqlSchema= "" +
                "create schema IF NOT EXISTS prenotation;";
        try {
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(sqlSchema);
            int res = preparedStatement.executeUpdate();
            if(res==1)
                L.info("Schema inserito o già esistente ");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    public void createTablePrenotationTable(){
        String sqlCreateTable = "" +
                "CREATE TABLE IF NOT EXISTS prenotation.prenotationRestaurant (\n" +
                "  cognome VARCHAR(45) NOT NULL,\n" +
                "  data DATE NOT NULL,\n" +
                "  numPersone INT NOT NULL,\n" +
                "  cellulare VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (cognome,data)) ;" ;


        /*
         "CREATE TABLE IF NOT EXISTS prenotation.prenotationRestaurant (\n" +
                "  cognome VARCHAR(45) NOT NULL,\n" +
                "  data DATE NOT NULL,\n" +
                "  numPersone INT NOT NULL,\n" +
                "  cellulare VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (cognome,data)," +
                "  FOREIGN KEY (cognome)\n" +
                "  REFERENCES prenotation.table (`number`));" ;
         */

        try {
            this.connect();

            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(sqlCreateTable);
             preparedStatement.execute();
             if(preparedStatement!=null)
                L.info("Tabella inserita o già esistente");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void createTable(){
        String sqlCreateTable = "" +
                "CREATE TABLE IF NOT EXISTS prenotation.table (\n" +
                "  number VARCHAR(20) NOT NULL,\n" +
                "  capacity INT NOT NULL,\n" +
                "  PRIMARY KEY (number));";

        try {
            this.connect();

            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(sqlCreateTable);
            preparedStatement.execute();
            if(preparedStatement!=null)
                L.info("Tabella Tavolo inserita o già esistente");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


}
