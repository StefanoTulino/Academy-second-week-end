package com.corso.marketExercise;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;


public class TestMarket {

    static final LOG L=LOG.getInstance();

    public static void main(String[] args) throws IOException, SQLException, ParseException {
        Scanner s= new Scanner(System.in);

        MarketMaster create= new MarketCreateTable();
        create.connect();
        create.run();

        L.info("Scegli cosa vedere: 1-Persone, 2-Ordini");
        int temp= s.nextInt();

        if(temp==1){
            PersonOperations personOp= new PersonOperations();
            personOp.run();
        }
        else if(temp==2){
            OrderOperations orderOperations=new OrderOperations();
            orderOperations.run();
        }

    }
}
