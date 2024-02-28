package com.lamayamei.pruebaviamaticabackend.config;

import com.lamayamei.pruebaviamaticabackend.entity.*;
import com.lamayamei.pruebaviamaticabackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Component
public class SetupDataLoader implements CommandLineRunner {
    private final RolRepository rolRepository;
    private final PersonaRepository personaRepository;
    private final UsuariosRepository usuariosRepository;
    private final RolUsuariosRepository rolUsuariosRepository;
    private final PasswordEncoder passwordEncoder;
    private final SesionesRepository sesionesRepository;


    @Autowired
    public SetupDataLoader(RolRepository rolRepository,
                           PersonaRepository personaRepository,
                           UsuariosRepository usuariosRepository,
                           RolUsuariosRepository rolUsuariosRepository,
                           PasswordEncoder passwordEncoder,
                           SesionesRepository sesionesRepository
                           ) {
        this.rolRepository = rolRepository;
        this.personaRepository = personaRepository;
        this.usuariosRepository = usuariosRepository;
        this.rolUsuariosRepository = rolUsuariosRepository;
        this.passwordEncoder = passwordEncoder;
        this.sesionesRepository = sesionesRepository;

    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //crear roles
        Rol rolUsuario = createRoleIfNotFound("USUARIO");
        Rol rolAdministrador = createRoleIfNotFound("ADMINISTRADOR");

        // crear usuario normal y admin
        createPersonaAndUsuarioIfNotFound("Yamei Julia", "Lama Chusan", "0923008569", "ylamac", rolUsuario);
        createPersonaAndUsuarioIfNotFound("Admin Nombre", "Admin Apellido", "0987654321", "aadmina", rolAdministrador);
    }

    private Rol createRoleIfNotFound(String roleName) {
        return rolRepository.findByRolName(roleName)
                .orElseGet(() -> {
                    Rol newRol = new Rol();
                    newRol.setRolName(roleName);
                    return rolRepository.save(newRol);
                });
    }

    private void createPersonaAndUsuarioIfNotFound(String nombres, String apellidos, String identificacion, String username, Rol rol) {
        Persona persona = personaRepository.findByIdentificacion(identificacion)
                .orElseGet(() -> {
                    Persona newPersona = new Persona();
                    newPersona.setNombres(nombres);
                    newPersona.setApellidos(apellidos);
                    newPersona.setIdentificacion(identificacion);
                    return personaRepository.save(newPersona);
                });
        String email = username + "@mail.com";
        Usuarios usuario = usuariosRepository.findByMail(email)
                .orElseGet(() -> {
                    Usuarios newUsuario = new Usuarios();
                    newUsuario.setUsername(username);
                    newUsuario.setPassword(passwordEncoder.encode("Contrasena123"));
                    newUsuario.setMail(email);
                    newUsuario.setSessionActive(false);
                    newUsuario.setStatus('A');
                    newUsuario.setPersona(persona);
                    newUsuario = usuariosRepository.save(newUsuario);

                    RolUsuarios rolUsuarios = new RolUsuarios();
                    rolUsuarios.setRol(rol);
                    rolUsuarios.setUsuario(newUsuario);
                    rolUsuariosRepository.save(rolUsuarios);

                    return newUsuario;
                });

    }
}
