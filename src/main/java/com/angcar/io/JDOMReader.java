package com.angcar.io;

import com.angcar.model.*;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JDOMReader {
    private static JDOMReader controller;
    public static final String PATH_FILES = System.getProperty("user.dir") + File.separator + "data";
    private static final String PATH_ZONAS = PATH_FILES + File.separator + "calidad_aire_zonas.xml";
    private static final String PATH_UBICA_ESTACIONES = PATH_FILES + File.separator + "calidad_aire_estaciones.xml";
    private static final String PATH_METEO = PATH_FILES + File.separator + "calidad_aire_datos_meteo_mes.xml";
    private static final String PATH_CONTAMINACION = PATH_FILES + File.separator + "calidad_aire_datos_mes.xml";
    private static final String PATH_MAGNITUDES_CONTAMINACION = PATH_FILES + File.separator + "magnitudes_contaminacion.xml";
    private static final String PATH_MAGNITUDES_METEO = PATH_FILES + File.separator + "magnitudes_meteorizacion.xml";
    private Document dataZonas;
    private Document dataUbicaciones;
    private Document dataMeteo;
    private Document dataContamina;
    private Document dataMagContamina;
    private Document dataMagMeteo;

    private JDOMReader() {
    }

    public static JDOMReader getInstance() {
        if (controller == null) {
            controller = new JDOMReader();
        }
        return controller;
    }

    public void loadData() throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        File zonasXML = new File(PATH_ZONAS);
        File ubicacionesXML = new File(PATH_UBICA_ESTACIONES);
        File meteoXML = new File(PATH_METEO);
        File contaminaXML = new File(PATH_CONTAMINACION);
        File magContaminaXML = new File(PATH_MAGNITUDES_CONTAMINACION);
        File magMeteoXML = new File(PATH_MAGNITUDES_METEO);
        this.dataZonas = (Document) builder.build(zonasXML);
        this.dataUbicaciones = (Document) builder.build(ubicacionesXML);
        this.dataMeteo = (Document) builder.build(meteoXML);
        this.dataContamina = (Document) builder.build(contaminaXML);
        this.dataMagContamina = (Document) builder.build(magContaminaXML);
        this.dataMagMeteo = (Document) builder.build(magMeteoXML);
    }

    public Optional<List<ZonasMunicipio>> getZonas() {
        Element root = (Element) this.dataZonas.getRootElement();
        List<Element> listOfZonas = root.getChildren("item");

        List<ZonasMunicipio> zonasList = new ArrayList<>();

        listOfZonas.forEach(zona -> {
            ZonasMunicipio zonas_m = new ZonasMunicipio();
            zonas_m.setAir_code_quality_zone(zona.getChildText("zona_calidad_aire_codigo"));
            zonas_m.setMunicipal_air_quality_zone(zona.getChildText("zona_calidad_aire_descripcion"));
            zonas_m.setMunicipal_air_quality_zone(zona.getChildText("zona_calidad_aire_municipio"));
            zonasList.add(zonas_m);
        });
        return Optional.of(zonasList);
    }

    public Optional<List<UbicacionEstaciones>> getUbicaciones() {
        Element root = (Element) this.dataUbicaciones.getRootElement();
        List<Element> listOfUbic = root.getChildren("item");
        List<UbicacionEstaciones> listUbica = new ArrayList<>();

        listOfUbic.forEach(ubica ->  {
            UbicacionEstaciones ubicacionEstaciones = new UbicacionEstaciones();
            ubicacionEstaciones.setStation_code(ubica.getChildText("estacion_codigo"));
            ubicacionEstaciones.setAir_quality_zone_description(ubica.getChildText("zona_calidad_aire_descripcion"));
            ubicacionEstaciones.setStation_municipal(ubica.getChildText("estacion_municipio"));
            ubicacionEstaciones.setHigh_date_station(ubica.getChildText("estacion_fecha_alta"));
            ubicacionEstaciones.setArea_type_station(ubica.getChildText("estacion_tipo_area"));
            ubicacionEstaciones.setStation_type_station(ubica.getChildText("estacion_tipo_estacion"));
            ubicacionEstaciones.setRural_subarea_station(ubica.getChildText("estacion_subarea_rural"));
            ubicacionEstaciones.setPostal_address_station(ubica.getChildText("estacion_direccion_postal"));
            ubicacionEstaciones.setStation_coord_UTM_ETRS89_x(ubica.getChildText("estacion_coord_UTM_ETRS89_x"));
            ubicacionEstaciones.setStation_coord_UTM_ETRS89_y(ubica.getChildText("estacion_coord_UTM_ETRS89_y"));
            ubicacionEstaciones.setStation_coord_length(ubica.getChildText("estacion_coord_longitud"));
            ubicacionEstaciones.setLatitude_coord_station(ubica.getChildText("estacion_coord_latitud"));
            ubicacionEstaciones.setStation_altitude(ubica.getChildText("estacion_altitud"));
            ubicacionEstaciones.setNO_analyzer_station(ubica.getChildText("estacion_analizador_NO"));
            ubicacionEstaciones.setNO2_analyzer_station(ubica.getChildText("estacion_analizador_NO2"));
            ubicacionEstaciones.setAnalyzer_station_PM10(ubica.getChildText("estacion_analizador_PM10"));
            ubicacionEstaciones.setAnalyzer_station_PM2_5(ubica.getChildText("estacion_analizador_PM2_5"));
            ubicacionEstaciones.setO3_analyzer_station(ubica.getChildText("estacion_analizador_O3"));
            ubicacionEstaciones.setTOL_analyzer_station(ubica.getChildText("estacion_analizador_TOL"));
            ubicacionEstaciones.setBEN_analyzer_station(ubica.getChildText("estacion_analizador_BEN"));
            ubicacionEstaciones.setXIL_analyzer_station(ubica.getChildText("estacion_analizador_XIL"));
            ubicacionEstaciones.setCO_analyzer_station(ubica.getChildText("estacion_analizador_CO"));
            ubicacionEstaciones.setSO2_analyzer_station(ubica.getChildText("estacion_analizador_SO2"));
            ubicacionEstaciones.setHCT_analyzer_station(ubica.getChildText("estacion_analizador_HCT"));
            ubicacionEstaciones.setHNM_analyzer_station(ubica.getChildText("estacion_analizador_HNM"));
            listUbica.add(ubicacionEstaciones);
        });
        return Optional.of(listUbica);
    }

    public Optional<List<Meteorizacion>> getMeteorizacion() {
        Element root = this.dataMeteo.getRootElement();
        List<Element> listOfMeteo = root.getChildren("item");
        List<Meteorizacion> listMeteo = new ArrayList<>();

        listOfMeteo.forEach(meteo -> {
            Meteorizacion meteorizacion = new Meteorizacion();
            meteorizacion.setProvincial(meteo.getChildText("provincia"));
            meteorizacion.setMunicipal(meteo.getChildText("municipio"));
            meteorizacion.setStation(meteo.getChildText("estacion"));
            meteorizacion.setMagnitude(meteo.getChildText("magnitud"));
            meteorizacion.setSampling_point(meteo.getChildText("punto_muestreo"));
            meteorizacion.setYear(Integer.parseInt(meteo.getChildText("ano")));
            meteorizacion.setMonth(Integer.parseInt(meteo.getChildText("mes")));
            meteorizacion.setDay(Integer.parseInt(meteo.getChildText("dia")));
            Hora[] horas = new Hora[24];
            for(int n = 0; n < 24; n++){
                horas[n] = new Hora(meteo.getChildText("h" + String.format("%02d", n + 1)),
                        meteo.getChildText("v" + String.format("%02d", n + 1)),n + 1);
            }
            listMeteo.add(meteorizacion);
        });
        return Optional.of(listMeteo);
    }

    public Optional<List<Contaminacion>> getContaminacion() {
        Element root = this.dataContamina.getRootElement();
        List<Element> listOfContamina = root.getChildren("item");
        List<Contaminacion> listContamina = new ArrayList<>();

        listOfContamina.forEach(cont -> {
            Contaminacion contaminacion = new Contaminacion();
            contaminacion.setProvincial(cont.getChildText("provincia"));
            contaminacion.setMunicipal(cont.getChildText("municipio"));
            contaminacion.setStation(cont.getChildText("estacion"));
            contaminacion.setMagnitude(cont.getChildText("magnitud"));
            contaminacion.setSampling_point(cont.getChildText("punto_muestreo"));
            contaminacion.setYear(Integer.parseInt(cont.getChildText("ano")));
            contaminacion.setMonth(Integer.parseInt(cont.getChildText("mes")));
            contaminacion.setDay(Integer.parseInt(cont.getChildText("dia")));
            Hora[] horas = new Hora[24];
            for(int n = 0; n < 24; n++){
                horas[n] = new Hora(cont.getChildText("h" + String.format("%02d", n + 1)),
                        cont.getChildText("v" + String.format("%02d", n + 1)),n + 1);
            }
            listContamina.add(contaminacion);
        });
        return Optional.of(listContamina);
    }

    public Optional<List<Magnitud>> getMagnitudMeteorizacion() {
        Element root = this.dataMagMeteo.getRootElement();
        List<Element> listOfMagMeteo = root.getChildren("item");
        List<Magnitud> listMagMeteo = new ArrayList<>();

        listOfMagMeteo.forEach(mag -> {
            MagnitudMeteorizacion magnitudMeteorizacion = new MagnitudMeteorizacion();
            magnitudMeteorizacion.setCode_magnitude(Integer.parseInt(mag.getChildText("cod_magnitud")));
            magnitudMeteorizacion.setDescription_magnitude(mag.getChildText("desc_magnitud"));
            magnitudMeteorizacion.setCode_technical_measure(Integer.parseInt(mag.getChildText("cod_tec_medida")));
            magnitudMeteorizacion.setUndid(mag.getChildText("unidad"));
            magnitudMeteorizacion.setDescriptions_undid(mag.getChildText("desc_unidad"));
            listMagMeteo.add(magnitudMeteorizacion);
        });
        return Optional.of(listMagMeteo);
    }

    public Optional<List<Magnitud>> getMagnitudContaminacion() {
        Element root = this.dataMagMeteo.getRootElement();
        List<Element> listOfMagMeteo = root.getChildren("item");
        List<Magnitud> listMagMeteo = new ArrayList<>();

        listOfMagMeteo.forEach(mag -> {
            MagnitudContaminacion magnitudContaminacion = new MagnitudContaminacion();
            magnitudContaminacion.setCode_magnitude(Integer.parseInt(mag.getChildText("cod_magnitud")));
            magnitudContaminacion.setDescription_magnitude(mag.getChildText("desc_magnitud"));
            magnitudContaminacion.setCode_technical_measure(Integer.parseInt(mag.getChildText("cod_tec_medida")));
            magnitudContaminacion.setUndid(mag.getChildText("unidad"));
            magnitudContaminacion.setDescriptions_undid(mag.getChildText("desc_unidad"));
            listMagMeteo.add(magnitudContaminacion);
        });
        return Optional.of(listMagMeteo);
    }
}
