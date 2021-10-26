package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Magnitud{ // POJO
    private int codigo_magnitud;
    private String descripcion_magnitud;
    private int codigo_tecnica_medida;
    private String unidad;
    private String descripcion_unidad;

    private String descripcion_tecnica_medida;
}
