package com.lamayamei.pruebaviamaticabackend.controller;

import com.lamayamei.pruebaviamaticabackend.DTO.LoginDTO;
import com.lamayamei.pruebaviamaticabackend.config.JwtTokenProvider;
import com.lamayamei.pruebaviamaticabackend.entity.Sesiones;
import com.lamayamei.pruebaviamaticabackend.entity.Usuarios;
import com.lamayamei.pruebaviamaticabackend.repository.UsuariosRepository;
import com.lamayamei.pruebaviamaticabackend.response.JwtAuthenticationResponse;
import com.lamayamei.pruebaviamaticabackend.service.CustomUserDetailsService;
import com.lamayamei.pruebaviamaticabackend.service.SesionesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final SesionesService sesionesService;
    private final UsuariosRepository usuariosRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
                           CustomUserDetailsService userDetailsService,
                           SesionesService sesionesService,
                           UsuariosRepository usuariosRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
        this.sesionesService = sesionesService;
        this.usuariosRepository = usuariosRepository;
    }
    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsernameOrEmail(),
                            loginDTO.getPassword()
                    )
            );

            logger.info("Usuario autenticado con exito");
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // sacar roles
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String roles = userDetailsService.loadUserByUsername(userDetails.getUsername())
                    .getAuthorities().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));

            String jwt = tokenProvider.generateToken(authentication);
            Usuarios usuario = usuariosRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


            Sesiones sesion = new Sesiones();
            sesion.setFechaIngreso(new Date());
            sesion.setUsuario(usuario);
            sesionesService.save(sesion);

            // retonar JWT con roles
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, roles, usuario.getUsername()));
        } catch (Exception e) {
            logger.error("Autenticación fallida: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
