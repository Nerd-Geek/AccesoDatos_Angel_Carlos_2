package com.angcar.util;

import com.angcar.io.ReaderFiles;
import com.angcar.model.*;

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

    /**
     * Obtiene una lista de las magnitudes según el nombre de la magnitud dada una lista
     * @param nombreMagnitud
     * @return
     */
    public static List<Medicion> obtenerMagnitudLista(String nombreMagnitud, List<Medicion> lista){
        List<Medicion> estacion = lista.stream().filter(nombreMagn ->
                nombreMagn.getMagnitud().equalsIgnoreCase(nombreMagnitud)).collect(Collectors.toList());
        return estacion;
    }

    /**
     * Filtra por ciudad
     * @param nombreCiudad
     * @return List<Medicion>
     */

    public static List<UbicacionEstaciones> filtrarPorCiudad(String nombreCiudad) {
        return estacionesUbi.stream().filter(ubicacionEstaciones ->
                ubicacionEstaciones.getEstacion_municipio().equalsIgnoreCase(nombreCiudad)).collect(Collectors.toList());
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
     * @param medicion
     * @return LocalDate
     */
    private static LocalDate obtenerFechaInicioMedicion(List<Medicion> medicion){
        LocalDate fecha = medicion.stream().min((c, c1) -> Integer.compare(c.getDia(), c1.getDia()))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia())).get();

        return fecha;
    }

    /**
     * Obtiene la fecha final
     * @param medicion
     * @return LocalDate
     */
    private static LocalDate obtenerFechaFinalMedicion(List<Medicion> medicion){
        LocalDate fecha = medicion.stream().max((c, c1) -> Integer.compare(c.getDia(), c1.getDia()))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia())).get();

        return fecha;
    }

    /**
     * Formatea la fecha en formato de España
     * @param medicion
     * @return List<String>
     */
    public static List<String> formatearFechaMedicion(List<Medicion> medicion){ //TODO: CHANGE CODE (OPTIMIZAR)
        List<String> fecha = new ArrayList<>();

        fecha.add(obtenerFechaInicioMedicion(medicion).format(DateTimeFormatter.ofPattern("dd/MM/yyyy - 00:00:00")));
        fecha.add(obtenerFechaFinalMedicion(medicion).format(DateTimeFormatter.ofPattern("dd/MM/yyyy - 00:00:00")));

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


    /**
     * Obtiene el código de magnitud dado un nombre
     * @param nombreMagnitud
     * @return int
     */
    public static int obtenerCodigoMagnitudMeteo(String nombreMagnitud) {
        Optional<MagnitudMeteorizacion> estacion = magnMeteo.stream().filter(ubicacionEstaciones ->
                ubicacionEstaciones.getDescripcion_magnitud().contains(nombreMagnitud)).findFirst();
        return estacion.get().getCodigo_magnitud();
    }

    public static List<String> nombreEstacion(String ciudadd) {

        String codigo = obtenerCodigo(ciudadd);
        List<UbicacionEstaciones> nombreEstacion;
        List<UbicacionEstaciones> estacion = estacionesUbi.stream().filter(ubicacionEstaciones ->
                codigo.substring(6).equals(ubicacionEstaciones.getEstacion_codigo().substring(6)))
                .collect(Collectors.toList());

        return estacion.stream().map(s ->s.getEstacion_municipio()).collect(Collectors.toList());
    }
}
