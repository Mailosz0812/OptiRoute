package org.locations.optiroute.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.locations.optiroute.entities.AddressEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class LocationLoader {
    private HashMap<String, AddressEntity> locations = new HashMap<>();
    private ObjectMapper mapper;
    public LocationLoader(ObjectMapper mapper){
        this.mapper = mapper;
    }
    public void loadJSON(){
        try {
            JsonNode jsonNode = mapper.readTree(new File("dane.json"));
            for (JsonNode node : jsonNode) {
                String name = node.get("name").asText();
                String street = node.get("address").asText();
                Double lat = node.get("lat").asDouble();
                Double lon = node.get("lon").asDouble();
                System.out.println(name);
                AddressEntity addressEntity = new AddressEntity(street,name,lat,lon);
                locations.put(name, addressEntity);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writeToJSON() throws IOException{
            List<AddressEntity> addressEntities = new ArrayList<>(locations.values());
            mapper.writeValue(new File("dane.json"),addressEntities);
    }
}
