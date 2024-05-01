package org.example.springbootapp.controllers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootapp.dto.NewsDto;
import org.example.springbootapp.entity.News;
import org.example.springbootapp.repository.CategoryRepository;
import org.example.springbootapp.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsCRUDService implements CRUDService<NewsDto>{
//    private final ConcurrentHashMap<Long, NewsDto> newsMap = new ConcurrentHashMap<>();
    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public NewsDto getById(Long id) {
        log.info("Get by id: " + id);
        News news = newsRepository.findById(id).orElseThrow();
        return mapToDto(news);
    }

    @Override
    public Collection<NewsDto> getAll() {
        log.info("Get all");
        return newsRepository.findAll()
                .stream()
                .map(NewsCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(NewsDto item) {
        log.info("Create");
        Long categoryId = item.getCategoryId();
        News news = mapToEntity(item);
        news.setCategory(categoryRepository.findById(categoryId).orElseThrow());
        newsRepository.save(news);
    }

    @Override
    public void update(NewsDto item) {
        log.info("Update");
        Long categoryId = item.getCategoryId();
        News news = mapToEntity(item);
        news.setCategory(categoryRepository.findById(categoryId).orElseThrow());
        newsRepository.save(news);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete");
        newsRepository.deleteById(id);
    }

    public static NewsDto mapToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setDate(news.getDate());
        newsDto.setCategoryId(news.getCategory().getId());
        return newsDto;
    }

    public static News mapToEntity(NewsDto newsDto) {
        News news = new News();
        news.setId(newsDto.getId());
        news.setTitle(newsDto.getTitle());
        news.setText(newsDto.getText());
        news.setDate(newsDto.getDate());
        return news;
    }

}
