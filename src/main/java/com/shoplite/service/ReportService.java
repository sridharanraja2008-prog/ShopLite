package com.shoplite.service;

import com.shoplite.dto.DailySalesResponse;
import com.shoplite.dto.DashboardResponse;
import com.shoplite.dto.TopSellingProductResponse;
import com.shoplite.entity.Bill;
import com.shoplite.entity.BillStatus;
import com.shoplite.repository.BillItemRepository;
import com.shoplite.repository.BillRepository;
import com.shoplite.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final BillRepository billRepository;
    private final ProductRepository productRepository;
    private final BillItemRepository billItemRepository;

    public ReportService(BillRepository billRepository, ProductRepository productRepository, BillItemRepository billItemRepository) {
        this.billRepository = billRepository;
        this.productRepository = productRepository;
        this.billItemRepository = billItemRepository;
    }

    public DailySalesResponse getDailySalesReport(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<Bill> finalizedBills = billRepository.findFinalizedBillsBetween(startOfDay, endOfDay);

        long billCount = finalizedBills.size();
        BigDecimal totalSales = finalizedBills.stream()
                .map(Bill::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DailySalesResponse(date, billCount, totalSales);
    }

    public DashboardResponse getDashboardSummary() {
        long totalProducts = productRepository.count();
        long lowStockCount = productRepository.countLowStockProducts();
        long totalFinalizedBills = billRepository.countByStatus(BillStatus.FINALIZED);
        BigDecimal totalRevenue = billRepository.sumTotalRevenue();

        return new DashboardResponse(totalProducts, lowStockCount, totalFinalizedBills, totalRevenue);
    }

    public List<TopSellingProductResponse> getTopSellingProducts() {
        List<Object[]> rawList = billItemRepository.findTopSellingProducts();
        List<TopSellingProductResponse> result = new ArrayList<>();

        for (Object[] row : rawList) {
            Long productId = (Long) row[0];
            String productName = (String) row[1];
            Long totalSold = ((Number) row[2]).longValue();
            result.add(new TopSellingProductResponse(productId, productName, totalSold));
        }

        return result;
    }
}
