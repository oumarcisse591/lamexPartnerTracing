package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Country;
import com.slabtech.lamexPartnerTracing.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService{

    private CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl( CountryRepository theCountryRepository){
        countryRepository = theCountryRepository;
    }
    @Override
    public List<Country> findAllCountry() {
        return countryRepository.findAll();
    }
}
