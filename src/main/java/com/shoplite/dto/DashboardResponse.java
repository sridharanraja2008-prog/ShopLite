package com.shoplite.dto;

import java.math.BigDecimal;

public class DashboardResponse {

    private long totalProducts;
    private long lowStockProductsCount;
    private long totalFinalizedBills;
    private BigDecimal totalRevenue;

    public DashboardResponse() {
    }

    public DashboardResponse(long totalProducts, long lowStockProductsCount, long totalFinalizedBills, BigDecimal totalRevenue) {
        this.totalProducts = totalProducts;
        this.lowStockProductsCount = lowStockProductsCount;
        this.totalFinalizedBills = totalFinalizedBills;
        this.totalRevenue = totalRevenue;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getLowStockProductsCount() {
        return lowStockProductsCount;
    }

    public void setLowStockProductsCount(long lowStockProductsCount) {
        this.lowStockProductsCount = lowStockProductsCount;
    }

    public long getTotalFinalizedBills() {
        return totalFinalizedBills;
    }

    public void setTotalFinalizedBills(long totalFinalizedBills) {
        this.totalFinalizedBills = totalFinalizedBills;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
