package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Data
@AllArgsConstructor
@XmlType(propOrder = {"value", "validation"})
public class Hora { // POJO
    private String value;
    private String validation;

    @XmlAttribute(name = "id")
    private int numHour;
}