package com.lamayamei.pruebaviamaticabackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolOpciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOpcion;
    private String nombreOpcion;
}
