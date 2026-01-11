package com.login.authenication.Controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.authenication.UserRepository.ProductRepsoitory;

import jakarta.validation.Valid;

import com.login.authenication.Entity.Product;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductRepsoitory productRepository;

    public ProductController(ProductRepsoitory productRepository) {
        this.productRepository = productRepository;
    }

    // CREATE
@PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public Product create(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    // READ ALL
   @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // READ BY ID
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // UPDATE
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Product update(
            @PathVariable Long id,@Valid
            @RequestBody Product product
    ) {
        Product existing = getById(id);
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        return productRepository.save(existing);
    }

    // DELETE
     @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "Product deleted";
    }
}
