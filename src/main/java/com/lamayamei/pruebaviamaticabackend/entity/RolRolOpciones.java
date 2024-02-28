package com.lamayamei.pruebaviamaticabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolRolOpciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idRol")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "idOpcion")
    private RolOpciones rolOpcion;
}
