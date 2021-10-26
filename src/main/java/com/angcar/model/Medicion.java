package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Medicion { //POJO
    private String provincia;
    private String municipio;
    private String estacion;
    private String magnitud;
    private String punto_muestreo;
    private int ano;
    private int mes;
    private int dia;
    private Hora[] horas;
}
