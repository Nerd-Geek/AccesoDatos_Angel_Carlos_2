package com.angcar.model;

import java.util.ArrayList;

public class Contaminacion extends Medicion {

    public Contaminacion(String provincia, String municipio, String estacion, String magnitud, String punto_muestreo, int ano, int mes, int dia, ArrayList horas, ArrayList validacion) {
        super(provincia, municipio, estacion, magnitud, punto_muestreo, ano, mes, dia, horas, validacion);
    }
}