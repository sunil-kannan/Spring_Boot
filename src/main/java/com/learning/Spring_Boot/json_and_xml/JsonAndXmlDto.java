package com.learning.Spring_Boot.json_and_xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JacksonXmlRootElement(localName = "User")
@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD) //JAXB (Java Architecture for XML Binding) will avoid duplicate mappings, and you can customize the XML attribute name as required.
@AllArgsConstructor
@NoArgsConstructor
public class JsonAndXmlDto {

    @JacksonXmlProperty(localName = "xml_id")
    @XmlElement(name = "user_id")
    private String id;

    @JacksonXmlProperty(localName = "name")
    @XmlElement(name = "user_name")
    private String name;

    @JacksonXmlProperty(localName = "department")
    @XmlElement(name = "user_department")
    private String department;
}
