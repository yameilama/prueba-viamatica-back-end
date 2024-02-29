package com.lamayamei.pruebaviamaticabackend.service.implementation;

import com.lamayamei.pruebaviamaticabackend.DTO.RegistroDTO;
import com.lamayamei.pruebaviamaticabackend.entity.Persona;
import com.lamayamei.pruebaviamaticabackend.entity.Rol;
import com.lamayamei.pruebaviamaticabackend.entity.RolUsuarios;
import com.lamayamei.pruebaviamaticabackend.entity.Usuarios;
import com.lamayamei.pruebaviamaticabackend.repository.PersonaRepository;
import com.lamayamei.pruebaviamaticabackend.repository.RolRepository;
import com.lamayamei.pruebaviamaticabackend.repository.RolUsuariosRepository;
import com.lamayamei.pruebaviamaticabackend.repository.UsuariosRepository;
import com.lamayamei.pruebaviamaticabackend.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistroServiceImpl implements RegistroService {
    private final PersonaRepository personaRepository;
    private final UsuariosRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RolRepository rolRepository;
    private final RolUsuariosRepository rolUsuariosRepository;


    @Autowired
    public RegistroServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, PersonaRepository personaRepository, UsuariosRepository usuarioRepository, RolRepository rolRepository, RolUsuariosRepository rolUsuariosRepository) {
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.rolUsuariosRepository = rolUsuariosRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public void registrar(RegistroDTO registroDTO) {
        System.out.println("o esteee???");
        Persona persona = new Persona();
        persona.setNombres(registroDTO.getNombres());
        persona.setApellidos(registroDTO.getApellidos());
        persona.setIdentificacion(registroDTO.getIdentificacion());

        persona = personaRepository.save(persona);


        Usuarios usuario = new Usuarios();
        usuario.setUsername(registroDTO.getUsername());
        String hashedPassword = bCryptPasswordEncoder.encode(registroDTO.getContrasena());
         System.out.println("Hashed Password: " + hashedPassword);

        usuario.setPassword(hashedPassword);
        usuario.setMail(registroDTO.getMail());
        usuario.setPersona(persona);
        usuario.setSessionActive(false);
        usuario.setStatus('A');


        usuario = usuarioRepository.save(usuario);

        Optional<Rol> usuarioRolOptional = rolRepository.findByRolName("USUARIO");
        Rol usuarioRol;
        if (usuarioRolOptional.isPresent()) {
            usuarioRol = usuarioRolOptional.get();
        } else {
            usuarioRol = new Rol();
            usuarioRol.setRolName("USUARIO");
            usuarioRol = rolRepository.save(usuarioRol);
        }

        RolUsuarios rolUsuario = new RolUsuarios();
        rolUsuario.setUsuario(usuario);
        rolUsuario.setRol(usuarioRol);
        rolUsuariosRepository.save(rolUsuario);
        System.out.println("RegistroDTO: " + registroDTO);
        System.out.println("Persona: " + persona);
        System.out.println("Usuarios: " + usuario);

    }
}
