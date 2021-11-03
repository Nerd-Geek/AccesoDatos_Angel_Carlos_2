package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UbicacionEstaciones { //POJO
    private String station_code;
    private String air_quality_zone_description;
    private String station_municipal;
    private String high_date_station;
    private String area_type_station;
    private String station_type_station;
    private String rural_subarea_station;
    private String postal_address_station;
    private String station_coord_UTM_ETRS89_x;
    private String station_coord_UTM_ETRS89_y;
    private String station_coord_length;
    private String latitude_coord_station;
    private String station_altitude;
    private String NO_analyzer_station;
    private String NO2_analyzer_station;
    private String analyzer_station_PM10;
    private String analyzer_station_PM2_5;
    private String O3_analyzer_station;
    private String TOL_analyzer_station;
    private String BEN_analyzer_station;
    private String XIL_analyzer_station;
    private String CO_analyzer_station;
    private String SO2_analyzer_station;
    private String HCT_analyzer_station;
    private String HNM_analyzer_station;
}
