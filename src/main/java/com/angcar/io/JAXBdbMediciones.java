package com.angcar.io;

import com.angcar.model.resultados.ResultadoMediciones;
import com.angcar.model.resultados.ResultadosMediciones;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JAXBdbMediciones {
    private static JAXBdbMediciones controller;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    /**
     * Constructor privado para Singleton
     */
    private JAXBdbMediciones(){
    }

    /**
     * Singleton
     * @return JAXBdbMediciones
     */
    public static JAXBdbMediciones getInstance() {
        if (controller == null) {
            controller = new JAXBdbMediciones();
        }
        return controller;
    }

    /**
     * Crear base de datos y devolver el documento
     * @param resultadoMediciones {@link String}
     * @param uri {@link String}
     * @throws JAXBException excepción
     */
    public Document crearBDMediciones(ResultadoMediciones resultadoMediciones, String uri) throws JAXBException, ParserConfigurationException {
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

        //DOM
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
        Document doc = domBuilder.newDocument();

        //Ahora escribir los cambios
        this.marshaller = context.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        this.marshaller.marshal(resultados, xml);
        this.marshaller.marshal(resultados, doc);

        return doc;
    }


}