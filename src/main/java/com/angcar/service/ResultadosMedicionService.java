package com.angcar.service;

import com.angcar.model.Hora;
import com.angcar.model.Magnitud;
import com.angcar.model.Medicion;
import com.angcar.model.resultados.DatosDiaMagnitud;
import com.angcar.model.resultados.DatosMagnitud;
import com.angcar.model.resultados.DatosMomento;
import com.angcar.model.resultados.ResultadoMediciones;
import com.angcar.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class ResultadosMedicionService {

    private final String CITY_NAME;

    public ResultadosMedicionService(String nombreCiudad){
        this.CITY_NAME = nombreCiudad;
    }

    public ResultadoMediciones cargarResultadosMediciones() {
        ResultadoMediciones resultadoMediciones = new ResultadoMediciones();

        //Agregar valores
        resultadoMediciones.setId(java.util.UUID.randomUUID().toString());
        resultadoMediciones.setCiudad(CITY_NAME);
        Utils.obtenerEstaciones(CITY_NAME).ifPresent(resultadoMediciones::setEstacionesAsociadas);
        resultadoMediciones.setFechaInicio(Utils.obtenerFechaInicioMedicion());
        resultadoMediciones.setFechaFin(Utils.obtenerFechaFinalMedicion());

        //AGREGAR LISTAS DE DATOS DE MEDICIONES (METEO Y CONTAMINA)
        String codigoCiudad;
        if (Utils.obtenerCodeEstacion(CITY_NAME).isPresent()){
            codigoCiudad = Utils.obtenerCodeEstacion(CITY_NAME).get();

            //Filtrar por ciudad pasada por parámetro
            List<Medicion> listaMeteorizacion = Utils.filtrarMeteorizacion(codigoCiudad);
            List<Medicion> listaContaminacion = Utils.filtrarContaminacion(codigoCiudad);

            //Agregar los respectivos filtros
            Predicate<Magnitud> filtroMedicion = (Magnitud m) -> m.getCodeMagnitud() == 83 || m.getCodeMagnitud() == 88
                    || m.getCodeMagnitud() == 89 || m.getCodeMagnitud() == 86 || m.getCodeMagnitud() == 81;

            Predicate<Magnitud> filtroContamina = (Magnitud m) -> m.getCodeMagnitud() != -1;

            Optional<List<DatosMagnitud>> listaMeteo = procesarDatosMagnitud(Utils.getMagnMeteo(),
                    listaMeteorizacion,filtroMedicion);
            Optional<List<DatosMagnitud>> listaContamina = procesarDatosMagnitud(Utils.getMagnContamina(),
                    listaContaminacion, filtroContamina);

            //Agregar
            listaMeteo.ifPresent(resultadoMediciones::setDatosMeteo);
            listaContamina.ifPresent(resultadoMediciones::setDatosContamina);

            System.out.println("Base de datos XML creada.");

            //Asignar los respectivos valores
        }else{
            System.err.println("No se ha podido localizar el código de la ciudad");
            System.exit(0);
        }

        return resultadoMediciones;
    }






    private Optional<List<DatosMagnitud>> procesarDatosMagnitud(List<Magnitud> magnitudLista, List<Medicion> listaMedicion, Predicate<Magnitud> filtro) {
        List<DatosMagnitud> listaDatos = new ArrayList<>();

        magnitudLista.stream().filter(filtro).forEach((magnitud) -> {
            int idMagnitud = magnitud.getCodeMagnitud();

            //Filtrar por magnitud
            List<Medicion> listaMediciones;

            if (Utils.obtenerMagnitudLista(idMagnitud, listaMedicion).isPresent()){
                listaMediciones = Utils.obtenerMagnitudLista(idMagnitud, listaMedicion).get();

                if (!listaMediciones.isEmpty()){
                    //AGREGAR MAGNITUD
                        DatosMagnitud datosMagnitud = new DatosMagnitud();
                        datosMagnitud.setTipo(magnitud.getDescriptionMagnitude());

                    //Agregar media mensual
                        Optional<Double> medicionOpt = MedicionesService.medicionMedia(listaMediciones);
                        medicionOpt.ifPresent(datosMagnitud::setMedia);

                    //Agregar momentos;
                        Map.Entry<Medicion, Optional<Hora>> mapEntry;

                    //Agregar momento máximo
                        mapEntry =  MedicionesService.medicionMaximaMomento(listaMediciones);
                        DatosMomento max = new DatosMomento();
                            Utils.obtenerFechaAndHoraMedicion(mapEntry).ifPresent(max::setFechaConvert);
                            mapEntry.getValue().ifPresent(hora -> max.setValor(hora.getValue()));
                        datosMagnitud.setMaxima(max);

                    //Agregar momento mínimo
                        mapEntry =  MedicionesService.medicionMinimaMomento(listaMediciones);
                        DatosMomento min = new DatosMomento();
                            Utils.obtenerFechaAndHoraMedicion(mapEntry).ifPresent(min::setFechaConvert);
                            mapEntry.getValue().ifPresent(hora -> min.setValor(hora.getValue()));
                        datosMagnitud.setMinima(min);

                    //Agregar días
                        List<DatosDiaMagnitud> listaDeDias = new ArrayList<>();


                        if (datosMagnitud.getTipo().equalsIgnoreCase("Precipitación")) {
                            MedicionesService.listaDiasPrecipitacion(listaMediciones).forEach((key, value) -> {
                                DatosDiaMagnitud datosDiaMagnitud = new DatosDiaMagnitud();
                                datosDiaMagnitud.setFecha(key.toString());
                                datosDiaMagnitud.setValor(value);
                                listaDeDias.add(datosDiaMagnitud);
                            });
                        }

                    datosMagnitud.setDias(listaDeDias);

                    //Agregar a la lista de datos
                        listaDatos.add(datosMagnitud);
                }
            }
        }
        );

        if (listaDatos.isEmpty()){
            return Optional.empty();
        }else{
            return Optional.of(listaDatos);
        }
    }
}