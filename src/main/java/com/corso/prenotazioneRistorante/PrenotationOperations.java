package com.corso.prenotazioneRistorante;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class PrenotationOperations extends PrenotationInit
        implements BasedRepositories<PrenotationRestaurant> {

    static final LOG L = LOG.getInstance();

    private Connection connection = connect();
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private Scanner s = new Scanner(System.in);
    private PrenotationRestaurant pR=new PrenotationRestaurant();
    private List<PrenotationRestaurant> lista=new ArrayList<>();


    public PrenotationOperations() throws IOException {
    }


    public void operation(){
        System.out.println("\n");

        L.info("Decidi cosa vuoi fare : \n" +
                "1: Stampa i dati del db \n" +
                "2: Inserisci una nuova prenotazione \n" +
                "3: Modifica il numero di persone al tavolo \n" +
                "4: Elimina una prenotazione \n"+
                "5: Per uscire");

        int scelta =0;

        try {
            while (scelta != 5 ) {
                scelta=s.nextInt();

                switch (scelta) {
                    case 1:
                        printAll();
                        L.info("Se vuoi effettuare una nuova operazione premi uno dei tasti " +
                                "precedentemente elencati");

                        break;
                    case 2: {
                        L.info("Inserisci i dati come segue:\n" +
                                "id, name, firstName,age");
                        pR = new PrenotationRestaurant(s.next(), Date.valueOf(s.next()), s.nextInt(), s.next());
                        insertObject(pR);
                        L.info("Se vuoi effettuare una nuova operazione premi uno dei tasti " +
                                "precedentemente elencati");
                        break;
                    }
                    case 3: {
                        L.info("Inserisci il numero di persone da sostituire ad un preciso tavolo" +
                                "identificato con un cognome");
                        this.pR.setNumPersone(s.nextInt());
                        this.pR.setCognome(s.next());
                        updateObject(pR);
                        L.info("Se vuoi effettuare una nuova operazione premi uno dei tasti " +
                                "precedentemente elencati");

                        break;
                    }
                    case 4: {
                        L.info("Inserisci l'id da voler eliminare");
                        this.pR.setCognome(s.next());
                        deleteObject(pR);
                        L.info("Se vuoi effettuare una nuova operazione premi uno dei tasti " +
                                "precedentemente elencati");
                        break;
                    }
                    case 5:
                        L.info("Arrivederci");break;
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
    public void insertObject(PrenotationRestaurant object) throws SQLException, ParseException {
        String sqlInsertTable= "" +
                "INSERT INTO prenotation.prenotationRestaurant ( cognome, data, numPersone, cellulare) " +
                "VALUES (?, ?, ?, ?)" ;

        preparedStatement=connection.prepareStatement(sqlInsertTable);
        preparedStatement.setString(1,object.getCognome());
        preparedStatement.setDate(2,object.getData());
        preparedStatement.setInt(3, object.getNumPersone());
        preparedStatement.setString(4, object.getCellulare());

        boolean risposta=richiestaPrenotazione(object.getCognome(),object.getData(),object.getNumPersone(),
                object.getCellulare());

        if(risposta) {
            preparedStatement.execute();
            L.info("Aggiunto record");
        }
        else L.info("Prenotazione NO");
    }


    @Override
    public void updateObject(PrenotationRestaurant object) throws SQLException {
        String printQ2= "" +
                "UPDATE prenotation.prenotationRestaurant " +
                "SET numPersone = ? " +
                "WHERE cognome = ?" ;
        preparedStatement=connection.prepareStatement(printQ2);

        preparedStatement.setInt(1,object.getNumPersone());
        preparedStatement.setString(2,object.getCognome());
        int temp=preparedStatement.executeUpdate();
        if(temp==1)
            L.info("Modifica effettuata");
        else L.error("Modifica non effettuata: uno o più dati sono errati");
    }


    @Override
    public void deleteObject(PrenotationRestaurant object) throws SQLException {
        String printQ3 = "" +
                "DELETE FROM prenotation.prenotationRestaurant " +
                "WHERE cognome = ? ";

        preparedStatement = connection.prepareStatement(printQ3);
        L.info("Inserisci l'id da voler eliminare");
        preparedStatement.setString(1, object.getCognome());
        preparedStatement.execute();
    }

    /**
     *
     * @return lista
     * @throws SQLException
     */
    @Override
    public List<PrenotationRestaurant> printAll() throws SQLException {
        String sqlAll = "" +
                "SELECT * FROM prenotation.prenotationRestaurant";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlAll);
        L.info("Ecco i risultati della stampa");
        while (resultSet.next()) {
            PrenotationRestaurant p= new PrenotationRestaurant();
            L.info(resultSet.getString("cognome") + "\t" +
                    resultSet.getDate("data") + "\t" +
                    resultSet.getInt("numPersone") + "\t" +
                    resultSet.getString("cellulare")+"\n" );

                p.setCognome(resultSet.getString("cognome"));
                p.setData(resultSet.getDate("data"));
                p.setNumPersone(resultSet.getInt("numPersone"));
                p.setCellulare(resultSet.getString("cellulare") );
                lista.add(p);
            }
        return lista;
    }


    public boolean richiestaPrenotazione(String cognome, Date data, int
            numeroPersone, String cellulare) throws SQLException {

        String result=this.disponibilitàTavolo(data,numeroPersone);
        if(result!=null) {
            L.info("la prenotazione è andata bene");
            return true;
        }
        else {
            L.info("la prenotazione non può essere effettuata");
            return false;
        }
    }

    /**
     * - metodo di supporto per il metodo richiestaPrenotazione
     * @param data
     * @param numeroPersone
     * @return
     * @throws SQLException
     */
    public String disponibilitàTavolo(Date data, int
            numeroPersone) throws SQLException {
        String sqlAll = "" +
                "SELECT * FROM prenotation.table";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlAll);
        while (resultSet.next()) {
            String temp = resultSet.getString("number");
            int cap = resultSet.getInt("capacity");
            if(numeroPersone<= cap)
                return temp;
        }
        return null;
    }

}
