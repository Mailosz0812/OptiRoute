package org.locations.optiroute.controllers;


import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.dto.AddressDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.repositories.AddressRepository;
import org.locations.optiroute.services.LocationManager;
import org.locations.optiroute.services.impl.LocationLoader;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddressController {
    private LocationLoader loader;
    private LocationManager manager;
    private Mapper<AddressEntity,AddressDTO> modelMapper;
    private AddressRepository addressRepo;

    public AddressController(LocationLoader loader, LocationManager manager,
                             Mapper<AddressEntity,AddressDTO> modelMapper,
                             AddressRepository addressRepo) {
        this.loader = loader;
        this.manager = manager;
        this.modelMapper = modelMapper;
        this.addressRepo = addressRepo;
    }

    @PostMapping("/addAddress")
    public AddressDTO addAddress(@RequestBody AddressDTO addressDTO){
        AddressEntity addressEntity = modelMapper.mapFrom(addressDTO);
        addressRepo.saveAddress(addressEntity);
        return modelMapper.mapTo(addressEntity);
    }

    @GetMapping("/findByName")
    public AddressDTO findAddressByName(@RequestParam String name){
        AddressEntity addressEntity = manager.findLocationByName(name);
        return modelMapper.mapTo(addressEntity);
    }
}
