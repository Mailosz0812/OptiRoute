package org.locations.optiroute.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.locations.optiroute.services.impl.LocationLoader;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class locationApiConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public LocationLoader locationLoader(){
        LocationLoader loader = new LocationLoader(objectMapper());
        loader.loadData();
        return loader;
    }
}
