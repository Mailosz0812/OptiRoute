package org.locations.optiroute.Mappers.impl;

import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.DTOs.AddressDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressMapperImpl implements Mapper<AddressEntity, AddressDTO> {
    private ModelMapper modelMapper;

    public AddressMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AddressDTO mapTo(AddressEntity addressEntity) {
        return modelMapper.map(addressEntity,AddressDTO.class);
    }

    @Override
    public AddressEntity mapFrom(AddressDTO addressDTO) {
        String name = addressDTO.getName().toUpperCase();
        String address = addressDTO.getAddress().toUpperCase();
        addressDTO.setName(name);
        addressDTO.setAddress(address);
        return modelMapper.map(addressDTO,AddressEntity.class);
    }

    @Override
    public List<AddressDTO> mapToList(List<AddressEntity> addressesList) {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        for (AddressEntity addressEntity : addressesList) {
            addressDTOList.add(mapTo(addressEntity));
        }
        return addressDTOList;
    }

    @Override
    public List<AddressEntity> mapFromList(List<AddressDTO> addressesList) {
        List<AddressEntity> addressEntityList = new ArrayList<>();
        for (AddressDTO addressDTO : addressesList) {
            addressEntityList.add(mapFrom(addressDTO));
        }
        return addressEntityList;
    }
}
