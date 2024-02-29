package com.lamayamei.pruebaviamaticabackend.controller;


import com.lamayamei.pruebaviamaticabackend.DTO.UserDTO;
import com.lamayamei.pruebaviamaticabackend.entity.Usuarios;
import com.lamayamei.pruebaviamaticabackend.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {


    private final UsuariosService usuariosService;

    @Autowired
    public UsersController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<Usuarios> users = usuariosService.getAllUsers();
        return users.stream()
                .map(user -> usuariosService.mapToUserDTO(user))
                .collect(Collectors.toList());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        Optional<Usuarios> userOptional = usuariosService.getUserByUsername(username);
        if (userOptional.isPresent()) {
            Usuarios userEntity = userOptional.get();
            UserDTO userDTO = usuariosService.mapToUserDTO(userEntity);

            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/edit/{username}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String username, @RequestBody UserDTO userDTO) {
        Optional<UserDTO> updatedUserDTO = usuariosService.updateUserByUsername(username, userDTO);

        return updatedUserDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
