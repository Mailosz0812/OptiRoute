package org.locations.optiroute.services;

import org.locations.optiroute.entities.AddressEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<AddressEntity> loadJSONFile(MultipartFile file);
}
