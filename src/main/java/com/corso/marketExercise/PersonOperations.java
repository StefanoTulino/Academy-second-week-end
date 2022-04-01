package com.corso.marketExercise;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;

public class PersonOperations extends MarketMaster implements BasedRepositories<Person>{

    static final LOG L=LOG.getInstance();
    private Scanner s=new Scanner(System.in);

    private Connection connection=connect();
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private Person p=new Person();

    public PersonOperations() throws IOException {
    }

    @Override
    public void run() {
        this.operation();
    }

    /** - methods that contains all methods of Person
     *
     */
    public void operation(){
        System.out.println("\n");

        L.info("Decidi cosa vuoi fare : \n" +
                "1: Stampa i dati nel db \n" +
                "2: Inserisci un nuovo impiegato \n" +
                "3: Modifica un impiegato e vedi il risultato \n" +
                "4: Elimina un impiegato \n"+
                "5: Visualizza la lista di ordini associati alle persone\n"+
                "0: Per uscire");

        int scelta = s.nextInt();

        try {

            switch (scelta) {
                case 1:
                    printAll();break;
                case 2: {
                    L.info("Inserisci i dati come segue:\n"+
                            "id, name, firstName,age");
                     p= new Person(s.nextInt(),s.next(),s.next(),s.nextInt());
                    insertObject(p);
                    break;
                }
                case 3: {
                    L.info("Inserisci il nome da sostituire ad un id(int)");
                    this.p.setName(s.next());
                    this.p.setId(s.nextInt());
                    updateObject(p);
                    break;
                }
                case 4: {
                    L.info("Inserisci l'id da voler eliminare");
                    this.p.setId(s.nextInt());
                    deleteObject(p);
                    break;
                }
                case 5:
                    findByForeignKey();break;
                default: L.error("Numero inserito errato");break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void insertObject(Person object) throws SQLException, ParseException {

        String sqlInsertTable= "" +
                "INSERT INTO market.person ( id, name, lastname, age) " +
                "VALUES (?, ?, ?, ?)" ;

        preparedStatement=connection.prepareStatement(sqlInsertTable);
        preparedStatement.setInt(1,object.getId());
        preparedStatement.setString(2,object.getName());
        preparedStatement.setString(3, object.getLastName());
        preparedStatement.setInt(4, object.getAge());

        preparedStatement.execute();
        L.info("Aggiunto record");
    }


    @Override
    public void updateObject(Person object) throws SQLException {
        String printQ2= "" +
                "UPDATE market.person " +
                "SET name = ? " +
                "WHERE id = ?" ;
        preparedStatement=connection.prepareStatement(printQ2);

        preparedStatement.setString(1,object.getName());
        preparedStatement.setInt(2,object.getId());
        preparedStatement.executeUpdate();
    }


    @Override
    public void deleteObject(Person object) throws SQLException {
        String printQ3= "" +
                "DELETE FROM market.person " +
                "WHERE id = ? ";

        preparedStatement=connection.prepareStatement(printQ3);
        preparedStatement.setInt(1,object.getId());
        preparedStatement.execute();
    }


    @Override
    public void findByForeignKey() throws SQLException {
        String sql="" +
                "SELECT * \n" +
                "FROM market.order o\n" +
                "JOIN market.person p ON o.personId=p.id;" ;

        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        L.info("Ecco i risultati della stampa delle 2 tabelle");

        while (resultSet.next()) {
            L.info(resultSet.getString("id") + "\t" +
                    resultSet.getString("name") + "\t" +
                    resultSet.getString("lastname") + "\t" +
                    resultSet.getString("age") + "\n"+
                    resultSet.getString("orderId") + "\t" +
                    resultSet.getString("orderNumber"));
        }
    }


    @Override
    public void printAll() throws SQLException {
        String sqlAll = "" +
                "SELECT * FROM market.person";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlAll);
        L.info("Ecco i risultati della stampa");
        while (resultSet.next()) {
            L.info(resultSet.getString("id") + "\t" +
                    resultSet.getString("name") + "\t" +
                    resultSet.getString("lastname") + "\t" +
                    resultSet.getString("age") );
        }
    }

}
