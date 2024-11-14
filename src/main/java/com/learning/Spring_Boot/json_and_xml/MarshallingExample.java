package com.learning.Spring_Boot.json_and_xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class MarshallingExample {
    public static void main(String[] args) {
        JsonAndXmlDto person = new JsonAndXmlDto("S01","Alice", "HR");

        try {
            JAXBContext context = JAXBContext.newInstance(JsonAndXmlDto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(person, System.out); // Output to console as XML
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}