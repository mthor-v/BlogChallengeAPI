package com.mthor.blogchallenge.domain.dto.auth;

public record LoginCredentialsDTO(
        String email,
        String password
) {
}
