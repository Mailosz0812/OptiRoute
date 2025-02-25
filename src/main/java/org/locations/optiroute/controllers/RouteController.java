package org.locations.optiroute.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.dto.AddressDTO;
import org.locations.optiroute.dto.RouteDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.requests.routeRequest;
import org.locations.optiroute.services.RouteManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
        String type = request.getType();
        String vehicle = request.getVehicle();
        RouteDTO route = null;
        try {
            route = routeManager.findRoute(startAddress, finishAddress, type, vehicle);
        } catch (IOException IOe) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during route generation");
        }
        return ResponseEntity.ok(route);
    }
}
