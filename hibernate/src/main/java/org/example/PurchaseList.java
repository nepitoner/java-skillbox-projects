package org.example;

import jakarta.persistence.*;

import lombok.Setter;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@Table(name = "purchaselist")
public class PurchaseList {
    @EmbeddedId
    private SubscriptionKey id;

    @Column(name = "student_name")
    private String studentName;
    @Column(name = "course_name")
    private String courseName;

    private int price;
    @Column(name = "subscription_date")
    private Date subscriptionDate;

}

