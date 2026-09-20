package com.example.ecommerce.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final Path uploadPath;

    public FileStorageService(
            @Value("${file.upload-dir}") String uploadDir) {

        this.uploadPath = Paths.get(uploadDir)
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not create upload directory",
                    e
            );
        }
    }

    // =========================================================
    // SAVE IMAGE
    // =========================================================

    public String saveImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException(
                    "Image file is empty"
            );
        }

        try {

            String fileName =
                    System.currentTimeMillis()
                    + "_"
                    + file.getOriginalFilename();

            Path targetLocation =
                    this.uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    targetLocation
            );

            return fileName;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not save image",
                    e
            );
        }
    }

    // =========================================================
    // DELETE IMAGE
    // =========================================================

    public void deleteImage(String imageUrl) {

        // No image URL
        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }

        try {

            // Example:
            // /images/products/1758123456789_iphone.png
            //
            // We only need:
            // 1758123456789_iphone.png

            String fileName =
                    Paths.get(imageUrl)
                            .getFileName()
                            .toString();

            Path imagePath =
                    uploadPath.resolve(fileName)
                            .normalize();

            // Security check:
            // Make sure the file is actually
            // inside uploads/products
            if (!imagePath.startsWith(uploadPath)) {
                throw new RuntimeException(
                        "Invalid image path"
                );
            }

            Files.deleteIfExists(imagePath);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not delete image",
                    e
            );
        }
    }
}