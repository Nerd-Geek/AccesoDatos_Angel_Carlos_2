package com.angcar;

import com.angcar.service.DatosHTML;
import com.angcar.service.GeneradorHTML;
import com.angcar.util.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProcesamientoDatos {
    private static String ciudad;
    private final String[] ARGS;
    private final long INIT_TIME;
    static public String path_destination;

    private static ProcesamientoDatos procesamientoDatos;

    private ProcesamientoDatos(String[] argumentos){
        INIT_TIME = System.currentTimeMillis();
        this.ARGS = argumentos;
        ejecutarPrograma();
        System.out.println(tiempoInforme()); //TODO: TRATAR SOUT
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

    public void ejecutarPrograma() {
        String WORKING_DIRECTORY = System.getProperty("user.dir");

        List<String[]> pares = IntStream.iterate(0, i -> i += 2).limit(ARGS.length / 2)
                .mapToObj(n -> new String[]{ARGS[n], ARGS[n + 1]}).collect(Collectors.toList());

        pares.forEach(pair -> {
            String ciudad = pair[0]; //Argumento ciudad
            Path path = Paths.get(WORKING_DIRECTORY + File.separator + pair[1]); //Archivo
            path_destination = path.toString();

            if (Utils.inicializarDatos()) {
                DatosHTML datosCiudad = new DatosHTML();
                datosCiudad.procesarDatosPorCiudad(pair[0]);
                try {
                    GeneradorHTML.generarHtml(pair[0], Utils.obtenerCodigo(pair[0]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // TODO: TEST - System.out.println(MeteoService.medicionMaximaDos(listaMeteorizacion, 83));
            } else {
                System.err.println("Los archivos CSV no se han podido leer.");
                System.exit(0);
            }
        });
    }

    public String tiempoInforme() {
        double tiempo = (double) ((System.currentTimeMillis() - INIT_TIME)/1000);
        DateTimeFormatter dtf5 = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
        LocalDate fecha = LocalDate.now();
        String formatearFecha = "dd/MM/yyyy";
        LocalTime hora = LocalTime.now();
        String formatearHora = "HH:mm:ss";

        String retorno = "Informe generado en el día " + fecha.format(DateTimeFormatter.ofPattern(formatearFecha))
                + " a las " + hora.format(DateTimeFormatter.ofPattern(formatearHora))+ " en "+ tiempo + " segundos";

        return retorno;
    }
}
