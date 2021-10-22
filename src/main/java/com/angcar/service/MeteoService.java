package com.angcar.service;

import com.angcar.model.Medicion;
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
    public static String temperaturaMediaMensual(List<Medicion> medicion){

        //SUMAR TODAS LAS TEMPERATURAS DE LAS MAGNITUDES Y HACER MEDIA POR MES

        //Utils.prueba();

        List<Medicion> listaMagnitudes = Utils.obtenerMagnitudLista(83, medicion);
        listaMagnitudes.stream().map(medicion1 -> Utils.obtenerHorasValidadas(medicion1.getDayHoras()))
                .collect(Collectors.toList());


       System.out.println(listaMagnitudes);

        //TODO: AHORA DE LAS MAGNITUDES HAY QUE COGER todas las temperaturas y devolver la media


        return "";
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