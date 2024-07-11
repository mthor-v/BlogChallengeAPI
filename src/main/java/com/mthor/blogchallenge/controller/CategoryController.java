package com.mthor.blogchallenge.controller;

import com.mthor.blogchallenge.domain.dto.category.CreateCategoryDTO;
import com.mthor.blogchallenge.domain.dto.category.CategoryResponseDTO;
import com.mthor.blogchallenge.domain.dto.category.UpdateCategoryDTO;
import com.mthor.blogchallenge.domain.entity.Category;
import com.mthor.blogchallenge.domain.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CreateCategoryDTO body,
                                                              UriComponentsBuilder uriComponentsBuilder){
        Category category = categoryService.createCategory(new Category(body));
        URI url = uriComponentsBuilder.path("/api/categories/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(url).body(new CategoryResponseDTO(category));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> listCategories(@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(categoryService.getAllCategories(pageable).map(CategoryResponseDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(new CategoryResponseDTO(categoryService.getCategoryById(id)));
    }

    @PutMapping
    public ResponseEntity<CategoryResponseDTO> updateCategory(@RequestBody @Valid UpdateCategoryDTO body){
        return ResponseEntity.ok(categoryService.updateCategory(body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
