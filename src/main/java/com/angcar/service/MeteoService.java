package com.angcar.service;

import com.angcar.model.Medicion;
import com.angcar.util.Hora;
import com.angcar.util.Utils;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MeteoService {

    /**
     * TEMPERATURA
     */
    public static Double temperaturaMediaMensual(List<Medicion> medicion, int idMagnitud){
        List<Medicion> listaMagnitudes = Utils.obtenerMagnitudLista(idMagnitud, medicion);

        listaMagnitudes.stream().mapToDouble(medicion1 ->
                Utils.obtenerHorasValidadas(medicion1.getDayHoras()).stream().mapToDouble(value ->
                        Double.parseDouble(value.getValor())).summaryStatistics().getAverage())
                .summaryStatistics().getAverage();

        return listaMagnitudes.stream().mapToDouble(medicion1 ->
                        Utils.obtenerHorasValidadas(medicion1.getDayHoras()).stream().mapToDouble(value ->
                                Double.parseDouble(value.getValor())).summaryStatistics().getAverage())
                .summaryStatistics().getAverage();
    }

    public void momentoAndTemperaturaMax(){
            //maxBy
    }

    public void momentoAndTemperaturaMin(){
            //minBy
    }

    /**
     * RADIACIÓN SOLAR
     */
    public void radiacionSolarMediaMensual(List<Medicion> medicion){
       // List<Medicion> listaMagnitudes = Utils.obtenerMagnitudLista("Temperatura", medicion);
    }

    public void momentoAndRadiacionMax(){

    }

    public void momentoAndRadiacionMin(){

    }

    /**
     * PRECIPITACIÓN
     */
    public void precipitacionMediaMensual(){

    }

    public void listRainDaysAndPrecipitacionEachDay(){
        //TODO: Lista de días que ha llovido y precipitación de cada día.
    }

    public void histogramaPorDiasPrecipitación(){

    }

    /**
     * HUMEDAD
     */
    public void humedadRelativaMediaMensual(){

    }

    public void momentoAndHumedadMax(){

    }

    public void momentoAndHumedadMin(){

    }

    /**
     * VELOCIDAD DEL VIENTO
     */
    public void velocidadMediaVientoMensual(){

    }

    public void momentoAndMaxVel(){

    }

    public void momentoAndMinVel(){

    }
}



/*
private String provincia;
    private String municipio;
    private String estacion;
    private String magnitud;
    private String punto_muestreo;
    private int ano;
    private int mes;
    private int dia;
    private ArrayList horas;
    private ArrayList validacion;
 */