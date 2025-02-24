package org.locations.optiroute.dto;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RouteDTO {
    private AddressDTO startAddress;
    private AddressDTO finishAddress;
    private String routingResponse;
}
