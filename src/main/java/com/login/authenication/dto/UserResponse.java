package com.login.authenication.dto;

public record UserResponse(
        Long id,
        String email,
        String role
) {}
