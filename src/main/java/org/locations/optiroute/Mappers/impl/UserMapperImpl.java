package org.locations.optiroute.Mappers.impl;

import org.locations.optiroute.DTOs.UserDTO;
import org.locations.optiroute.DTOs.UserDTO;
import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDTO> {
    private ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity,UserDTO.class);
    }

    public UserEntity mapFrom(UserDTO userDTO) {
        return modelMapper.map(userDTO,UserEntity.class);
    }

    @Override
    public List<UserDTO> mapToList(List<UserEntity> userEntityList) {
        List<UserDTO> addressDTOList = new ArrayList<>();
        for (UserEntity user: userEntityList) {
            addressDTOList.add(mapTo(user));
        }
        return addressDTOList;
    }

    @Override
    public List<UserEntity> mapFromList(List<UserDTO> userDTOList) {
        List<UserEntity> userEntityList = new ArrayList<>();
        for (UserDTO userDTO : userDTOList) {
            userEntityList.add(modelMapper.map(userDTO, UserEntity.class));
        }
        return userEntityList;
    }
}
