package org.locations.optiroute.repositories.impl;

import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.dto.AddressDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.repositories.AddressRepository;
import org.locations.optiroute.services.impl.LocationLoader;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AddressRepositoryImpl implements AddressRepository {
    private LocationLoader loader;
    private Mapper<AddressEntity,AddressDTO> modelMapper;

    public AddressRepositoryImpl(LocationLoader loader,Mapper<AddressEntity,AddressDTO> modelMapper) {
        this.loader = loader;
        this.modelMapper = modelMapper;
    }

    @Override
    public HashMap<String,AddressDTO> getAllAddresses() {
        return modelMapper.mapToAll(loader.getLocations());
    }

    @Override
    public AddressEntity saveAddress(AddressEntity addressEntity) {
        AddressEntity address = loader.getLocations().put(addressEntity.getName(),addressEntity);
        loader.writeToJSON();
        return address;
    }
}
