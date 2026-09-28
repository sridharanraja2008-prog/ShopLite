package com.shoplite.service;

import com.shoplite.dto.BillItemRequest;
import com.shoplite.dto.CreateBillRequest;
import com.shoplite.entity.Bill;
import com.shoplite.entity.BillItem;
import com.shoplite.entity.BillStatus;
import com.shoplite.entity.Product;
import com.shoplite.exception.InsufficientStockException;
import com.shoplite.exception.ResourceNotFoundException;
import com.shoplite.repository.BillRepository;
import com.shoplite.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final ProductRepository productRepository;

    public BillService(BillRepository billRepository, ProductRepository productRepository) {
        this.billRepository = billRepository;
        this.productRepository = productRepository;
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + billId));
    }

    @Transactional
    public Bill createBill(CreateBillRequest request) {
        Bill bill = new Bill();
        bill.setBillDate(LocalDateTime.now());
        bill.setFinalizedAt(LocalDateTime.now());
        bill.setStatus(BillStatus.FINALIZED);

        // Group quantities in case duplicate product IDs are passed
        Map<Long, Integer> productQuantities = new HashMap<>();
        for (BillItemRequest itemReq : request.getItems()) {
            productQuantities.put(
                    itemReq.getProductId(),
                    productQuantities.getOrDefault(itemReq.getProductId(), 0) + itemReq.getQuantity()
            );
        }

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Long productId = entry.getKey();
            int requestedQty = entry.getValue();

            Product product = productRepository.findByIdWithLock(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

            if (product.getStockQuantity() < requestedQty) {
                throw new InsufficientStockException("Insufficient stock for " + product.getName() +
                        ". Available: " + product.getStockQuantity() +
                        ", Requested: " + requestedQty + ".");
            }

            // Deduct stock
            product.setStockQuantity(product.getStockQuantity() - requestedQty);
            productRepository.save(product);

            // Create line item
            BillItem item = new BillItem();
            item.setProduct(product);
            item.setQuantity(requestedQty);
            item.setUnitPrice(product.getPrice());
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(requestedQty));
            item.setSubtotal(subtotal);

            bill.addItem(item);
            totalAmount = totalAmount.add(subtotal);
        }

        bill.setTotalAmount(totalAmount);
        return billRepository.save(bill);
    }
}
