# Stock Management System  

A Spring Boot application for managing orders and items with RESTful APIs. The application supports CRUD operations, ensures data validation, and includes an embedded H2 database for easy setup.  

---

## 🚀 Features  
- Manage **Orders** and **Items** with intuitive APIs  
- Built-in **data validation** using Jakarta Validation  
- Embedded H2 database for a lightweight and fast setup  
- Configurable and easy-to-use development environment  

---

## 📋 Prerequisites  
Before running the application, ensure the following are installed:  
- **Java** 17 or later  
- **Maven** 3.6 or later  

---

## ⚙️ Configuration  

### Database Setup  
The application uses an **H2 database** by default. Configure the database in `src/main/resources/application.properties` as needed:  

```properties
spring.application.name=stockmanagement

# H2 Database Configuration
spring.datasource.url=jdbc:h2:file:C:/Users/akaya/test;AUTO_SERVER=TRUE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true


Access the H2 console at: http://localhost:8080/h2-console
Default credentials:

Username: sa
Password: password
🛠️ Build & Run
Build the Project
To build the project, use:

bash
Copy code
mvn clean install
Run the Application
To start the application, use:

bash
Copy code
mvn spring-boot:run
The application will run on http://localhost:8080.

📚 API Endpoints
Orders
Method	Endpoint	Description
GET	/api/orders	Fetch all orders
POST	/api/orders	Create a new order
PUT	/api/orders/{id}	Update an existing order
GET	/api/orders/{id}	Fetch a specific order
DELETE	/api/orders/{id}	Delete a specific order
Items
Method	Endpoint	Description
GET	/api/items	Fetch all items
POST	/api/items	Create a new item
PUT	/api/items/{id}	Update an existing item
GET	/api/items/{id}	Fetch a specific item
DELETE	/api/items/{id}	Delete a specific item
✅ Validation
The application ensures data integrity using Jakarta Validation.

ItemDTO Validation Rules:
name: Must not be blank
price: Must be positive and not null
stock: Must be positive and not null
📄 License
This project is licensed under the MIT License.

Feel free to use, modify, and share!

🤝 Contributing
We welcome contributions to improve this project. Please submit a pull request or raise an issue for any suggestions or bugs.

📞 Support
For questions or assistance, contact:
Email: support@example.com
GitHub Repository: Stock Management System

Happy Coding! 🎉
