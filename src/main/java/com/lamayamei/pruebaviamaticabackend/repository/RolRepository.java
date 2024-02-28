package com.lamayamei.pruebaviamaticabackend.repository;

import com.lamayamei.pruebaviamaticabackend.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRolName(String rolName);

}
