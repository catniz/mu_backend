package com.musinsa.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="brand_id", nullable = false)
    private Brand brand;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Long price;

}
