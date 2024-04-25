# Shop Service

The Shop Service provides functionality for managing orders, products, and shopping carts. It includes controllers for handling orders, products, and shopping carts, as well as exception handling and proxies to interact with other services.

## Table of Contents
1. [Order Controller](#order-controller)
2. [Product Controller](#product-controller)
3. [Shopping Cart Controller](#shopping-cart-controller)
4. [Exception Handling](#exception-handling)
5. [Proxies](#proxies)
6. [Usage](#usage)
7. [Dependencies](#dependencies)
8. [Setup](#setup)

## Order Controller

### Endpoints

- **GET** `/shop/orders/{id}`: Retrieve details of a specific order by its ID.
- **GET** `/shop/orders/{orderId}/products`: Retrieve products associated with an order.
- **POST** `/shop/orders/{userId}`: Create a new order.
- **DELETE** `/shop/orders/{id}`: Delete an order by its ID.
- **GET** `/shop/orders`: Retrieve a paginated list of all orders.
- **PUT** `/shop/orders/{orderId}/status`: Update the status of an order.
- **PUT** `/shop/orders/{orderId}/cancel`: Cancel an order.

## Product Controller

### Endpoints

- **GET** `/shop/products`: Retrieve a paginated list of all products.
- **GET** `/shop/products/{id}`: Retrieve details of a specific product by its ID.

## Shopping Cart Controller

### Endpoints

- **GET** `/shop/shopping-carts/{userId}/products`: Retrieve products in a user's shopping cart.
- **GET** `/shop/shopping-carts/{userId}`: Retrieve the shopping cart of a specific user.
- **POST** `/shop/shopping-carts/{userId}/products/{productId}`: Add a product to a user's shopping cart.
- **PUT** `/shop/shopping-carts/{userId}/products/{productId}/increment`: Increment the quantity of a product in a user's shopping cart.
- **PUT** `/shop/shopping-carts/{userId}`: Create a shopping cart for a user.
- **PUT** `/shop/shopping-carts/{userId}/products/{productId}/decrement`: Decrement the quantity of a product in a user's shopping cart.
- **DELETE** `/shop/shopping-carts/{userId}/products/{productId}`: Delete a product from a user's shopping cart.

## Exception Handling

The service includes exception handling for handling entity not found exceptions and method argument validation errors.

## Proxies

- **InventoryProxy**: Interacts with the Inventory service to retrieve product information.
- **WalletProxy**: Interacts with the Wallet service to manage user transactions.

## Usage

- Utilize the provided endpoints to manage orders, products, and shopping carts.
- Ensure proper exception handling mechanisms are in place to handle errors gracefully.

## Dependencies

- Spring Boot
- Spring Cloud OpenFeign
- Resilience4j Circuit Breaker
- Spring Retry

## Setup

1. Clone this repository.
2. Configure any necessary properties in `application.properties`.
3. Build and run the application using Maven or your preferred IDE.

   related repo 
https://github.com/obada404/spring-service-wallet
https://github.com/obada404/spring-service-inventory

Feel free to contribute by submitting bug reports, feature requests, or pull requests.
