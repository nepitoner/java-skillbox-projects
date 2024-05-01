package org.example;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

import java.util.Date;

@Getter
@Setter

@Entity
@Table(name = "Students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String name;
    @Column(name = "registration_date")
    private Date registrationDate;
    private int age;
}

