package com.angcar;

import com.angcar.io.ReaderFiles;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) {

        String WORKING_DIRECTORY = System.getProperty("user.dir");

        /**
         * TODO: refactorizar nombre : argsTemporal por args &&
         * TODO: ELIMINAR ESTA PARTE CUANDO TERMINAMOS EL PROYECTO
         * ESTO LO QUE HACE ES PONER MANUALMENTE LOS ARGUMENTOS
         * ASÍ NO TENEMOS QUE CREAR Y EJECUTAR EL .JAR CADA VEZ
         */
        String[] argsTemporal = new String[2];
        argsTemporal[0] = "MADRID";
        argsTemporal[1] = "out";

        /**
         * Detectar el número de argumentos y procesarlos
         */
        if (argsTemporal.length >= 2 && (argsTemporal.length % 2 == 0)) {
            List<String[]> pares = IntStream.iterate(0, i -> i += 2).limit(argsTemporal.length/2)
                    .mapToObj(n -> new String[] { argsTemporal[n], argsTemporal[n + 1] }).collect(Collectors.toList());

            pares.stream().forEach(pair -> {
                String ciudad = pair[0]; //Argumento ciudad
                Path path = Paths.get(WORKING_DIRECTORY + File.separator + pair[1]); //Archivo

                System.out.println(ciudad.toString());
                System.out.println(path.toString());

                //Lee los datos
                //MeteoReader.readDataOfPathMeteorologia().stream().forEach(System.out::println);


                //MeteoReader.readDataOfPathContaminacion();
                //MeteoReader.readDataOfPathUbicacionEstaciones();
                //MeteoReader.readDataOfPathZonasMunicipio();

                //MeteoReader.readDataOfPathZonasMunicipio().stream().forEach(System.out::println);

                ReaderFiles.readDataOfPathContaminacion().stream().forEach(System.out::println);

                //MeteoReader.readDataOfPathZonasMunicipio().ifPresent((list) -> {
                    //servicio = new ServicioClimatologia(list);
                    //imprimirInformeMeteorologico(ld.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));

                //    System.out.println(list);

                //});
            });

        } else {
            //TODO: Comprobar que el ejemplo funcione
            System.err.println(
                    "Error de sintaxis:\nDebes de utilizar el siguiente formato:\njava -jar meteo.jar <ciudad> <fichero>\n\nEjemplo: java -jar meteo.jar fuenlabrada ciudad_aire_zonas.csv");
        }
    }
}