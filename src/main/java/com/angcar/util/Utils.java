package com.angcar.util;

import com.angcar.io.ReaderFiles;
import com.angcar.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class Utils {
    private static List<UbicacionEstaciones> estacionesUbi;
    private static Optional<List<Contaminacion>> contamina;
    private static Optional<List<Meteorizacion>> meteo;
    private static Optional<List<ZonasMunicipio>> municipio;
    private static Optional<List<MagnitudContaminacion>> magnContamina;
    private static Optional<List<MagnitudMeteorizacion>> magnMeteo;

    /**
     * Carga e inicializa los CSV's
     */
    public static void inicializarDatos(){
        //Leer .csv's
        estacionesUbi = ReaderFiles.readDataOfPathUbicacionEstaciones();
        contamina = ReaderFiles.readDataOfPathContaminacion();
        meteo = ReaderFiles.readDataOfPathMeteorologia();
        municipio = ReaderFiles.readDataOfPathZonasMunicipio();
        magnContamina = ReaderFiles.readDataOfPathMagnitudContaminacion();
        magnMeteo = ReaderFiles.readDataOfPathMagnitudMeteorizacion();
    }


    /**
     * Obtener código dada una ciudad
     * @param nombreCiudad
     * @return String
     */
    public static StringBuilder obtenerCodigo(String nombreCiudad) {
        StringBuilder codigo = new StringBuilder("");

            //Encontrar el primer elemento que coincida y obtener su código

            List<UbicacionEstaciones> lista = estacionesUbi.stream().filter(ubicacionEstaciones ->
                    ubicacionEstaciones.getEstacion_municipio().equalsIgnoreCase(nombreCiudad))
                    .collect(Collectors.toList());

            //TODO: DESPLAZAR ESTA CONDICIÓN A SERVICE
            if (!lista.isEmpty()) {
                codigo.append(lista.get(0).getEstacion_codigo());
            }
            else{
                System.out.println("No se ha encontrado " + nombreCiudad);
            }

        return codigo;
    }

    public static Optional<LocalDate> obtenerFechaInicio(Medicion medicion){

        //TODO: FALTA AGREGAR HORA, MIN, SEC
        LocalDate fecha=LocalDate.of(medicion.getAno(),medicion.getMes(),medicion.getDia());
        fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy",
                new Locale("es", "ES")));

        return Optional.of(fecha);
    }


}
