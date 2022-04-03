package com.corso.prenotazioneRistorante;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;


public interface BasedRepositories<K> {

    void insertObject(K object) throws SQLException, ParseException;
    void updateObject(K object) throws SQLException;
    void deleteObject(K object) throws SQLException;

    List<K> printAll() throws SQLException;

}
