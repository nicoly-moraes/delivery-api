package com.delivery.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Table(name = "restaurants")
@Entity
@Data
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, name = "user_id")
    private Integer userId;

    @Column(nullable = false)
    private String name;

    @Column()
    private String image;

    @Column(nullable = false, name = "postal_code")
    private String postalCode;

    @Column(nullable = false, name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false, name = "delivery_price")
    private Double deliveryPrice;

    @Column(nullable = false, name = "delivery_radius")
    private Double deliveryRadius;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
}
