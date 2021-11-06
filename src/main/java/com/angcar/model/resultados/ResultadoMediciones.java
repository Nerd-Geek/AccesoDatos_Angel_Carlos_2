package com.angcar.model.resultados;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlType(name = "resultado", propOrder = {"ciudad", "estaciones_asociadas", "fecha_inicio", "fecha_final", "datosMeteo", "datosContamina"})
public class ResultadoMediciones {
    @Getter(AccessLevel.NONE) private String id;
    private String ciudad;
    @Getter(AccessLevel.NONE) private List<String> estaciones_asociadas;
    private String fecha_inicio;
    private String fecha_final;
    @Getter(AccessLevel.NONE) private List<DatosMagnitud> datosMeteo;
    @Getter(AccessLevel.NONE) private List<DatosMagnitud> datosContamina;

    @XmlAttribute(name = "id")
    public String getId() {
        return id;
    }

    @XmlElementWrapper(name = "estaciones_asociadas")
    @XmlElement(name = "estacion")
    public List<String> getEstaciones_asociadas() {
        return estaciones_asociadas;
    }

    @XmlElementWrapper(name = "datos-metereologicos")
    @XmlElement(name = "dato")
    public List<DatosMagnitud> getDatosMeteo() {
        return datosMeteo;
    }

    @XmlElementWrapper(name = "datos-contaminacion")
    @XmlElement(name = "dato")
    public List<DatosMagnitud> getDatosContamina() {
        return datosContamina;
    }

}
