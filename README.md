# User Account System

## Overview

The User Account System is a Spring Boot application that manages user accounts, product transactions, and integrates with a MySQL database. It provides RESTful APIs for user creation, product purchase, and transaction management. The application is designed to be lightweight and easily deployable using Docker.

## Features

- User registration and management
- Product purchasing and transaction handling
- Swagger UI for API documentation
- Integration with MySQL for data persistence

## Technologies Used

- Java 17
- Spring Boot
- MySQL 5.7
- Maven
- Docker
- Swagger for API documentation

## Prerequisites

- Docker and Docker Compose installed
- Java 17 (if building outside of Docker)
- Maven (if building outside of Docker)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/ShamilKVS/userAccountSystem.git
cd userAccountSystem
Build and Run with Docker
Ensure that your Docker daemon is running.
Build and run the application using Docker Compose:
bash
Copy code
docker-compose up --build
Accessing the Application
Once the application is running, you can access the APIs at:

Base URL: http://localhost:8080
Swagger UI
Swagger UI is available for testing the API endpoints. You can access it at:

bash
Copy code
http://localhost:8080/swagger-ui.html
