package com.lamayamei.pruebaviamaticabackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String nombres;
    private String apellidos;
    private boolean sessionActive;
    private String identificacion;
    private char status;
    private String mail;
    private String username;
}
