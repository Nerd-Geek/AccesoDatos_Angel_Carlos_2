package com.angcar.model;

public class Contaminacion extends Medicion {

    public Contaminacion(String provincial, String municipal, String station, String magnitude, String point_maestro, int year, int month, int day, Hora[] hours) {
        super(provincial, municipal, station, magnitude, point_maestro, year, month, day, hours);
    }
}