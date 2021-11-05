package com.angcar.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MagnitudContaminacion extends Magnitud{
    public MagnitudContaminacion(int code_magnitude, String description_magnitude, int code_technical_measure, String undid, String description_undid, String description_technical_media) {
        super(code_magnitude, description_magnitude, code_technical_measure, undid, description_undid, description_technical_media);
    }
}
