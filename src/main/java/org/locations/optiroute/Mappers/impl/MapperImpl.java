package org.locations.optiroute.Mappers.impl;

import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.dto.AddressDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperImpl implements Mapper<AddressEntity, AddressDTO> {
    private ModelMapper modelMapper;

    public MapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AddressDTO mapTo(AddressEntity addressEntity) {
        return modelMapper.map(addressEntity,AddressDTO.class);
    }

    @Override
    public AddressEntity mapFrom(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO,AddressEntity.class);
    }
}
