package com.angcar.model.resultados;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Data
@XmlType(name = "magnitud", propOrder = {"media", "maxima", "minima", "dias"})
public class DatosMagnitud {
    @Getter(AccessLevel.NONE) private String tipo;
    private double media;
    private DatosMomento maxima;
    private DatosMomento minima;
    @Getter(AccessLevel.NONE) private List<DatosDiaMagnitud> dias;


    @XmlAttribute(name = "tipo")
    public String getTipo() {
        return tipo;
    }

    @XmlElementWrapper(name = "dias", required = false)
    @XmlElement(name = "dia", required = false)
    public List<DatosDiaMagnitud> getDias() {
        return dias;
    }
}
