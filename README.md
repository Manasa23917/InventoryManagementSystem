# InventoryManagementSystem

A desktop-based Inventory Management System developed using Java Swing, MySQL, and JDBC.

## Features

- Add new products
- View all products
- Update product details
- Delete products
- Search products by ID or name
- Sell products and automatically update stock
- Stock validation to prevent selling more than available quantity
- Select a product from the table to automatically fill the form fields
- MySQL database integration using JDBC

## Technologies Used

- Java
- Java Swing
- JDBC
- MySQL
- Eclipse IDE

## Database

The project uses a MySQL database named:

`inventory_db`

### Products Table

```sql
CREATE DATABASE inventory_db;

USE inventory_db;

CREATE TABLE products (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    category VARCHAR(100),
    price DOUBLE,
    quantity INT
);
