package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Magnitud{ // POJO
    private int codeMagnitud;
    private String descriptionMagnitude;
    private int codeTechnicalMeasure;
    private String unidad;
    private String descriptionsUnidad;

    private String technicalDescriptionMeasure;
}
