package com.angcar;

import com.angcar.model.Medicion;
import com.angcar.model.UbicacionEstaciones;
import com.angcar.util.Utils;

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
        argsTemporal[0] = "Torrejón de Ardoz";
        argsTemporal[1] = "out";
        //ReaderFiles.readDataOfPathZonasMunicipio().stream().forEach(System.out::println);

        /**
         * Detectar el número de argumentos y procesarlos
         */
        if (argsTemporal.length >= 2 && (argsTemporal.length % 2 == 0)) {
            List<String[]> pares = IntStream.iterate(0, i -> i += 2).limit(argsTemporal.length/2)
                    .mapToObj(n -> new String[] { argsTemporal[n], argsTemporal[n + 1] }).collect(Collectors.toList());

            pares.stream().forEach(pair -> {
                String ciudad = pair[0]; //Argumento ciudad
                Path path = Paths.get(WORKING_DIRECTORY + File.separator + pair[1]); //Archivo
                Utils.inicializarDatos();

                //FILTRAMOS POR ESTACIONES LOS ARCHIVOS //TODO: Quitar toda la mierda innecesaria y dejar solo la imprescindible
                List<UbicacionEstaciones> listaEstaciones = Utils.filtrarPorCiudad(pair[0]);
                if (listaEstaciones.size() == 0) {
                    System.out.printf("Ciudad no encontrada: %s", argsTemporal[0]);
                    System.exit(0);
                }
                String codigoCiudad = listaEstaciones.get(0).getEstacion_codigo(); //TODO: Si queremos expandir y agregar zonas, hay que editar esto
                List<Medicion> listaMeteorizacion = Utils.filtrarMeteorizacion(codigoCiudad);
                List<Medicion> listaContaminacion = Utils.filtrarContaminacion(codigoCiudad);


                System.out.println(Utils.formatearFechaMedicion(listaMeteorizacion));
                System.out.println(Utils.formatearFechaMedicion(listaContaminacion));


                System.out.println(Utils.nombreEstacion(argsTemporal[0]));
                /*
                System.out.println(ciudad.toString());
                System.out.println(path.toString());

                Utils.inicializarDatos();

                System.out.println(Utils.obtenerCodigo(argsTemporal[0]));
                System.out.println(Utils.formatearFechaMeteo(new StringBuilder("28092005")));
                System.out.println(Utils.formatearFechaContamina(new StringBuilder("28092005")));
                */
                //ReaderFiles.readDataOfPathZonasMunicipio().stream().forEach(System.out::println);

                //MeteoReader.readDataOfPathZonasMunicipio().ifPresent((list) -> {
                    //servicio = new ServicioClimatologia(list);
                    //imprimirInformeMeteorologico(ld.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));

                //    System.out.println(list);


                //});

                ////////////////////////////////////////////System.out.println(MeteoService.temperaturaMediaMensual("Leganés","Temperatura"));

            });

        } else {
            //TODO: Comprobar que el ejemplo funcione
            System.err.println(
                    "Error de sintaxis:\nDebes de utilizar el siguiente formato:\njava -jar meteo.jar <ciudad> <fichero>\n\nEjemplo: java -jar meteo.jar fuenlabrada ciudad_aire_zonas.csv");
        }
    }
}