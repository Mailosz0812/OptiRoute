package org.locations.optiroute.services.impl;

import org.apache.tomcat.util.json.JSONParser;
import org.locations.optiroute.dto.AddressDTO;
import org.locations.optiroute.dto.RouteDTO;
import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.exceptions.ResponseCodeException;
import org.locations.optiroute.services.RouteManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class RouteManagerImpl implements RouteManager {
    @Override
    public RouteDTO findRoute(AddressDTO start, AddressDTO finish,String type,String vehicle) throws IOException {
        StringBuilder buildURL = new StringBuilder();
        buildURL.append("https://router.project-osrm.org/");
        buildURL.append(type);
        buildURL.append("/v1/");
        buildURL.append(vehicle);
        buildURL.append("/");
        String urlString = String.format("%s%s,%s;%s,%s?overview=full&geometries=geojson",
                buildURL,start.getLon(),start.getLat(),finish.getLon(),finish.getLat());
        System.out.println(urlString);
        String response = getResponse(urlString);
        RouteDTO routeDTO = constructRouteDTO(start,finish,response);
        return routeDTO;
    }

    private String getResponse(String urlString) throws IOException {
        URL url = new URL(urlString);
        StringBuilder output = new StringBuilder();

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept","application/json");
        try {
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new ResponseCodeException(
                        "OSRM API request failed: " + conn.getResponseMessage(),
                        HttpStatus.valueOf(responseCode)
                );
            }

            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = buffer.readLine()) != null) {
                    output.append(line);
                }
            }
            return output.toString();
        } finally {
            conn.disconnect();
        }
    }

    private RouteDTO constructRouteDTO(AddressDTO startAddress, AddressDTO finishAddress, String response){
        RouteDTO routeDTO = RouteDTO.builder()
                .routingResponse(response)
                .finishAddress(finishAddress)
                .startAddress(startAddress)
                .build();
        return routeDTO;
    }
}
