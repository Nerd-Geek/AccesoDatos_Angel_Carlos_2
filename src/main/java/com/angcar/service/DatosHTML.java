package com.angcar.service;

import com.angcar.model.Medicion;
import com.angcar.model.UbicacionEstaciones;
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
    static private final String PATH_IMAGES = "src/main/resources/image/";
    private String nombreCiudad;

    /*System.out.println(ARGS[0]); //Nombre de la ciudad
                    System.out.println(Utils.formatearFechaMedicion(listaMeteorizacion)); //Fecha inicio medición
                    System.out.println(Utils.formatearFechaMedicion(listaContaminacion)); //Fecha final medición
                    Utils.obtenerEstaciones(ARGS[0]); //Estaciones asociadas*/

    public void procesarDatosPorCiudad(String nombreCiudad){

        this.nombreCiudad = nombreCiudad;

        //Localizar código de ciudad //TODO: REFACTORIZAR ESTO
        Optional<List<UbicacionEstaciones>> listaEstaciones = Utils.filtrarPorCiudad(nombreCiudad);
        String codigoCiudad = Utils.filtrarPorCiudad(nombreCiudad).get().get(0).getEstacion_codigo(); //TODO: Si queremos expandir y agregar zonas, hay que editar esto

        if (listaEstaciones.isPresent()){
            procesarDatosPorCode(codigoCiudad);
        }else{
            System.out.printf("Ciudad no encontrada: %s", nombreCiudad);
            System.exit(0);
        }
    }

        private void procesarDatosPorCode(String codigoCiudad){
            //Filtrar por ciudad pasada por parámetro
            List<Medicion> listaMeteorizacion = Utils.filtrarMeteorizacion(codigoCiudad);
            List<Medicion> listaContaminacion = Utils.filtrarContaminacion(codigoCiudad);

            System.out.println(MedicionesService.medicionMaximaMomento(listaMeteorizacion));

        /////////////////////////////
        //INFORMACIÓN METEOROLÓGICA// /TODO: magnMeteo
        /////////////////////////////

            Utils.getMagnMeteo().forEach((magnitudMeteorizacion) ->
            {
                try {
                   ChartUtilities.saveChartAsPNG(new File("src/main/resources/image/" +
                                   magnitudMeteorizacion.getCodigo_magnitud() +".png"),
                           datosMedicion(listaMeteorizacion, magnitudMeteorizacion.getDescripcion_magnitud(),
                                    magnitudMeteorizacion.getCodigo_magnitud()), 800, 800);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Utils.getMagnContamina().forEach((magnitudContaminacion) -> {
                try {
                    ChartUtilities.saveChartAsPNG(new File(PATH_IMAGES +
                                    magnitudContaminacion.getCodigo_magnitud() + ".png"),
                            datosMedicion(listaContaminacion, magnitudContaminacion.getDescripcion_magnitud(),
                                    magnitudContaminacion.getCodigo_magnitud()), 800, 800);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("Datos procesados.");
        }

        private JFreeChart datosMedicion(List<Medicion> listaMedicion,String descripcion_magnitud, int idMagnitud) {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //Filtrar por magnitud
            Optional<List<Medicion>> listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, listaMedicion);

            listaMediciones.ifPresent(medicions -> medicions.forEach(medicion ->
                    dataset.setValue(Utils.obtenerMediaDiaria(medicion),
                            "Día " + medicion.getDia(), "Día " + medicion.getDia())));


            /*if (listaMediciones.isPresent()) {
                //Procesar los datos
                Optional<Double> mediaTemperatura = MedicionesService.medicionMedia(listaMediciones.get());
                Optional<Double> maximaTemperatura = MedicionesService.medicionMaxima(listaMediciones.get());
                Optional<Double> minimaTemperatura = MedicionesService.medicionMinima(listaMediciones.get());

                mediaTemperatura.ifPresent(aDouble -> dataset.setValue(aDouble, "Media", "Media"));
                maximaTemperatura.ifPresent(aDouble -> dataset.setValue(aDouble, "Máxima", "Máxima"));
                minimaTemperatura.ifPresent(aDouble -> dataset.setValue(aDouble, "Mínima", "Mínima"));
            }*/

            return ChartFactory.createBarChart3D(nombreCiudad, "Magnitud",
                    descripcion_magnitud, dataset, PlotOrientation.HORIZONTAL, true,
                    true, false);
        }
}