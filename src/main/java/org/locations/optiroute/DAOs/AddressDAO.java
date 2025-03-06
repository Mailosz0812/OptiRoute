package org.locations.optiroute.DAOs;

import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.exceptions.AddressNotFoundException;
import org.locations.optiroute.repositories.AddressRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressDAO {
    private AddressRepository repository;

    public AddressDAO(AddressRepository repository) {
        this.repository = repository;
    }

    public AddressEntity saveAddress(AddressEntity address){
        return repository.save(address);
    }
    public AddressEntity findAddressByName(String name){
        return repository.findByNAME(name)
                .orElseThrow(() -> new AddressNotFoundException("Address not found in database",name));
    }
    public List<AddressEntity> getAllAddresses(){
        return repository.findAll();
    }
    public List<AddressEntity> saveAllAddresses(List<AddressEntity> entityList){
        return repository.saveAll(entityList);
    }
}
