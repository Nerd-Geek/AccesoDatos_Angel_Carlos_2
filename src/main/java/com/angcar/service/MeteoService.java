package com.angcar.service;

import com.angcar.model.Medicion;
import com.angcar.util.Hora;
import com.angcar.util.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class MeteoService {

    /**
     * TEMPERATURA
     */
    public static Double medicionMedia(List<Medicion> medicion, int idMagnitud) {
        List<Medicion> listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, medicion);

        if (!listaMediciones.isEmpty()){
            double media = listaMediciones.stream()
                    .mapToDouble(medicion1 -> Utils.obtenerMediaDiaria(medicion1))
                    .summaryStatistics().getAverage();

            return Math.round(media * 100d) / 100d;
        }else{
            return null;
        }
    }

    public static Optional<Hora> medicionMaximaDos(List<Medicion> medicion, int idMagnitud) {

        List<Medicion> listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, medicion);

        if (!listaMediciones.isEmpty()){

            Hora[] horas = listaMediciones
                    .stream()
                    .max(Comparator
                            .comparing(medicion1 -> Arrays.stream(medicion1.getHoras())
                                    .max(Comparator
                                            .comparing(Hora::getValor))
                                    .get().getValor()))
                    .get().getHoras();


            System.out.println("Nuestra esperanza:");
            System.out.println(Arrays.stream(horas).max(Comparator.comparing(Hora::getValor)).get());

            return null;
        }else{
            return null;
        }
    }


    public static Double medicionMaxima(List<Medicion> medicion, int idMagnitud) {
        List<Medicion> listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, medicion);
        if (!listaMediciones.isEmpty()){

            double maxima = listaMediciones.stream()
                    .mapToDouble(medicion1 -> Utils.obtenerHorasValidadas(medicion1.getHoras()).stream()
                            .mapToDouble(value -> Double.parseDouble(value.getValor()))
                            .summaryStatistics().getMax())
                    .summaryStatistics().getMax();

            return Math.round(maxima * 100d) / 100d;
        }else{
            return null;
        }
    }

    public static Double medicionMinima(List<Medicion> medicion, int idMagnitud) {
        List<Medicion> listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, medicion);
        if (!listaMediciones.isEmpty()){
            double minima = listaMediciones.stream()
                    .mapToDouble(medicion1 -> Utils.obtenerHorasValidadas(medicion1.getHoras()).stream()
                            .mapToDouble(value -> Double.parseDouble(value.getValor()))
                            .summaryStatistics().getMin())
                    .summaryStatistics().getMin();

            return null;
        }else{
            return null;
            }
        }
    public static List<Medicion> listaDiasPrecipitacion(List<Medicion> medicion) {
        List<Medicion> listaMediciones = Utils.obtenerMagnitudLista(89, medicion);

        //Lista de días que ha llovido
//TODO: PRECIPITACIÓN: LISTA DE DÍAS QUE HA LLOVIDO Y PRECIPITACIÓN DE CADA DÍA.
         //listaMediciones.stream()
          //       .map((fecha, media) -> Utils.obtenerFechaMedicion(fecha), Utils.obtenerMediaDiaria(fecha))
          //               .collect(Collectors.toList());


        return listaMediciones;
    }
}