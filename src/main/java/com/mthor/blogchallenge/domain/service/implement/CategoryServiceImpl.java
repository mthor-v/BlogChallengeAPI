package com.mthor.blogchallenge.domain.service.implement;

import com.mthor.blogchallenge.domain.dto.category.CategoryResponseDTO;
import com.mthor.blogchallenge.domain.dto.category.UpdateCategoryDTO;
import com.mthor.blogchallenge.domain.entity.Category;
import com.mthor.blogchallenge.domain.service.ICategoryService;
import com.mthor.blogchallenge.infra.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findByActiveTrue(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findByActiveTrueAndId(id);
        return category.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        category.changeState();
    }

    @Override
    @Transactional
    public CategoryResponseDTO updateCategory(UpdateCategoryDTO updateCategoryDTO) {
        Category category = categoryRepository.getReferenceById(updateCategoryDTO.id());
        category.updateData(updateCategoryDTO);
        return new CategoryResponseDTO(category);
    }

}
