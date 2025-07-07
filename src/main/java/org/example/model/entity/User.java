package org.example.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "users")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;

    @Column(name = "full_name")
    private String fullName;
    private String role;

//    @OneToMany(mappedBy = "client")
//    private List<Booking> bookings;
//
//    @OneToMany(mappedBy = "owner")
//    private List<Car> cars;
//
//    @OneToMany(mappedBy = "user")
//    private List<Review> reviews;

}
