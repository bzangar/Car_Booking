package org.example.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "reviews")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comment;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "car_id")
    @ManyToOne
    private Car car;
    private Integer rating;


}
