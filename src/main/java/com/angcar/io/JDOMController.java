package com.angcar.io;


import com.angcar.model.ZonasMunicipio;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JDOMController {


    public void crearZonas(List<ZonasMunicipio> zonasList) throws IOException {
        Element zonas = new Element("zonas");
        Document doc = new Document(zonas);

        ArrayList<Element> elementos = new ArrayList<>();


        zonasList.forEach(zonasMunicipio -> {
            Element zona = new Element("zona");
                Element airCode = new Element("airCode");
                airCode.setText(zonasMunicipio.getAir_code_quality_zone());

                Element airDesc = new Element("airDesc");
                airCode.setText(zonasMunicipio.getAir_quality_zone_description());

                Element airMunicipalZone = new Element("airMunicipalZone");
                airMunicipalZone.setText(zonasMunicipio.getMunicipal_air_quality_zone());

                //Agregar
                zona.addContent(airCode);
                zona.addContent(airDesc);
                zona.addContent(airMunicipalZone);

            zonas.addContent(zona);
        });

        XMLOutputter xml = new XMLOutputter();
        xml.setFormat(Format.getPrettyFormat());
        xml.output(doc, new FileWriter("test/test.xml"));


    }

}
