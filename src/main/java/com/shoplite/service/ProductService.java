package com.shoplite.service;

import com.shoplite.dto.LowStockProductResponse;
import com.shoplite.dto.ProductRequest;
import com.shoplite.entity.Product;
import com.shoplite.exception.InvalidOperationException;
import com.shoplite.exception.ResourceNotFoundException;
import com.shoplite.repository.BillItemRepository;
import com.shoplite.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BillItemRepository billItemRepository;

    public ProductService(ProductRepository productRepository, BillItemRepository billItemRepository) {
        this.productRepository = productRepository;
        this.billItemRepository = billItemRepository;
    }

    @Transactional
    public Product addProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName().trim());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setReorderThreshold(request.getReorderThreshold());
        product.setDeleted(false);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllActiveProducts();
    }

    public Product findRawProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (Boolean.TRUE.equals(product.getDeleted())) {
            throw new ResourceNotFoundException("The product was previously deleted");
        }
        return product;
    }

    @Transactional
    public Product updateProduct(Long id, ProductRequest request) {
        Product product = getProductById(id);
        product.setName(request.getName().trim());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setReorderThreshold(request.getReorderThreshold());
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (Boolean.TRUE.equals(product.getDeleted())) {
            throw new InvalidOperationException("The product was previously deleted");
        }

        product.setDeleted(true);
        productRepository.save(product);
    }

    public List<Product> getDeletedProducts() {
        return productRepository.findByDeletedTrue();
    }

    public List<LowStockProductResponse> getLowStockProducts() {
        List<Product> lowStockList = productRepository.findLowStockProducts();
        return lowStockList.stream()
                .map(p -> new LowStockProductResponse(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStockQuantity(),
                        p.getReorderThreshold(),
                        "REORDER REQUIRED"
                ))
                .collect(Collectors.toList());
    }

    public List<Product> searchProducts(String name) {
        if (name == null || name.trim().isEmpty()) {
            return productRepository.findAllActiveProducts();
        }
        return productRepository.findByNameContainingIgnoreCase(name.trim());
    }
}
