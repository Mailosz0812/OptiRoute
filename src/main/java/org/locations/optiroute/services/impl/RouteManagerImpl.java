package org.locations.optiroute.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.locations.optiroute.entities.AddressEntity;
import org.locations.optiroute.exceptions.ResponseCodeException;
import org.locations.optiroute.exceptions.ResponseException;
import org.locations.optiroute.repositories.TSPRepository;
import org.locations.optiroute.services.RouteManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class RouteManagerImpl implements RouteManager {

    private TSPRepository tspRepository;

    public RouteManagerImpl(TSPRepository tspRepository) {
        this.tspRepository = tspRepository;
    }

    @Override
    public JsonNode findRoute(List<AddressEntity> addressEntities,String type,String vehicle) throws IOException {
        StringBuilder buildURL = new StringBuilder();
        buildURL.append("https://router.project-osrm.org/")
                .append(type)
                .append("/v1/")
                .append(vehicle)
                .append("/");
        for (AddressEntity addressEntity : addressEntities) {
            buildURL.append(addressEntity.getLON())
                    .append(",")
                    .append(addressEntity.getLAT())
                    .append(";");
        }
        buildURL.deleteCharAt(buildURL.lastIndexOf(";"));
        buildURL.append("?overview=full&geometries=geojson");
        JsonNode response = getResponse(buildURL.toString());
        return response;
    }
    @Override
    public List<AddressEntity> findTspProblemExactSolution(AddressEntity depotAddressEntity, List<AddressEntity> addressEntityList) throws IOException {
        addressEntityList.add(0,depotAddressEntity);
        Double[][] distanceMatrix = distanceOnRoadMatrix(addressEntityList);
        List<AddressEntity> solvedEntityList = tspRepository.solveLinearWithMTZModel(distanceMatrix,addressEntityList);
        return solvedEntityList;
    }

    @Override
    public List<AddressEntity> findTspProblemApproximateSolution(List<AddressEntity> addressEntities,AddressEntity depot) throws IOException {
        addressEntities.add(0,depot);
        Double[][] distanceMatrix = distanceOnRoadMatrix(addressEntities);
        List<AddressEntity> solvedEntityList = tspRepository.christofidesAlgorithm(distanceMatrix,addressEntities);
        return solvedEntityList;
    }

    private Double[][] distanceOnRoadMatrix(List<AddressEntity> addressEntityList) throws IOException{
        StringBuilder buildURL = new StringBuilder();
        buildURL.append("https://router.project-osrm.org/")
                .append("table/")
                .append("v1/")
                .append("driving/");
        for (AddressEntity addressEntity : addressEntityList) {
            buildURL.append(addressEntity.getLON())
                    .append(",")
                    .append(addressEntity.getLAT())
                    .append(";");
        }
        buildURL.deleteCharAt(buildURL.lastIndexOf(";"));

        JsonNode response = getResponse(buildURL.toString());
        return getDurationMatrix(response,addressEntityList.size());
    }

    private Double[][] getDurationMatrix(JsonNode response,int size) {
        JsonNode durationMatrix = response.get("durations");
        Double[][] distanceMatrix = new Double[size][size];
        int i = 0,j;
        if(!durationMatrix.isArray()){
            throw new ResponseException("Malformed external API respond", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        for (JsonNode matrix : durationMatrix) {
            j = 0;
            if(matrix.isArray()){
                for (JsonNode jsonNode : matrix) {
                        distanceMatrix[i][j] = jsonNode.asDouble();
                        j++;
                }
            }
            i++;
        }
        return distanceMatrix;
    }

    private JsonNode getResponse(String urlString) throws IOException {
        System.out.println(urlString);
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept","application/json");
        try {
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println(responseCode);
                throw new ResponseCodeException(
                        "OSRM API request failed: " + conn.getResponseMessage(),
                        HttpStatus.valueOf(responseCode)
                );
            }

            try (InputStream inputStream = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(inputStream);
            }
        } finally {
            conn.disconnect();
        }
    }
}
