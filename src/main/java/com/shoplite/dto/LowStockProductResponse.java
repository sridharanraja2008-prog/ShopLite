package com.shoplite.dto;

import java.math.BigDecimal;

public class LowStockProductResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer availableStock;
    private Integer reorderThreshold;
    private String status;

    public LowStockProductResponse() {
    }

    public LowStockProductResponse(Long id, String name, BigDecimal price, Integer availableStock, Integer reorderThreshold, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availableStock = availableStock;
        this.reorderThreshold = reorderThreshold;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public Integer getReorderThreshold() {
        return reorderThreshold;
    }

    public void setReorderThreshold(Integer reorderThreshold) {
        this.reorderThreshold = reorderThreshold;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
