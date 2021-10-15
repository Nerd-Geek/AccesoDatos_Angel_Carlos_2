package com.angcar.io;

import com.angcar.model.UbicacionEstaciones;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UbicaEstacionesReader {

    public static Optional<List<UbicacionEstaciones>> readDataOfPath(Path p, LocalDate fecha) {

        if (Files.exists(p)) {
            try (Stream<String> stream = Files.lines(p, Charset.forName("Cp1252"))) {
                return Optional.of(stream
                        .map(s -> s.split(";"))
                        .map(splitted -> {
                            String estacion_codigo = splitted[0];
                            String zona_calidad_aire_descripcion = splitted[1];
                            String estacion_municipio = splitted[2];
                            String estacion_fecha_alta = splitted[3];
                            String estacion_tipo_area = splitted[4];
                            String estacion_tipo_estacion = splitted[5];
                            String estacion_subarea_rural = splitted[6];
                            String estacion_direccion_postal = splitted[7];
                            String estacion_coord_UTM_ETRS89_x = splitted[8];
                            String estacion_coord_UTM_ETRS89_y = splitted[9];
                            String estacion_coord_longitud = splitted[10];
                            String estacion_coord_latitud = splitted[11];
                            String estacion_altitud = splitted[12];
                            String estacion_analizador_NO = splitted[13];
                            String estacion_analizador_NO2 = splitted[14];
                            String estacion_analizador_PM10 = splitted[15];
                            String estacion_analizador_PM2_5 = splitted[16];
                            String estacion_analizador_O3 = splitted[17];
                            String estacion_analizador_TOL = splitted[18];
                            String estacion_analizador_BEN = splitted[19];
                            String estacion_analizador_XIL = splitted[20];
                            String estacion_analizador_CO = splitted[21];
                            String estacion_analizador_SO2 = splitted[22];
                            String estacion_analizador_HCT = splitted[23];
                            String estacion_analizador_HNM = splitted[24];

                            float precipitaciones = Float.parseFloat(splitted[6]);
                            return new UbicacionEstaciones(
                                    estacion_codigo
                                    , zona_calidad_aire_descripcion
                                    , estacion_municipio
                                    , estacion_fecha_alta
                                    , estacion_tipo_area
                                    , estacion_tipo_estacion
                                    , estacion_subarea_rural
                                    , estacion_direccion_postal
                                    , estacion_coord_UTM_ETRS89_x
                                    , estacion_coord_UTM_ETRS89_y
                                    , estacion_coord_longitud
                                    , estacion_coord_latitud
                                    , estacion_altitud
                                    , estacion_analizador_NO
                                    , estacion_analizador_NO2
                                    , estacion_analizador_PM10
                                    , estacion_analizador_PM2_5
                                    , estacion_analizador_O3
                                    , estacion_analizador_TOL
                                    , estacion_analizador_BEN
                                    , estacion_analizador_XIL
                                    , estacion_analizador_CO
                                    , estacion_analizador_SO2
                                    , estacion_analizador_HCT
                                    , estacion_analizador_HNM
                            );

                        })
                        .collect(Collectors.toList()));
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }

    }

}