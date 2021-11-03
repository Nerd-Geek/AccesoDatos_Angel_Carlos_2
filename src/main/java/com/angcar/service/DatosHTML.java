package com.angcar.service;

import com.angcar.ProcesamientoDatos;
import com.angcar.model.*;
import com.angcar.util.Utils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Datos de HTML
 */
public class DatosHTML {
    private String nombreCiudad;
    static private StringBuilder stringHTMLData;
    public static StringBuilder getStringHTMLData() {
        return stringHTMLData;
    }

    /**
     * Reset HTML Data
     */
    public static void resetHTMLData(){

        if (stringHTMLData !=null) {
            stringHTMLData.setLength(0);
        }
    }

    /**
     * Procesar los datos por ciudad
     * @param nombreCiudad {@link String}
     */
    public void procesarDatosPorCiudad(String nombreCiudad){

        this.nombreCiudad = nombreCiudad;

        Optional<List<UbicacionEstaciones>> listaEstaciones = Utils.filtrarPorCiudad(nombreCiudad);
        String codigoCiudad = Utils.filtrarPorCiudad(nombreCiudad).get().get(0).getStation_code();

        if (listaEstaciones.isPresent()){
            codigoCiudad = listaEstaciones.get().get(0).getStation_code();
        }

        if (listaEstaciones.isPresent()){
            procesarDatosPorCode(codigoCiudad);
        }else{
            System.out.printf("Ciudad no encontrada: %s", nombreCiudad);
            System.exit(0);
        }
    }

    /**
     * Procesar datos por código de ciudad
     * @param codigoCiudad {@link String}
     */
        private void procesarDatosPorCode(String codigoCiudad){
            stringHTMLData = new StringBuilder();

            //Filtrar por ciudad pasada por parámetro
            List<Medicion> listaMeteorizacion = Utils.filtrarMeteorizacion(codigoCiudad);
            List<Medicion> listaContaminacion = Utils.filtrarContaminacion(codigoCiudad);

            Predicate<Magnitud> filtroMedicion = (Magnitud m) -> m.getCode_magnitude() == 83 || m.getCode_magnitude() == 88
                    || m.getCode_magnitude() == 89 || m.getCode_magnitude() == 86 || m.getCode_magnitude() == 81;

            Predicate<Magnitud> filtroContamina = (Magnitud m) ->
                    m.getCode_magnitude() != -1;

            procesarDatosMeteo(listaMeteorizacion, filtroMedicion);
            procesarDatosContamina(listaContaminacion, filtroContamina);

            System.out.println("Datos procesados.");
        }

