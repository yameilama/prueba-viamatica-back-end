package com.lamayamei.pruebaviamaticabackend.service;

import com.lamayamei.pruebaviamaticabackend.DTO.RegistroDTO;
import com.lamayamei.pruebaviamaticabackend.DTO.UserDTO;
import com.lamayamei.pruebaviamaticabackend.entity.Usuarios;

import java.util.List;
import java.util.Optional;

public interface UsuariosService {
    List<Usuarios> getAllUsers();
    Optional<Usuarios> getUserByUsername(String username);
    UserDTO mapToUserDTO(Usuarios usuario);

    Optional<UserDTO> updateUserByUsername(String username, UserDTO userDTO);

    boolean registerUser(RegistroDTO registroDTO);

}
