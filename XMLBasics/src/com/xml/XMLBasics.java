/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xml;

import com.xml.parser.sax.CountryDetailsParser;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.SAXException;
import com.details.CountryDetails;

/**
 *
 * @author Shibu
 */
public class XMLBasics {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//       com.xml.parser.dom.CountryDetailsSequentialParser domHandler = new com.xml.parser.dom.CountryDetailsSequentialParser();
//        domHandler.domParserDemo();
//        System.out.println(domHandler.getCountryDetails());  
//          CountryDetailsDirectElementParser parser = new CountryDetailsDirectElementParser();
//          parser.parseXML();
//          System.out.println(parser.getCountryDetails());
//           new CountryDetailsXMLSupplier().createXML(CountryDetailsUtility.getCountryUtility(),"countryDetails_new.xml");
//            com.xml.parser.stax.CountryDetailsParser countryDetailsParser = new com.xml.parser.stax.CountryDetailsParser();
//          countryDetailsParser.parseXMLFromStream();
//            countryDetailsParser.parseXMLFromEvent();
//            System.out.println(countryDetailsParser.getCountryDetails());

            new com.xml.parser.xpath.CountryDetailsParser().parseXMLUsingXPath();
            
    }

    private static void saxParserDemo() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        CountryDetailsParser countryDetailsHandler = new CountryDetailsParser();
        SAXParser saxParser = null;
        CountryDetails countryDetails = null;
        try {
            saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(new File((XMLBasics.class).getResource("/com/xml/docs/xsd/countryDetails.xml").getPath()), countryDetailsHandler);
            countryDetails = countryDetailsHandler.getCountryDetails();
            System.out.println("Country Details =" + countryDetails);
        } catch (ParserConfigurationException | SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
