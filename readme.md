# My E-Commerce – Backend

A Spring Boot REST API backend for an online electronics shopping application.

The backend handles user registration, authentication, products, shopping carts, orders, and order history using MySQL.

## Tech Stack

* Java 25
* Spring Boot 4.0.8
* Spring Web
* Spring Data JPA
* Spring Security
* JWT Authentication
* BCrypt
* MySQL 8
* Maven

## Features

* User registration
* User login
* BCrypt password hashing
* JWT authentication
* Protected REST APIs
* Product management
* Shopping cart management
* Product quantity management
* Order creation
* Order history
* Order item retrieval
* User-specific cart and order access
* CORS configuration



## Database

The application uses MySQL.

Database name:
ecommerce


Main tables:

users
product
cart_item
orders
order_item


## Database Configuration

Configure the database in:
src/main/resources/application.properties



## Spring Security
The application uses:
* JWT authentication
* Stateless sessions
* BCrypt password encoding
* JWT authentication filter
* CORS
* Protected endpoints
* Public authentication endpoints

### Public endpoints:

POST /api/users/register
POST /api/users/login
GET  /api/products
GET  /api/products/**

Other endpoints require authentication.

## REST API

### User APIs
POST /api/users/register
POST /api/users/login

### Product APIs
GET /api/products

### Cart APIs
POST   /api/cart/add?productId={id}
GET    /api/cart
PUT    /api/cart/{productId}/increase
PUT    /api/cart/{productId}/decrease
DELETE /api/cart/{productId}


### Order APIs

POST /api/orders
GET  /api/orders
GET  /api/orders/{orderId}/items


## Running the Backend

Navigate to the project:
Run:

.\mvnw.cmd spring-boot:run

The backend normally runs at:
http://localhost:8080


## Testing the API

You can test the APIs using:

* Postman
* Browser for GET requests
* React frontend

Example:

```text
GET http://localhost:8080/api/products
```

## Frontend Integration

The React frontend normally runs at:

```text
http://localhost:5173
```
The Spring Boot backend runs at:

```text
http://localhost:8080
```



Then start the frontend:

```bash
npm install
npm run dev
```

