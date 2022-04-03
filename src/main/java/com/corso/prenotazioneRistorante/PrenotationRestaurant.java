package com.corso.prenotazioneRistorante;

import lombok.Data;

import java.sql.Date;

@Data
public class PrenotationRestaurant {
    private String cognome;
    //da cambiare in DATE
    private Date data;
    private int numPersone;
    private String cellulare;

    /*
    @ManyToOne
    @JoinColumn(name = "family_id")
     */

    public PrenotationRestaurant(){

    }

    public PrenotationRestaurant(String cognome, Date data, int numPersone, String cellulare) {
        this.cognome = cognome;
        this.data = data;
        this.numPersone = numPersone;
        this.cellulare = cellulare;
    }
}
