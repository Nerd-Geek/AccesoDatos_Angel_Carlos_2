package com.angcar;
import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String PATH_UBICA_ESTACIONES = System.getProperty("user.dir")+ File.separator+"data"+File.separator+"calidad_aire_estaciones.csv";
        ProcesamientoDatos.getInstance(args);
    }
}