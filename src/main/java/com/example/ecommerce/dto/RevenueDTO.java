package com.example.ecommerce.dto;

public class RevenueDTO {

    private String date;
    private double revenue;

    public RevenueDTO() {
    }

    public RevenueDTO(
            String date,
            double revenue) {

        this.date = date;
        this.revenue = revenue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}