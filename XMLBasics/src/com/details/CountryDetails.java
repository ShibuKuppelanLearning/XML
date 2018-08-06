/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.details;

import java.util.List;

/**
 *
 * @author Shibu
 */
public class CountryDetails {

    private List<Country> countries;

    public CountryDetails() {
    }

    public CountryDetails(List<Country> countries) {
        this.countries = countries;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "CountryDetails{" + "countries:" + countries + '}';
    } 
}
