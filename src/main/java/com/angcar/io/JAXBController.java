package com.angcar.io;

import com.angcar.model.Magnitud;
import com.angcar.model.Medicion;
import com.angcar.model.UbicacionEstaciones;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class JAXBController {
    private JAXBController controller;
    private Marshaller marshaller;
    private UbicacionEstaciones ubicacionEstaciones;
    private Object object;

    private JAXBController() {
    }

    //Método para que solamente haya una instancia
    public JAXBController getInstance() {
        if (controller == null)
            controller = new JAXBController();
        return controller;
    }

    /**
     * Método que se encarga de convertir un objeto a XML
     * @param object
     * @throws JAXBException
     */
    public void convertObjectToXML(Object object, String uri) throws JAXBException {
        this.object = ubicacionEstaciones;
        JAXBContext context = JAXBContext.newInstance(Object.class);
        this.marshaller = context.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        this.marshaller.marshal(object, new File(uri));
    }
}