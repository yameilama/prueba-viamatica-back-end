package com.lamayamei.pruebaviamaticabackend.repository;

import com.lamayamei.pruebaviamaticabackend.entity.RolUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolUsuariosRepository extends JpaRepository<RolUsuarios, Long>  {
    List<RolUsuarios> findByUsuarioUsername(String username);

}
