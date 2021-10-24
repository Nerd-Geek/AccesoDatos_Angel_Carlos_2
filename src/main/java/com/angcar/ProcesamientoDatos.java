package com.angcar;

import com.angcar.model.Medicion;
import com.angcar.model.UbicacionEstaciones;
import com.angcar.service.DatosHTML;
import com.angcar.util.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProcesamientoDatos {
    private final String[] ARGS;

    private static ProcesamientoDatos procesamientoDatos;

    private ProcesamientoDatos(String[] argumentos){
        this.ARGS = argumentos;
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

        List<String[]> pares = IntStream.iterate(0, i -> i += 2).limit(ARGS.length/2)
                .mapToObj(n -> new String[] { ARGS[n], ARGS[n + 1] }).collect(Collectors.toList());

        pares.forEach(pair -> {
            String ciudad = pair[0]; //Argumento ciudad
            Path path = Paths.get(WORKING_DIRECTORY + File.separator + pair[1]); //Archivo

            if (Utils.inicializarDatos()){
            //FILTRAMOS POR ESTACIONES LOS ARCHIVOS
            Optional<List<UbicacionEstaciones>> listaEstaciones = Utils.filtrarPorCiudad(pair[0]);

                if (listaEstaciones.isPresent()){

                    List<UbicacionEstaciones> lista = listaEstaciones.get();

                    String codigoCiudad = lista.get(0).getEstacion_codigo(); //TODO: Si queremos expandir y agregar zonas, hay que editar esto
                    List<Medicion> listaMeteorizacion = Utils.filtrarMeteorizacion(codigoCiudad);
                    List<Medicion> listaContaminacion = Utils.filtrarContaminacion(codigoCiudad);

                    //TODO: ESTO PA'L PDF:

                    System.out.println(ARGS[0]); //Nombre de la ciudad
                    System.out.println(Utils.formatearFechaMedicion(listaMeteorizacion)); //Fecha inicio medición
                    System.out.println(Utils.formatearFechaMedicion(listaContaminacion)); //Fecha final medición
                    Utils.obtenerEstaciones(ARGS[0]); //Estaciones asociadas


                    DatosHTML.mediciones(listaMeteorizacion,listaContaminacion);

                    // System.out.println(MeteoService.medicionMaximaDos(listaMeteorizacion, 83));
                    //System.out.println(listaMeteorizacion);

                }else{
                    System.out.printf("Ciudad no encontrada: %s", ciudad);
                    System.exit(0);
                }
            }else{
                System.err.println("Los archivos CSV no se han podido leer.");
                System.exit(0);
            }
        });
    }
}
