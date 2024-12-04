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
-- ![ER Diagram](file:///C:/Users/adity/Downloads/DALL%C2%B7E%202024-12-04%2013.57.58%20-%20A%20normal%20ER%20(Entity-Relationship)%20diagram%20for%20an%20e-commerce%20application,%20featuring%20entities%20and%20their%20relationships_%20_1.%20Entities_%20Cart,%20CartItem,%20Cat.webp)

## UML Diagram
-- ![UML Diagram](file:///C:/Users/adity/Downloads/DALL%C2%B7E%202024-12-04%2013.56.51%20-%20A%20clear%20and%20simplified%20UML%20class%20diagram%20for%20an%20e-commerce%20RESTful%20application%20showcasing%20entities_%20Cart,%20CartItem,%20Category,%20Order,%20OrderItems,%20Produ.webp)
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

