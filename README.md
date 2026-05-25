# Banking Microservices Platform

A production-ready backend banking system built with **Java 21**, **Spring Boot 3.3.4**, and **Spring Cloud** microservices architecture. The platform handles core banking operations including customer management, account lifecycle, fund transfers, and real-time email notifications.

## Architecture Overview

The system follows a microservices architecture with six independent services communicating via REST (Feign Client) and secured behind a unified API Gateway.

```
Client
  └── API Gateway (port 8888)
        ├── Discovery Service / Eureka (port 8761)
        ├── Authentication Service (port 8081)
        ├── Customer Service (port 8082)
        ├── Account Service (port 8083)
        └── Notification Service (port 8084)
```

## Services

| Service | Port | Responsibility |
|---|---|---|
| `discovery-service` | 8761 | Eureka service registry — all services register here |
| `gateway-service` | 8888 | API Gateway — single entry point, JWT validation, routing |
| `authentication-service` | 8081 | User registration, login, JWT issuance, role management |
| `customer-service` | 8082 | Customer CRUD, profile management, Feign client to auth |
| `account-service` | 8083 | Account lifecycle, credit/debit/transfer operations |
| `notification-service` | 8084 | Email notifications for all account events |

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.3.4
- **Service Discovery:** Spring Cloud Netflix Eureka
- **API Gateway:** Spring Cloud Gateway
- **Inter-service Communication:** OpenFeign
- **Security:** Spring Security 6 + JWT (Auth0 java-jwt)
- **Database:** MySQL (separate schema per service)
- **ORM:** Spring Data JPA / Hibernate
- **Build Tool:** Maven
- **Containerization:** Docker

## Key Features

- **JWT Authentication** — stateless auth with role-based access control
- **Account Lifecycle** — create, activate, suspend, delete accounts
- **Financial Operations** — credit, debit, fund transfer with automatic rollback on failure
- **Transaction History** — paginated operation history per account
- **Email Notifications** — automated emails on account creation, activation, suspension, credit/debit
- **Resilient Transfers** — debit rollback if credit fails during inter-account transfer
- **Centralized Routing** — all traffic flows through API Gateway with JWT validation

## Prerequisites

- Java 21+
- Maven 3.8+
- MySQL 8.0+
- Docker (optional)

## Getting Started

### 1. Database Setup

Create separate MySQL databases for each service:

```sql
CREATE DATABASE auth_db;
CREATE DATABASE customer_db;
CREATE DATABASE account_db;
CREATE DATABASE notification_db;
```

### 2. Configure Each Service

Update `application.yml` in each service with your MySQL credentials:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/{service_db}
    username: your_username
    password: your_password
```

### 3. Start Services (in order)

```bash
# 1. Discovery Service (must start first)
cd discovery-service && mvn spring-boot:run

# 2. Gateway Service
cd gateway-service && mvn spring-boot:run

# 3. Authentication Service
cd authentication-service && mvn spring-boot:run

# 4. Customer Service
cd customer-service && mvn spring-boot:run

# 5. Account Service
cd account-service && mvn spring-boot:run

# 6. Notification Service
cd notification-service && mvn spring-boot:run
```

### 4. Verify Services Are Registered

Open Eureka Dashboard: `http://localhost:8761`

All six services should appear as registered instances.

## API Endpoints

All requests go through the Gateway at `http://localhost:8888`.

### Authentication
| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | Login and receive JWT |

### Customers
| Method | Endpoint | Description |
|---|---|---|
| POST | `/customers/create` | Create customer profile |
| GET | `/customers/{id}` | Get customer by ID |
| PUT | `/customers/update` | Update customer |
| DELETE | `/customers/delete/{id}` | Delete customer |

### Accounts
| Method | Endpoint | Description |
|---|---|---|
| POST | `/accounts/commands/create` | Create account for a customer |
| PUT | `/accounts/commands/update` | Activate or suspend account |
| POST | `/accounts/commands/credit` | Credit account |
| POST | `/accounts/commands/debit` | Debit account |
| POST | `/accounts/commands/transfer` | Transfer between accounts (with rollback) |
| DELETE | `/accounts/commands/delete/{id}` | Delete account (balance must be 0) |
| GET | `/accounts/queries/get-account/{id}` | Get account by ID |
| GET | `/accounts/queries/find-account/{customerId}` | Get account by customer |
| GET | `/accounts/queries/all-operations` | Paginated transaction history |
| GET | `/accounts/queries/get-operation/{id}` | Get single operation |

## Security

All endpoints except `/auth/register` and `/auth/login` require a valid JWT token:

```
Authorization: Bearer <token>
```

JWT tokens are issued by the Authentication Service and validated at the Gateway level before routing to downstream services.

## Project Structure

```
banking-microservices/
├── discovery-service/          # Eureka Server
├── gateway-service/            # Spring Cloud Gateway
├── authentication-service/     # JWT auth + user management
├── customer-service/           # Customer profiles
├── account-service/            # Core banking operations
│   └── src/main/java/com/abubakar/accountservice/
│       ├── commands/           # Write operations (create, credit, debit)
│       ├── queries/            # Read operations + service layer
│       │   ├── service/        # AccountService + AccountServiceImpl
│       │   ├── entity/         # Account, Operation JPA entities
│       │   └── repository/     # Spring Data repositories
│       └── common/             # Shared enums, security, properties
└── notification-service/       # Email notification service
```

## Author

**Abubakar Tariq**
- GitHub: [github.com/abubakar-tq](https://github.com/abubakar-tq)
- Email: abubakar.tariq.contact@gmail.com
