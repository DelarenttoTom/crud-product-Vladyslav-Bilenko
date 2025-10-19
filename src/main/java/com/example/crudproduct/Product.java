package com.example.crudproduct;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length = 255)
    private String name;

    @Column(nullable=false)
    private double price;

    @Column(nullable=false, length = 120)
    private String category;

    @Column(nullable=false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    public Product() {}

    public Product(String name, double price, String category, LocalDate createdAt) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
}