package org.locations.optiroute.services.impl;

import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.services.LocationManager;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class LocationManagerImpl implements LocationManager {
    private final LocationLoader locationLoader;

    public LocationManagerImpl(LocationLoader locationLoader) {
        this.locationLoader = locationLoader;
    }

    @Override
    public AddressEntity findLocationByName(String address) {
        AddressEntity addressEntityValue = locationLoader.getLocations().get(address.toUpperCase());
        if(addressEntityValue == null){
            throw new NoSuchElementException();
        }
        return addressEntityValue;
    }

}
