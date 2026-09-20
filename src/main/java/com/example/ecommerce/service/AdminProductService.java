package com.example.ecommerce.service;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AdminProductService {

    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    public AdminProductService(
            ProductRepository productRepository,
            FileStorageService fileStorageService) {

        this.productRepository = productRepository;
        this.fileStorageService = fileStorageService;
    }

    // =========================================================
    // GET ALL PRODUCTS
    // =========================================================

    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    // =========================================================
    // ADD PRODUCT WITH IMAGE
    // =========================================================

    public Product addProduct(
            Product product,
            MultipartFile image) {

        if (image != null && !image.isEmpty()) {

            String fileName =
                    fileStorageService.saveImage(image);

            product.setImageUrl(
                    "/images/products/" + fileName
            );
        }

        return productRepository.save(product);
    }

    // =========================================================
    // UPDATE PRODUCT WITH OPTIONAL NEW IMAGE
    // =========================================================

    public Product updateProduct(
            Long productId,
            Product updatedProduct,
            MultipartFile image) {

        Product existingProduct =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"
                                )
                        );

        // Store the old image URL before changing it
        String oldImageUrl =
                existingProduct.getImageUrl();

        // =========================================================
        // UPDATE PRODUCT DETAILS
        // =========================================================

        existingProduct.setName(
                updatedProduct.getName()
        );

        existingProduct.setPrice(
                updatedProduct.getPrice()
        );

        existingProduct.setCategory(
                updatedProduct.getCategory()
        );

        existingProduct.setBrand(
                updatedProduct.getBrand()
        );

        // Product Description
        existingProduct.setDescription(
                updatedProduct.getDescription()
        );

        existingProduct.setStorage(
                updatedProduct.getStorage()
        );

        existingProduct.setRam(
                updatedProduct.getRam()
        );

        existingProduct.setScreenSize(
                updatedProduct.getScreenSize()
        );

        existingProduct.setOperatingSystem(
                updatedProduct.getOperatingSystem()
        );

        existingProduct.setModelName(
                updatedProduct.getModelName()
        );

        existingProduct.setHardDiskSize(
                updatedProduct.getHardDiskSize()
        );

        existingProduct.setCpuModel(
                updatedProduct.getCpuModel()
        );

        existingProduct.setRamMemoryInstalledSize(
                updatedProduct.getRamMemoryInstalledSize()
        );

        existingProduct.setColor(
                updatedProduct.getColor()
        );

        existingProduct.setEarPlacement(
                updatedProduct.getEarPlacement()
        );

        existingProduct.setFormFactor(
                updatedProduct.getFormFactor()
        );

        existingProduct.setNoiseControl(
                updatedProduct.getNoiseControl()
        );

        existingProduct.setConnectivity(
                updatedProduct.getConnectivity()
        );

        existingProduct.setConnectionType(
                updatedProduct.getConnectionType()
        );

        existingProduct.setCompatibility(
                updatedProduct.getCompatibility()
        );

        existingProduct.setResolution(
                updatedProduct.getResolution()
        );

        existingProduct.setRefreshRate(
                updatedProduct.getRefreshRate()
        );

        existingProduct.setPanelType(
                updatedProduct.getPanelType()
        );

        // =========================================================
        // REPLACE IMAGE IF NEW IMAGE IS PROVIDED
        // =========================================================

        if (image != null && !image.isEmpty()) {

            // Save the new image
            String newFileName =
                    fileStorageService.saveImage(image);

            String newImageUrl =
                    "/images/products/" + newFileName;

            // Update database object with new image
            existingProduct.setImageUrl(
                    newImageUrl
            );
        }

        // =========================================================
        // SAVE PRODUCT
        // =========================================================

        Product savedProduct =
                productRepository.save(existingProduct);

        // =========================================================
        // DELETE OLD IMAGE
        // =========================================================

        if (image != null
                && !image.isEmpty()
                && oldImageUrl != null
                && !oldImageUrl.isBlank()) {

            fileStorageService.deleteImage(
                    oldImageUrl
            );
        }

        return savedProduct;
    }

    // =========================================================
    // DELETE PRODUCT
    // =========================================================

    public void deleteProduct(Long productId) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"
                                )
                        );

        // Get the image URL before deleting the product
        String imageUrl =
                product.getImageUrl();

        // Delete product from database
        productRepository.delete(product);

        // Delete the corresponding image file
        if (imageUrl != null
                && !imageUrl.isBlank()) {

            fileStorageService.deleteImage(
                    imageUrl
            );
        }
    }
}