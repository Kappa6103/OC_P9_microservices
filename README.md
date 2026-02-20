# MediLabo Solutions

MediLabo is a Spring Boot web application that is part of the OpenClassrooms Java Development course.

It is a mock application that implements a microservice architecture, utilizing SQL and NoSQL databases, containerization of services, and deployment with Docker Compose.

The web UI is dealt with using Java Templating Engine, allowing for the functionalities of the application that are :

* CRUD operations on patient’s entity
* CRUD operations on note’s entity
* diagnosis of diabetes

# Project Architecture

The project is organized as a multi-module Maven project using a microservices architecture. It consists of the following modules:
*   **`api-gateway-webmvc`**: The entry point for the system, handling routing and security.
*   **`patient-service-back`**: Manages patient related data.
*   **`note-service`**: Manages note related data.
*   **`risk-assessment`**: Evaluates patient health risks based on notes and specific key words.
*   **`web-frontend`**: The user interface for the application.

For testing and demonstration purposes, the Docker Compose file also launches a Mongo Express and Adminer instance.

# Technologies used

* Core
  *   **Java 21**: The project uses the Long-Term Support (LTS) version of Java.
  *   **Spring Boot 3.5.8**: The underlying framework for all microservices.
  *   **Spring Cloud (2025.0.0)**: Used for microservices coordination, specifically through the **Spring Cloud Gateway (WebMVC)** in the `api-gateway-webmvc` module.
  *   **Maven**: Dependency management and build tool.

* Data Storage & Persistence
  *   **PostgreSQL**: Used by `patient-service-back` and `api-gateway-webmvc` for relational data storage.
  *   **MongoDB**: Used by `note-service` for flexible, document-based storage of doctor notes.
  *   **Spring Data JPA**: Abstraction layer used for database interactions in the PostgreSQL-based and MongoDB services.

* Frontend
  *   **JTE (Java Template Engine)**: The `web-frontend` uses it for server-side rendering of HTML templates.

* Security
  *   **Spring Security**: Integrated into the `api-gateway-webmvc` to handle authentication and authorization.

* Utilities & Testing
  *   **Lombok**: Used across all modules to reduce boilerplate code.
  *   **Spring Boot Starter Validation**: Used for input validation across services.
  *   **JUnit 5 / Spring Boot Starter Test**: Standard testing framework for unit and integration tests.

# Installation & Run

**Prerequisites**
* maven
* docker

**Intallation**
* download source code
* decompresse .zip file
* cd into decompressed source folder
* package the project
```bash
 mvn package
```
**Run**
```bash
docker compose up -d
```
**Access**
  * web app: http://localhost:8080/login
    * usr: user
    * pwd: password
  * Mongo Express: http://localhost:8085
    * usr: admin
    * pwd: pass
  * Adminer: http://localhost:8086
    * system: PostgreSQL
    * server: postgres
    * usr: MEDILABO_USR
    * pwd: MEDILABO_USR
    * database: p9_test

**Stop**
```bash
docker compose stop
```
