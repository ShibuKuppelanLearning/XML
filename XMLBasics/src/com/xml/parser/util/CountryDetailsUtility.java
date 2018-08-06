/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xml.parser.util;

import com.details.Country;
import com.details.CountryDetails;
import com.details.State;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shibu
 */
public class CountryDetailsUtility {
    
    public static CountryDetails getCountryUtility(){
        CountryDetails countryDetails = new CountryDetails();
        List<Country> countries = new ArrayList<>();
        
        Country country = new Country();
        country.setName("India");
        country.setLanguage("Hindi");
        country.setCapital("Delhi");
        country.setCode("IND");
        country.setStates(new ArrayList<>());
        countries.add(country);
        
        State state1 = new State();
        state1.setName("Kerala");
        state1.setLanguage("Malayalam");
        state1.setCapital("Thiruvananthapuram");
        country.getStates().add(state1);
        
         State state2 = new State();
        state2.setName("Tamilnadu");
        state2.setLanguage("Tamil");
        state2.setCapital("Chennai");
        country.getStates().add(state2);
        
        countryDetails.setCountries(countries);
        return countryDetails;
    }
}
