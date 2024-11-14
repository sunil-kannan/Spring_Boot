package com.learning.Spring_Boot.json_and_xml;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * In Spring applications, Jackson is used as the default data-binding library.
 * By including jackson-dataformat-xml,you can enable XML support seamlessly within your
 * Spring MVC or Spring Boot application.
 */
@RestController
public class JsonAndXmlResponse {

    @GetMapping(path = "/json_and_xml/get",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<JsonAndXmlDto> get() {
        return ResponseEntity.ok(JsonAndXmlDto.builder().id("S01").name("John").department("HR").build());
    }

    /**
     *  When sending the XML data to the /json_and_xml/save endpoint, make sure
     *  the Content-Type and Accept header is set to application/xml.
     * @param jsonAndXmlDto
     * @return jsonAndXmlDto in xml format or json format as user requested format
     */
    @PostMapping(path = "/json_and_xml/save",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<JsonAndXmlDto> save(@RequestBody JsonAndXmlDto jsonAndXmlDto) {
        // save the data
        return ResponseEntity.ok(jsonAndXmlDto);
    }
}
