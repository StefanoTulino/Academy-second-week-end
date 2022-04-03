package com.corso.prenotazioneRistorante;


import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class TestPrenotation {

        static final LOG L=LOG.getInstance();

    public static void main(String[] args) throws IOException, SQLException, ParseException {
        PrenotationRestaurant pR;
        Scanner s= new Scanner(System.in);

        PrenotationInit prenotationInit= new PrenotationOperations();
        prenotationInit.connect();
        prenotationInit.createSchema();
        prenotationInit.createTable();
        prenotationInit.createTablePrenotationTable();

            PrenotationOperations prenotationOperations= new PrenotationOperations();
            prenotationOperations.operation();

            TableOperations tableOperations= new TableOperations();
            tableOperations.operation();

            //FILE
            File file= new File("C:\\Users\\stefa\\IdeaProjects\\Academy-second-week-end\\file1.txt");
            L.info(""+file.exists());

            FileWriter fx= new FileWriter(file,true);
            PrintWriter printWriter= new PrintWriter(fx);
            printWriter.println("Lista prenotazioni: \n"+ prenotationOperations.printAll()+"\n");
            printWriter.println("Lista tavoli: \n"+ tableOperations.printAll()+"\n");
            printWriter.flush();

            fx.close();
    }
}


