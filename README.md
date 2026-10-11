# Java Servlet CRUD API

A beginner-friendly backend project built using Java Servlets, Jakarta Servlet API, Apache Tomcat, and Maven. This project demonstrates how to implement CRUD (Create, Read, Update, Delete) operations through HTTP requests.

## Features

- Create a user
- Retrieve all users
- Retrieve a user by ID
- Update an existing user
- Delete a user
- Validate request parameters
- Return JSON responses with appropriate HTTP status codes

## Technologies Used

- Java
- Jakarta Servlet API
- Apache Tomcat 10.1
- Maven
- Postman

## API Endpoints

Base URL: `http://localhost:8080/crud-app-by-servlet/users`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/users` | Create a user |
| GET | `/users` | Retrieve all users |
| GET | `/users?id=101` | Retrieve a user by ID |
| PUT | `/users?id=101` | Update a user |
| DELETE | `/users?id=101` | Delete a user |

## Data Storage

User information is stored in an in-memory `HashMap`. Data is not persistent and will be lost when the application restarts.

## Running the Project

1. Install a compatible JDK and Apache Tomcat 10.1.
2. Open the project in IntelliJ IDEA.
3. Build the project using Maven.
4. Deploy the generated WAR file to Tomcat.
5. Start Tomcat and test the endpoints using Postman.

## Learning Outcomes

This project helped me practice HTTP methods, servlet request and response handling, JSON generation, input validation, and basic REST-style API development.