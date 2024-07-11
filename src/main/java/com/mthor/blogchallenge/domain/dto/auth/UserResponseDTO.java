package com.mthor.blogchallenge.domain.dto.auth;

import com.mthor.blogchallenge.domain.entity.User;

public record UserResponseDTO(Long id, String name, String email) {
    public UserResponseDTO(User user){
        this(user.getId(), user.getName(), user.getEmail());
    }
}
