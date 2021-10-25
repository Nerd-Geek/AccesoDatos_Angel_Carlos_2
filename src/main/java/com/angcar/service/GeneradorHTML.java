package com.angcar.service;

import com.angcar.ProcesamientoDatos;
import com.angcar.model.Medicion;
import com.angcar.util.Utils;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GeneradorHTML {

    public static void generarHtml(String nombreCiudad, String codigoCiudad) throws IOException {
        List<Medicion> listaContaminacion = Utils.filtrarContaminacion(codigoCiudad);
        List<Medicion> listaMeteorizacion = Utils.filtrarMeteorizacion(codigoCiudad);
        File htmlTemplateFile = new File("src/main/resources/template.html");
        String htmlString = FileUtils.readFileToString(htmlTemplateFile);
        String title = "Servicio meteorologico y contaminación";

        String estacion = String.valueOf(Utils.obtenerEstaciones(nombreCiudad)).replace("[", "").replace("]", "");

        String fechaIniMeteo = Utils.obtenerFechaInicioMedicion();
        String fechaFinMeteo = Utils.obtenerFechaFinalMedicion();

        String tempep = meteo("Temperatura");
        String radip = meteo("Radiación solar");
        String prep = meteo("Precipitación");
        String humep = meteo("Humep");
        String velop = meteo("Velocidad del viento");



        List<String> Tmax = medidas(listaMeteorizacion,83);

        String RMedia = String.valueOf(media(listaMeteorizacion, 88));
        String RMaxima = String.valueOf(max(listaMeteorizacion, 88));
        String RMinima = String.valueOf(min(listaMeteorizacion, 88));
        String radiacionG = "../image/88.png";

        htmlString = htmlString.replace("$title", title);
        htmlString = htmlString.replace("ciudad", nombreCiudad);
        htmlString = htmlString.replace("estacion", estacion);
        htmlString = htmlString.replace("fechaIniMeteo", fechaIniMeteo);
        htmlString = htmlString.replace("fechaFinMeteo", fechaFinMeteo);
        htmlString = htmlString.replace("tempep", tempep);
        htmlString = htmlString.replace("Tmax", String.valueOf(Tmax));
        //htmlString = htmlString.replace("TMaxima", String.valueOf(TMaxima));
        //htmlString = htmlString.replace("TMinima", String.valueOf(TMinima));
        //htmlString = htmlString.replace("temperatuG", temperatuG);
        htmlString = htmlString.replace("radip", radip);
        htmlString = htmlString.replace("RMedia", String.valueOf(RMedia));
        htmlString = htmlString.replace("RMaxima", String.valueOf(RMaxima));
        htmlString = htmlString.replace("RMinima", String.valueOf(RMinima));
        htmlString = htmlString.replace("radiacionG", radiacionG);


        File newHtmlFile = new File("src/main/resources/path/new.html");
        FileUtils.writeStringToFile(newHtmlFile, htmlString);
    }

    private static Optional<Double> media(List<Medicion> listaMedicion, int idMagnitud) {

        Optional<List<Medicion>> listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, listaMedicion);
        Optional<Double> mediaTemperatura = MedicionesService.medicionMedia(listaMediciones.get());

        return  mediaTemperatura;
    }

    private static Optional<Double> max(List<Medicion> listaMedicion, int idMagnitud) {

        Optional<List<Medicion>> listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, listaMedicion);
        Optional<Double> mediaTemperatura = MedicionesService.medicionMaxima(listaMediciones.get());

        return  mediaTemperatura;
    }

    private static Optional<Double> min(List<Medicion> listaMedicion, int idMagnitud) {

        Optional<List<Medicion>> listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, listaMedicion);
        Optional<Double> mediaTemperatura = MedicionesService.medicionMinima(listaMediciones.get());

        return  mediaTemperatura;
    }

    private static String meteo (String descripcion_magnitud) {
        String medic = String.valueOf(Utils.getMagnMeteo().stream()
                        .map(m -> m.getDescripcion_magnitud()).filter(m -> m.contains("Radiación solar")).collect(Collectors.toList()))
                .replace("[", "").replace("]", "");

        return medic;
    }
    private static List<String> medidas (List<Medicion>listaMediciones, int codigo) {
        String media = String.valueOf(media(listaMediciones, codigo));
        String maxima = String.valueOf(max(listaMediciones, codigo));
        String minima = String.valueOf(min(listaMediciones, codigo));
        String grafico = "../image/" + codigo + ".png";

        ArrayList<String> datos = new ArrayList<String>();
        datos.add(media);
        datos.add(maxima);
        datos.add(minima);

        return datos;
    }
}