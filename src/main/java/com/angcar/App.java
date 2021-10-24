package com.angcar;

import com.angcar.service.GeneradorHTML;
import htmlflow.DynamicHtml;
import htmlflow.HtmlView;
import org.apache.commons.io.FileUtils;
import org.jboss.windup.util.Task;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) throws IOException {
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