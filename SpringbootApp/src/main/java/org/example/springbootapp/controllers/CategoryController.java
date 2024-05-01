package org.example.springbootapp.controllers;

import lombok.RequiredArgsConstructor;
import org.example.springbootapp.dto.CategoryDto;
import org.example.springbootapp.services.CategoryCRUDService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryCRUDService categoryCRUDService;
    @GetMapping
    public Collection<CategoryDto> getAll() {
        return categoryCRUDService.getAll();
    }

    @PostMapping
    public void create(@RequestBody CategoryDto categoryDto) {
        categoryCRUDService.create(categoryDto);
    }

    @PutMapping
    public void update(@RequestBody CategoryDto categoryDto) {
        categoryCRUDService.update(categoryDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryCRUDService.deleteById(id);
    }

}
