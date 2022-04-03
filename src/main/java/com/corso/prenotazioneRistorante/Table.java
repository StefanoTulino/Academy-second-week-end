package com.corso.prenotazioneRistorante;

import lombok.Data;

@Data
public class Table {
    private String numero;
    private int capienza;

    /*
    @OneToMany(
            //sarebbe la classe
            mappedBy = "family",
            cascade = {CascadeType.ALL}
    )
     */

    public Table(){

    }

    public Table(String numero, int capienza) {
        this.numero = numero;
        this.capienza = capienza;
    }
}
