# BIF4 SWEN tour planner Server

This repository contains the Backend applications for the Tour Planner program, which allows users to create, view, update, and delete tours and tour logs.

---

### Overview

The Backend is a RESTful API built with Java/Spring Boot. It manages tours, tour logs and related business logic, exposing endpoints for CRUD operations.

The usage of a Map API is in the works

### Features

- Create, read, update, and delete tours
- Create, read, update, and delete tour logs
- Validation and error handling
- Logging of API requests and operations

### Prerequisites

- Java 24 or later
- Maven 3.6+
- (Optional) Postman or similar for API testing

### Setup and Running

Clone the repository:

     git clone https://github.com/ThomasBoigner/BIF4-SWEN-Tour-Planner-Server.git
	

Build the project:
	
	mvn clean install

Run the Server

	mvn spring-boot:run

The backend API will be available at:

    http://localhost:8080/

API Endpoints

    GET /api/tour — List all tours

    GET /api/tour/{id} — Get tour by ID

    POST /api/tour — Create a new tour

    PUT /api/tour/{id} — Update tour

    DELETE /api/tour/{id} — Delete tour

    GET /api/tourLog/tour/{tourId} — Get logs for a specific tour

    POST /api/tourLog — Create a new tour log

    DELETE /api/tourLog/{id} — Delete a tour log
