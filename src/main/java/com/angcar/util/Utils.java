package com.angcar.util;

import com.angcar.io.ReaderFiles;
import com.angcar.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    private static List<UbicacionEstaciones> estacionesUbi;
    private static List<Contaminacion> contamina;
    private static List<Meteorizacion> meteo;
    private static List<ZonasMunicipio> municipio;
    private static List<MagnitudContaminacion> magnContamina;
    private static List<MagnitudMeteorizacion> magnMeteo;

    /**
     * Carga e inicializa los CSV's
     */
    public static void inicializarDatos(){
        //Leer .csv's
        estacionesUbi = ReaderFiles.readDataOfPathUbicacionEstaciones();
        contamina = ReaderFiles.readDataOfPathContaminacion();
        meteo = ReaderFiles.readDataOfPathMeteorologia();
        municipio = ReaderFiles.readDataOfPathZonasMunicipio();
        magnContamina = ReaderFiles.readDataOfPathMagnitudContaminacion();
        magnMeteo = ReaderFiles.readDataOfPathMagnitudMeteorizacion();
    }


    //////
    //////METEOROLOGÍA
    //////
    /**
     * Obtener código de estación dada una ciudad
     * @param nombreCiudad
     * @return String
     */
    public static String obtenerCodigo(String nombreCiudad) { //TODO: Al finalizar proyecto, comprobar public, privates, etc
            Optional<UbicacionEstaciones> estacion = estacionesUbi.stream().filter(ubicacionEstaciones ->
                    ubicacionEstaciones.getEstacion_municipio().equalsIgnoreCase(nombreCiudad)).findFirst();
        return estacion.get().getEstacion_codigo();
    }

    /**
     * Obtiene la fecha de inicio del código de la ciudad pasada como parámetro
     * @param codigoCiudad
     * @return LocalDate
     */
    private static LocalDate obtenerFechaInicioMeteo(StringBuilder codigoCiudad){
        LocalDate fecha = meteo.stream().filter(punto_muestreo -> punto_muestreo.getPunto_muestreo()
                        .contains(codigoCiudad)).min((c, c1) -> Integer.compare(c.getDia(), c1.getDia()))
                        .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia())).get();

        return fecha;
    }

    /**
     * Obtiene la fecha final del código de la ciudad pasada como parámetro
     * @param codigoCiudad
     * @return LocalDate
     */
    private static LocalDate obtenerFechaFinalMeteo(StringBuilder codigoCiudad){
        LocalDate fecha = meteo.stream().filter(punto_muestreo -> punto_muestreo.getPunto_muestreo()
                        .contains(codigoCiudad)).max((c, c1) -> Integer.compare(c.getDia(), c1.getDia()))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia())).get();

        return fecha;
    }

    /**
     * Formatea la fecha en formato de España
     * @param codigoCiudad
     * @return List<String>
     */
    public static List<String> formatearFechaMeteo(StringBuilder codigoCiudad){
        List<String> fecha = new ArrayList<>();

        fecha.add(obtenerFechaInicioMeteo(codigoCiudad).format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00")));
        fecha.add(obtenerFechaFinalMeteo(codigoCiudad).format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00")));

        return fecha;
    }







    //////
    //////CONTAMINACIÓN
    //////
    /**
     * Obtiene la fecha de inicio del código de la ciudad pasada como parámetro
     * @param codigoCiudad
     * @return LocalDate
     */
    private static LocalDate obtenerFechaInicioContamina(StringBuilder codigoCiudad){
        LocalDate fecha = contamina.stream().filter(punto_muestreo -> punto_muestreo.getPunto_muestreo()
                        .contains(codigoCiudad)).min((c, c1) -> Integer.compare(c.getDia(), c1.getDia()))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia())).get();

        return fecha;
    }

    /**
     * Obtiene la fecha final del código de la ciudad pasada como parámetro
     * @param codigoCiudad
     * @return LocalDate
     */
    private static LocalDate obtenerFechaFinalContamina(StringBuilder codigoCiudad){
        LocalDate fecha = contamina.stream().filter(punto_muestreo -> punto_muestreo.getPunto_muestreo()
                        .contains(codigoCiudad)).max((c, c1) -> Integer.compare(c.getDia(), c1.getDia()))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia())).get();

        return fecha;
    }

    /**
     * Formatea la fecha en formato de España
     * @param codigoCiudad
     * @return List<String>
     */
    public static List<String> formatearFechaContamina(StringBuilder codigoCiudad){
        List<String> fecha = new ArrayList<>();

        fecha.add(obtenerFechaInicioContamina(codigoCiudad).format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00")));
        fecha.add(obtenerFechaFinalContamina(codigoCiudad).format(DateTimeFormatter.ofPattern("dd/MM/yyyy 00:00:00")));

        return fecha;
    }

}
