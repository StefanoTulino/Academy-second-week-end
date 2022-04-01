package com.corso.marketExercise;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderOperations extends MarketMaster implements BasedRepositories<Order> {

    static final LOG L = LOG.getInstance();

    private Connection connection = connect();
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private Scanner s = new Scanner(System.in);
    private Order o = new Order();
    private List<Order> list = new ArrayList<>();

    public OrderOperations() throws IOException {
    }

    @Override
    public void run() {
        this.operation();
    }


    /** - methods that contains all methods of Order
     *
     */
    public void operation() {
        System.out.println("\n");

        L.info("Decidi cosa vuoi fare : \n" +
                "1: Stampa i dati nel db \n" +
                "2: Inserisci un nuovo ordine \n" +
                "3: Modifica il numero di ordini su un prodotto, passando l'id  \n" +
                "4: Elimina un ordine \n" +
                "5: Visualizza la lista di ordini associati alle persone\n" +
                "0: Per uscire");

        int scelta = s.nextInt();

        try {

            switch (scelta) {
                case 1:
                    printAll();
                    break;
                case 2: {
                    L.info("Inserisci i dati come segue:\n" +
                            "id, name, firstName,age");
                    o = new Order(s.nextInt(), s.nextInt(), s.nextInt());
                    insertObject(o);
                    break;
                }
                case 3: {
                    L.info("Inserisci il nome da sostituire ad un id(int)");
                    this.o.setOrderNumber(s.nextInt());
                    this.o.setOrderId(s.nextInt());
                    updateObject(o);
                    break;
                }
                case 4: {
                    L.info("Inserisci l'id da voler eliminare");
                    this.o.setOrderId(s.nextInt());
                    deleteObject(o);
                    break;
                }
                case 5:
                    findByForeignKey();
                    break;
                default:
                    L.error("Numero inserito errato");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insertObject(Order object) throws SQLException {

        String sqlInsertTable = "" +
                "INSERT INTO market.order ( orderId, orderNumber, personId) " +
                "VALUES (?, ?, ?)";

        preparedStatement = connection.prepareStatement(sqlInsertTable);
        preparedStatement.setInt(1, object.getOrderId());
        preparedStatement.setInt(2, object.getOrderNumber());
        preparedStatement.setInt(3, object.getPersonId());

        preparedStatement.execute();
        L.info("Aggiunto record");
    }


    @Override
    public void updateObject(Order object) throws SQLException {
        String printQ2 = "" +
                "UPDATE market.order " +
                "SET orderNumber = ? " +
                "WHERE orderId = ?";
        preparedStatement = connection.prepareStatement(printQ2);

        preparedStatement.setInt(1, object.getOrderNumber());
        preparedStatement.setInt(2, object.getOrderId());
        preparedStatement.executeUpdate();
    }


    @Override
    public void deleteObject(Order object) throws SQLException {
        String printQ3 = "" +
                "DELETE FROM market.order " +
                "WHERE orderId = ? ";

        preparedStatement = connection.prepareStatement(printQ3);
        L.info("Inserisci l'id da voler eliminare");
        preparedStatement.setInt(1, object.getOrderId());
        preparedStatement.execute();
    }


    @Override
    public void findByForeignKey() throws SQLException {
        String sql = "" +
                "SELECT * \n" +
                "FROM market.order o\n" +
                "JOIN market.person p ON o.personId=p.id;";

        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        L.info("Ecco i risultati della stampa delle 2 tabelle");

        while (resultSet.next()) {
            L.info(resultSet.getString("id") + "\t" +
                    resultSet.getString("name") + "\t" +
                    resultSet.getString("lastname") + "\t" +
                    resultSet.getString("age") + "\n" +
                    resultSet.getString("orderId") + "\t" +
                    resultSet.getString("orderNumber"));
        }
    }


    @Override
    public void printAll() throws SQLException {
        String sqlAll = "" +
                "SELECT * FROM market.order";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlAll);
        ResultSetMetaData md = resultSet.getMetaData();
        // StringBuilder builder = new StringBuilder();
        L.info("" + md.getColumnCount());
        L.info("Ecco i risultati della stampaaa");


        while (resultSet.next()) {
            L.info(resultSet.getString("orderId") + "\t" +
                    resultSet.getString("orderNumber") + "\t" +
                    resultSet.getString("personId"));
        }
    }

}



