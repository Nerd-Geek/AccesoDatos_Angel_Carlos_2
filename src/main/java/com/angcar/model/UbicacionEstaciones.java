package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UbicacionEstaciones {

    String estacion_codigo;
    String zona_calidad_aire_descripcion;
    String estacion_municipio;
    String estacion_fecha_alta;
    String estacion_tipo_area;
    String estacion_tipo_estacion;
    String estacion_subarea_rural;
    String estacion_direccion_postal;
    String estacion_coord_UTM_ETRS89_x;
    String estacion_coord_UTM_ETRS89_y;
    String estacion_coord_longitud;
    String estacion_coord_latitud;
    String estacion_altitud;
    String estacion_analizador_NO;
    String estacion_analizador_NO2;
    String estacion_analizador_PM10;
    String estacion_analizador_PM2_5;
    String estacion_analizador_O3;
    String estacion_analizador_TOL;
    String estacion_analizador_BEN;
    String estacion_analizador_XIL;
    String estacion_analizador_CO;
    String estacion_analizador_SO2;
    String estacion_analizador_HCT;
    String estacion_analizador_HNM;
}
