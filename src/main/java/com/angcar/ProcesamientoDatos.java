package com.angcar;

import com.angcar.model.Medicion;
import com.angcar.model.UbicacionEstaciones;
import com.angcar.service.MeteoService;
import com.angcar.util.Utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProcesamientoDatos {
    private String[] argumentos;

    private static ProcesamientoDatos procesamientoDatos;

    private ProcesamientoDatos(String[] argumentos){
        this.argumentos = argumentos;
        ejecutarPrograma();
    }

    //Singleton
    public static ProcesamientoDatos getInstance(String[] argumentos){
        if (procesamientoDatos == null){
            if (argumentos.length >= 2 && (argumentos.length % 2 == 0)) {
                procesamientoDatos = new ProcesamientoDatos(argumentos);
            } else {
                System.err.println(
                        "Error de sintaxis:\nDebes de utilizar el siguiente formato:\njava -jar meteo.jar <ciudad>" +
                                " <fichero>\n\nEjemplo: java -jar meteo.jar fuenlabrada ciudad_aire_zonas.csv");
            }
        }
        return procesamientoDatos;
    }

    public void ejecutarPrograma(){
        String WORKING_DIRECTORY = System.getProperty("user.dir");

        List<String[]> pares = IntStream.iterate(0, i -> i += 2).limit(argumentos.length/2)
                .mapToObj(n -> new String[] { argumentos[n], argumentos[n + 1] }).collect(Collectors.toList());

        pares.stream().forEach(pair -> {
            String ciudad = pair[0]; //Argumento ciudad
            Path path = Paths.get(WORKING_DIRECTORY + File.separator + pair[1]); //Archivo
            Utils.inicializarDatos();


            //FILTRAMOS POR ESTACIONES LOS ARCHIVOS
            List<UbicacionEstaciones> listaEstaciones = Utils.filtrarPorCiudad(pair[0]);
            if (listaEstaciones.size() == 0) {
                System.out.printf("Ciudad no encontrada: %s", argumentos[0]);
                System.exit(0);
            }
            String codigoCiudad = listaEstaciones.get(0).getEstacion_codigo(); //TODO: Si queremos expandir y agregar zonas, hay que editar esto
            List<Medicion> listaMeteorizacion = Utils.filtrarMeteorizacion(codigoCiudad);
            List<Medicion> listaContaminacion = Utils.filtrarContaminacion(codigoCiudad);

            //TODO: ESTO PA'L PDF:

            System.out.println(argumentos[0]); //Nombre de la ciudad
            System.out.println(Utils.formatearFechaMedicion(listaMeteorizacion)); //Fecha inicio medición
            System.out.println(Utils.formatearFechaMedicion(listaContaminacion)); //Fecha final medición
            Utils.obtenerEstaciones(argumentos[0]); //Estaciones asociadas

            /////////////////////////////
            //INFORMACIÓN METEOROLÓGICA//
            /////////////////////////////
            //TEMPERATURA
            System.out.println("Datos temperatura:");
            System.out.println(MeteoService.medicionMedia(listaMeteorizacion,83)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,83)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,83)); //minima mes
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //RADIACIÓN SOLAR
            System.out.println(MeteoService.medicionMedia(listaMeteorizacion,88)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,88)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,88)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

                //PRECIPITACIÓN
            System.out.println(MeteoService.medicionMedia(listaMeteorizacion,89)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,89)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,89)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

                //HUMEDAD
            System.out.println(MeteoService.medicionMedia(listaMeteorizacion,86)+ " Media"); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,86)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,86)); //minima mes
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

                //VELOCIDAD DEL VIENTO
            System.out.println(MeteoService.medicionMedia(listaMeteorizacion,81)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,81)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,81)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual
            System.out.println("Contaminación");
            /////////////////////////////
            //INFORMACIÓN CONTAMINACIÓN//
            /////////////////////////////
            //DIÓXIDO DE AZUFRE
            System.out.println(MeteoService.medicionMedia(listaContaminacion,1)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,1)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,1)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //MONÓXIDO DE CARBONO
            System.out.println(MeteoService.medicionMedia(listaContaminacion,6)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,6)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,6)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //MONÓXIDO DE NITRÓGENO
            System.out.println(MeteoService.medicionMedia(listaContaminacion,7)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,7)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,7)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //DIÓXIDO DE NITRÓGENO
            System.out.println(MeteoService.medicionMedia(listaContaminacion,8)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,8)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,8)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //PARTÍCULAS EN SUSPENSIÓN < PM2,5
            System.out.println(MeteoService.medicionMedia(listaContaminacion,9)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,9)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,9)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //PARTÍCULAS EN SUSPENSIÓN < PM10
            System.out.println(MeteoService.medicionMedia(listaContaminacion,10)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,10)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,10)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //ÓXIDOS DE NITRÓGENO
            System.out.println(MeteoService.medicionMedia(listaContaminacion,12)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,12)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,12)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //OZONO
            System.out.println(MeteoService.medicionMedia(listaContaminacion,14)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,14)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,14)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //TOLUENO
            System.out.println(MeteoService.medicionMedia(listaContaminacion,20)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,20)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,20)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //BLACK CARBON
            System.out.println(MeteoService.medicionMedia(listaContaminacion,22)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,22)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,22)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //BECENO
            System.out.println(MeteoService.medicionMedia(listaContaminacion,30)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,30)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,30)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //HIDROCARBUROS TOTALES
            System.out.println(MeteoService.medicionMedia(listaContaminacion,42)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,42)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,42)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //HIDROCARBUROS NO METÁNICOS
            System.out.println(MeteoService.medicionMedia(listaContaminacion,44)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,44)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,44)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual

            //METAPARAXILENO
            System.out.println(MeteoService.medicionMedia(listaContaminacion,431)); //media mes
            System.out.println(MeteoService.medicionMinima(listaMeteorizacion,431)); //maxima mes
            System.out.println(MeteoService.medicionMaxima(listaMeteorizacion,431)); //minima mes
                //TODO: Momento y temperatura máxima
                //TODO: Momento y temperatura mínima
                //TODO: Gráfica de la evolución de la temperatura a nivel mensual






            //System.out.println(listaMeteorizacion);
        });
    }
}
