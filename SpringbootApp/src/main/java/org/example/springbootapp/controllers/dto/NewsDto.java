package org.example.springbootapp.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class NewsDto {
    private Long id;
    private String title;
    private String text;
    private Instant date;
    private Long categoryId;

    public NewsDto(Long id, String title, String text, Long categoryId, Instant date) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.categoryId = categoryId;
    }

    public NewsDto(Long id, String title, String text, Long category_id) {
        this(id, title, text, category_id, Instant.now());
    }
}
