package com.mthor.blogchallenge.domain.dto.category;

import javax.validation.constraints.NotBlank;

public record CreateCategoryDTO(@NotBlank String category, String description) {
}
