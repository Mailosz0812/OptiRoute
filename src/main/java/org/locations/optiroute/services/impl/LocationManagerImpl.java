package org.locations.optiroute.services.impl;

import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.exceptions.AddressNotFoundException;
import org.locations.optiroute.services.LocationManager;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class LocationManagerImpl implements LocationManager {
    private final LocationLoader locationLoader;

    public LocationManagerImpl(LocationLoader locationLoader) {
        this.locationLoader = locationLoader;
    }

    @Override
    public AddressEntity findLocationByName(String name) {
        AddressEntity addressEntityValue = locationLoader.getLocations().get(name.toUpperCase());
        if(addressEntityValue == null){
            throw new AddressNotFoundException("Address not found",name);
        }
        return addressEntityValue;
    }

}
