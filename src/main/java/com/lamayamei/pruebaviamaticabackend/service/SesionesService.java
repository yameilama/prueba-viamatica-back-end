package com.lamayamei.pruebaviamaticabackend.service;

import com.lamayamei.pruebaviamaticabackend.entity.Sesiones;

public interface SesionesService {
    Sesiones save(Sesiones sesion);
    Sesiones getLastSessionForUser(String username);

}
