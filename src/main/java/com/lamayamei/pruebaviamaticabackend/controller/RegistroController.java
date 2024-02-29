package com.lamayamei.pruebaviamaticabackend.controller;

import com.lamayamei.pruebaviamaticabackend.DTO.RegistroDTO;
import com.lamayamei.pruebaviamaticabackend.service.RegistroService;
import com.lamayamei.pruebaviamaticabackend.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registro")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistroController {
    private final RegistroService registroService;

    @Autowired
    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody RegistroDTO registroDTO) {
        registroService.registrar(registroDTO);
        return ResponseEntity.ok().body("Usuario registrado exitosamente.");
    }
}
