package com.angcar.service;

import com.angcar.model.Medicion;
import com.angcar.model.Hora;
import com.angcar.util.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MedicionesService {

    /**
     * TEMPERATURA
     */
    public static Optional<Double> medicionMedia(List<Medicion> listaMediciones) {
            double media = listaMediciones.stream()
                    .mapToDouble(Utils::obtenerMediaDiaria)
                    .summaryStatistics().getAverage();

            return Optional.of(Math.round(media * 100d) / 100d);
    }


    public static String medicionMaximaMomento(List<Medicion> listaMediciones) {
        //Mapear la medición con su hora máxima
        Map<Medicion, Hora> medicionesMax = listaMediciones.stream()
                .collect(Collectors.toMap(
                        medicion -> medicion, (o) -> Arrays.stream(o.getHoras())
                                    .max(Comparator.comparing(Hora::getValor)).get(),(o, o2) -> o
                ));

        //Conseguir la medición con la hora máxima y meterla en un "'Map.Entry'"
        Map.Entry<Medicion, Hora> mapEntry = Collections.max(medicionesMax.entrySet(),
                Map.Entry.comparingByValue(Comparator.comparingDouble(o -> Double.parseDouble(o.getValor()))));

        //TODO: ESTO LLEVARLO A OTRO MÉTODO
            return "Valor máximo " + mapEntry.getValue().getValor()
                    + " el día " + Utils.obtenerFechaMedicion(mapEntry.getKey())
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + " a las " + String.format("%02d:00:00" , mapEntry.getValue().getNumHora());
    }


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
     * @param listaMediciones
     * @return
     */
    public static Map<LocalDate, Double> listaDiasPrecipitacion(List<Medicion> listaMediciones) {
        return listaMediciones.stream()
                .collect(Collectors.toMap(Utils::obtenerFechaMedicion,
                        Utils::obtenerMediaDiaria, (k1, k2) -> k1));
    }
}