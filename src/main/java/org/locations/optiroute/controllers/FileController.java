package org.locations.optiroute.controllers;

import jakarta.validation.Valid;
import org.locations.optiroute.dto.AddressDTO;
import org.locations.optiroute.services.impl.LocationLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FileController {
    private LocationLoader loader;

    @PostMapping("/uploadJSON")
    public ResponseEntity<String> uploadJSON(@Valid @RequestBody List<AddressDTO> addressDTOS){
        return ResponseEntity.ok("Addresses processed");
    }
}
