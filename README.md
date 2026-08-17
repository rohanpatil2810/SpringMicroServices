# Spring Microservices with API Gateway, JWT Security, Eureka, Config Server & Spring Boot Admin

A complete, production-style microservices architecture built using **Spring Boot** and **Spring Cloud**.

This project demonstrates:

- Service Discovery with Netflix Eureka
- Centralized Configuration using Spring Cloud Config + Git
- API Gateway with JWT Authentication & Authorization
- Custom JWT Filter + Spring Security
- Spring Boot Admin for monitoring
- Secure Login & Register flow
- Inter-service communication via Gateway

---

## Architecture
Client
│
▼
API Gateway (8080)  ← JWT Validation + Routing
│
├── /auth/**  → UserLoginMicroservice (8081)
└── /msg/**   → UserMsgMicroservice (8082)
Supporting Services:

Eureka Server          → 8761
Config Server          → 7070
Spring Boot Admin      → 9090

text


---

## Microservices Overview

| Service                     | Port | Role                                      |
|----------------------------|------|-------------------------------------------|
| ServiceDiscovery           | 8761 | Eureka Server (Service Registry)          |
| ConfigServerFromGitRepo    | 7070 | Spring Cloud Config Server (Git-backed)   |
| AdminServer                | 9090 | Spring Boot Admin Dashboard               |
| APIGateway                 | 8080 | Gateway + JWT Security Filter             |
| UserLoginMicroservice-1    | 8081 | User Registration & Login (JWT Issuer)    |
| UserMsgMicroservice-1      | 8082 | Protected business service                |

---

## Security Implementation (JWT)

### Public Endpoints
- `POST /auth/register`
- `POST /auth/login`

These are allowed without a token.

### Protected Endpoints
Any other request (example: `GET /msg/getMsg`) requires a valid JWT in the header:

### How JWT Validation Works in API Gateway

1. `JwtAuthenticationFilter` intercepts the request
2. Extracts the Bearer token
3. Validates signature and expiration using shared secret
4. Extracts username from the token
5. Sets `Authentication` in `SecurityContextHolder`
6. Spring Security allows the request only if Authentication exists
7. Request is forwarded to the target microservice

**Key classes in API Gateway:**
- `JwtUtil` – Token parsing & validation
- `JwtAuthenticationFilter` – Custom security filter
- `SecurityConfig` – Public vs Protected routes
- `MutableHttpServletRequest` – Adds `X-Username` header for downstream services

---

## How to Run

### Prerequisites
- Java 17+
- Maven
- MySQL
- Git

### Start Order

```bash
# 1. Eureka Server
cd ServiceDiscovery
mvn spring-boot:run

# 2. Config Server
cd ConfigServerFromGitRepo
mvn spring-boot:run

# 3. Spring Boot Admin
cd AdminServer
mvn spring-boot:run

# 4. User Login Service
cd UserLoginMicroservice-1
mvn spring-boot:run

# 5. User Message Service
cd UserMsgMicroservice-1
mvn spring-boot:run

# 6. API Gateway
cd APIGateway
mvn spring-boot:run
