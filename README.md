# Stock Management System

This is a Stock Management System built with Java and Spring Boot. It allows you to manage orders and items in a stock management context.

## Features

- Create, update, delete, and fetch orders
- Create, update, delete, and fetch items
- Validation for order and item data

## Technologies Used

- Java
- Spring Boot
- Maven
- JPA (Java Persistence API)
- Hibernate
- Jakarta Validation

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- A database (e.g., MySQL, PostgreSQL)

Configure the database in src/main/resources/application.properties:  
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
Build the project:  
mvn clean install
Run the application:  
mvn spring-boot:run
API Endpoints
Orders
GET /api/orders: Fetch all orders
POST /api/orders: Create a new order
PUT /api/orders/{id}: Update an existing order
GET /api/orders/{id}: Fetch a specific order
DELETE /api/orders/{id}: Delete a specific order
Items
GET /api/items: Fetch all items
POST /api/items: Create a new item
PUT /api/items/{id}: Update an existing item
GET /api/items/{id}: Fetch a specific item
DELETE /api/items/{id}: Delete a specific item
Validation
The application uses Jakarta Validation to ensure data integrity. Here are some of the validations:  
ItemDTO:
name: Must not be blank
price: Must be positive and not null
stock: Must be positive and not null
License
This project is licensed under the MIT License. See the LICENSE file for details.
