package org.locations.optiroute.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.dto.AddressDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.services.impl.LocationLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class FileController {
    private LocationLoader loader;
    private Mapper<AddressEntity,AddressDTO> mapper;
    public FileController(LocationLoader loader, Mapper<AddressEntity,AddressDTO> mapper){
        this.loader = loader;
        this.mapper = mapper;
    }
    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping("/uploadJSON")
    public ResponseEntity<?> uploadJSON(@RequestParam("file") MultipartFile multipartFile){
        if(multipartFile.isEmpty() || !multipartFile.getContentType().equals("application/json")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty or type is incorrect");
        }
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            List<@Valid AddressDTO> addressDTOS = objectMapper.readValue(multipartFile.getInputStream(), new TypeReference<@Valid List<AddressDTO>>() {});
            HashMap<String,AddressEntity> addressEntities = mapper.mapFromAll(addressDTOS);
            loader.setLocations(addressEntities);
        }catch(IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Mapping JSON file went wrong");
        }
        return ResponseEntity.ok("File processed successfully");
    }
}
