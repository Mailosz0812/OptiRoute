package org.locations.optiroute.DTOs;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RouteDTO {
    private List<AddressDTO> addressDTOList;
    private JsonNode routingResponse;
}
