package com.corso.prenotazioneRistorante;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TableOperations extends PrenotationInit
        implements BasedRepositories<Table> {

    static final LOG L = LOG.getInstance();

    private Connection connection = connect();
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private Scanner s = new Scanner(System.in);
    private Table tavolo= new Table();
    private List<Table> lista=new ArrayList<>();


    public TableOperations() throws IOException {
    }


    public void operation(){
        System.out.println("\n");

        L.info("Decidi cosa vuoi fare : \n" +
                "1: Stampa i dati del db \n" +
                "2: Inserisci un nuovo tavolo \n" +
                "3: Modifica il numero di persone a quel preciso tavolo \n" +
                "4: Elimina un tavolo \n"+
                "5: Per uscire");

        int scelta = 0;

        try {
            while (scelta != 5) {
                scelta = s.nextInt();

                switch (scelta) {
                    case 1: {
                        printAll();
                        L.info("Se vuoi effettuare una nuova operazione premi uno dei tasti " +
                                "precedentemente elencati");
                        break;
                    }
                    case 2: {
                        L.info("Inserisci i dati come segue:\n" +
                                "id, name, firstName,age");
                        tavolo = new Table(s.next(), s.nextInt());
                        insertObject(tavolo);
                        L.info("Se vuoi effettuare una nuova operazione premi uno dei tasti " +
                                "precedentemente elencati");
                        break;
                    }
                    case 3: {
                        L.info("Inserisci il numero di persone da sostituire ad un preciso tavolo" +
                                "identificato con un cognome");
                        this.tavolo.setCapienza(s.nextInt());
                        this.tavolo.setNumero(s.next());
                        updateObject(tavolo);
                        L.info("Se vuoi effettuare una nuova operazione premi uno dei tasti " +
                                "precedentemente elencati");
                        break;
                    }
                    case 4: {
                        L.info("Inserisci l'id da voler eliminare");
                        this.tavolo.setNumero(s.next());
                        deleteObject(tavolo);
                        L.info("Se vuoi effettuare una nuova operazione premi uno dei tasti " +
                                "precedentemente elencati");
                        break;
                    }
                    case 5:
                        L.info("Arrivederci");
                        break;
                    default: {
                        L.error("Numero inserito errato");
                        L.info("Se vuoi effettuare una nuova operazione premi uno dei tasti " +
                                "precedentemente elencati");
                        break;
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void insertObject(Table object) throws SQLException, ParseException {
        String sqlInsertTable= "" +
                "INSERT INTO prenotation.table ( number, capacity) " +
                "VALUES (?, ?)" ;

        preparedStatement=connection.prepareStatement(sqlInsertTable);
        preparedStatement.setString(1,object.getNumero());
        preparedStatement.setInt(2,object.getCapienza());
        preparedStatement.execute();
        L.info("Aggiunto record");
    }


    @Override
    public void updateObject(Table object) throws SQLException {
        String printQ2= "" +
                "UPDATE prenotation.table " +
                "SET capacity = ? " +
                "WHERE number = ?" ;
        preparedStatement=connection.prepareStatement(printQ2);

        preparedStatement.setInt(1,object.getCapienza());
        preparedStatement.setString(2,object.getNumero());
        int temp=preparedStatement.executeUpdate();
        if(temp==1)
            L.info("Modifica effettuata");
        else L.error("Modifica non effettuata: uno o più dati sono errati");
    }

    @Override
    public void deleteObject(Table object) throws SQLException {
        String printQ3 = "" +
                "DELETE FROM prenotation.table " +
                "WHERE number = ? ";

        preparedStatement = connection.prepareStatement(printQ3);
        L.info("Inserisci l'id da voler eliminare");
        preparedStatement.setString(1, object.getNumero());
        preparedStatement.execute();
    }

    /**
     *
     * @return lista
     * @throws SQLException
     */
    @Override
    public List<Table> printAll() throws SQLException {
        String sqlAll = "" +
                "SELECT * FROM prenotation.table";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlAll);
        L.info("Ecco i risultati della stampa");
        while (resultSet.next()) {
            Table t= new Table();
            L.info(resultSet.getString("number") + "\t" +
                    resultSet.getString("capacity") );

            t.setNumero(resultSet.getString("number"));
            t.setCapienza(resultSet.getInt("capacity"));
            lista.add(t);
        }
        return lista;
        }
}

