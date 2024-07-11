package com.mthor.blogchallenge.domain.dto.post;

import javax.validation.constraints.NotBlank;

public record CreatePostDTO(@NotBlank String title, String content, Long categoryId){
}
