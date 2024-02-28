package com.lamayamei.pruebaviamaticabackend.repository;

import com.lamayamei.pruebaviamaticabackend.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByIdentificacion(String identificacion);
}
