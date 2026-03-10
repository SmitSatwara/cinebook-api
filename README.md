# 🎬 CineBook API — Movie Ticket Booking System

A production-ready RESTful backend API for a movie ticket booking platform, built with **Spring Boot 3.5**, **PostgreSQL**, and **Redis**. Features JWT authentication, role-based access control, real-time seat locking, and full Docker support.

---

## 📌 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
    - [Prerequisites](#prerequisites)
    - [Option 1: Run with Docker (Recommended)](#option-1-run-with-docker-recommended)
    - [Option 2: Run Locally with IntelliJ](#option-2-run-locally-with-intellij)
- [Environment Variables](#-environment-variables)
- [API Endpoints](#-api-endpoints)
- [Key Design Decisions](#-key-design-decisions)
- [Database Schema](#-database-schema)

---

## ✅ Features

- **JWT Authentication** — Secure login and registration with token-based auth
- **Role-Based Access Control (RBAC)** — USER and ADMIN roles with protected endpoints
- **Seat Locking with Redis** — Prevents double booking using Redis TTL (10-minute lock)
- **Complete Booking Flow** — Lock seat → Book ticket → Cancel booking
- **Dynamic Pricing** — PREMIUM and REGULAR seat types with different pricing
- **Multi-language Support** — Movies in ENGLISH, HINDI, and GUJARATI
- **Scheduled Cleanup** — Auto-releases expired seat locks every 60 seconds
- **Swagger UI** — Interactive API documentation
- **Docker Support** — Full containerization with multi-stage build
- **Sample Data** — Auto-loaded test data on startup

---

## 🛠 Tech Stack

| Category | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.11 |
| Database | PostgreSQL 15 |
| Cache / Locking | Redis |
| Authentication | JWT (jjwt 0.11.5) |
| Security | Spring Security 6 |
| ORM | Spring Data JPA + Hibernate 6 |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Build Tool | Maven |
| Containerization | Docker + Docker Compose |
| Code Generation | Lombok |

---

## 🏗 Architecture

```
┌─────────────────────────────────────────────────────┐
│                    Client (Postman / Frontend)      │
└─────────────────────┬───────────────────────────────┘
                      │ HTTP Requests
                      ▼
┌─────────────────────────────────────────────────────┐
│                  Spring Boot App                    │
│                                                     │
│  JwtFilter → Controller → Service → Repository      │
│                              │                      │
│                    ┌─────────┴──────────┐           │
│                    ▼                    ▼           │
│             PostgreSQL 15           Redis           │
│          (persistent data)     (seat locking)       │
└─────────────────────────────────────────────────────┘
```

### Seat Locking Flow

```
User selects seat
      ↓
Lock seat in DB (status = LOCKED)
      ↓
Store in Redis with 10-min TTL
key: seat:lock:{showId}:{seatId}
      ↓
User completes payment / booking
      ↓
Mark seat as BOOKED in DB
Remove Redis key
      ↓
If user abandons → Redis TTL expires
→ Cleanup job runs every 60s
→ Seat released back to AVAILABLE
```

---

## 📁 Project Structure

```
src/main/java/com/smitsatwara/cinebook/
├── config/
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── BookingController.java
│   ├── MovieController.java
│   ├── ScreenController.java
│   ├── SeatController.java
│   ├── ShowController.java
│   ├── ShowSeatController.java
│   └── TheatreController.java
├── dto/
│   ├── AuthResponse.java
│   ├── BookingRequest.java
│   ├── BookingResponse.java
│   └── ... (all request/response DTOs)
├── exception/
│   └── GlobalExceptionHandler.java
├── job/
│   └── SeatLockCleanupJob.java
├── model/
│   ├── Booking.java / BookingStatus.java
│   ├── Movie.java / Language.java
│   ├── Screen.java
│   ├── Seat.java / SeatType.java / SeatStatus.java
│   ├── Show.java
│   ├── ShowSeat.java
│   ├── Theatre.java
│   └── User.java / UserRole.java
├── repository/         (8 JPA repositories)
├── security/
│   ├── JwtFilter.java
│   ├── JwtUtil.java
│   └── UserDetailsServiceImpl.java
└── service/            (8 services)

src/main/resources/
├── application.properties
└── data.sql            (sample data — auto loaded on startup)
```

---

## 🚀 Getting Started

### Prerequisites

| Tool | Version |
|---|---|
| Java | 17+ |
| Maven | 3.9+ |
| Docker Desktop | Latest |
| PostgreSQL | 15 (via Docker) |
| Redis | Latest (via Docker) |

---

### Option 1: Run with Docker (Recommended)

This runs all 3 services — app, postgres, and redis — in containers.

**Step 1 — Clone the repository**
```bash
git clone https://github.com/SmitSatwara/cinebook-api.git
cd cinebook-api
```

**Step 2 — Build and start all containers**
```bash
docker compose up --build
```

> ⚠️ First build takes 15-20 minutes as Maven downloads all dependencies.
> Subsequent builds are much faster due to Docker layer caching.

**Step 3 — Verify the app is running**

Open Swagger UI in your browser:
```
http://localhost:8080/swagger-ui/index.html
```

**Stop all containers**
```bash
# Stop but keep data
docker compose down

# Stop and wipe all data
docker compose down -v
```

---

### Option 2: Run Locally with IntelliJ

This is the recommended approach for **daily development** — faster startup, no rebuild needed on code changes.

**Step 1 — Start only postgres and redis in Docker**
```bash
docker compose up postgres redis
```

**Step 2 — Run the Spring Boot app from IntelliJ**

Open the project in IntelliJ IDEA and click the ▶ Run button on `CinebookApiApplication.java`

**Step 3 — Access Swagger UI**
```
http://localhost:8080/swagger-ui/index.html
```

---

## ⚙️ Environment Variables

When running via Docker, these environment variables override `application.properties`:

| Variable | Value | Description |
|---|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://postgres:5432/cinebook_db` | PostgreSQL connection (uses service name) |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | DB username |
| `SPRING_DATASOURCE_PASSWORD` | `1234` | DB password |
| `SPRING_DATA_REDIS_HOST` | `redis` | Redis host (uses service name) |
| `SPRING_DATA_REDIS_PORT` | `6379` | Redis port |

For local development, `application.properties` uses `localhost` for both postgres and redis.

---

## 📡 API Endpoints

### Authentication
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Register new user |
| POST | `/api/auth/login` | Public | Login and get JWT token |

### Movies
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/movies` | Public | Get all movies |
| GET | `/api/movies/{id}` | Public | Get movie by ID |
| GET | `/api/movies/genre/{genre}` | Public | Search by genre |
| POST | `/api/movies` | ADMIN | Add new movie |
| PUT | `/api/movies/{id}` | ADMIN | Update movie |
| DELETE | `/api/movies/{id}` | ADMIN | Delete movie |

### Theatres & Screens
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/theatres` | Public | Get all theatres |
| POST | `/api/theatres` | ADMIN | Add theatre |
| GET | `/api/screens` | Public | Get all screens |
| POST | `/api/screens` | ADMIN | Add screen |

### Shows
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/shows` | Public | Get all shows |
| POST | `/api/shows` | ADMIN | Create show |

### Seat Locking & Booking
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/show-seats/{showId}` | USER | Get seats for a show |
| GET | `/api/show-seats/{showId}/available` | USER | Get available seats |
| POST | `/api/show-seats/lock` | USER | Lock a seat (10 min TTL) |
| POST | `/api/show-seats/unlock` | USER | Unlock a seat |
| POST | `/api/bookings` | USER | Create booking |
| GET | `/api/bookings/{id}` | USER | Get booking by ID |
| GET | `/api/bookings/user` | USER | Get my bookings |
| PUT | `/api/bookings/{id}/cancel` | USER | Cancel booking |

> 💡 Use Swagger UI at `http://localhost:8080/swagger-ui/index.html` for interactive API testing.

---

## 🧠 Key Design Decisions

### 1. Redis for Seat Locking
Seats are locked in both PostgreSQL (status = LOCKED) and Redis with a 10-minute TTL. Redis ensures that even if a user closes the browser, the lock automatically expires. A scheduled job (`SeatLockCleanupJob`) runs every 60 seconds to clean up any locks that were missed.

```
Redis Key Format: seat:lock:{showId}:{seatId}
TTL: 10 minutes (600 seconds)
Cleanup Job: Every 60 seconds
```

### 2. Multi-Stage Docker Build
The Dockerfile uses a two-stage build:
- **Stage 1** (maven:3.9-eclipse-temurin-17): Compiles code and packages JAR (~600MB)
- **Stage 2** (eclipse-temurin:17-jre): Runs only the JAR (~200MB)

This reduces the final image size by ~66% and keeps source code out of the production image.

### 3. JWT Authentication
All protected endpoints require a `Bearer` token in the `Authorization` header. Tokens expire in 24 hours (`jwt.expiration=86400000` ms).

### 4. @Transactional on Booking
`createBooking()` and `cancelBooking()` are annotated with `@Transactional` to ensure atomicity. If any step fails (e.g., seat already booked), the entire operation rolls back.

### 5. Unique Constraints
- `movies`: unique on `(title, language)` — same movie can exist in multiple languages
- `theatres`: unique on `(name, city, address)` — two PVR cinemas in same city are valid if different address
- `users`: unique on `email`

### 6. Response DTOs
All API responses use dedicated DTO classes instead of returning entity objects directly. This prevents circular references, hides sensitive fields, and decouples the API contract from the database schema.

---

## 🗄 Database Schema

```
theatres ──< screens ──< seats
                │
               shows ──< show_seats ──< bookings
                │                           │
              movies                      users
```

| Table | Key Columns |
|---|---|
| `theatres` | theatreId, name, city, address |
| `screens` | screenId, name, totalSeats, theatreId |
| `seats` | seatId, seatNumber, seatType (PREMIUM/REGULAR), screenId |
| `movies` | movieId, title, genre, language, duration, rating |
| `shows` | showId, showDate, showTime, price, movieId, screenId |
| `show_seats` | showSeatId, status, price, seatId, showId, bookingId |
| `bookings` | bookingId, status, totalAmount, bookedAt, userId, showId |
| `users` | userId, userName, email, password, role |

---

## 🚧 Roadmap

| Feature | Status |
|---|---|
| Core REST API + JWT Auth | ✅ Done |
| Redis Seat Locking | ✅ Done |
| Docker + docker-compose | ✅ Done |
| Swagger UI | ✅ Done |
| Unit Tests (JUnit 5 + Mockito) | 🔄 In Progress |
| AWS Deployment (EC2 + RDS + ElastiCache) | 🔄 In Progress |
| CI/CD Pipeline (GitHub Actions) | 📅 Planned |
| Payment Integration (Razorpay) | 📅 Planned |
| Pagination & Indexes | 📅 Planned |

---
