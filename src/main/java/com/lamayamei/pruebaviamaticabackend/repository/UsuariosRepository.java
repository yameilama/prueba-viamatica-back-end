package com.lamayamei.pruebaviamaticabackend.repository;

import com.lamayamei.pruebaviamaticabackend.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByMail(String email);
    Optional<Usuarios> findByUsername(String username);

}
