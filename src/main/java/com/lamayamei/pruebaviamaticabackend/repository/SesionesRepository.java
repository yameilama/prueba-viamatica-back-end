package com.lamayamei.pruebaviamaticabackend.repository;

import com.lamayamei.pruebaviamaticabackend.entity.Sesiones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesionesRepository extends JpaRepository<Sesiones, Long> {
    Sesiones findTopByUsuarioUsernameOrderByFechaIngresoDesc(String username);

}
