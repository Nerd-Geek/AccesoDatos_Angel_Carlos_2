package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Contaminacion {
    String provincia;
    String municipio;
    String estacion;
    String magnitud;
    String punto_muestreo;
    String ano;
    String mes;
    String dia;
    ArrayList horas;
    ArrayList validacion;
}
