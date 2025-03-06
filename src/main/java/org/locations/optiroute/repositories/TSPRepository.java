package org.locations.optiroute.repositories;

import org.locations.optiroute.entities.AddressEntity;

import java.util.List;

public interface TSPRepository {
    List<AddressEntity> solveLinearWithMTZModel(Double[][] addressDistancesList, List<AddressEntity> entityList);
    List<AddressEntity> christofidesAlgorithm(Double[][] distanceMatrix,List<AddressEntity> entityList);
}
