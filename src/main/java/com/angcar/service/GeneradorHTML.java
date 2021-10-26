package com.angcar.service;

import com.angcar.ProcesamientoDatos;
import com.angcar.io.WriterFiles;
import com.angcar.util.Utils;
import java.io.IOException;

import static com.angcar.ProcesamientoDatos.path_destination;

public class GeneradorHTML {

    private static final String PROGRAM_NAME = "Servicio meteorológico y contaminación";

    /**
     * Genera el HTML
     * @param nombreCiudad {@link String}
     * @throws IOException Excepción IO
     */
    public static void generarHtml(String nombreCiudad) throws IOException {
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

        String fechaIni = Utils.obtenerFechaInicioMedicion();
        String fechaFin = Utils.obtenerFechaFinalMedicion();

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


        htmlString.append("<p>" + Utils.tiempoInforme() + "</p>");

        //END
        htmlString .append("</body>\n" +
                "</html>");

        WriterFiles.writeFile(htmlString.toString(), nombreCiudad);
    }
}