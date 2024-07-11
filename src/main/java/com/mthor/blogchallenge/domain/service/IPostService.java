package com.mthor.blogchallenge.domain.service;

import com.mthor.blogchallenge.domain.dto.post.CreatePostDTO;
import com.mthor.blogchallenge.domain.dto.post.PostResponseDTO;
import com.mthor.blogchallenge.domain.dto.post.UpdatePostDTO;
import com.mthor.blogchallenge.domain.entity.Category;
import com.mthor.blogchallenge.domain.entity.Post;
import com.mthor.blogchallenge.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostService {

    Page<Post> getAllPosts(Pageable pageable);

    PostResponseDTO getPostById(Long id);

    Post createPost(CreatePostDTO post, User user, Category category);

    PostResponseDTO updatePost(UpdatePostDTO updatePostDTO, Category category);

    Page<Post> getPostsByCategory(Long categoryId, Pageable pageable);

    Page<Post> getPostsByUser(Long userId, Pageable pageable);

    void deletePost(Long id);

}
