package com.mthor.blogchallenge.domain.dto.post;

public record UpdatePostDTO(Long id, String title, String content, Long categoryId) {
}
