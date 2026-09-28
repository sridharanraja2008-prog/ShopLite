package com.shoplite.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailySalesResponse {

    private LocalDate selectedDate;
    private long totalFinalizedBills;
    private BigDecimal totalSalesAmount;

    public DailySalesResponse() {
    }

    public DailySalesResponse(LocalDate selectedDate, long totalFinalizedBills, BigDecimal totalSalesAmount) {
        this.selectedDate = selectedDate;
        this.totalFinalizedBills = totalFinalizedBills;
        this.totalSalesAmount = totalSalesAmount;
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }

    public long getTotalFinalizedBills() {
        return totalFinalizedBills;
    }

    public void setTotalFinalizedBills(long totalFinalizedBills) {
        this.totalFinalizedBills = totalFinalizedBills;
    }

    public BigDecimal getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public void setTotalSalesAmount(BigDecimal totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }
}
