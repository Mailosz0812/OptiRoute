package org.locations.optiroute.services;

import org.locations.optiroute.dto.AddressDTO;
import org.locations.optiroute.dto.RouteDTO;

import java.io.IOException;


public interface RouteManager {
    RouteDTO findRoute(AddressDTO start, AddressDTO finish, String type, String vehicle) throws IOException;
}
