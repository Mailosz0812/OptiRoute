package org.locations.optiroute.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.locations.optiroute.entities.AddressEntity;

import java.io.IOException;
import java.util.List;


public interface RouteManager {
    JsonNode findRoute(List<AddressEntity> addressEntities, String type, String vehicle) throws IOException;
    List<AddressEntity> findTspProblemExactSolution(AddressEntity depotAddressEntity, List<AddressEntity> addressEntityList) throws IOException;
    List<AddressEntity> findTspProblemApproximateSolution(List<AddressEntity> addressEntities,AddressEntity depot) throws IOException;

}
