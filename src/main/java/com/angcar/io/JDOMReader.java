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

public class JDOMReader {
    private static JDOMReader controller;
    public static final String PATH_FILES = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "main"  + File.separator + "resources" +  File.separator +"data";
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
        this.dataZonas = builder.build(zonasXML);
        this.dataUbicaciones = builder.build(ubicacionesXML);
        this.dataMeteo = builder.build(meteoXML);
        this.dataContamina = builder.build(contaminaXML);
        this.dataMagContamina = builder.build(magContaminaXML);
        this.dataMagMeteo = builder.build(magMeteoXML);
    }

    public Optional<List<ZonasMunicipio>> getZonas() {
        Element root = (Element) this.dataZonas.getRootElement();
        List<Element> listOfZonas = root.getChildren("item");

        List<ZonasMunicipio> zonasList = new ArrayList<>();

        listOfZonas.forEach(zona -> {
            ZonasMunicipio zonas_m = new ZonasMunicipio();
            zonas_m.setAirCodeQualityZone(zona.getChildText("zona_calidad_aire_codigo"));
            zonas_m.setMunicipalAirQualityZone(zona.getChildText("zona_calidad_aire_descripcion"));
            zonas_m.setMunicipalAirQualityZone(zona.getChildText("zona_calidad_aire_municipio"));
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
            ubicacionEstaciones.setStationCode(ubica.getChildText("estacion_codigo"));
            ubicacionEstaciones.setAirQualityZoneDescription(ubica.getChildText("zona_calidad_aire_descripcion"));
            ubicacionEstaciones.setStationMunicipal(ubica.getChildText("estacion_municipio"));
            ubicacionEstaciones.setHighDateStation(ubica.getChildText("estacion_fecha_alta"));
            ubicacionEstaciones.setAreaTypeStation(ubica.getChildText("estacion_tipo_area"));
            ubicacionEstaciones.setStationTypeStation(ubica.getChildText("estacion_tipo_estacion"));
            ubicacionEstaciones.setRuralSubareaStation(ubica.getChildText("estacion_subarea_rural"));
            ubicacionEstaciones.setPostalAddressStation(ubica.getChildText("estacion_direccion_postal"));
            ubicacionEstaciones.setStationCoordUTMETRS89x(ubica.getChildText("estacion_coord_UTM_ETRS89_x"));
            ubicacionEstaciones.setStationCoordUTMETRS89y(ubica.getChildText("estacion_coord_UTM_ETRS89_y"));
            ubicacionEstaciones.setStationCoordLength(ubica.getChildText("estacion_coord_longitud"));
            ubicacionEstaciones.setLatitudeCoordStation(ubica.getChildText("estacion_coord_latitud"));
            ubicacionEstaciones.setStationAltitude(ubica.getChildText("estacion_altitud"));
            ubicacionEstaciones.setNoAnalyzerStation(ubica.getChildText("estacion_analizador_NO"));
            ubicacionEstaciones.setNO2AnalyzerStation(ubica.getChildText("estacion_analizador_NO2"));
            ubicacionEstaciones.setAnalyzerStationPM10(ubica.getChildText("estacion_analizador_PM10"));
            ubicacionEstaciones.setAnalyzerStationPM25(ubica.getChildText("estacion_analizador_PM2_5"));
            ubicacionEstaciones.setO3AnalyzerStation(ubica.getChildText("estacion_analizador_O3"));
            ubicacionEstaciones.setTolAnalyzerStation(ubica.getChildText("estacion_analizador_TOL"));
            ubicacionEstaciones.setBenAnalyzerStation(ubica.getChildText("estacion_analizador_BEN"));
            ubicacionEstaciones.setXilAnalyzerStation(ubica.getChildText("estacion_analizador_XIL"));
            ubicacionEstaciones.setCoAnalyzerStation(ubica.getChildText("estacion_analizador_CO"));
            ubicacionEstaciones.setSo2AnalyzerStation(ubica.getChildText("estacion_analizador_SO2"));
            ubicacionEstaciones.setHctAnalyzerStation(ubica.getChildText("estacion_analizador_HCT"));
            ubicacionEstaciones.setHnmAnalyzerStation(ubica.getChildText("estacion_analizador_HNM"));
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
            meteorizacion.setSamplingPoint(meteo.getChildText("punto_muestreo"));
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
            contaminacion.setSamplingPoint(cont.getChildText("punto_muestreo"));
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
            magnitudMeteorizacion.setCodeMagnitud(Integer.parseInt(mag.getChildText("cod_magnitud")));
            magnitudMeteorizacion.setDescriptionMagnitude(mag.getChildText("desc_magnitud"));
            magnitudMeteorizacion.setCodeTechnicalMeasure(Integer.parseInt(mag.getChildText("cod_tec_medida")));
            magnitudMeteorizacion.setUnidad(mag.getChildText("unidad"));
            magnitudMeteorizacion.setDescriptionsUnidad(mag.getChildText("desc_unidad"));
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
            magnitudContaminacion.setCodeMagnitud(Integer.parseInt(mag.getChildText("cod_magnitud")));
            magnitudContaminacion.setDescriptionMagnitude(mag.getChildText("desc_magnitud"));
            magnitudContaminacion.setCodeTechnicalMeasure(Integer.parseInt(mag.getChildText("cod_tec_medida")));
            magnitudContaminacion.setUnidad(mag.getChildText("unidad"));
            magnitudContaminacion.setDescriptionsUnidad(mag.getChildText("desc_unidad"));
            listMagMeteo.add(magnitudContaminacion);
        });
        return Optional.of(listMagMeteo);
    }
}
