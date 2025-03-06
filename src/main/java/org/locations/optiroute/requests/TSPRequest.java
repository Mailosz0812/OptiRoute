package org.locations.optiroute.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locations.optiroute.DTOs.AddressDTO;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TSPRequest {
    @NotNull(message = "Depot cannot be null")
    private AddressDTO depot;

    @NotNull(message = "List of points cannot be null")
    private List<AddressDTO> addressDTOList;

    @NotBlank(message = "Vehicle type cannot be blank")
    private String vehicle;
}
