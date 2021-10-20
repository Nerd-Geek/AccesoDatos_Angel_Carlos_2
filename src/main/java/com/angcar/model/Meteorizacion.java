package com.angcar.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Meteorizacion extends Medicion{

    public Meteorizacion(String provincia, String municipio, String estacion, String magnitud, String punto_muestreo, int ano, int mes, int dia, String[] horas, String[] validacion) {
        super(provincia, municipio, estacion, magnitud, punto_muestreo, ano, mes, dia, horas, validacion);
    }
}
