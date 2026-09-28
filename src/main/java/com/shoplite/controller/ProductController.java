package com.shoplite.controller;

import com.shoplite.dto.LowStockProductResponse;
import com.shoplite.dto.ProductRequest;
import com.shoplite.entity.Product;
import com.shoplite.exception.ResourceNotFoundException;
import com.shoplite.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Manage shop inventory and low stock alerts")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Add a new product", description = "Adds a product with price, stock quantity, and reorder threshold.")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductRequest request) {
        Product created = productService.addProduct(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "View all active products", description = "Returns a list of all active products with their current stock levels.")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/deleted")
    @Operation(summary = "View previously deleted products", description = "Returns a list of all products that were previously deleted.")
    public ResponseEntity<List<Product>> getDeletedProducts() {
        return ResponseEntity.ok(productService.getDeletedProducts());
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Low stock alert", description = "Displays products whose stock is below the reorder threshold.")
    public ResponseEntity<List<LowStockProductResponse>> getLowStockProducts() {
        return ResponseEntity.ok(productService.getLowStockProducts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Fetches a single product by its unique ID. If the product was previously deleted, shows 'The product was previously deleted'.")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.findRawProductById(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        if (Boolean.TRUE.equals(product.getDeleted())) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "The product was previously deleted");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates the product details and stock quantity.")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Deletes a product by its ID from active inventory.")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
