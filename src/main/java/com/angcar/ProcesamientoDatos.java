package com.angcar;

import com.angcar.model.Medicion;
import com.angcar.model.UbicacionEstaciones;
import com.angcar.service.MeteoService;
import com.angcar.util.Utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProcesamientoDatos {
    private String[] argumentos;

    private static ProcesamientoDatos procesamientoDatos;

    private ProcesamientoDatos(String[] argumentos){
        this.argumentos = argumentos;
        ejecutarPrograma();
    }

    //Singleton
    public static ProcesamientoDatos getInstance(String[] argumentos){
        if (procesamientoDatos == null){
            if (argumentos.length >= 2 && (argumentos.length % 2 == 0)) {
                procesamientoDatos = new ProcesamientoDatos(argumentos);
            } else {
                System.err.println(
                        "Error de sintaxis:\nDebes de utilizar el siguiente formato:\njava -jar meteo.jar <ciudad>" +
                                " <fichero>\n\nEjemplo: java -jar meteo.jar fuenlabrada ciudad_aire_zonas.csv");
            }
        }
        return procesamientoDatos;
    }

    public void ejecutarPrograma(){
        String WORKING_DIRECTORY = System.getProperty("user.dir");

        List<String[]> pares = IntStream.iterate(0, i -> i += 2).limit(argumentos.length/2)
                .mapToObj(n -> new String[] { argumentos[n], argumentos[n + 1] }).collect(Collectors.toList());

        pares.stream().forEach(pair -> {
            String ciudad = pair[0]; //Argumento ciudad
            Path path = Paths.get(WORKING_DIRECTORY + File.separator + pair[1]); //Archivo
            Utils.inicializarDatos();

            //FILTRAMOS POR ESTACIONES LOS ARCHIVOS
            List<UbicacionEstaciones> listaEstaciones = Utils.filtrarPorCiudad(pair[0]);
            if (listaEstaciones.size() == 0) {
                System.out.printf("Ciudad no encontrada: %s", argumentos[0]);
                System.exit(0);
            }
            String codigoCiudad = listaEstaciones.get(0).getEstacion_codigo(); //TODO: Si queremos expandir y agregar zonas, hay que editar esto
            List<Medicion> listaMeteorizacion = Utils.filtrarMeteorizacion(codigoCiudad);
            List<Medicion> listaContaminacion = Utils.filtrarContaminacion(codigoCiudad);


            System.out.println(Utils.formatearFechaMedicion(listaMeteorizacion));
            System.out.println(Utils.formatearFechaMedicion(listaContaminacion));
            Utils.obtenerEstaciones(argumentos[0]);

            System.out.println(listaMeteorizacion);

            MeteoService.temperaturaMediaMensual(listaMeteorizacion);
        });
    }
}
