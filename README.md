## Overview

This project is a RESTful API for managing tasks, built with Java and Spring Boot. The API allows clients to perform CRUD (Create, Read, Update, Delete) operations on tasks, including pagination and sorting options.

## Features

- **Task Management:** Create, retrieve, update, and delete tasks.
- **Pagination and Sorting:** Support for paginated and sorted retrieval of tasks.
- **Validation:** Input validation using annotations.
- **Error Handling:** Custom error responses for invalid operations.
- **Mapper:** Uses MapStruct for mapping between entities and DTOs.
- **OpenAPI Documentation:** API documentation generated using OpenAPI 3.0.

## Technology Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- - **Docker**
- **MapStruct**
- **Lombok**
- **H2 Database**
- **JUnit 5**
- **MockMvc** (for testing controllers)
- **Swagger/OpenAPI 3.0**

## Prerequisites

- **Java 17** or higher
- **Maven 3.8** or higher

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/your-username/task-management-api.git
cd task-management-api
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

### Running Tests

To run the unit and integration tests, execute:

```bash
mvn test
```

## OpenAPI Documentation

The API documentation is available at `http://localhost:8080/swagger-ui.html` after running the application.

## Docker
This project includes a Docker setup to containerize the application, making it easy to deploy and run in different environments.