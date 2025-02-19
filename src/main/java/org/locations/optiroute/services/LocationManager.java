package org.locations.optiroute.services;

import org.locations.optiroute.entities.AddressEntity;

public interface LocationManager {
    AddressEntity findLocationByName(String address);
}
