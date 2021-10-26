package com.angcar.util;

import com.angcar.ProcesamientoDatos;
import com.angcar.io.ReaderFiles;
import com.angcar.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Utils {
    private static List<UbicacionEstaciones> estacionesUbi;
    private static List<Contaminacion> contamina;
    private static List<Meteorizacion> meteo;
    //private static List<ZonasMunicipio> municipio;
    private static List<MagnitudContaminacion> magnContamina;
    private static List<MagnitudMeteorizacion> magnMeteo;
    static public long init_time;

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
     * @return Optional con una lista de mediciones
     */
    public static Optional<List<Medicion>> obtenerMagnitudLista(int idMagnitud, List<Medicion> lista){
        return Optional.of(lista.stream().filter(nombreMagn ->
                        nombreMagn.getMagnitud().equalsIgnoreCase(String.valueOf(idMagnitud)))
                .collect(Collectors.toList()));

    }

    /**
     * Filtra por ciudad
     * @param nombreCiudad El nombre de la ciudad
     * @return Optional con una lista de mediciones
     */

    public static Optional<List<UbicacionEstaciones>> filtrarPorCiudad(String nombreCiudad) {
        return Optional.of(estacionesUbi.stream().filter(ubicacionEstaciones ->
                ubicacionEstaciones.getEstacion_municipio().equalsIgnoreCase(nombreCiudad)).collect(Collectors.toList()));
    }

    /**
     * Filtrar por meteorización
     * @param codigoCiudad {@link String}
     * @return Lista de mediciones
     */
    public static List<Medicion> filtrarMeteorizacion(String codigoCiudad) {
        return meteo.stream().filter(punto_muestreo -> punto_muestreo.getPunto_muestreo()
                .contains(codigoCiudad)).collect(Collectors.toList());
    }

    /**
     * Filtrar por contaminación
     * @param codigoCiudad {@link String}
     * @return Lista de contaminaciones
     */
    public static List<Medicion> filtrarContaminacion(String codigoCiudad) {
        return contamina.stream().filter(punto_muestreo -> punto_muestreo.getPunto_muestreo()
                .contains(codigoCiudad)).collect(Collectors.toList());
    }

    /**
     * Obtiene la fecha de inicio
     * @return LocalDate
     */
    public static String obtenerFechaInicioMedicion(){
        String formatearFecha = "dd/MM/yyyy - 00:00:00";
        Optional<LocalDate> fecha = meteo.stream().min(Comparator.comparingInt(Medicion::getDia))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia()));

        Optional<LocalDate> fecha2 = contamina.stream().min(Comparator.comparingInt(Medicion::getDia))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia()));

        if (fecha.isPresent() && fecha2.isPresent()){
            if (fecha.get().equals(fecha2.get())) {
                return fecha.get().format(DateTimeFormatter.ofPattern(formatearFecha));
            }else if(fecha.get().getDayOfMonth() < fecha2.get().getDayOfMonth())
                return fecha.get().format(DateTimeFormatter.ofPattern(formatearFecha));
            else {
                return fecha2.get().format(DateTimeFormatter.ofPattern(formatearFecha));
            }
        }
        else{
            return "";
        }


    }

    /**
     * Obtiene la fecha final
     * @return LocalDate
     */
    public static String obtenerFechaFinalMedicion(){
        String formatearFecha = "dd/MM/yyyy - 00:00:00";

        Optional<LocalDate> fecha = meteo.stream().max(Comparator.comparingInt(Medicion::getDia))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia()));

        Optional<LocalDate> fecha2 = contamina.stream().max(Comparator.comparingInt(Medicion::getDia))
                .map(s -> LocalDate.of(s.getAno(), s.getMes(), s.getDia()));

        if (fecha.isPresent() && fecha2.isPresent()){
            if (fecha.get().equals(fecha2.get())) {
                return fecha.get().format(DateTimeFormatter.ofPattern(formatearFecha));
            }else if(fecha.get().getDayOfMonth() > fecha2.get().getDayOfMonth())
                return fecha.get().format(DateTimeFormatter.ofPattern(formatearFecha));
            else {
                return fecha2.get().format(DateTimeFormatter.ofPattern(formatearFecha));
            }
        }
        else{
            return "";
        }
    }


    /**
     * Obtiene la media diaria de una medición
     * @param medicion {@link Medicion}
     * @return double con la media diaria
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
     * @return fecha de medición
     */
    public static LocalDate obtenerFechaMedicion(Medicion medicion){
        return LocalDate.of(medicion.getAno(), medicion.getMes(), medicion.getDia());
    }


    /**
     * Obtener código de la estación principal dada una ciudad
     * @param nombreCiudad {@link String}
     * @return String
     */
    public static String obtenerCodigo(String nombreCiudad) {
        Optional<UbicacionEstaciones> estacion = estacionesUbi.stream().filter(ubicacionEstaciones ->
                ubicacionEstaciones.getEstacion_municipio().equalsIgnoreCase(nombreCiudad)).findFirst();

        String name = "";
        if (estacion.isPresent()) name = estacion.get().getEstacion_codigo();
        return name;
    }

    /**
     * Obtener estaciones
     * @param ciudad {@link String}
     * @return Optional con una lista de las estaciones en String
     */
    public static Optional<List<String>> obtenerEstaciones(String ciudad) {

        String codigo = obtenerCodigo(ciudad);

        List<UbicacionEstaciones> estacion = estacionesUbi.stream().filter(ubicacionEstaciones ->
                        codigo.substring(6).equals(ubicacionEstaciones.getEstacion_codigo().substring(6)))
                .collect(Collectors.toList());

       return Optional.of(estacion.stream().map(UbicacionEstaciones::getEstacion_municipio).collect(Collectors.toList()));

    }

    /**
     * Obtiene las horas validadas
     * @param horas Array de horas
     * @return Lista de horas
     */
    public static List<Hora> obtenerHorasValidadas(Hora[] horas){
        return Arrays.stream(horas).filter(hora -> hora.getValidation().equals("V"))
                .collect(Collectors.toList());
    }

    /**
     * Mide el tiempo de ejecución del programa y devuelve un informe
     * @return Devuelve cuándo se ha creado el informe y cuánto tiempo ha tardado
     */
    public static String tiempoInforme() {
        double tiempo = (double) ((System.currentTimeMillis() - Utils.init_time)/1000);
        LocalDate fecha = LocalDate.now();
        String formatearFecha = "dd/MM/yyyy";
        LocalTime hora = LocalTime.now();
        String formatearHora = "HH:mm:ss";

        return "Informe generado en el día " + fecha.format(DateTimeFormatter.ofPattern(formatearFecha))
                + " a las " + hora.format(DateTimeFormatter.ofPattern(formatearHora))+ " en "+ tiempo + " segundos";
    }


}
