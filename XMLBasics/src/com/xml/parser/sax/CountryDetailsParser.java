package com.xml.parser.sax;

import com.details.Country;
import com.details.CountryDetails;
import com.details.State;
import com.xml.handler.constants.CountryDetailsConstants;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Shibu
 */
public class CountryDetailsParser extends DefaultHandler implements CountryDetailsConstants {

    private String currentElement;
    
    private CountryDetails countryDetails;

    private Country currentCountry;

    private List<Country> currentCountries;

    private List<State> currentStates;

    private State currentState;

    private String value;

    @Override
    public void startDocument() throws SAXException {

    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("com.xml.handler.CountryDetailsHandler.endDocument()");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        currentElement = qName;
         String attributeName;
        switch (qName) {
            case ROOT:
                countryDetails = new CountryDetails();
                currentCountries = new ArrayList<>();
                break;
            case COUNTRY:
                currentCountry = new Country();               
                for(int i=0;i<attributes.getLength();i++){
                    attributeName = attributes.getQName(i);
                    if(CODE.equalsIgnoreCase(attributeName)){
                        currentCountry.setCode(attributes.getValue(i));
                    }
                }
                break;
            case STATES:
                currentStates = new ArrayList<>();
                break;
            case STATE:
                currentState = new State();                 
                for(int i=0;i<attributes.getLength();i++){
                    attributeName = attributes.getQName(i);
                    if(CODE.equalsIgnoreCase(attributeName)){
                        currentState.setCode(attributes.getValue(i));
                    }
                }
                break;
            case NAME:
              
            case LANGUAGE:
               
            case CAPITAL:
                
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        switch (qName) {            
            case NAME:
                  if (currentState != null) {
                    currentState.setName(value);
                } else if (currentCountry != null) {
                    currentCountry.setName(value);
                }
                break;
            case LANGUAGE:
                 if (currentState != null) {
                    currentState.setLanguage(value);
                } else if (currentCountry != null) {
                    currentCountry.setLanguage(value);
                }
                break;
            case CAPITAL:
                if (currentState != null) {
                    currentState.setCapital(value);
                } else if (currentCountry != null) {
                    currentCountry.setCapital(value);
                }
                break;
            case ROOT:
                countryDetails.setCountries(currentCountries);
                break;
            case STATES:
                currentCountry.setStates(currentStates);
                break;
            case COUNTRY:
                currentCountries.add(currentCountry);
                break;
            case STATE:
                currentStates.add(currentState);
                break;
        }
    }

    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        switch (currentElement) {
            case NAME:
            case LANGUAGE:
            case CAPITAL:
                value = new String(ch).substring(start,start+length);
                break;
            case ROOT:
            case STATES:
            case COUNTRY:
            case STATE:
                value = "";              
        }
    }

    public CountryDetails getCountryDetails() {
        return this.countryDetails;
    }
}
