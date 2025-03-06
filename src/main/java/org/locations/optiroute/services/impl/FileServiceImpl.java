package org.locations.optiroute.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.locations.optiroute.DAOs.AddressDAO;
import org.locations.optiroute.DTOs.AddressDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.exceptions.AddressNotFoundException;
import org.locations.optiroute.exceptions.FileLoadingException;
import org.locations.optiroute.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class FileServiceImpl implements FileService {
    private ObjectMapper objectMapper;
    private AddressDAO addressDAO;

    public FileServiceImpl(AddressDAO addressDAO, ObjectMapper objectMapper) {
        this.addressDAO = addressDAO;
        this.objectMapper = objectMapper;
    }
    @Override
    public List<AddressEntity> loadJSONFile(MultipartFile file) {
        try {
            List<@Valid AddressEntity> addressEntities = objectMapper.readValue(file.getInputStream(), new TypeReference<@Valid List<AddressEntity>>() {});
            return addressDAO.saveAllAddresses(addressEntities);
        }catch (IOException ioException){
            throw new FileLoadingException("File loading went wrong: "+file.getName());
        }
    }
}
