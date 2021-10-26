package com.angcar;

import com.angcar.service.DatosHTML;
import com.angcar.service.GeneradorHTML;
import com.angcar.util.Utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProcesamientoDatos {
    private final String[] ARGS;
    static public String path_destination;

    private static ProcesamientoDatos procesamientoDatos;

    /**
     * ProcesamientoDatos constructor privado
     * @param argumentos Array de String con los argumentos iniciales del programa
     */
    private ProcesamientoDatos(String[] argumentos){
        Utils.init_time = System.currentTimeMillis();
        this.ARGS = argumentos;
        creteEmptyFolders();
        ejecutarPrograma();
    }

    /**
     * Crea las carpetas necesarias si no existen
     */
    private void creteEmptyFolders() {
        File directory = new File(path_destination + "/image/");
        while (!directory.exists()){
            directory.mkdirs();
        }
    }

    /**
     * Singleton
     * @param argumentos Argumentos iniciales del programa
     * @return Singleton respuesta
     */
    public static ProcesamientoDatos getInstance(String[] argumentos){
        if (procesamientoDatos == null){
            if (argumentos.length >= 2 && (argumentos.length % 2 == 0)) {
                procesamientoDatos = new ProcesamientoDatos(argumentos);
            } else {
                System.err.println(
                        "Error de sintaxis:\nDebes de utilizar el siguiente formato:\njava -jar meteo.jar <ciudad>" +
                                " <fichero>\n\nEjemplo: java -jar meteo.jar C:\\Users\\Madirex\\Escritorio ");
            }
        }
        return procesamientoDatos;
    }

    /**
     * Ejecución del programa
     */
    public void ejecutarPrograma() {

        List<String[]> pares = IntStream.iterate(0, i -> i += 2).limit(ARGS.length / 2)
                .mapToObj(n -> new String[]{ARGS[n], ARGS[n + 1]}).collect(Collectors.toList());

        pares.forEach(pair -> {
            String ciudad = pair[0]; //Argumento ciudad
            path_destination = pair[1] + File.separator;

            creteEmptyFolders();

            if (Utils.inicializarDatos()) {
                DatosHTML datosCiudad = new DatosHTML();
                datosCiudad.procesarDatosPorCiudad(ciudad);
                try {
                    GeneradorHTML.generarHtml(ciudad);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                System.err.println("Los archivos CSV no se han podido leer.");
                System.exit(0);
            }
        });
    }

    // Mide el tiempo de ejecucion del programa
    public String tiempoInforme() {
        double tiempo = (double) ((System.currentTimeMillis() - Utils.init_time)/1000);
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
