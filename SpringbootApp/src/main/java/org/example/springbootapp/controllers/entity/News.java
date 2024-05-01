package org.example.springbootapp.controllers.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Entity
@Getter
@Setter
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "text")
    private String text;
    @Column(name = "date")
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
