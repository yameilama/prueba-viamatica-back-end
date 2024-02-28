package com.lamayamei.pruebaviamaticabackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDTO {
    private String nombres;
    private String apellidos;
    private String identificacion;
    private String usuario;
    private String contrasena;
    private String email;
}
