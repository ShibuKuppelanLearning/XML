/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xml.parser.dom;

import com.xml.XMLBasics;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import com.details.CountryDetails;
import com.details.Country;
import com.details.State;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.xml.handler.constants.CountryDetailsConstants;
import java.util.ArrayList;

/**
 *
 * @author Shibu
 */
public class CountryDetailsSequentialParser implements CountryDetailsConstants {

    private CountryDetails countryDetails;
    private Country currentCountry;
    private State currentState;

    public void domParserDemo() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(new File((XMLBasics.class).getResource("/com/xml/docs/xsd/countryDetails.xml").getPath()));
            constructElementFromNode(document.getDocumentElement());
            parseXML(document.getDocumentElement().getChildNodes());
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XMLBasics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void parseXML(NodeList nodeList) {
        Node node;
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                constructElementFromNode(node);
                if (node.hasChildNodes()) {
                    parseXML(node.getChildNodes());
                }            
            }
        }
    }

    private void constructElementFromNode(Node node) {
        String nodeName = node.getNodeName();
        switch (nodeName) {
            case ROOT:
                countryDetails = new CountryDetails();
                countryDetails.setCountries(new ArrayList<>());
                break;
            case COUNTRY:
                currentCountry = new Country();
                if(node.hasAttributes()){
                    currentCountry.setCode(node.getAttributes().getNamedItem(CODE).getNodeValue());
                }
                countryDetails.getCountries().add(currentCountry);
                break;
            case STATES:
                currentCountry.setStates(new ArrayList<>());
                break;
            case STATE:
                currentState = new State();
                if(node.hasAttributes()){
                    currentState.setCode(node.getAttributes().getNamedItem(CODE).getNodeValue());
                }
                currentCountry.getStates().add(currentState);
                break;
            case NAME:
                if (COUNTRY.equalsIgnoreCase(node.getParentNode().getNodeName())) {
                    currentCountry.setName(node.getTextContent());
                } else {
                    currentState.setName(node.getTextContent());
                }
                break;
            case LANGUAGE:
                if (currentCountry.getStates() == null) {
                    currentCountry.setLanguage(node.getTextContent());
                } else {
                    currentState.setLanguage(node.getTextContent());
                }
                break;
            case CAPITAL:
                if (currentCountry.getStates() == null) {
                    currentCountry.setCapital(node.getTextContent());
                } else {
                    currentState.setCapital(node.getTextContent());
                }
                break;
        }
    }

    public CountryDetails getCountryDetails() {
        return countryDetails;
    }
}
