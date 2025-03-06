package org.locations.optiroute.controllers;


import jakarta.validation.Valid;
import org.locations.optiroute.DAOs.AddressDAO;
import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.DTOs.AddressDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {
    private Mapper<AddressEntity,AddressDTO> modelMapper;
    private AddressDAO addressDAO;

    public AddressController(
                             Mapper<AddressEntity,AddressDTO> modelMapper,
                             AddressDAO addressDAO) {
        this.modelMapper = modelMapper;
        this.addressDAO = addressDAO;
    }
    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping("/addAddress")
    public ResponseEntity<?> addAddress(@RequestBody @Valid AddressDTO addressDTO){
        AddressEntity addressEntity;
        addressEntity = modelMapper.mapFrom(addressDTO);
        addressDAO.saveAddress(addressEntity);
        return ResponseEntity.ok(modelMapper.mapTo(addressEntity));
    }
    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/findByName")
    public ResponseEntity<?> findAddressByName(@RequestParam String name){
        AddressEntity addressEntity = addressDAO.findAddressByName(name);
        AddressDTO addressDTO = modelMapper.mapTo(addressEntity);
        return ResponseEntity.ok(addressDTO);
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/getAllPoints")
    public ResponseEntity<?> getAllPoints(){
        List<AddressEntity> entitiesList = addressDAO.getAllAddresses();
        return ResponseEntity.ok(modelMapper.mapToList(entitiesList));
    }
}
