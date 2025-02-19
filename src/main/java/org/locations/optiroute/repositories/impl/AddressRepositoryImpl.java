package org.locations.optiroute.repositories.impl;

import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.repositories.AddressRepository;
import org.locations.optiroute.services.impl.LocationLoader;
import org.springframework.stereotype.Component;

@Component
public class AddressRepositoryImpl implements AddressRepository {
    private LocationLoader loader;

    public AddressRepositoryImpl(LocationLoader loader) {
        this.loader = loader;
    }

    @Override
    public AddressEntity saveAddress(AddressEntity addressEntity) {
        return loader.getLocations().put(addressEntity.getName(),addressEntity);
    }
}
