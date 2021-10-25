package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ZonasMunicipio { //POJO
    private String zona_calidad_aire_codigo;
    private String zona_calidad_aire_descripcion;
    private String zona_calidad_aire_municipio;
}
