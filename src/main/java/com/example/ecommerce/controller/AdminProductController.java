package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.AdminProductService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    public AdminProductController(
            AdminProductService adminProductService) {

        this.adminProductService = adminProductService;
    }


    // =========================================================
    // GET ALL PRODUCTS
    // =========================================================

    @GetMapping
    public List<Product> getAllProducts() {

        return adminProductService.getAllProducts();
    }


    // =========================================================
    // ADD PRODUCT
    // =========================================================

    @PostMapping(consumes = "multipart/form-data")
    public Product addProduct(
            @RequestPart("product") Product product,
            @RequestPart("image") MultipartFile image) {

        return adminProductService.addProduct(
                product,
                image
        );
    }


    // =========================================================
    // UPDATE PRODUCT
    // =========================================================

    @PutMapping(
            value = "/{productId}",
            consumes = "multipart/form-data"
    )
    public Product updateProduct(
            @PathVariable Long productId,

            @RequestPart("product")
            Product product,

            @RequestPart(
                    value = "image",
                    required = false
            )
            MultipartFile image) {

        return adminProductService.updateProduct(
                productId,
                product,
                image
        );
    }


    // =========================================================
    // DELETE PRODUCT
    // =========================================================

    @DeleteMapping("/{productId}")
    public void deleteProduct(
            @PathVariable Long productId) {

        adminProductService.deleteProduct(
                productId
        );
    }
}
