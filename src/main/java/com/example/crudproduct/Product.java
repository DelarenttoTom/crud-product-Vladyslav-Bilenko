package com.example.crudproduct;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

@Entity
@Table(name = "product")
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

    @Column(name = "created_at", nullable=false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    // nowe pola z Części B
    @Column(name = "ilosc", nullable = false)
    private Integer ilosc;

    @Column(name = "opis", nullable = false, length = 500)
    private String opis;

    public Product() {}

    public Product(String name, double price, String category, LocalDate createdAt, Integer ilosc, String opis) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.createdAt = createdAt;
        this.ilosc = ilosc;
        this.opis = opis;
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

    public Integer getIlosc() { return ilosc; }
    public void setIlosc(Integer ilosc) { this.ilosc = ilosc; }

    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }
}