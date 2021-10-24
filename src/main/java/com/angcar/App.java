package com.angcar;

public class App {
    public static void main(String[] args) {
        /**
         * TODO: refactorizar nombre : argsTemporal por args y borrar la array y declaraciones de abajo
         */
        String[] argsTemporal = new String[2];
        argsTemporal[0] = "fuenlabrada";
        argsTemporal[1] = "out";

        //Dejar solo la línea de abajo
        ProcesamientoDatos.getInstance(argsTemporal);
    }
}