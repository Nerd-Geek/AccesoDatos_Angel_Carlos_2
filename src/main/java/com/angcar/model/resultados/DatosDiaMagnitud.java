package com.angcar.model.resultados;

import lombok.Data;

import javax.xml.bind.annotation.XmlType;

@Data
@XmlType(propOrder = {"fecha", "valor"})
public class DatosDiaMagnitud {
    private String fecha;
    private double valor;
}
