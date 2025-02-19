package org.locations.optiroute.repositories;

import org.locations.optiroute.entities.AddressEntity;

public interface AddressRepository {
    AddressEntity saveAddress(AddressEntity addressEntity);
}
