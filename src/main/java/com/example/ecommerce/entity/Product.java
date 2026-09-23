package com.example.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================================================
    // COMMON PRODUCT FIELDS
    // =========================================================

    private String name;
    private double price;
    private String category;
    private int stock;

    private String brand;

    @Lob
    private String description;

    private String imageUrl;


    // =========================================================
    // GENERAL PRODUCT DETAILS
    // These can be used by multiple categories
    // =========================================================

    private String productType;
    private String modelName;
    private String color;
    private String size;
    private String material;
    private String capacity;


    // =========================================================
    // ELECTRONICS FIELDS
    // =========================================================

    private String storage;
    private String ram;
    private String screenSize;
    private String operatingSystem;

    private String hardDiskSize;
    private String cpuModel;
    private String ramMemoryInstalledSize;

    private String resolution;
    private String refreshRate;
    private String panelType;

    private String connectivity;
    private String connectionType;
    private String noiseControl;
    private String earPlacement;
    private String formFactor;
    private String compatibility;


    // =========================================================
    // FASHION FIELDS
    // =========================================================

    private String fit;
    private String pattern;
    private String occasion;


    // =========================================================
    // TOYS FIELDS
    // =========================================================

    private String ageGroup;
    private String batteryRequired;


    // =========================================================
    // BOOK FIELDS
    // =========================================================

    private String author;
    private String publisher;
    private String isbn;
    private String language;
    private String edition;
    private String format;
    private Integer pages;


    // =========================================================
    // HEALTH & HOUSEHOLD FIELDS
    // =========================================================

    private String packSize;
    @Column(name = "product_usage")
    private String usage;


    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public Product() {
    }


    // =========================================================
    // EXISTING PARAMETERIZED CONSTRUCTOR
    // Kept so existing code does not break
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
        this.ramMemoryInstalledSize =
                ramMemoryInstalledSize;

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

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    // =========================================================
    // GENERAL PRODUCT GETTERS
    // =========================================================

    public String getProductType() {
        return productType;
    }

    public String getModelName() {
        return modelName;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public String getMaterial() {
        return material;
    }

    public String getCapacity() {
        return capacity;
    }


    // =========================================================
    // ELECTRONICS GETTERS
    // =========================================================

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

    public String getHardDiskSize() {
        return hardDiskSize;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public String getRamMemoryInstalledSize() {
        return ramMemoryInstalledSize;
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

    public String getConnectivity() {
        return connectivity;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public String getNoiseControl() {
        return noiseControl;
    }

    public String getEarPlacement() {
        return earPlacement;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public String getCompatibility() {
        return compatibility;
    }


    // =========================================================
    // FASHION GETTERS
    // =========================================================

    public String getFit() {
        return fit;
    }

    public String getPattern() {
        return pattern;
    }

    public String getOccasion() {
        return occasion;
    }


    // =========================================================
    // TOYS GETTERS
    // =========================================================

    public String getAgeGroup() {
        return ageGroup;
    }

    public String getBatteryRequired() {
        return batteryRequired;
    }


    // =========================================================
    // BOOK GETTERS
    // =========================================================

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getLanguage() {
        return language;
    }

    public String getEdition() {
        return edition;
    }

    public String getFormat() {
        return format;
    }

    public Integer getPages() {
        return pages;
    }


    // =========================================================
    // HEALTH & HOUSEHOLD GETTERS
    // =========================================================

    public String getPackSize() {
        return packSize;
    }

    public String getUsage() {
        return usage;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    // =========================================================
    // GENERAL PRODUCT SETTERS
    // =========================================================

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }


    // =========================================================
    // ELECTRONICS SETTERS
    // =========================================================

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public void setOperatingSystem(
            String operatingSystem
    ) {
        this.operatingSystem =
                operatingSystem;
    }

    public void setHardDiskSize(
            String hardDiskSize
    ) {
        this.hardDiskSize =
                hardDiskSize;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public void setRamMemoryInstalledSize(
            String ramMemoryInstalledSize
    ) {
        this.ramMemoryInstalledSize =
                ramMemoryInstalledSize;
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

    public void setConnectivity(
            String connectivity
    ) {
        this.connectivity = connectivity;
    }

    public void setConnectionType(
            String connectionType
    ) {
        this.connectionType =
                connectionType;
    }

    public void setNoiseControl(
            String noiseControl
    ) {
        this.noiseControl = noiseControl;
    }

    public void setEarPlacement(
            String earPlacement
    ) {
        this.earPlacement = earPlacement;
    }

    public void setFormFactor(
            String formFactor
    ) {
        this.formFactor = formFactor;
    }

    public void setCompatibility(
            String compatibility
    ) {
        this.compatibility = compatibility;
    }


    // =========================================================
    // FASHION SETTERS
    // =========================================================

    public void setFit(String fit) {
        this.fit = fit;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }


    // =========================================================
    // TOYS SETTERS
    // =========================================================

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public void setBatteryRequired(
            String batteryRequired
    ) {
        this.batteryRequired =
                batteryRequired;
    }


    // =========================================================
    // BOOK SETTERS
    // =========================================================

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }


    // =========================================================
    // HEALTH & HOUSEHOLD SETTERS
    // =========================================================

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}