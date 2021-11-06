package com.angcar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Magnitud{ // POJO
    private int code_magnitude;
    private String description_magnitude;
    private int code_technical_measure;
    private String undid;
    private String descriptions_undid;

    private String technical_description_measure;
}
