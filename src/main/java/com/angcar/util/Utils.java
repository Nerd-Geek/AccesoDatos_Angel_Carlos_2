package com.angcar.util;

import com.angcar.io.ReaderFiles;
import com.angcar.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Utils {
    private static List<UbicacionEstaciones> estacionesUbi;
    private static List<Contaminacion> contamina;
    private static List<Meteorizacion> meteo;
    private static List<ZonasMunicipio> municipio;
    private static List<MagnitudContaminacion> magnContamina;
    private static List<MagnitudMeteorizacion> magnMeteo;

    //GETTERS
    public static List<MagnitudContaminacion> getMagnContamina() {
        return magnContamina;
    }

    public static List<MagnitudMeteorizacion> getMagnMeteo() {
        return magnMeteo;
    }


    /**
     * Carga e inicializa los CSV's
     */
    public static boolean inicializarDatos(){
        //Leer .csv's
        AtomicBoolean realizado = new AtomicBoolean(true);

        ReaderFiles.readDataOfPathUbicacionEstaciones().ifPresentOrElse(
                ubicacionEstaciones -> estacionesUbi = ubicacionEstaciones,
                () -> {System.err.println("No se ha localizado el csv de Estaciones");
            realizado.set(false);
        });

        ReaderFiles.readDataOfPathContaminacion().ifPresentOrElse(
                contaminacion -> contamina = contaminacion,
                () -> {System.err.println("No se ha localizado el csv de contaminación");
            realizado.set(false);
        });

        ReaderFiles.readDataOfPathMeteorologia().ifPresentOrElse(
                meteorologia -> meteo = meteorologia,
                () -> {System.err.println("No se ha localizado el csv de meteorología");
            realizado.set(false);
        });

        ReaderFiles.readDataOfPathZonasMunicipio().ifPresentOrElse(
                zonasMunicipio -> municipio = zonasMunicipio,
                () -> {System.err.println("No se ha localizado el csv de municipio");
            realizado.set(false);
        });

        ReaderFiles.readDataOfPathMagnitudContaminacion().ifPresentOrElse(
                magnitudContamina -> magnContamina = magnitudContamina,
                () -> {System.err.println("No se ha localizado el csv de magnitudes de contaminación");
            realizado.set(false);
        });

        ReaderFiles.readDataOfPathMagnitudMeteorizacion().ifPresentOrElse(
                magnitudMeteo -> magnMeteo = magnitudMeteo,
                () -> {System.err.println("No se ha localizado el csv de magnitudes de meteorización");
            realizado.set(false);
        });

        return realizado.get();
    }

    /**
     * Obtiene una lista de las magnitudes según el nombre de la magnitud dada una lista
     * @param idMagnitud La ID de la magnitud
     * @param lista Lista de mediciones
     * @return
     */
    public static Optional<List<Medicion>> obtenerMagnitudLista(int idMagnitud, List<Medicion> lista){
        return Optional.of(lista.stream().filter(nombreMagn ->
                        nombreMagn.getMagnitud().equalsIgnoreCase(String.valueOf(idMagnitud)))
                .collect(Collectors.toList()));

    }

    /**
     * Filtra por ciudad
     * @param nombreCiudad El nombre de la ciudad
     * @return List<Medicion>
     */

    public static Optional<List<UbicacionEstaciones>> filtrarPorCiudad(String nombreCiudad) {
        return Optional.of(estacionesUbi.stream().filter(ubicacionEstaciones ->
                ubicacionEstaciones.getEstacion_municipio().equalsIgnoreCase(nombreCiudad)).collect(Collectors.toList()));
    }

    public static List<Medicion> filtrarMeteorizacion(String codigoCiudad) {
        return meteo.stream().filter(punto_muestreo -> punto_muestreo.getPunto_muestreo()
                .contains(codigoCiudad)).collect(Collectors.toList());
    }

    public static List<Medicion> filtrarContaminacion(String codigoCiudad) {
        return contamina.stream().filter(punto_muestreo -> punto_muestreo.getPunto_muestreo()
                .contains(codigoCiudad)).collect(Collectors.toList());
    }

    //////
    //////MEDICIÓN
    //////
    /**
     * Obtiene la fecha de inicio
     * @return LocalDate
     */
    public static String obtenerFechaInicioMedicion(){
        String formatearFecha = "dd/MM/yyyy - 00:00:00";
        LocalDate fecha = meteo.stream().min((c, c1) -> Integer.compare(c.getDia(), c1.getDia()))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia())).get();
        LocalDate fecha2 = contamina.stream().min((c, c1) -> Integer.compare(c.getDia(), c1.getDia()))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia())).get();

        if (fecha.equals(fecha2)) {
            return fecha.format(DateTimeFormatter.ofPattern(formatearFecha));
        }else if(fecha.getDayOfMonth() < fecha2.getDayOfMonth()) {
            return fecha.format(DateTimeFormatter.ofPattern(formatearFecha));
        }else {
            return fecha2.format(DateTimeFormatter.ofPattern(formatearFecha));
        }
    }

    /**
     * Obtiene la fecha final
     * @return LocalDate
     */
    public static String obtenerFechaFinalMedicion(){
        String formatearFecha = "dd/MM/yyyy - 00:00:00";

        LocalDate fecha = meteo.stream().max((c, c1) -> Integer.compare(c.getDia(), c1.getDia()))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia())).get();

        LocalDate fecha2 = contamina.stream().max((c, c1) -> Integer.compare(c.getDia(), c1.getDia()))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia())).get();

        if (fecha.equals(fecha2)) {
            return fecha.format(DateTimeFormatter.ofPattern(formatearFecha));
        }else if(fecha.getDayOfMonth() > fecha2.getDayOfMonth()) {
            return fecha.format(DateTimeFormatter.ofPattern(formatearFecha));
        }else {
            return fecha2.format(DateTimeFormatter.ofPattern(formatearFecha));
        }
    }


    /**
     * Obtiene la media diaria de una medición
     * @param medicion {@link Medicion}
     * @return
     */
    public static double obtenerMediaDiaria(Medicion medicion){
            double media = Utils.obtenerHorasValidadas(medicion.getHoras())
                    .stream()
                    .mapToDouble(value -> Double.parseDouble(value.getValor()))
                    .summaryStatistics().getAverage();

            return Math.round(media * 100d) / 100d;
    }

    /**
     * Obtiene la fecha de una medición
     * @param medicion {@link Medicion}
     * @return
     */
    public static LocalDate obtenerFechaMedicion(Medicion medicion){
        LocalDate fecha = LocalDate.of(medicion.getAno(), medicion.getMes(), medicion.getDia());
        return fecha;
    }




    //////
    //////TEMPORAL
    //////

    //TODO: ESTO DE ABAJO ES TEMPORAL; AL FINALIZAR EL PROYECTO REMOVERLO SI NO LO USAMOS
    /**
     * Obtener código de la estación principal dada una ciudad
     * @param nombreCiudad
     * @return String
     */
    public static String obtenerCodigo(String nombreCiudad) { //TODO: Al finalizar proyecto, comprobar public, privates, etc
        Optional<UbicacionEstaciones> estacion = estacionesUbi.stream().filter(ubicacionEstaciones ->
                ubicacionEstaciones.getEstacion_municipio().equalsIgnoreCase(nombreCiudad)).findFirst();
        return estacion.get().getEstacion_codigo();
    }

    public static List<String> obtenerEstaciones(String ciudadd) {

        String codigo = obtenerCodigo(ciudadd);

        List<UbicacionEstaciones> estacion = estacionesUbi.stream().filter(ubicacionEstaciones ->
                        codigo.substring(6).equals(ubicacionEstaciones.getEstacion_codigo().substring(6)))
                .collect(Collectors.toList());
       return estacion.stream().map(s -> s.getEstacion_municipio()).collect(Collectors.toList());

    }

    /**
     * Obtiene las horas validadas
     * @param horas
     * @return
     */
    public static List<Hora> obtenerHorasValidadas(Hora[] horas){
        return Arrays.stream(horas).filter(hora -> hora.getValidation().equals("V"))
                .collect(Collectors.toList());
    }


}
