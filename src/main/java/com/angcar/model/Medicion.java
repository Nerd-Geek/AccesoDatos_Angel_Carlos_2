package com.angcar.model;

import lombok.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

