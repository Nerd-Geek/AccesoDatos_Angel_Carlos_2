package com.angcar.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MagnitudMeteorizacion extends Magnitud{
    public MagnitudMeteorizacion(int code_magnitude, String description_magnitude, int code_technical_media, String undid, String descriptions_undid, String description_technical_media) {
        super(code_magnitude, description_magnitude, code_technical_media, undid, descriptions_undid, description_technical_media);
    }
}
