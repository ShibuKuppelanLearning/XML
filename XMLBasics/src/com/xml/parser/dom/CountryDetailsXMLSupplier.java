/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xml.parser.dom;

import com.details.CountryDetails;
import com.xml.handler.constants.CountryDetailsConstants;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Shibu
 */
public class CountryDetailsXMLSupplier implements CountryDetailsConstants {

    public void createXML(CountryDetails countryDetails, String fileName){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document;
        final Element root;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
            root = document.createElement("root");
            countryDetails.getCountries().stream().forEach(country -> {
                Element newCountry = document.createElement(COUNTRY);
                final Element newStates = document.createElement(STATES);
                Element newCountryName = document.createElement(NAME);
                newCountryName.setTextContent(country.getName());
                newCountry.setAttribute(CODE, country.getCode());
                Element newCountryLanguage = document.createElement(LANGUAGE);
                newCountryLanguage.setTextContent(country.getLanguage());

                Element newCountryCapital = document.createElement(CAPITAL);
                newCountryCapital.setTextContent(country.getCapital());

                newCountry.appendChild(newCountryName);
                newCountry.appendChild(newCountryLanguage);
                newCountry.appendChild(newCountryCapital);
                newCountry.appendChild(newStates);
                country.getStates().stream().forEach(state -> {
                    final Element newState = document.createElement(STATE);

                    Element newStateName = document.createElement(NAME);
                    newStateName.setTextContent(state.getName());

                    Element newStateLanguage = document.createElement(LANGUAGE);
                    newStateLanguage.setTextContent(state.getLanguage());

                    Element newStateCapital = document.createElement(CAPITAL);
                    newStateCapital.setTextContent(state.getCapital());

                    newState.appendChild(newStateName);
                    newState.appendChild(newStateLanguage);
                    newState.appendChild(newStateCapital);
                    newStates.appendChild(newState);
                });
                root.appendChild(newCountry);
                document.appendChild(root);
            });
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new FileOutputStream(fileName));            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(source, streamResult);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CountryDetailsXMLSupplier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(CountryDetailsXMLSupplier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(CountryDetailsXMLSupplier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CountryDetailsXMLSupplier.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
