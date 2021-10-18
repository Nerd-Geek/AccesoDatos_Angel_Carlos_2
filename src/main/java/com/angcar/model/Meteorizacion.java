package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Meteorizacion {
    private String provincia;
    private String municipio;
    private String estacion;
    private String magnitud;
    private String punto_muestreo;
    private String ano;
    private String mes;
    private String dia;
    private ArrayList horas;
    private ArrayList validacion;
}
