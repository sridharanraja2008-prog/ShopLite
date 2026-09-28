package com.shoplite.controller;

import com.shoplite.dto.CreateBillRequest;
import com.shoplite.entity.Bill;
import com.shoplite.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@Tag(name = "Billing", description = "Create sales bills and view billing history")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    @Operation(summary = "Generate a new bill", description = "Creates a sales bill, automatically checks and deducts stock from inventory, and calculates total amount.")
    public ResponseEntity<Bill> createBill(@Valid @RequestBody CreateBillRequest request) {
        Bill bill = billService.createBill(request);
        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "View all bills", description = "Returns a list of all sales bills with items and totals.")
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @GetMapping("/{billId}")
    @Operation(summary = "View bill by ID", description = "Returns details of a single bill.")
    public ResponseEntity<Bill> getBillById(@PathVariable Long billId) {
        return ResponseEntity.ok(billService.getBillById(billId));
    }
}
