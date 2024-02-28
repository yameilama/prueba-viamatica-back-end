package com.lamayamei.pruebaviamaticabackend.service.implementation;

import com.lamayamei.pruebaviamaticabackend.entity.Sesiones;
import com.lamayamei.pruebaviamaticabackend.repository.SesionesRepository;
import com.lamayamei.pruebaviamaticabackend.service.SesionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SesionesServiceImpl implements SesionesService {
    private final SesionesRepository sesionesRepository;

    @Autowired
    public SesionesServiceImpl(SesionesRepository sesionesRepository) {
        this.sesionesRepository = sesionesRepository;
    }
    @Override
    public Sesiones save(Sesiones sesion) {
        return sesionesRepository.save(sesion);

    }

    @Override
    public Sesiones getLastSessionForUser(String username) {
        return sesionesRepository.findTopByUsuarioUsernameOrderByFechaIngresoDesc(username);

    }
}
