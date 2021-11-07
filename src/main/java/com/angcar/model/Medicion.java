package com.angcar.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicion { //POJO
    private String provincial;
    private String municipal;
    private String station;
    private String magnitude;
    private String samplingPoint;
    private int year;
    private int month;
    private int day;
    private Hora[] hours;
}

