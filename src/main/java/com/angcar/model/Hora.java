package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Hora { // POJO
    private String value;
    private String validation;
    private int numHour;
}