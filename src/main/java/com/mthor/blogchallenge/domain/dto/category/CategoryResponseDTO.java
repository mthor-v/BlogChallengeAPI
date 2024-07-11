package com.mthor.blogchallenge.domain.dto.category;

import com.mthor.blogchallenge.domain.entity.Category;

public record CategoryResponseDTO(Long id, String category, String description) {

    public CategoryResponseDTO (Category category) {
        this(category.getId(), category.getName(), category.getDescription());
    }
}
