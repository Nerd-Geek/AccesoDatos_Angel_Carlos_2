package com.angcar.util;

public class DatosMedicionDia {
    public Hora[] getHoras() {
        return horas;
    }

    private Hora[] horas;

    public DatosMedicionDia(Hora[] horas) {
        this.horas = horas;
    }
}
