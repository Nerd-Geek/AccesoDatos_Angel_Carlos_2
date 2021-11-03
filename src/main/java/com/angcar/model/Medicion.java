package com.angcar.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@AllArgsConstructor
@XmlRootElement(name = "mediciones", namespace = "com.angar")
@XmlType(name = "medicion", propOrder = {"provincial", "municipal", "station", "magnitude",
        "sampling_point", "year", "month", "day", "hours"})
public class Medicion { //POJO
    private String provincial;
    private String municipal;
    private String station;
    private String magnitude;
    private String sampling_point;
    private int year;
    private int month;
    private int day;
    @Getter(AccessLevel.NONE) private Hora[] hours;

    @XmlElementWrapper(name = "horas")
    @XmlElement(name ="hora")
    public Hora[] getHours(){
        return this.hours;
    }
}

