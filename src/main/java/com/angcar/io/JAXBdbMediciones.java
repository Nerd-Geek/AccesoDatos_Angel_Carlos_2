package com.angcar.io;

import com.angcar.model.resultados.ResultadoMediciones;
import com.angcar.model.resultados.ResultadosMediciones;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JAXBdbMediciones {
    private static JAXBdbMediciones controller;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public JAXBdbMediciones(){
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

    public void domTest(ResultadoMediciones resultadoMediciones, String uri, String ciudad, String path)
            throws JAXBException, IOException, ParserConfigurationException, XPathExpressionException {
        JAXBContext context = JAXBContext.newInstance(ResultadosMediciones.class);

        //Leer archivo e introducir resultados
        File xml = new File(uri);
        this.unmarshaller = context.createUnmarshaller();
        ResultadosMediciones resultados;
//        List<ResultadoMediciones> resultadosList;

        if (xml.exists()) {
            resultados = (ResultadosMediciones) unmarshaller.unmarshal(xml);
            //resultadosList = resultados.getResultados(); Esto provoca que se duplique el DOM
        }else{
            resultados = new ResultadosMediciones();
            //resultadosList = new ArrayList<>(); Esto provoca que se duplique el DOM
        }

        //DOM
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
        Document doc = domBuilder.newDocument();

//        resultadosList.add(resultadoMediciones); Esto provoca que se duplique el DOM
//        resultados.setResultados(resultadosList); Esto provoca que se duplique el DOM
//
        //Ahora escribir los cambios
        this.marshaller = context.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        this.marshaller.marshal(resultados, doc);


        // Almacenar los datos necesarios para mostrarselo al usuario
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        NodeList list2 = (NodeList) xpath.evaluate(
                "//resultado/datos-metereologicos/dato/media",
                doc, XPathConstants.NODESET);
        NodeList list1 = (NodeList) xpath.evaluate(
          "//resultado/datos-metereologicos/dato/@tipo",
          doc, XPathConstants.NODESET);

        NodeList list5 = (NodeList) xpath.evaluate(
                "//resultado/datos-contaminacion/dato/media",
                doc, XPathConstants.NODESET);

        NodeList list6 = (NodeList) xpath.evaluate(
                "//resultado/datos-contaminacion/dato/@tipo",
                doc, XPathConstants.NODESET);
        NodeList list3 = (NodeList) xpath.evaluate(
                "//resultado/@id",
                doc, XPathConstants.NODESET);
        NodeList list4 = (NodeList) xpath.evaluate(
                "//resultado/ciudad",
                doc, XPathConstants.NODESET);

        // Operaciónes para obtener el número de atributos meteorologicos y de contaminacion de una ciudad
        int numMeteo = list1.getLength()/list3.getLength();
        int numConta = list6.getLength()/list3.getLength();
        //String idBuscado = list3.item(list3.getLength() -1).getTextContent(); Manera de sacar el útimo ID generado
        String md = "";

        // Sacar los datos por pantalla
        //for(int i = list3.getLength() -1; i < list4.getLength(); i++) { Otra manera para sacar la última medición añadida
        for(int i = 0; i < list4.getLength(); i++) {
            if (ciudad.equals(list4.item(i).getTextContent())) { // Mostrar la ciudad que ha sido pasada por parametro
                System.out.println(list4.item(i).getTextContent() + " id: " + list3.item(i).getTextContent());
                md = md + "#"+ list4.item(i).getTextContent() + " id: " + list3.item(i).getTextContent() + "\n";
                System.out.println("Meteorización");
                md = md +"##"+ "Meteorización" + "\n";
                for (int j = i * numMeteo; j < (i * numMeteo) + numMeteo; j++) {
                    System.out.println(list1.item(j).getTextContent() + ": " + list2.item(j).getTextContent());
                    md = md + "###"+ list1.item(j).getTextContent() + ": " + list2.item(j).getTextContent() + "\n";
                }
                System.out.println("Contaminación");
                for (int x = i * numConta; x < (i * numConta) + numConta; x++) {
                    System.out.println(list6.item(x).getTextContent() + ": " + list5.item(x).getTextContent());
                    md = md + "###"+ list6.item(x).getTextContent() + ": " + list5.item(x).getTextContent() + "\n";
                }
            }
        }

        FileWriter markdown = new FileWriter(path+"informe-ciudad-"+ciudad+".md");
        markdown.write(md);
        markdown.close();
    }
}