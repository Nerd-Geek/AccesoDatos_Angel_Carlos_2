package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZonasMunicipio { //POJO
    private String air_code_quality_zone;
    private String air_quality_zone_description;
    private String municipal_air_quality_zone;
}
