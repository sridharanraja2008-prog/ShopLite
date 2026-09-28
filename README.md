# ShopLite - Small Shop Inventory & Billing System

ShopLite is a Spring Boot application designed to manage inventory and generate billing receipts for small retail shops. It tracks stock levels, provides low-stock alerts, and automatically deducts product inventory when a sale is finalized.

## Tech Stack
- **Java 17**
- **Spring Boot 3.2.5** (Spring Web, Spring Data JPA, Spring Validation)
- **MySQL 8.0**
- **Maven**
- **Springdoc OpenAPI (Swagger UI)**
- **Bootstrap 5 (Frontend Dashboard)**

---

## Features
1. **Inventory Management**: Add, update, view, and delete products with real-time stock tracking.
2. **Sales Billing**: Create invoices with multi-item selection and automatic inventory deduction with pessimistic locking to prevent race conditions.
3. **Low Stock Alerts**: Displays products falling below their configured reorder threshold.
4. **Daily Sales Reports**: Summarizes total revenue and number of completed bills for any selected date.
5. **Interactive Swagger API Documentation**: Test and inspect all REST endpoints directly in browser.
6. **Built-in Web Dashboard**: Clean Bootstrap-based UI for inventory, billing, alerts, and reporting.

---

## Getting Started

### Prerequisites
- JDK 17 or higher
- Apache Maven 3.8+
- MySQL Server 8.0 running locally

### Database Setup
Configure your database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shoplite?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

### Running the Application
```bash
mvn spring-boot:run
```

Once started:
- **Web App Dashboard:** [http://localhost:8080/index.html](http://localhost:8080/index.html)
- **Swagger UI Documentation:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## REST Endpoints Overview

### Products (`/api/products`)
- `POST /api/products` - Add a new product
- `GET /api/products` - List all active products
- `GET /api/products/deleted` - List soft-deleted products
- `GET /api/products/low-stock` - List products below reorder threshold
- `GET /api/products/{id}` - Get product details by ID
- `PUT /api/products/{id}` - Update product details / stock
- `DELETE /api/products/{id}` - Soft-delete a product

### Billing (`/api/bills`)
- `POST /api/bills` - Generate a new sales bill and deduct stock
- `GET /api/bills` - View billing history
- `GET /api/bills/{billId}` - View details of a specific bill

### Reports (`/api/reports`)
- `GET /api/reports/daily?date=YYYY-MM-DD` - Get daily sales report
