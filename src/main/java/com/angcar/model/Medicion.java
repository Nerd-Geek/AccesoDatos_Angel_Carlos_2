package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Medicion { //POJO
    private String provincial;
    private String municipal;
    private String station;
    private String magnitude;
    private String sampling_point;
    private int year;
    private int month;
    private int day;
    private Hora[] hours;
}
