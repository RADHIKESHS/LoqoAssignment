# Product Management API

The **Product Management API** is a Spring Boot application designed to manage product details. It provides RESTful endpoints for CRUD operations, filtering, sorting, and bulk uploading products. The application uses MySQL as the database and includes features such as validation, sorting, and custom exception handling.

## Features

- **Product Management**: Add, retrieve, and manage product details.
- **Filtering and Sorting**: Filter products by category, price range, and stock availability, and sort them by various fields.
- **Bulk Upload**: Upload multiple products at once with error handling for duplicates.
- **Validation**: Input validation using JSR-380 annotations.
- **Exception Handling**: Global exception handling with custom error messages.
- **Documentation**: API documentation using Springdoc OpenAPI.

## Technologies Used
- **Spring Boot**: Framework for building the application.
- **Spring Data JPA**: For ORM and database interactions.
- **MySQL**: Database for storing product information.
- **Lombok**: Reduces boilerplate code for data models.
- **Swagger OpenAPI**: For UI Experience
- **JUnit & Mockito**: For unit testing the application.

## Getting Started

### Prerequisites

- **Java 17**: Ensure you have JDK 17 installed.
- **Maven**: For building and running the application.
- **MySQL**: A MySQL server for database operations.

### Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/ProductManagement.git

   
2. **Setup the Database**

   - Ensure that MySQL is installed and running. Create a database named product_management.

   
3. **Build and Run the Application**

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run

4. **API Endpoints**
      - POST /products
      - Add a new product.
      - Request Body:
        
      ```bash
         {
           "name": "Product Name",
           "category": "Category",
           "price": 99.99,
           "inStock": true,
           "rating": 4.5,
           "createdAt": "2024-07-20T00:00:00Z"
         }
      ```
      - POST /products/bulk-upload
      - Bulk upload multiple products.
      - Request Body:
        
      ```bash
         [
           {
             "name": "Product1",
             "category": "Category1",
             "price": 100.00,
             "inStock": true,
             "rating": 4.0,
             "createdAt": "2024-07-20T00:00:00Z"
           },
           ...
         ]
      ```
      - Response:
         ```bash
            {
              "savedProducts": [ ... ],
              "existingProducts": [ ... ]
            }
   
         ```
      - GET /products
      - Retrieve products with optional filters.
      - Query Parameters:
        
         - category (optional)
         - minPrice (optional)
         - maxPrice (optional)
         - inStock (optional)
         - sortField (optional, default: "createdAt")
         - sortOrder (optional, default: "asc")

        Response:
      ```bash
            [
              {
                "id": 1,
                "name": "Product Name",
                "category": "Category",
                "price": 99.99,
                "inStock": true,
                "rating": 4.5,
                "createdAt": "2024-07-20T00:00:00Z"
              },
              ...
            ]
      ```
      - GET /products/category
      - Retrieve products by category.
      - Query Parameters:
        
         - category (required)

        Response:
      ```bash
            [
              {
                "id": 1,
                "name": "Product Name",
                "category": "Category",
                "price": 99.99,
                "inStock": true,
                "rating": 4.5,
                "createdAt": "2024-07-20T00:00:00Z"
              },
              ...
            ]
      ```
      - GET /products/price-range
      - Retrieve products within a price range.
      - Query Parameters:
        
         - minPrice (required)
         - maxPrice (required)

        Response:
      ```bash
            [
              {
                "id": 1,
                "name": "Product Name",
                "category": "Category",
                "price": 99.99,
                "inStock": true,
                "rating": 4.5,
                "createdAt": "2024-07-20T00:00:00Z"
              },
              ...
            ]
      ```
      - GET /products/in-stock
      - Retrieve products based on stock availability.
      - Query Parameters:
        
         - inStock (required)

        Response:
      ```bash
            [
              {
                "id": 1,
                "name": "Product Name",
                "category": "Category",
                "price": 99.99,
                "inStock": true,
                "rating": 4.5,
                "createdAt": "2024-07-20T00:00:00Z"
              },
              ...
            ]
      ```
1. **Run the Tests**

   ```bash
   ./mvnw test
   ```
