package com.angcar.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Meteorizacion extends Medicion{

    public Meteorizacion(String provincial, String municipal, String station, String magnitude, String sampling_point, int year, int month, int day, Hora[] hours) {
        super(provincial, municipal, station, magnitude, sampling_point, year, month, day, hours);
    }
}
