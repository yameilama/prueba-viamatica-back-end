package com.lamayamei.pruebaviamaticabackend.service.implementation;

import com.lamayamei.pruebaviamaticabackend.DTO.RegistroDTO;
import com.lamayamei.pruebaviamaticabackend.entity.Persona;
import com.lamayamei.pruebaviamaticabackend.entity.Usuarios;
import com.lamayamei.pruebaviamaticabackend.repository.PersonaRepository;
import com.lamayamei.pruebaviamaticabackend.repository.UsuariosRepository;
import com.lamayamei.pruebaviamaticabackend.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistroServiceImpl implements RegistroService {
    private final PersonaRepository personaRepository;
    private final UsuariosRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public RegistroServiceImpl(PersonaRepository personaRepository, UsuariosRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public void registrar(RegistroDTO registroDTO) {

        Persona persona = new Persona();
        persona.setNombres(registroDTO.getNombres());
        persona.setApellidos(registroDTO.getApellidos());
        persona.setIdentificacion(registroDTO.getIdentificacion());

        persona = personaRepository.save(persona);


        Usuarios usuario = new Usuarios();
        usuario.setUsername(registroDTO.getUsuario());
        String hashedPassword = bCryptPasswordEncoder.encode(registroDTO.getContrasena());
        usuario.setPassword(hashedPassword);
        usuario.setMail(registroDTO.getEmail());
        usuario.setPersona(persona);

        usuario = usuarioRepository.save(usuario);
    }
}
