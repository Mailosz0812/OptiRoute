package org.locations.optiroute.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDTO {
    private String address;
    private String name;
    private Double Lat;
    private Double Lon;
}