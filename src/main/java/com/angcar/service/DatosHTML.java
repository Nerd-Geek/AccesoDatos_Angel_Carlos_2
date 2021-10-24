package com.angcar.service;

import com.angcar.model.Medicion;
import com.angcar.util.Utils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DatosHTML {
    static private final String pathImages = "src/main/resources/image/";

        public static void mediciones(List<Medicion> listaMeteorizacion, List<Medicion> listaContaminacion){

            /////////////////////////////
            //INFORMACIÓN METEOROLÓGICA// /TODO: magnMeteo
            /////////////////////////////

            datosMedicion(listaMeteorizacion,"Test", 81);
            datosMedicion(listaMeteorizacion, "Dirección del viento", 82);
            datosMedicion(listaMeteorizacion, "Temperatura", 83);
            datosMedicion(listaMeteorizacion, "Humedad relativa", 86);
            datosMedicion(listaMeteorizacion, "Presión atmosférica", 87);
            datosMedicion(listaMeteorizacion, "Radiación solar", 88);
            datosMedicion(listaMeteorizacion, "Precipitación", 89);

            /////////////////////////////
            //INFORMACIÓN CONTAMINACION// //TODO: Utils.magnContamina
            /////////////////////////////

            Utils.getMagnContamina().forEach((magnitudContaminacion) -> {
                try {
                    ChartUtilities.saveChartAsPNG(new File(pathImages +
                                    magnitudContaminacion.getCodigo_magnitud() + ".png"),
                            datosMedicion(listaContaminacion, magnitudContaminacion.getDescripcion_magnitud(),
                                    magnitudContaminacion.getCodigo_magnitud()), 300, 300);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        private static JFreeChart datosMedicion(List<Medicion> listaMedicion,String descripcion_magnitud, int idMagnitud) {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //Filtrar por magnitud
            Optional<List<Medicion>> listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, listaMedicion);

            if (listaMediciones.isPresent()) {
                //Procesar datos
                Optional<Double> mediaTemperatura = MeteoService.medicionMedia(listaMediciones.get());
                Optional<Double> maximaTemperatura = MeteoService.medicionMaxima(listaMediciones.get());
                Optional<Double> minimaTemperatura = MeteoService.medicionMinima(listaMediciones.get());

                mediaTemperatura.ifPresent(aDouble -> dataset.setValue(aDouble, "Media", "Media"));
                maximaTemperatura.ifPresent(aDouble -> dataset.setValue(aDouble, "Máxima", "Máxima"));
                minimaTemperatura.ifPresent(aDouble -> dataset.setValue(aDouble, "Mínima", "Mínima"));
            }

            return ChartFactory.createBarChart3D("Ciudad", "Magnitud",
                    "Medición", dataset, PlotOrientation.HORIZONTAL, true,
                    true, false);
        }
    }

