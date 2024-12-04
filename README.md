# E-Cart RESTful Application  

An advanced backend solution for e-commerce applications with robust features, scalable architecture, and efficient API interactions.  

---

## Features  

### Core Features  
- **RESTful API Development**: Built using Spring Boot for modular and scalable backend architecture.  
- **Authentication & Authorization**: Implemented using Spring Security with role-based access control.  
- **Custom Exception Handling**: Gracefully handle errors with user-friendly messages.  
- **Validation**: Enforce rules for incoming requests to ensure data integrity.  
- **Swagger Documentation**: Comprehensive API documentation for easy exploration and testing.  
- **Jackson Dependency**: Support for multiple representations (e.g., JSON, XML).  
- **Versioning**: Support for versioned APIs for backward compatibility.  
- **HATEOAS**: Hypermedia as the Engine of Application State for richer API interactions.  

### Customizing Responses  
- **Serialization and Field Name Customization**: Tailor data serialization and field mappings.  
- **Selective Field Filtering**: Respond with only the fields relevant to the client’s request.  

### Performance Enhancements  
- **Pagination**: Efficiently handle large datasets for endpoints returning lists.  
- **Model Mapper**: Simplify data transformations between layers.  

---

## Documentation  

### Available Resources  
- **Swagger**: Explore and test API endpoints for each service.  
- **HATEOAS**: Included in API responses to facilitate navigation and richer client interactions.  

---

## ER Diagram
-- [View ER Diagram here](ER_Diagram_E_Store.png)
--![ER_Diagram_E_Store](https://github.com/user-attachments/assets/8cc91239-689e-4931-83c3-db1743ad55d9)



## Technology Stack  

- **Programming Language**: Java  
- **Frameworks**: Spring, Spring Boot  
- **Database**: MySQL  
- **ORM**: JPA, Hibernate  
- **REST API Development**: Built using RESTful principles  

---

## Entities  

The application supports the following entities and associated services:  
- **Cart**: Manage user-specific shopping cart functionality.  
- **CartItem**: Handle items within the cart.  
- **Category**: Organize products into categories.  
- **Order**: Manage user orders and their statuses.  
- **OrderItems**: Track individual items within an order.  
- **Product**: CRUD operations for managing product details.  
- **Role**: Define and assign roles for users (e.g., Admin, Customer).  
- **User**: Manage user data, authentication, and role assignments.  

---

## Getting Started  

### Prerequisites  
- Java 17 or later  
- MySQL Server  
- Maven  

