package com.lamayamei.pruebaviamaticabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sesiones {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSesiones;
    private Date fechaIngreso;
    private Date fechaCierre;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuarios usuario;
}
