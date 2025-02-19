package org.locations.optiroute.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.locations.optiroute.entities.AddressEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Getter
public class LocationLoader {
    private HashMap<String, AddressEntity> locations = new HashMap<>();
    private ObjectMapper mapper;
    public LocationLoader(ObjectMapper mapper){
        this.mapper = mapper;
    }
    public void loadData(){
        try {
            JsonNode jsonNode = mapper.readTree(new File("dane.json"));
            for (JsonNode node : jsonNode) {
                String name = node.get("Nazwa").asText();
                String street = node.get("Ulica").asText();
                Double lat = node.get("Latitude").asDouble();
                Double lon = node.get("Longitude").asDouble();
                AddressEntity addressEntity = new AddressEntity(street,name,lat,lon);
                locations.put(name, addressEntity);
                System.out.println(name);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
