# Task Management Service

## Overview

A Spring Boot-based Task Management system with the following key features:

- CRUD operations for Users and Tasks
- Support for two task types: **Bug** and **Feature**
- Filtering tasks based on dynamic conditions

## Architecture Highlights

- **Spring AOP** for request/response logging
- **JPA Auditing** to track create/update/delete events
- **Swagger UI** for interactive API documentation
- **Flyway** for managing database migrations
- **MockMvc** for integration testing
- **Centralized error handling** with custom responses

## How to build & run

### Prerequisites

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

### ️ Build & Run

```
cd taskmanagement
docker compose up -d --build
```

### Migrate Database

1. add migration scripts to src/main/resources/db/migration with below format

```
V[number]__[description].sql

number: The migration version number. It must be sequential and incremented from the previous version (e.g., V1__, V2__, V3__, ...).
description: A brief, readable description of the migration's purpose. Use underscores instead of spaces (e.g., add_user_table, rename_column_bug_table).

Example: V6__drop_actual_column_of_bug.sql
```

2. Rerun the application by run command

```
cd taskmanagement
docker compose up -d --build
```

## API Documentation

After the application starts, access the Swagger UI:
- [API Document](http://localhost:8080/swagger-ui/index.html)

## How to Run Tests

### API Tests (Swagger UI)

1. Open the Swagger UI at [API Document](http://localhost:8080/swagger-ui/index.html)
2. Click on the **Execute** button in the top-right corner of each api request

### API Tests (Postman)

1. Import api_collection.json from source code into Postman
2. Execute the API requests defined in the collection

### JUnit/Integration Tests

```
cd taskmanagement
docker compose run -rm test
```

## Brief design

### Domain Model Design

#### Users:
- Fields: id, username (unique), fullname, password
- Username cannot be updated after creation
- Fullname is updatable
- Returns errors when a user is not found

#### Task:
- Fields: id, title, description, category, status, user_id, category_id
- category: BUG or FEATURE (enum)
- Status: OPEN, IN_PROGRESS, DONE (enum)
- Each task is assigned to one user
- Task links to either Bug or Feature table based on its category
- Tasks can be switched between bug and feature (with proper rollback)
- Validation for title, category, and status

#### Bug:
- Fields: id, severity, stepsToReproduce, expectedResult, actualResult
- Severity: LOW, MEDIUM, HIGH (enum)

#### Feature:
- Fields: id, businessValue, deadline
- Deadline must be in the future and follow the format yyyy/MM/dd

### Search & Filter
- Supports pagination for performance
- Uses native SQL queries for filtering and partial text matching

### Database migration
- Managed by Flyway
- Migration scripts are automatically applied at startup

### Dockerized Setup
- PostgreSQL and Spring Boot services are containerized
- No need for manual environment configuration
- Fast and consistent local development environment