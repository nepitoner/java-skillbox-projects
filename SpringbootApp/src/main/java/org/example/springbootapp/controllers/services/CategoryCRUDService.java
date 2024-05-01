package org.example.springbootapp.controllers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootapp.dto.CategoryDto;
import org.example.springbootapp.entity.Category;
import org.example.springbootapp.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryCRUDService implements CRUDService<CategoryDto>{
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto getById(Long id) {
        log.info("Get by id");
        return mapToDto(categoryRepository.findById(id).orElseThrow());
    }

    @Override
    public Collection<CategoryDto> getAll() {
        log.info("Get all");
        return categoryRepository.findAll().stream()
                .map(CategoryCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(CategoryDto item) {
        log.info("Create");
        Category category = mapToEntity(item);
        categoryRepository.save(category);
    }

    @Override
    public void update(CategoryDto item) {
        log.info("Update");
        Long cat_id = item.getId();
        Category category = categoryRepository.findById(cat_id).orElseThrow();
        category.setTitle(item.getTitle());
        category.setNews(item.getNews().stream()
                .map(NewsCRUDService::mapToEntity)
                .toList());
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete");
        categoryRepository.deleteById(id);
    }

    public static CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        categoryDto.setNews(category.getNews()
                .stream()
                .map(NewsCRUDService::mapToDto)
                .toList());
        return categoryDto;
    }

    public static Category mapToEntity (CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());
        category.setNews(categoryDto.getNews()
                .stream()
                .map(NewsCRUDService::mapToEntity)
                .toList());
        return category;
    }
}
