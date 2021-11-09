package com.angcar.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Contaminacion extends Medicion {

    public Contaminacion(String provincial, String municipal, String station, String magnitude, String pointMaestro, int year, int month, int day, Hora[] hours) {
        super(provincial, municipal, station, magnitude, pointMaestro, year, month, day, hours);
    }
}