    /**
     * Procesar los datos de meteorización
     * @param listaMeteo {@link List}<{@link Medicion}>
     * @param filtro {@link Predicate}<{@link Magnitud}>
     */
    private void procesarDatosMeteo(List<Medicion> listaMeteo, Predicate<Magnitud> filtro) {
        stringHTMLData.append("<h1>Meteorología</h1>\n");

        Utils.getMagnMeteo().stream().filter(filtro).forEach((magnitudMeteo) -> {
            String actualPath = "image" + File.separator + magnitudMeteo.getCode_magnitude() + ".png";
            int idMagnitud = magnitudMeteo.getCode_magnitude();


            //Filtrar por magnitud
            List<Medicion> listaMediciones;

            if (Utils.obtenerMagnitudLista(idMagnitud, listaMeteo).isPresent()){
                listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, listaMeteo).get();

                if (!listaMediciones.isEmpty()){
                    try {
                        if (magnitudMeteo.getCode_magnitude() == 89){

                            ChartUtilities.saveChartAsPNG(
                                    new File(ProcesamientoDatos.path_destination + actualPath),
                                    datosMedicion(listaMeteo, magnitudMeteo.getDescription_magnitude())
                                    , 800, 800);

                            //MEDICIÓN MENSUAL
                            double valorMediomensual = 0;
                            Optional<Double> medicionOpt = MedicionesService.medicionMedia(listaMediciones);
                            if (medicionOpt.isPresent()) {
                                valorMediomensual = medicionOpt.get();
                            }

                            //PRECIPITACIÓN DE CADA DÍA QUE HA LLOVIDO
                            StringBuilder tabla = new StringBuilder();
                            tabla.append("\n<table>\n" +
                                    "<tr>\n" +
                                    "<td>Día</td>\n" +
                                    "<td>Precipitación</td>\n" +
                                    "</tr>\n");
                            MedicionesService.listaDiasPrecipitacion(listaMediciones)
                                    .forEach((key, value) ->
                                            tabla.append("<tr>\n" + "<td>").append(key).append("</td>\n")
                                                    .append("<td>").append(value).append("</td>\n").append("</tr>\n")
                                    );
                            tabla.append("</table>\n");

                            stringHTMLData.append(String.format("\n<h2>%s</h2>\n" +
                                            "<p>Precipitación media mensual: %s</p>\n" +
                                            "<p>Lista de los días que ha llovido y precipitación de cada día:</p>\n" +
                                            "%s" +
                                            "<p>Histograma por días de precipitación:</p>\n" +
                                            "<img src=\"%s\" />",
                                    magnitudMeteo.getDescription_magnitude(), valorMediomensual,
                                    tabla, actualPath));
                        }
                        //Si es otro tipo de medición, tratar de manera habitual
                        else {
                            ChartUtilities.saveChartAsPNG(new File(ProcesamientoDatos.path_destination + actualPath),
                                    datosMedicion(listaMeteo, magnitudMeteo.getDescription_magnitude())
                                    , 800, 800);


                            //MEDICIÓN MÁXIMA
                            String medicionMaxMomento = "No se ha encontrado valor máximo."; //Por defecto

                            Map.Entry<Medicion, Optional<Hora>> mapEntry =  MedicionesService
                                    .medicionMaximaMomento(listaMediciones);
                            if (mapEntry.getValue().isPresent()){
                                medicionMaxMomento = "Valor máximo <b>" + mapEntry.getValue().get().getValue()
                                        + " " + magnitudMeteo.getUndid()
                                        + "</b> el día <b>" + Utils.obtenerFechaMedicion(mapEntry.getKey())
                                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                        + "</b> a las <b>" + String.format("%02d:00:00"
                                        , mapEntry.getValue().get().getNumHour()) + "</b>";
                            }

                            //MEDICIÓN MÍNIMA
                            String medicionMinMomento = "No se ha encontrado valor máximo."; //Por defecto

                            mapEntry = MedicionesService.medicionMinimaMomento(listaMediciones);
                            if (mapEntry.getValue().isPresent()){
                                medicionMinMomento = "Valor mínimo <b>" + mapEntry.getValue().get().getValue()
                                         + " " + magnitudMeteo.getUndid()
                                        + "</b> el día <b>" + Utils.obtenerFechaMedicion(mapEntry.getKey())
                                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                        + "</b> a las <b>" + String.format("%02d:00:00"
                                        , mapEntry.getValue().get().getNumHour()) + "</b>";
                            }

                            //VALOR MEDIO MENSUAL
                            double valorMediomensual = 0;
                            Optional<Double> medicionOpt = MedicionesService.medicionMedia(listaMediciones);
                            if (medicionOpt.isPresent()){
                                valorMediomensual = medicionOpt.get();
                            }

                            stringHTMLData.append(String.format("\n<h2>%s</h2>\n" +
                                            "<p>Valor medio mensual: %s</p>\n" +
                                            "<p>Momento y valor máximo impacto: %s</p>\n" +
                                            "<p>Momento y valor mínimo impacto: %s</p>\n" +
                                            "<p>Gráfica de la evolución del impacto:</p>\n" +
                                            "<img src=\"%s\" />",
                                    magnitudMeteo.getDescription_magnitude(), valorMediomensual,
                                    medicionMaxMomento, medicionMinMomento, actualPath));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            else{
                System.err.println("Error: no se ha podido procesar la lista de mediciones");
                System.exit(0);
            }
        });
    }

    /**
     * Procesar los datos de contaminación
     * @param listaContamina {@link List}<{@link Medicion}>
     * @param filtro {@link Predicate}<{@link Magnitud}>
     */
    private void procesarDatosContamina(List<Medicion> listaContamina, Predicate<Magnitud> filtro) {
        stringHTMLData.append("<h1>Contaminación</h1>\n");

        Utils.getMagnContamina().stream().filter(filtro).forEach((magnitudContamina) -> {
            String actualPath = "image" + File.separator + magnitudContamina.getCode_magnitude() + ".png";
            int idMagnitud = magnitudContamina.getCode_magnitude();


            //Filtrar por magnitud
            List<Medicion> listaMediciones;

            if (Utils.obtenerMagnitudLista(idMagnitud, listaContamina).isPresent()){
                listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, listaContamina).get();

                if (!listaMediciones.isEmpty()){
                    try {

                        ChartUtilities.saveChartAsPNG(new File(ProcesamientoDatos.path_destination + actualPath),
                                datosMedicion(listaContamina, magnitudContamina.getDescription_magnitude())
                                , 800, 800);

                        //MEDICIÓN MÁXIMA
                        String medicionMaxMomento = "No se ha encontrado valor máximo."; //Por defecto

                        Map.Entry<Medicion, Optional<Hora>> mapEntry =  MedicionesService
                                .medicionMaximaMomento(listaMediciones);
                        if (mapEntry.getValue().isPresent()){
                            medicionMaxMomento = "Valor máximo <b>" + mapEntry.getValue().get().getValue()
                                    + " " + magnitudContamina.getUndid()
                                    + "</b> el día <b>" + Utils.obtenerFechaMedicion(mapEntry.getKey())
                                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                    + "</b> a las <b>" + String.format("%02d:00:00"
                                    , mapEntry.getValue().get().getNumHour()) + "</b>";
                        }

                        //MEDICIÓN MÍNIMA
                        String medicionMinMomento = "No se ha encontrado valor máximo."; //Por defecto

                        mapEntry = MedicionesService.medicionMinimaMomento(listaMediciones);
                        if (mapEntry.getValue().isPresent()){
                            medicionMinMomento = "Valor mínimo <b>" + mapEntry.getValue().get().getValue()
                                    + " " + magnitudContamina.getUndid()
                                    + "</b> el día <b>" + Utils.obtenerFechaMedicion(mapEntry.getKey())
                                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                    + "</b> a las <b>" + String.format("%02d:00:00"
                                    , mapEntry.getValue().get().getNumHour()) + "</b>";
                        }

                        double valorMediomensual = 0;
                        Optional<Double> medicionOpt = MedicionesService.medicionMedia(listaMediciones);
                        if (medicionOpt.isPresent()){

                            valorMediomensual = medicionOpt.get();
                        }

                        stringHTMLData.append(String.format("\n<h2>%s</h2>\n" +
                                        "<p>Valor medio mensual: %s</p>\n" +
                                        "<p>Momento y valor máximo impacto: %s</p>\n" +
                                        "<p>Momento y valor mínimo impacto: %s</p>\n" +
                                        "<p>Gráfica de la evolución del impacto:</p>\n" +
                                        "<img src=\"%s\" />",
                                magnitudContamina.getDescription_magnitude(), valorMediomensual,
                                medicionMaxMomento, medicionMinMomento, actualPath));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            else{
                System.err.println("Error: no se ha podido procesar la lista de mediciones");
                System.exit(0);
            }
        });
    }

    /**
     * Crea la gráfica con los datos de medición
     * @param listaMediciones Lista de mediciones {@link List}<{@link Medicion}>
     * @param descripcion_magnitud Descripción de magnitud {@link String}
     * @return List<Medicion>
     */
        private JFreeChart datosMedicion(List<Medicion> listaMediciones,String descripcion_magnitud) {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            listaMediciones.forEach(medicion ->
                    dataset.setValue(Utils.obtenerMediaDiaria(medicion),
                            "Día " + medicion.getDay(), "Día " + medicion.getDay()));

            return ChartFactory.createBarChart3D(nombreCiudad, descripcion_magnitud,
                    descripcion_magnitud, dataset, PlotOrientation.VERTICAL, true,
                    true, false);
        }
}