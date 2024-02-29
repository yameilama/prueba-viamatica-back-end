package com.lamayamei.pruebaviamaticabackend.service.implementation;

import com.lamayamei.pruebaviamaticabackend.DTO.RegistroDTO;
import com.lamayamei.pruebaviamaticabackend.DTO.UserDTO;
import com.lamayamei.pruebaviamaticabackend.entity.Persona;
import com.lamayamei.pruebaviamaticabackend.entity.Usuarios;
import com.lamayamei.pruebaviamaticabackend.repository.UsuariosRepository;
import com.lamayamei.pruebaviamaticabackend.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServiceImpl implements UsuariosService {

    private final UsuariosRepository usuariosRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UsuariosServiceImpl(UsuariosRepository usuariosRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuariosRepository = usuariosRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public List<Usuarios> getAllUsers() {
        return usuariosRepository.findAll();
    }

    @Override
    public Optional<Usuarios> getUserByUsername(String username) {
        return usuariosRepository.findByUsername(username);

    }

    @Override
    public Optional<UserDTO> updateUserByUsername(String username, UserDTO userDTO) {
        Optional<Usuarios> userOptional = usuariosRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            Usuarios user = userOptional.get();
            user.setMail(userDTO.getMail());
            user.getPersona().setNombres(userDTO.getNombres());
            user.getPersona().setApellidos(userDTO.getApellidos());
            user.setSessionActive(userDTO.isSessionActive());
            user.getPersona().setIdentificacion(userDTO.getIdentificacion());
            user.setStatus(userDTO.getStatus());


            Usuarios updatedUser = usuariosRepository.save(user);
            return Optional.of(mapToUserDTO(updatedUser));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean registerUser(RegistroDTO registroDTO) {
        System.out.println("este???");
        Usuarios newUser = new Usuarios();

        newUser.setUsername(registroDTO.getUsername());
        newUser.setMail(registroDTO.getMail());
        Persona persona = new Persona();
        persona.setNombres(registroDTO.getNombres());
        persona.setApellidos(registroDTO.getApellidos());
        persona.setIdentificacion(registroDTO.getIdentificacion());
        newUser.setPersona(persona);
        String hashedPassword = bCryptPasswordEncoder.encode(registroDTO.getContrasena());
        newUser.setPassword(hashedPassword);
        newUser.setSessionActive(true);

        usuariosRepository.save(newUser);
        return true;
    }

    @Override
    public UserDTO mapToUserDTO(Usuarios usuario) {
        UserDTO dto = new UserDTO();
        dto.setUsername(usuario.getUsername());
        dto.setMail(usuario.getMail());
        dto.setNombres(usuario.getPersona().getNombres());
        dto.setApellidos(usuario.getPersona().getApellidos());
        dto.setSessionActive(usuario.isSessionActive());
        dto.setIdentificacion(usuario.getPersona().getIdentificacion());
        dto.setStatus(usuario.getStatus());
        return dto;
    }
}
