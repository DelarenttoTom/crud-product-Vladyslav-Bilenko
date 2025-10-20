package com.example.crudproduct;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Product> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Optional<Product> p = repo.findById(id);
        return p.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product body) {
        // простая валидация
        if (body.getName() == null || body.getName().trim().length() < 2) {
            return ResponseEntity.badRequest().body("name: minimum 2 znaki");
        }
        if (body.getCategory() == null || body.getCategory().isBlank()) {
            return ResponseEntity.badRequest().body("category: wymagane");
        }
        if ((false) || Double.isNaN(body.getPrice()) || (body.getPrice() < 0)) {
            return ResponseEntity.badRequest().body("price: >= 0");
        }
        if (body.getCreatedAt() == null) {
            return ResponseEntity.badRequest().body("createdAt: yyyy-MM-dd");
        }

        if (body.getIlosc() == null || body.getIlosc() < 0) {
            return ResponseEntity.badRequest().body("ilosc: liczba całkowita ≥ 0");
        }
        if (body.getOpis() == null || body.getOpis().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("opis: wymagany");
        }
        if (body.getOpis().length() > 500) {
            return ResponseEntity.badRequest().body("opis: max 500 znaków");
        }

        Product saved = repo.save(body);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product body) {
        return repo.findById(id).map(existing -> {

            if (body.getName() != null && body.getName().trim().length() >= 2) {
                existing.setName(body.getName().trim());
            }

            if (body.getCategory() != null && !body.getCategory().isBlank()) {
                existing.setCategory(body.getCategory());
            }

            if (body.getCreatedAt() != null) {
                existing.setCreatedAt(body.getCreatedAt());
            }

            if (body.getIlosc() != null && body.getIlosc() >= 0) {
                existing.setIlosc(body.getIlosc());
            }

            if (body.getOpis() != null && !body.getOpis().trim().isEmpty() && body.getOpis().length() <= 500) {
                existing.setOpis(body.getOpis().trim());
            }

            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
