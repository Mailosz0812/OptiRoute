package org.locations.optiroute.controllers;


import jakarta.validation.Valid;
import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.dto.AddressDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.repositories.AddressRepository;
import org.locations.optiroute.services.LocationManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ResponseEntity<?> addAddress(@RequestBody @Valid AddressDTO addressDTO){
        AddressEntity addressEntity = null;
        try{
            addressEntity = modelMapper.mapFrom(addressDTO);
            addressRepo.saveAddress(addressEntity);
            System.out.println(addressEntity.getLat());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Writing new Address to JSON file went wrong");
        }
        return ResponseEntity.ok(modelMapper.mapTo(addressEntity));
    }
    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/findByName")
    public ResponseEntity<?> findAddressByName(@RequestParam String name){
        AddressEntity addressEntity = manager.findLocationByName(name);
        return ResponseEntity.ok(addressEntity);
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/getAllPoints")
    public ResponseEntity<?> getAllPoints(){
        return ResponseEntity.ok(addressRepo.getAllAddresses());
    }
}
