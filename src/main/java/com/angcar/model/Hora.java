package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Hora {
    private String valor;
    private String validation;
    private int numHora;
}