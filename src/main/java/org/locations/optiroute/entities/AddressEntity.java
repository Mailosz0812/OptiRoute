package org.locations.optiroute.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class AddressEntity {
    private String address;
    private String name;
    private Double Lat;
    private Double Lon;
}
