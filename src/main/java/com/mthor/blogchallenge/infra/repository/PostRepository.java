package com.mthor.blogchallenge.infra.repository;

import com.mthor.blogchallenge.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByActiveTrue(Pageable pageable);

    Optional<Post> findByActiveTrueAndId(Long id);

    Page<Post> findByCategory_Id(Long categoryId, Pageable pageable);

    Page<Post> findByUser_Id(Long userId, Pageable pageable);

}
