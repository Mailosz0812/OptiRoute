package org.locations.optiroute.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.DTOs.AddressDTO;
import org.locations.optiroute.DTOs.RouteDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.requests.TSPRequest;
import org.locations.optiroute.requests.routeRequest;
import org.locations.optiroute.services.RouteManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RouteController {
    RouteManager routeManager;
    Mapper<AddressEntity,AddressDTO> mapper;

    public RouteController(RouteManager routeManager,Mapper<AddressEntity,AddressDTO> mapper) {
        this.routeManager = routeManager;
        this.mapper = mapper;
    }
    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping("/routing")
    public ResponseEntity<?> getRoute(@RequestBody @Valid routeRequest request){
        AddressDTO startAddress = request.getStartAddress();
        AddressDTO finishAddress = request.getFinishAddress();
        List<AddressDTO> addressDTOS = new ArrayList<>();
        addressDTOS.add(startAddress);
        addressDTOS.add(finishAddress);
        List<AddressEntity> addressEntities = mapper.mapFromList(addressDTOS);
        String type = request.getType();
        String vehicle = request.getVehicle();
        RouteDTO routeDTO = null;
        try {
            routeDTO = RouteDTO.builder()
                    .routingResponse(routeManager.findRoute(addressEntities,type,vehicle))
                    .addressDTOList(addressDTOS)
                    .build();
        } catch (IOException IOe) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during route generation");
        }
        return ResponseEntity.ok(routeDTO);
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping("/TSP/Exact")
    public ResponseEntity<?> tspExactSolution(@RequestBody @Valid TSPRequest tspRequest){
        List<AddressEntity> addressEntities = mapper.mapFromList(tspRequest.getAddressDTOList());
        AddressEntity depotAddressEntity = mapper.mapFrom(tspRequest.getDepot());
        List<AddressEntity> solvedAddressEntities;
        List<AddressDTO> solvedAddressDTO = null;
        RouteDTO routeDTO = null;
        try{
            solvedAddressEntities = routeManager.findTspProblemExactSolution(depotAddressEntity,addressEntities);
            solvedAddressDTO = mapper.mapToList(solvedAddressEntities);
            JsonNode routingResponse = routeManager.findRoute(solvedAddressEntities,"route",tspRequest.getVehicle());
            routeDTO = RouteDTO.builder()
                    .addressDTOList(solvedAddressDTO)
                    .routingResponse(routingResponse)
                    .build();
        } catch (IOException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during route generation");
        }
        return ResponseEntity.ok(routeDTO);
    }

//    public ResponseEntity<?> tspApproximateSolution(@RequestBody @Valid TSPRequest tspRequest){
//        List<AddressEntity> addressEntities = mapper.mapFromList(tspRequest.getAddressDTOList());
//
//    }
}
