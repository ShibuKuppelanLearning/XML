/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xml.parser.dom;

import com.details.Country;
import com.details.CountryDetails;
import com.details.State;
import com.xml.XMLBasics;
import com.xml.handler.constants.CountryDetailsConstants;
import static com.xml.handler.constants.CountryDetailsConstants.CAPITAL;
import static com.xml.handler.constants.CountryDetailsConstants.CODE;
import static com.xml.handler.constants.CountryDetailsConstants.COUNTRY;
import static com.xml.handler.constants.CountryDetailsConstants.LANGUAGE;
import static com.xml.handler.constants.CountryDetailsConstants.NAME;
import static com.xml.handler.constants.CountryDetailsConstants.ROOT;
import static com.xml.handler.constants.CountryDetailsConstants.STATE;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Shibu
 */
public class CountryDetailsDirectElementParser implements CountryDetailsConstants {

    private CountryDetails countryDetails;
    private Country currentCountry;
    private State currentState;

    public void parseXML() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File((XMLBasics.class).getResource("/com/xml/docs/xsd/countryDetails.xml").getPath()));
            Element root = document.getDocumentElement();
            NodeList countryList = root.getElementsByTagName(COUNTRY);
            NodeList stateList = root.getElementsByTagName(STATE);
            countryDetails = new CountryDetails();
            countryDetails.setCountries(new ArrayList<>());
            mapCountryAndStateList(countryList, stateList);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CountryDetailsDirectElementParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CountryDetailsDirectElementParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CountryDetailsDirectElementParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mapCountryAndStateList(NodeList countryNodeList, NodeList stateNodeList) {
        if (countryNodeList != null && stateNodeList != null) {
            Node countryNode, stateNode;
            String countryCode;
            for (int i = 0; i < countryNodeList.getLength(); i++) {
                countryNode = countryNodeList.item(i);
                constructElementFromNode(countryNode);
                for (int j = 0; j < stateNodeList.getLength(); j++) {
                    stateNode = stateNodeList.item(j);
                    if (parentCountryMatch(countryNode, stateNode)) {
                        constructElementFromNode(stateNode);
                    }
                }
            }
        }
    }

    private boolean parentCountryMatch(Node countryNode, Node stateNode) {
        return countryNode.hasAttributes() && stateNode.getParentNode().getParentNode().hasAttributes() && countryNode.getAttributes().getNamedItem(CODE).getNodeValue().equalsIgnoreCase(stateNode.getParentNode().getParentNode().getAttributes().getNamedItem(CODE).getNodeValue());
    }

    private void constructElementFromNode(Node node) {
        String nodeName = node.getNodeName();
        Node childNode;
        switch (nodeName) {
            case COUNTRY:
                currentCountry = new Country();
                currentCountry.setCode(node.getAttributes().getNamedItem(CODE).getNodeValue());                
                for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                    childNode = node.getChildNodes().item(i);
                    switch (childNode.getNodeName()) {
                        case NAME:
                            currentCountry.setName(childNode.getTextContent());
                            break;
                        case LANGUAGE:
                            currentCountry.setLanguage(childNode.getTextContent());
                            break;
                        case CAPITAL:
                            currentCountry.setCapital(childNode.getTextContent());
                            break;
                    }
                }
                currentCountry.setStates(new ArrayList<>());
                countryDetails.getCountries().add(currentCountry);
                break;
            case STATE:
                currentState = new State();                 
                for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                    childNode = node.getChildNodes().item(i);
                    switch (childNode.getNodeName()) {
                        case NAME:
                            currentState.setName(childNode.getTextContent());
                            break;
                        case LANGUAGE:
                            currentState.setLanguage(childNode.getTextContent());
                            break;
                        case CAPITAL:
                            currentState.setCapital(childNode.getTextContent());
                            break;
                    }
                }
                currentCountry.getStates().add(currentState);
                break;
        }
    }

    public CountryDetails getCountryDetails() {
        return countryDetails;
    }

}
