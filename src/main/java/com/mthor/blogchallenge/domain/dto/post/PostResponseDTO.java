package com.mthor.blogchallenge.domain.dto.post;

import com.mthor.blogchallenge.domain.entity.Category;
import com.mthor.blogchallenge.domain.entity.Post;

import java.time.format.DateTimeFormatter;

public record PostResponseDTO(Long id, String title, String content, String createdAt, String category) {

    public PostResponseDTO(Post post) {
        this(post.getId(), post.getTitle(), post.getContent(),
                post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")), post.getCategory().getName());
    }
}
