package com.angcar.service;

import com.angcar.io.ReaderFiles;
import com.angcar.model.MagnitudContaminacion;
import com.angcar.model.MagnitudMeteorizacion;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class DatosHTML {

    private static List<MagnitudContaminacion> magnContamina = ReaderFiles.readDataOfPathMagnitudContaminacion();;
    private static List<MagnitudMeteorizacion> magnMeteo = ReaderFiles.readDataOfPathMagnitudMeteorizacion();

    public DatosHTML(){
        ReaderFiles.readDataOfPathMagnitudContaminacion();
        ReaderFiles.readDataOfPathMagnitudMeteorizacion();
    }

    public static void mediciones(List<Medicion> listaMeteorizacion, List<Medicion> listaContaminacion, String nombreCiudad) {

        /////////////////////////////
        //INFORMACIÓN METEOROLÓGICA// /TODO: magnMeteo
        /////////////////////////////

        magnMeteo.forEach((magnitudMeteorizacion) ->
        {
            try {
               ChartUtilities.saveChartAsPNG(new File("src/main/resources/image/" +
                               magnitudMeteorizacion.getCodigo_magnitud() +".png"),
                        meteorizacion(listaMeteorizacion, magnitudMeteorizacion.getDescripcion_magnitud(),
                                magnitudMeteorizacion.getCodigo_magnitud(), nombreCiudad), 300, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        /////////////////////////////
        //INFORMACIÓN CONTAMINACION// //TODO: Utils.magnContamina
        /////////////////////////////
        magnContamina.forEach((magnitudContaminacion) ->
        {
            try {
                ChartUtilities.saveChartAsPNG(new File("src/main/resources/image/" +
                                magnitudContaminacion.getCodigo_magnitud() + ".png"),
                        contaminacion(listaContaminacion, magnitudContaminacion.getDescripcion_magnitud(),
                                magnitudContaminacion.getCodigo_magnitud(), nombreCiudad), 300, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static JFreeChart meteorizacion(List<Medicion> listaMeteorizacion, String nombreMagnitud, int idMagnitud, String nombreCiudad) {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            AtomicReference<Double> mediaTemperatura = new AtomicReference<>((double) 0);
            MeteoService.medicionMedia(listaMeteorizacion,idMagnitud).ifPresent(mediaTemperatura::set);
            dataset.setValue(mediaTemperatura.get(), "Media", "Media");

            AtomicReference<Double> maximaTemperatura = new AtomicReference<>((double) 0);
            MeteoService.medicionMedia(listaMeteorizacion,idMagnitud).ifPresent(maximaTemperatura::set);
            dataset.setValue(maximaTemperatura.get(), "Máxima", "Máxima");

            AtomicReference<Double> minimaTemperatura = new AtomicReference<>((double) 0);
            MeteoService.medicionMedia(listaMeteorizacion,idMagnitud).ifPresent(minimaTemperatura::set);
            dataset.setValue(minimaTemperatura.get(), "Mínima", "Mínima");

            JFreeChart chart = ChartFactory.createBarChart3D(nombreCiudad, "Magnitud",
                    nombreMagnitud, dataset, PlotOrientation.HORIZONTAL, true,
                    true, false);
            return chart;
    }

    private static JFreeChart contaminacion(List<Medicion> listaContaminacion, String nombreMagnitud, int idMagnitud, String nombreCiudad) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        AtomicReference<Double> mediaTemperatura = new AtomicReference<>((double) 0);
        MeteoService.medicionMedia(listaContaminacion,idMagnitud).ifPresent(mediaTemperatura::set);
        dataset.setValue(mediaTemperatura.get(), "Media", "Media");

        AtomicReference<Double> maximaTemperatura = new AtomicReference<>((double) 0);
        MeteoService.medicionMedia(listaContaminacion,idMagnitud).ifPresent(maximaTemperatura::set);
        dataset.setValue(maximaTemperatura.get(), "Máxima", "Máxima");

        AtomicReference<Double> minimaTemperatura = new AtomicReference<>((double) 0);
        MeteoService.medicionMedia(listaContaminacion,idMagnitud).ifPresent(minimaTemperatura::set);
        dataset.setValue(minimaTemperatura.get(), "Mínima", "Mínima");

        JFreeChart chart = ChartFactory.createBarChart3D(nombreCiudad, "Magnitud",
                nombreMagnitud, dataset, PlotOrientation.HORIZONTAL, true,
                true, false);
        return chart;
    }
}