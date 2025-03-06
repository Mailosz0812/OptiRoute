package org.locations.optiroute.DTOs;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "User name is required")
    private String USER_NAME;

    private String USER_COMPANY;

    @NotBlank(message = "Password is required")
    private String USER_PASSWORD;
}
