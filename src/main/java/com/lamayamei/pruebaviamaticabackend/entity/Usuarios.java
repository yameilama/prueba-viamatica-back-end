package com.lamayamei.pruebaviamaticabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String mail;
    private boolean sessionActive;
    private char status;

    @OneToOne
    @JoinColumn(name = "idPersona", referencedColumnName = "idPersona")
    private Persona persona;

    @OneToMany(mappedBy = "usuario")
    private Set<Sesiones> sesiones;

}
