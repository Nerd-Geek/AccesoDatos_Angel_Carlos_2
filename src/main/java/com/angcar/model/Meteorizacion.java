package com.angcar.model;

public class Meteorizacion extends Medicion{

    public Meteorizacion(String provincia, String municipio, String estacion, String magnitud, String punto_muestreo, int ano, int mes, int dia, Hora[] horas) {
        super(provincia, municipio, estacion, magnitud, punto_muestreo, ano, mes, dia, horas);
    }
}
