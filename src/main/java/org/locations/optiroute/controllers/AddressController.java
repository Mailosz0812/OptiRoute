package org.locations.optiroute.controllers;


import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.dto.AddressDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.repositories.AddressRepository;
import org.locations.optiroute.services.LocationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class AddressController {
    private LocationManager manager;
    private Mapper<AddressEntity,AddressDTO> modelMapper;
    private AddressRepository addressRepo;

    public AddressController(LocationManager manager,
                             Mapper<AddressEntity,AddressDTO> modelMapper,
                             AddressRepository addressRepo) {
        this.manager = manager;
        this.modelMapper = modelMapper;
        this.addressRepo = addressRepo;
    }
    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping("/addAddress")
    public AddressDTO addAddress(@RequestBody AddressDTO addressDTO){
        AddressEntity addressEntity = modelMapper.mapFrom(addressDTO);
        addressRepo.saveAddress(addressEntity);
        System.out.println(addressEntity.getLat());
        return modelMapper.mapTo(addressEntity);
    }
    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/findByName")
    public AddressDTO findAddressByName(@RequestParam String name){
        AddressEntity addressEntity = manager.findLocationByName(name);
        return modelMapper.mapTo(addressEntity);
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/getAllPoints")
    public HashMap<String,AddressDTO> getAllPoints(){
        return addressRepo.getAllAddresses();
    }
}
