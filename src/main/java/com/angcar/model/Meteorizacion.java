package com.angcar.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Meteorizacion extends Medicion{

    public Meteorizacion(String provincial, String municipal, String station, String magnitude, String samplingPoint, int year, int month, int day, Hora[] hours) {
        super(provincial, municipal, station, magnitude, samplingPoint, year, month, day, hours);
    }
}
