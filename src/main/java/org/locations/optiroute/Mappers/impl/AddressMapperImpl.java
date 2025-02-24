package org.locations.optiroute.Mappers.impl;

import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.dto.AddressDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
    public HashMap<String, AddressDTO> mapToAll(HashMap<String, AddressEntity> addressesMap) {
        HashMap<String, AddressDTO> addressDTOHashMap = new HashMap<>();
        for (String s : addressesMap.keySet()) {
            AddressEntity addrEntity = addressesMap.get(s);
            addressDTOHashMap.put(s,mapTo(addrEntity));
        }
        return addressDTOHashMap;
    }

    @Override
    public HashMap<String, AddressEntity> mapFromAll(List<AddressDTO> addressDTOList) {
        HashMap<String,AddressEntity> addressEntityHashMap = new HashMap<>();
        for (AddressDTO addressDTO : addressDTOList) {
            AddressEntity addressEntity = mapFrom(addressDTO);
            addressEntityHashMap.put(addressEntity.getName(),addressEntity);
        }
        return addressEntityHashMap;
    }

    @Override
    public HashMap<String, AddressDTO> mapToAll(List<AddressEntity> addressEntityList) {
        HashMap<String,AddressDTO> addressDTOHashMap = new HashMap<>();
        for (AddressEntity addressEntity : addressEntityList) {
            AddressDTO addressDTO = mapTo(addressEntity);
            addressDTOHashMap.put(addressDTO.getName(),addressDTO);
        }
        return addressDTOHashMap;
    }

    @Override
    public HashMap<String, AddressEntity> mapFromAll(HashMap<String, AddressDTO> addressesMap) {
        HashMap<String, AddressEntity> addressEntityHashMap = new HashMap<>();
        for (String s : addressesMap.keySet()) {
            AddressDTO addrDTO = addressesMap.get(s);
            addressEntityHashMap.put(s,mapFrom(addrDTO));
        }
        return addressEntityHashMap;
    }
}
