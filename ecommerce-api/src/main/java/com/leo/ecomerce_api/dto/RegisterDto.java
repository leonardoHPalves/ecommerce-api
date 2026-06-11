package com.leo.ecomerce_api.dto;


import lombok.Data;

@Data
public class RegisterDto {
    private String nome;
    private String email;
    private String senha;
}
