/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xml.parser.stax;

import com.details.Country;
import com.details.CountryDetails;
import com.details.State;
import com.xml.XMLBasics;
import com.xml.handler.constants.CountryDetailsConstants;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Shibu
 */
public class CountryDetailsParser implements CountryDetailsConstants, XMLStreamConstants {

    private String currentElement;

    private CountryDetails countryDetails;

    private Country currentCountry;

    private State currentState;

    public void parseXMLFromStream() {
        try (Reader reader = new FileReader(new File((XMLBasics.class).getResource("/com/xml/docs/xsd/countryDetails.xml").getPath()))) {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLStreamReader streamReader = xmlInputFactory.createXMLStreamReader(reader);
            readXMLFromStream(streamReader);
        } catch (XMLStreamException | IOException ex) {
            Logger.getLogger(CountryDetailsParser.class.getName()).log(Level.SEVERE, null, ex);
     
        }
    }

    private void readXMLFromStream(XMLStreamReader streamReader) {
        try {

            while (streamReader.hasNext()) {
                int itemType = streamReader.next();
                switch (itemType) {
                    case START_ELEMENT:
                        constructObjectByNodeTypeAndNameFromStream(streamReader, itemType);
                        break;
                    case END_ELEMENT:
                        constructObjectByNodeTypeAndNameFromStream(streamReader, itemType);
                        break;
                    case ATTRIBUTE:
                        currentCountry.setCode(streamReader.getAttributeValue(0));
                        break;
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(CountryDetailsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void constructObjectByNodeTypeAndNameFromStream(XMLStreamReader xmlStreamReader, int itemType) throws XMLStreamException {
        switch (xmlStreamReader.getName().getLocalPart()) {
            case ROOT:
                if (itemType == START_ELEMENT) {
                    countryDetails = new CountryDetails();
                    countryDetails.setCountries(new ArrayList<>());
                }
                break;
            case COUNTRY:
                if (itemType == START_ELEMENT) {
                    currentCountry = new Country();
                    countryDetails.getCountries().add(currentCountry);
                    currentElement = COUNTRY;
                }
                break;
            case STATES:
                if (itemType == START_ELEMENT) {
                    currentCountry.setStates(new ArrayList<>());
                }
                break;
            case STATE:
                if (itemType == START_ELEMENT) {
                    currentState = new State();
                    currentCountry.getStates().add(currentState);
                    currentElement = STATE;
                }
                break;
            case NAME:
                if (itemType == START_ELEMENT) {
                    if (COUNTRY.equalsIgnoreCase(currentElement)) {
                        currentCountry.setName(xmlStreamReader.getElementText());
                    } else if (STATE.equalsIgnoreCase(currentElement)) {
                        currentState.setName(xmlStreamReader.getElementText());
                    }
                }
                break;
            case LANGUAGE:
                if (itemType == START_ELEMENT) {
                    if (COUNTRY.equalsIgnoreCase(currentElement)) {
                        currentCountry.setLanguage(xmlStreamReader.getElementText());
                    } else if (STATE.equalsIgnoreCase(currentElement)) {
                        currentState.setLanguage(xmlStreamReader.getElementText());
                    }
                }
                break;
            case CAPITAL:
                if (itemType == START_ELEMENT) {
                    if (COUNTRY.equalsIgnoreCase(currentElement)) {
                        currentCountry.setCapital(xmlStreamReader.getElementText());
                    } else if (STATE.equalsIgnoreCase(currentElement)) {
                        currentState.setCapital(xmlStreamReader.getElementText());
                    }
                }
                break;
        }
    }
    
    public void parseXMLFromEvent() {
        try (Reader reader = new FileReader(new File((XMLBasics.class).getResource("/com/xml/docs/xsd/countryDetails.xml").getPath()))) {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLEventReader eventReader = xmlInputFactory.createXMLEventReader(reader);
            readXMLFromEvent(eventReader);
        } catch (XMLStreamException | IOException ex) {
            Logger.getLogger(CountryDetailsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readXMLFromEvent(XMLEventReader eventReader) {
        try {
            while (eventReader.hasNext()) {
                XMLEvent xmlEvent = eventReader.nextEvent();
                switch (xmlEvent.getEventType()) {
                    case START_ELEMENT:
                        constructObjectByNodeTypeAndNameAndEvent(eventReader,xmlEvent);
                        break;                        
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(CountryDetailsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void constructObjectByNodeTypeAndNameAndEvent(XMLEventReader eventReader,XMLEvent xmlEvent) throws XMLStreamException {
        StartElement startElement = xmlEvent.asStartElement(); 
        switch (startElement.getName().getLocalPart()) {
            case ROOT:
                countryDetails = new CountryDetails();
                countryDetails.setCountries(new ArrayList<>());

                break;
            case COUNTRY:
                currentCountry = new Country();
                countryDetails.getCountries().add(currentCountry);
                currentElement = COUNTRY;
                startElement.getAttributeByName(new QName(CODE));
                break;
            case STATES:

                currentCountry.setStates(new ArrayList<>());

                break;
            case STATE:

                currentState = new State();
                currentCountry.getStates().add(currentState);
                currentElement = STATE;

                break;
            case NAME:

                if (COUNTRY.equalsIgnoreCase(currentElement)) {
                    currentCountry.setName(eventReader.getElementText());
                } else if (STATE.equalsIgnoreCase(currentElement)) {
                    currentState.setName(eventReader.getElementText());
                }

                break;
            case LANGUAGE:

                if (COUNTRY.equalsIgnoreCase(currentElement)) {
                    currentCountry.setLanguage(eventReader.getElementText());
                } else if (STATE.equalsIgnoreCase(currentElement)) {
                    currentState.setLanguage(eventReader.getElementText());
                }

                break;
            case CAPITAL:

                if (COUNTRY.equalsIgnoreCase(currentElement)) {
                    currentCountry.setCapital(eventReader.getElementText());
                } else if (STATE.equalsIgnoreCase(currentElement)) {
                    currentState.setCapital(eventReader.getElementText());
                }        
                break;
    }
}

public CountryDetails getCountryDetails() {
        return countryDetails;
    }
}
