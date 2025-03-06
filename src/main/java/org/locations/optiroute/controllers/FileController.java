package org.locations.optiroute.controllers;

import org.locations.optiroute.Mappers.Mapper;
import org.locations.optiroute.DTOs.AddressDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.services.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class FileController {
    private FileService fileService;
    private Mapper<AddressEntity,AddressDTO> mapper;
    public FileController(FileService fileService, Mapper<AddressEntity,AddressDTO> mapper){
        this.fileService = fileService;
        this.mapper = mapper;
    }
    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping("/uploadJSON")
    public ResponseEntity<?> uploadJSON(@RequestParam("file") MultipartFile multipartFile){
        fileService.loadJSONFile(multipartFile);
        return ResponseEntity.ok("File processed successfully");
    }
}
