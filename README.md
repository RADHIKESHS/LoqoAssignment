# Product Management API

The **Product Management API** is a Spring Boot application designed to manage product details. It provides RESTful endpoints for CRUD operations, filtering, sorting, and bulk uploading products. The application uses MySQL as the database and includes features such as validation, sorting, and custom exception handling.

## Features

- **Product Management**: Add, retrieve, and manage product details.
- **Filtering and Sorting**: Filter products by category, price range, and stock availability, and sort them by various fields.
- **Bulk Upload**: Upload multiple products at once with error handling for duplicates.
- **Validation**: Input validation using JSR-380 annotations.
- **Exception Handling**: Global exception handling with custom error messages.
- **Documentation**: API documentation using Springdoc OpenAPI.

## Getting Started

### Prerequisites

- **Java 17**: Ensure you have JDK 17 installed.
- **Maven**: For building and running the application.
- **MySQL**: A MySQL server for database operations.

### Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/ProductManagement.git
   cd ProductManagement
