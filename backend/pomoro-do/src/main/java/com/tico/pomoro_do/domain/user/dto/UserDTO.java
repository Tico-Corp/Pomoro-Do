package com.tico.pomoro_do.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDTO {
    private String username;
    private String role;

    @Builder
    public UserDTO(String username, String role){
        this.username = username;
        this.role = role;
    }
}