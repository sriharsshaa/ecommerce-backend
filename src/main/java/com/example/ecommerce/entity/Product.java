package com.example.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String category;

    // Stock quantity
    private int stock;

    // Common product fields
    private String brand;
    private String storage;
    private String ram;
    private String screenSize;
    private String operatingSystem;

    // Laptop-specific fields
    private String modelName;
    private String hardDiskSize;
    private String cpuModel;
    private String ramMemoryInstalledSize;

    // Audio-specific fields
    private String color;
    private String earPlacement;
    private String formFactor;
    private String noiseControl;
    private String connectivity;

    // Accessories-specific fields
    private String connectionType;
    private String compatibility;

    // Monitor-specific fields
    private String resolution;
    private String refreshRate;
    private String panelType;

    @Lob 
    private String description;
    private String imageUrl;


    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public Product() {
    }


    // =========================================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================================

    public Product(
            String name,
            double price,
            String category,
            int stock,
            String brand,
            String storage,
            String ram,
            String screenSize,
            String operatingSystem,
            String modelName,
            String hardDiskSize,
            String cpuModel,
            String ramMemoryInstalledSize,
            String color,
            String earPlacement,
            String formFactor,
            String noiseControl,
            String connectivity,
            String connectionType,
            String compatibility,
            String resolution,
            String refreshRate,
            String panelType,
            String description,
            String imageUrl
    ) {

        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;

        this.brand = brand;
        this.storage = storage;
        this.ram = ram;
        this.screenSize = screenSize;
        this.operatingSystem = operatingSystem;

        this.modelName = modelName;
        this.hardDiskSize = hardDiskSize;
        this.cpuModel = cpuModel;
        this.ramMemoryInstalledSize = ramMemoryInstalledSize;

        this.color = color;
        this.earPlacement = earPlacement;
        this.formFactor = formFactor;
        this.noiseControl = noiseControl;
        this.connectivity = connectivity;

        this.connectionType = connectionType;
        this.compatibility = compatibility;

        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.panelType = panelType;

        this.description = description;
        this.imageUrl = imageUrl;
    }


    // =========================================================
    // GETTERS
    // =========================================================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    public String getBrand() {
        return brand;
    }

    public String getStorage() {
        return storage;
    }

    public String getRam() {
        return ram;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getModelName() {
        return modelName;
    }

    public String getHardDiskSize() {
        return hardDiskSize;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public String getRamMemoryInstalledSize() {
        return ramMemoryInstalledSize;
    }

    public String getColor() {
        return color;
    }

    public String getEarPlacement() {
        return earPlacement;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public String getNoiseControl() {
        return noiseControl;
    }

    public String getConnectivity() {
        return connectivity;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public String getCompatibility() {
        return compatibility;
    }

    public String getResolution() {
        return resolution;
    }

    public String getRefreshRate() {
        return refreshRate;
    }

    public String getPanelType() {
        return panelType;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    // =========================================================
    // SETTERS
    // =========================================================

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setHardDiskSize(String hardDiskSize) {
        this.hardDiskSize = hardDiskSize;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public void setRamMemoryInstalledSize(String ramMemoryInstalledSize) {
        this.ramMemoryInstalledSize = ramMemoryInstalledSize;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setEarPlacement(String earPlacement) {
        this.earPlacement = earPlacement;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public void setNoiseControl(String noiseControl) {
        this.noiseControl = noiseControl;
    }

    public void setConnectivity(String connectivity) {
        this.connectivity = connectivity;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setRefreshRate(String refreshRate) {
        this.refreshRate = refreshRate;
    }

    public void setPanelType(String panelType) {
        this.panelType = panelType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}