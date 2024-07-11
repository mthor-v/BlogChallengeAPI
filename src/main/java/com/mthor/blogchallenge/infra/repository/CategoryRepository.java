package com.mthor.blogchallenge.infra.repository;

import com.mthor.blogchallenge.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findByActiveTrue(Pageable pageable);

    Optional<Category> findByActiveTrueAndId(Long id);
}
