package com.angcar.service;

import com.angcar.io.ReaderFiles;
import com.angcar.model.MagnitudContaminacion;
import com.angcar.model.MagnitudMeteorizacion;
import com.angcar.model.Medicion;
import com.angcar.util.Utils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;

    public class DatosHTML {
        private static List<MagnitudContaminacion> magnContamina;
        private static List<MagnitudMeteorizacion> magnMeteo;

        public DatosHTML(){
            magnContamina = ReaderFiles.readDataOfPathMagnitudContaminacion();
            magnMeteo = ReaderFiles.readDataOfPathMagnitudMeteorizacion();
        }

        public static void mediciones(List<Medicion> listaMeteorizacion, List<Medicion> listaContaminacion){

            /////////////////////////////
            //INFORMACIÓN METEOROLÓGICA// /TODO: magnMeteo
            /////////////////////////////

            meteorizacion(listaMeteorizacion, "Velocidad del viento", 81);
            meteorizacion(listaMeteorizacion, "Dirección del viento", 82);
            meteorizacion(listaMeteorizacion, "Temperatura", 83);
            meteorizacion(listaMeteorizacion, "Humedad relativa", 86);
            meteorizacion(listaMeteorizacion, "Presión atmosférica", 87);
            meteorizacion(listaMeteorizacion, "Radiación solar", 88);
            meteorizacion(listaMeteorizacion, "Precipitación", 89);

            /////////////////////////////
            //INFORMACIÓN CONTAMINACION// //TODO: Utils.magnContamina
            /////////////////////////////

            magnContamina.stream().forEach(magnitudContaminacion ->
                    contaminacion(listaContaminacion, magnitudContaminacion.getDescripcion_magnitud(),
                            magnitudContaminacion.getCodigo_magnitud()));
        }

        private static JFreeChart meteorizacion(List<Medicion> listaMeteorizacion, String nombreMagnitud, int idMagnitud) {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();


            double mediaTemperatura = MeteoService.medicionMedia(listaMeteorizacion,idMagnitud);
            dataset.setValue(mediaTemperatura, "Media", "Media");

            double maximaTemperatura =MeteoService.medicionMaxima(listaMeteorizacion,idMagnitud);
            dataset.setValue(maximaTemperatura, "Máxima", "Máxima");

            double minimaTemperatura = MeteoService.medicionMinima(listaMeteorizacion,idMagnitud);
            dataset.setValue(minimaTemperatura, "Mínima", "Mínima");


            JFreeChart chart = ChartFactory.createBarChart3D("Ciudad", "Magnitud",
                    "Meteorización", dataset, PlotOrientation.HORIZONTAL, true,
                    true, false);
            return chart;
        }

        private static JFreeChart contaminacion(List<Medicion> listaContaminacion, String nombreMagnitud, int idMagnitud) {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            double mediaTemperatura = MeteoService.medicionMedia(listaContaminacion,idMagnitud);
            dataset.setValue(mediaTemperatura, "Media", "Media");

            double maximaTemperatura =MeteoService.medicionMaxima(listaContaminacion,idMagnitud);
            dataset.setValue(maximaTemperatura, "Máxima", "Máxima");

            double minimaTemperatura = MeteoService.medicionMinima(listaContaminacion,idMagnitud);
            dataset.setValue(minimaTemperatura, "Mínima", "Mínima");


            JFreeChart chart = ChartFactory.createBarChart3D("Ciudad", "Magnitud",
                    "Meteorización", dataset, PlotOrientation.HORIZONTAL, true,
                    true, false);
            return chart;
        }
    }

