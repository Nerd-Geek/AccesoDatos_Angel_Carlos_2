package com.angcar.io;

import com.angcar.model.resultados.ResultadoMediciones;
import com.angcar.model.resultados.ResultadosMediciones;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JAXBdbMediciones {
    private static JAXBdbMediciones controller;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    private JAXBdbMediciones(){
    }

    //Método para que solamente haya una instancia
    public static JAXBdbMediciones getInstance() {
        if (controller == null) {
            controller = new JAXBdbMediciones();
        }
        return controller;
    }

    public void crearBDMediciones(ResultadoMediciones resultadoMediciones, String uri) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(ResultadosMediciones.class);

        //Leer archivo e introducir resultados
        File xml = new File(uri);
        this.unmarshaller = context.createUnmarshaller();
        ResultadosMediciones resultados;
        List<ResultadoMediciones> resultadosList;

        if (xml.exists()) {
            resultados = (ResultadosMediciones) unmarshaller.unmarshal(xml);
            resultadosList = resultados.getResultados();
        }else{
            resultados = new ResultadosMediciones();
            resultadosList = new ArrayList<>();
        }

        resultadosList.add(resultadoMediciones);
        resultados.setResultados(resultadosList);

        //Ahora escribir los cambios
        this.marshaller = context.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        this.marshaller.marshal(resultados, xml);
    }

    public void domTest(ResultadoMediciones resultadoMediciones, String uri) throws JAXBException, IOException, ParserConfigurationException {
        JAXBContext context = JAXBContext.newInstance(ResultadosMediciones.class);

        //Leer archivo e introducir resultados
        File xml = new File(uri);
        this.unmarshaller = context.createUnmarshaller();
        ResultadosMediciones resultados;
        List<ResultadoMediciones> resultadosList;

        if (xml.exists()) {
            resultados = (ResultadosMediciones) unmarshaller.unmarshal(xml);
            resultadosList = resultados.getResultados();
        }else{
            resultados = new ResultadosMediciones();
            resultadosList = new ArrayList<>();
        }

        //DOM
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
        Document doc = domBuilder.newDocument();

        resultadosList.add(resultadoMediciones);
        resultados.setResultados(resultadosList);

        //Ahora escribir los cambios
        this.marshaller = context.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        this.marshaller.marshal(resultados, doc);

        /* TODO XPATH
        XPathFactory factory = XPathFactory.newInstance();
XPath xpath = factory.newXPath();

NodeList list = (NodeList) xpath.evaluate(
          "/movieLibrary/collection[releaseYear < 1980]/title",
          doc, XPathConstants.NODESET);
for (int i = 0; i < list.getLength(); i++)  // print list of movies older than 1980
  System.out.println(list.item(i).getTextContent());
         */
    }

}
