package org.locations.optiroute.controllers;

import org.locations.optiroute.DTOs.UserDTO;
import org.locations.optiroute.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(UserDTO userDTO){
        userDTO = userService.registerUser(userDTO);
        return ResponseEntity.ok(userDTO);
    }
}
