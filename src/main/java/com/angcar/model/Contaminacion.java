package com.angcar.model;

import com.angcar.util.Dia;
import com.angcar.util.Hora;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Contaminacion extends Medicion {


    public Contaminacion(String provincia, String municipio, String estacion, String magnitud, String punto_muestreo, int ano, int mes, int dia, Dia dayHoras) {
        super(provincia, municipio, estacion, magnitud, punto_muestreo, ano, mes, dia, dayHoras);
    }
}