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
    public static Double medicionMedia(List<Medicion> medicion, int idMagnitud) {
        List<Medicion> listaMagnitudes = Utils.obtenerMagnitudLista(idMagnitud, medicion);

        double media = listaMagnitudes.stream().mapToDouble(medicion1 ->
                        Utils.obtenerHorasValidadas(medicion1.getDayHoras()).stream().mapToDouble(value ->
                                Double.parseDouble(value.getValor())).summaryStatistics().getAverage())
                .summaryStatistics().getAverage();

        return Math.round(media * 100) / 100d;
    }

    public static Double medicionMaxima(List<Medicion> medicion, int idMagnitud) {
        List<Medicion> listaMagnitudes = Utils.obtenerMagnitudLista(idMagnitud, medicion);

        double maxima = listaMagnitudes.stream().mapToDouble(medicion1 ->
                Utils.obtenerHorasValidadas(medicion1.getDayHoras()).stream().mapToDouble(value ->
                        Double.parseDouble(value.getValor())).summaryStatistics().getMax()).summaryStatistics().getMax();

        return Math.round(maxima * 100) / 100d;
    }

    public static Double medicionMinima(List<Medicion> medicion, int idMagnitud) {
        List<Medicion> listaMagnitudes = Utils.obtenerMagnitudLista(idMagnitud, medicion);

        double minima = listaMagnitudes.stream().mapToDouble(medicion1 ->
                Utils.obtenerHorasValidadas(medicion1.getDayHoras()).stream().mapToDouble(value ->
                        Double.parseDouble(value.getValor())).summaryStatistics().getMin()).summaryStatistics().getMin();
        return Math.round(minima * 100) / 100d;
    }
}