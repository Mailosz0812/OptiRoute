package org.locations.optiroute.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    private String address;
    private String name;
    private Double Lat;
    private Double Lon;
}
