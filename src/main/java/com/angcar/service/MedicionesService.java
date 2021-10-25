package com.angcar.service;

import com.angcar.model.Medicion;
import com.angcar.model.Hora;
import com.angcar.util.Utils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MedicionesService {


    public static Optional<Double> medicionMedia(List<Medicion> listaMediciones) {
            double media = listaMediciones.stream()
                    .mapToDouble(Utils::obtenerMediaDiaria)
                    .summaryStatistics().getAverage();

            return Optional.of(Math.round(media * 100d) / 100d);
    }


    public static Map.Entry<Medicion, Optional<Hora>> medicionMaximaMomento(List<Medicion> listaMediciones) {
        //Mapear la medición con su hora máxima
        Map<Medicion, Optional<Hora>> medicionesMax = listaMediciones.stream()
                .collect(Collectors.toMap(
                        medicion -> medicion, (o) -> Arrays.stream(o.getHoras())
                                .filter(hora -> hora.getValidation().equals("V"))
                                .max(Comparator.comparing(Hora::getValor)),(o, o2) -> o
                ));

        //Conseguir la medición con la hora máxima y meterla en un "'Map.Entry'"
        return Collections.max(medicionesMax.entrySet(),
                Map.Entry.comparingByValue(Comparator.comparingDouble(o -> o.map(hora ->
                        Double.parseDouble(hora.getValor())).orElse(0.0))));
    }

    public static Map.Entry<Medicion, Optional<Hora>> medicionMinimaMomento(List<Medicion> listaMediciones) {
        //Mapear la medición con su hora máxima
        Map<Medicion, Optional<Hora>> medicionesMin = listaMediciones.stream()
                .collect(Collectors.toMap(
                        medicion -> medicion, (o) -> Arrays.stream(o.getHoras())
                                .filter(hora -> hora.getValidation().equals("V"))
                                .min(Comparator.comparing(Hora::getValor)),(o, o2) -> o
                ));

        //Conseguir la medición con la hora máxima y meterla en un "'Map.Entry'"
        return Collections.min(medicionesMin.entrySet(),
                Map.Entry.comparingByValue(Comparator.comparingDouble(o -> o.map(hora ->
                        Double.parseDouble(hora.getValor())).orElse(0.0))));
    }

    /*public static Map.Entry<Medicion, Hora> medicionMinimaMomento(List<Medicion> listaMediciones) {
        //Mapear la medición con su hora máxima
        Map<Medicion, Hora> medicionesMin = listaMediciones.stream()
                .collect(Collectors.toMap(
                        medicion -> medicion, (o) -> Arrays.stream(o.getHoras())
                                .min(Comparator.comparing(Hora::getValor)).get(),(o, o2) -> o
                ));

        //Conseguir la medición con la hora máxima y meterla en un "'Map.Entry'"
        return Collections.min(medicionesMin.entrySet(),
                Map.Entry.comparingByValue(Comparator.comparingDouble(o -> Double.parseDouble(o.getValor()))));
    }*/


    public static Optional<Double> medicionMaxima(List<Medicion> listaMediciones) {
            double maxima = listaMediciones.stream()
                    .mapToDouble(medicion1 -> Utils.obtenerHorasValidadas(medicion1.getHoras()).stream()
                            .mapToDouble(value -> Double.parseDouble(value.getValor()))
                            .summaryStatistics().getMax())
                    .summaryStatistics().getMax();

            return Optional.of(Math.round(maxima * 100d) / 100d);
    }

    public static Optional<Double> medicionMinima(List<Medicion> listaMediciones) {
            double minima = listaMediciones.stream()
                    .mapToDouble(medicion1 -> Utils.obtenerHorasValidadas(medicion1.getHoras()).stream()
                            .mapToDouble(value -> Double.parseDouble(value.getValor()))
                            .summaryStatistics().getMin())
                    .summaryStatistics().getMin();

            return Optional.of(Math.round(minima * 100d) / 100d);
        }


    /**
     * Lista de días que ha llovido y precipitación de cada día
     * @param listaMediciones Una lista de mediciones
     * @return Devuelve un map con le fecha de precipitación y su cantidad
     */
    public static Map<LocalDate, Double> listaDiasPrecipitacion(List<Medicion> listaMediciones) {
        return listaMediciones.stream()
                .collect(Collectors.toMap(Utils::obtenerFechaMedicion,
                        Utils::obtenerMediaDiaria, (k1, k2) -> k1));
    }
}