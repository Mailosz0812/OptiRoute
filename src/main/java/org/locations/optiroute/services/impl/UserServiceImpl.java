package org.locations.optiroute.services.impl;

import org.locations.optiroute.DTOs.UserDTO;
import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.entities.UserEntity;
import org.locations.optiroute.repositories.UserRepository;
import org.locations.optiroute.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private Mapper<UserEntity,UserDTO> mapper;


    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           Mapper<UserEntity, UserDTO> mapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setNAME(userDTO.getUSER_NAME());
        if(userDTO.getUSER_COMPANY() != null) {
            userEntity.setCOMPANY(userDTO.getUSER_COMPANY());
        }
        userEntity.setPASSWORD(passwordEncoder.encode(userDTO.getUSER_PASSWORD()));
        userRepository.save(userEntity);
        return mapper.mapTo(userEntity);
    }

}
