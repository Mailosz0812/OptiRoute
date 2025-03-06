package org.locations.optiroute.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locations.optiroute.DTOs.AddressDTO;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class routeRequest {

    @NotBlank(message = "type can't be blank")
    String type;

    @NotBlank(message = "vehicle type can't be blank")
    String vehicle;

    @NotNull(message = "Start Address is required")
    AddressDTO startAddress;

    @NotNull(message = "Finish Address is required")
    AddressDTO finishAddress;
}
