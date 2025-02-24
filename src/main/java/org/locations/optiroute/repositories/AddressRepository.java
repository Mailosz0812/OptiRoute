package org.locations.optiroute.repositories;

import org.locations.optiroute.dto.AddressDTO;
import org.locations.optiroute.entities.AddressEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface AddressRepository {
    AddressEntity saveAddress(AddressEntity addressEntity) throws IOException;
    HashMap<String,AddressDTO> getAllAddresses();
}
