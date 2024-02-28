package com.lamayamei.pruebaviamaticabackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDTO {
    private String nombres;
    private String apellidos;
    private String identificacion;
    private String email;
}
