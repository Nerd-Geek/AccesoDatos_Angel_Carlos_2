package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Medicion {
    private String provincia;
    private String municipio;
    private String estacion;
    private String magnitud;
    private String punto_muestreo;
    private int ano;
    private int mes;
    private int dia;
    private ArrayList horas;
    private ArrayList validacion;
}
