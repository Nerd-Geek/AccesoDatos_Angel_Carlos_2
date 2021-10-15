package com.angcar.io;

import com.angcar.model.ZonasMunicipio;

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

public class ZonasMunicipioReader {

    public static Optional<List<ZonasMunicipio>> readDataOfPath(Path p, LocalDate fecha) {

        if (Files.exists(p)) {
            try (Stream<String> stream = Files.lines(p, Charset.forName("Cp1252"))) {
                return Optional.of(stream
                        .map(s -> s.split(";"))
                        .map(splitted -> {

                            String zona_calidad_aire_codigo = splitted[0];
                            String zona_calidad_aire_descripcion = splitted[1];
                            String zona_calidad_aire_municipio = splitted[2];

                            float precipitaciones = Float.parseFloat(splitted[6]);
                            return new ZonasMunicipio(zona_calidad_aire_codigo,
                                    zona_calidad_aire_descripcion, zona_calidad_aire_municipio);

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