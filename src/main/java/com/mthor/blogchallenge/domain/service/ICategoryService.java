package com.mthor.blogchallenge.domain.service;

import com.mthor.blogchallenge.domain.dto.category.CategoryResponseDTO;
import com.mthor.blogchallenge.domain.dto.category.UpdateCategoryDTO;
import com.mthor.blogchallenge.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {

    Page<Category> getAllCategories(Pageable pageable);

    Category getCategoryById(Long id);

    Category createCategory(Category category);

    void deleteCategory(Long id);

    CategoryResponseDTO updateCategory(UpdateCategoryDTO updateCategoryDTO);

}
