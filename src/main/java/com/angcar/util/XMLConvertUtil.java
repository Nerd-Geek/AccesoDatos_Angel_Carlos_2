package com.angcar.util;

import com.angcar.io.ReaderFiles;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class XMLConvertUtil {
    public static ArrayList<String> getTitlesCSV(Path path) {
        ArrayList<String> elementosString = new ArrayList<>();
        try (Stream<String> stream = Files.lines(path, Charset.forName("windows-1252"))) {
            Optional<String[]> elementosArray = stream.map(s -> s.split(";", -1)).findFirst();
            Arrays.stream(elementosArray.get()).forEach(s -> elementosString.add(s));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return elementosString;
    }

    public static void generarXML(String nombreArchivo, Document doc) throws IOException {
        XMLOutputter xml = new XMLOutputter();
        xml.setFormat(Format.getPrettyFormat());
        xml.output(doc, new FileWriter(ReaderFiles.PATH_FILES + File.separator + nombreArchivo + ".xml"));
    }

    public static Element contenidoCSVLoad(ArrayList<String> elementos, String[] splitted) {
        Element contenido = new Element("item");
        for(int n = 0; n < elementos.size(); n++) {
            Element c = new Element(elementos.get(n));
            c.setText(splitted[n]);
            contenido.addContent(c);
        }

        return contenido;
    }

    public static String nombreArchivoDeCSV(String nombreArchivo) {
        StringBuilder b = new StringBuilder(nombreArchivo);
        b.replace(nombreArchivo.lastIndexOf(".csv"), nombreArchivo.length(), "" );
        return b.toString();
    }
}
