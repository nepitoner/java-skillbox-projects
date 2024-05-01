package org.example.springbootapp.controllers;

import org.example.springbootapp.dto.NewsDto;
import org.example.springbootapp.services.NewsCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsCRUDService newsCRUDService;

    public NewsController(NewsCRUDService newsCRUDService) {
        this.newsCRUDService = newsCRUDService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long id) {
        return new ResponseEntity<>(newsCRUDService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<NewsDto>> getAllNews(){
        return new ResponseEntity<>(newsCRUDService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createNews(@RequestBody NewsDto news) {
        newsCRUDService.create(news);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity updateNews(@RequestBody NewsDto item){
        newsCRUDService.update(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNews(@PathVariable Long id) {
        newsCRUDService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
