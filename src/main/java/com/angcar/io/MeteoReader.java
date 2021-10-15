package com.angcar.io;

import com.angcar.model.Meteorizacion;

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

public class MeteoReader {

    public static Optional<List<Meteorizacion>> readDataOfPath(Path p, LocalDate fecha) {

        if (Files.exists(p)) {
            try (Stream<String> stream = Files.lines(p, Charset.forName("Cp1252"))) {
                return Optional.of(stream
                        .map(s -> s.split(";"))
                        .map(splitted -> {

                            String provincia = splitted[0];
                            String municipio = splitted[1];
                            String estacion = splitted[2];
                            String magnitud = splitted[3];
                            String punto_muestreo = splitted[4];
                            String ano = splitted[5];
                            String mes = splitted[6];
                            String dia = splitted[7];

                            //TODO: FALTA IMPLEMENTAR HORAS Y VALIDACIÓN
                            ArrayList horas = new ArrayList(); //= splitted[8];
                            ArrayList validacion = new ArrayList(); //= splitted[9];

                            float precipitaciones = Float.parseFloat(splitted[6]);
                            return new Meteorizacion(provincia, municipio, estacion, magnitud, punto_muestreo, ano, mes, dia, horas, validacion);

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