package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "zonas", namespace = "com.angar")
@XmlType(name="zonas",
        propOrder = {"air_code_quality_zone", "air_quality_zone_description", "municipal_air_quality_zone"})
public class ZonasMunicipio { //POJO
    private String air_code_quality_zone;
    private String air_quality_zone_description;
    private String municipal_air_quality_zone;
}
