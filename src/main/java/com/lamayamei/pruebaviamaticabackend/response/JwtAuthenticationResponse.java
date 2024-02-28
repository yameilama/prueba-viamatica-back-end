package com.lamayamei.pruebaviamaticabackend.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String roles;
    private String username;

    public JwtAuthenticationResponse(String accessToken, String roles, String username) {
        this.accessToken = accessToken;
        this.roles = roles;
        this.username = username;
    }
}
