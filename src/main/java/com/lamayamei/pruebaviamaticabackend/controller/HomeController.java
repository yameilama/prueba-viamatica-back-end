package com.lamayamei.pruebaviamaticabackend.controller;

import com.lamayamei.pruebaviamaticabackend.DTO.LastSessionDTO;
import com.lamayamei.pruebaviamaticabackend.entity.Sesiones;
import com.lamayamei.pruebaviamaticabackend.service.SesionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userdata")
@CrossOrigin(origins = "http://localhost:4200")
public class HomeController {
    private final SesionesService sesionesService;

    @Autowired
    public HomeController(SesionesService sesionesService) {
        this.sesionesService = sesionesService;
    }

    @GetMapping("/lastsession/{username}")
    public LastSessionDTO getLastSessionForUser(@PathVariable String username) {
        Sesiones lastSession = sesionesService.getLastSessionForUser(username);

        LastSessionDTO dto = new LastSessionDTO();
        dto.setStartTime(lastSession.getFechaIngreso());
        dto.setEndTime(lastSession.getFechaCierre());

        return dto;
    }
}
