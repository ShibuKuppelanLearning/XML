/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xml.marshalling;

import com.xml.country.details.ObjectFactory;
import com.xml.country.details.Root;
import com.xml.country.details.States;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Shibu
 */
public class Marshalling {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ObjectFactory objectFactory = new ObjectFactory();
        
        Root root = objectFactory.createRoot();
        
        Root.Country country = objectFactory.createRootCountry();
        country.setName("India");
        country.setCode("IN");
        country.setLanguage("Hindi");
        country.setCapital("Delhi");
        
        States states = objectFactory.createStates();
        
        States.State state1 = objectFactory.createStatesState();
        state1.setName("Kerala");
        state1.setCode("KL");
        state1.setLanguage("Malayalam");
        state1.setCapital("Thrivananthapuram");        
        states.getState().add(state1);
        
        States.State state2 = objectFactory.createStatesState();
        state2.setName("Tamilnadu");
        state2.setCode("TN");
        state2.setLanguage("Tamil");
        state2.setCapital("Chennai");        
        states.getState().add(state2);
        
        country.setStates(states);
        
        root.setCountry(country);
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("com.xml.country.details");
            
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(jaxbContext, new File("newCountryDetails.xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(Marshalling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
