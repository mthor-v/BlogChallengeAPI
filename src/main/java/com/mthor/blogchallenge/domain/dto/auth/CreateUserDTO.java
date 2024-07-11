package com.mthor.blogchallenge.domain.dto.auth;

import javax.validation.constraints.NotBlank;

public record CreateUserDTO(String name, @NotBlank String email, @NotBlank String password) {
}
