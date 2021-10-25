package com.angcar.service;

import com.angcar.model.Medicion;
import com.angcar.util.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GeneradorHTML {

    private static final String PROGRAM_NAME = "Servicio meteorológico y contaminación";

    public static void generarHtml(String nombreCiudad, String codigoCiudad) throws IOException {
        List<Medicion> listaContaminacion = Utils.filtrarContaminacion(codigoCiudad);
        List<Medicion> listaMeteorizacion = Utils.filtrarMeteorizacion(codigoCiudad);

        //HEAD
        StringBuilder htmlString = new StringBuilder();
        htmlString.append(String.format("<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                "    <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <title>%s</title>\n" +
                "    </head>\n" +
                "    <body>",PROGRAM_NAME));

        //Obtener estaciones
        StringBuilder sb = new StringBuilder();
        Utils.obtenerEstaciones(nombreCiudad).ifPresent(strings -> strings
                .forEach(s -> sb.append("<p>").append(s).append("</p>")));

        String fechaIni = Utils.obtenerFechaInicioMedicion(listaMeteorizacion); //TODO: MAX Y MIN
        String fechaFin = Utils.obtenerFechaFinalMedicion(listaMeteorizacion);

        htmlString.append(String.format("<h1>%s</h1>\n" +
                "<h2>%s</h2>\n" +
                "        <h3>Estaciones asociadas:</h3>\n" +
                "        %s\n" +
                "<h3>Fecha de inicio de la medición:</h3>\n" +
                "<p>%s</p>\n" +
                        "<h3>Fecha de fin de la medición:</h3>\n" +
                        "<p>%s</p>\n"
                ,PROGRAM_NAME, nombreCiudad, sb, fechaIni, fechaFin));




        ///CARGAR DATOS MEDICIONES
        htmlString.append(DatosHTML.getStringHTMLData()); //Agregar StringHTMLData
        DatosHTML.resetHTMLData(); //Resetear StringHTMLData



        //END
        htmlString .append("</body>\n" +
                "</html>");

        File newHtmlFile = new File("src/main/resources/new.html"); //TODO: UNIFICAR
        FileUtils.writeStringToFile(newHtmlFile, htmlString.toString());
    }
}