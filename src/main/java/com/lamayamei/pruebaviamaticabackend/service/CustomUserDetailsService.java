package com.lamayamei.pruebaviamaticabackend.service;

import com.lamayamei.pruebaviamaticabackend.entity.RolUsuarios;
import com.lamayamei.pruebaviamaticabackend.entity.Usuarios;
import com.lamayamei.pruebaviamaticabackend.repository.RolUsuariosRepository;
import com.lamayamei.pruebaviamaticabackend.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuariosRepository userRepository;
    private final RolUsuariosRepository rolUsuariosRepository;

    @Autowired
    public CustomUserDetailsService(UsuariosRepository userRepository, RolUsuariosRepository rolUsuariosRepository) {
        this.userRepository = userRepository;
        this.rolUsuariosRepository = rolUsuariosRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Buscando usuario con nombre de usuario: " + username);

        Usuarios user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        List<RolUsuarios> rolUsuarios = rolUsuariosRepository.findByUsuarioUsername(username);

        Set<GrantedAuthority> authorities = new HashSet<>();
        for (RolUsuarios ru : rolUsuarios) {
            authorities.add(new SimpleGrantedAuthority(ru.getRol().getRolName()));
        }
      // imprimir roles
        System.out.println("Roles para el usuario " + username + ":");
        for (RolUsuarios ru : rolUsuarios) {
            System.out.println(ru.getRol().getRolName());
        }

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();

    }
}
