package com.ekabotdev.ecommerce.user.dto;


import com.ekabotdev.ecommerce.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {

    private Long id;
    private String username;
    private String email;
    private String role;
}
