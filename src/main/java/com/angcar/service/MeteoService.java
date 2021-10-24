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
    public static Optional <Double> medicionMedia(List<Medicion> medicion, int idMagnitud) {
        List<Medicion> listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, medicion);

            double media = listaMediciones.stream()
                    .mapToDouble(medicion1 -> Utils.obtenerMediaDiaria(medicion1))
                    .summaryStatistics().getAverage();

            return Optional.of(Math.round(media * 100d) / 100d);
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


    public static Optional <Double> medicionMaxima(List<Medicion> medicion, int idMagnitud) {
        List<Medicion> listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, medicion);

            double maxima = listaMediciones.stream()
                    .mapToDouble(medicion1 -> Utils.obtenerHorasValidadas(medicion1.getHoras()).stream()
                            .mapToDouble(value -> Double.parseDouble(value.getValor()))
                            .summaryStatistics().getMax())
                    .summaryStatistics().getMax();

            return Optional.of(Math.round(maxima * 100d) / 100d);
    }

    public static Optional<Double> medicionMinima(List<Medicion> medicion, int idMagnitud) {
        List<Medicion> listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, medicion);
            double minima = listaMediciones.stream()
                    .mapToDouble(medicion1 -> Utils.obtenerHorasValidadas(medicion1.getHoras()).stream()
                            .mapToDouble(value -> Double.parseDouble(value.getValor()))
                            .summaryStatistics().getMin())
                    .summaryStatistics().getMin();
            return Optional.of(Math.round(minima * 100d) / 100d);
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