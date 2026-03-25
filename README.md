# 🎬 CineBook API — Movie Ticket Booking System

A production-ready RESTful backend API for a movie ticket booking platform, built with **Spring Boot 3.5**, **PostgreSQL**, and **Redis**. Features JWT authentication, role-based access control, real-time seat locking, full Docker support, AWS deployment, and automated CI/CD.

🌐 AWS deployment paused to avoid costs. To run locally, see Getting Started section below.
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
- [AWS Deployment](#-aws-deployment)
- [CI/CD Pipeline](#-cicd-pipeline)
- [Environment Variables](#-environment-variables)
- [API Endpoints](#-api-endpoints)
- [Key Design Decisions](#-key-design-decisions)
- [Database Schema](#-database-schema)
- [Roadmap](#-roadmap)

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
- **AWS Deployed** — Live on EC2 with RDS PostgreSQL
- **CI/CD Pipeline** — Auto-deploy to EC2 on every git push via GitHub Actions
- **Sample Data** — Auto-loaded test data on startup

---

## 🛠 Tech Stack

| Category | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.11 |
| Database | PostgreSQL 17.6 (AWS RDS) |
| Cache / Locking | Redis |
| Authentication | JWT (jjwt 0.11.5) |
| Security | Spring Security 6 |
| ORM | Spring Data JPA + Hibernate 6 |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Build Tool | Maven |
| Containerization | Docker + Docker Compose |
| Cloud | AWS EC2 + RDS |
| CI/CD | GitHub Actions |
| Code Generation | Lombok |

---

## 🏗 Architecture

### Local Development
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

### AWS Production Architecture
```
┌─────────────────────────────────────────────────────┐
│                     Internet                        │
└─────────────────────┬───────────────────────────────┘
                      │ HTTP :8080
                      ▼
┌─────────────────────────────────────────────────────┐
│           EC2 (cinebook-api-server)                 │
│           65.0.205.118 | t3.micro | Ubuntu 24.04    │
│                                                     │
│   ┌─────────────────────┐  ┌──────────────────┐     │
│   │  Spring Boot App    │  │  Redis Container │     │
│   │  (Docker :8080)     │  │  (Docker :6379)  │     │
│   └──────────┬──────────┘  └──────────────────┘     │
└──────────────┼──────────────────────────────────────┘
               │ PostgreSQL :5432
               ▼
┌─────────────────────────────────────────────────────┐
│           AWS RDS (cinebook-db)                     │
│           PostgreSQL 17.6 | db.t4g.micro            │
│           Mumbai (ap-south-1)                       │
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

.github/
└── workflows/
    └── deploy.yml      (GitHub Actions CI/CD pipeline)
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

## ☁️ AWS Deployment

The application is deployed on AWS using EC2 and RDS.

### Infrastructure

| Component | Service | Details |
|---|---|---|
| Application Server | EC2 t3.micro | Ubuntu 24.04, 20GB, Mumbai region |
| Database | RDS PostgreSQL 17.6 | db.t4g.micro, managed, Mumbai region |
| Cache | Redis (Docker on EC2) | Running as container alongside the app |
| Public IP | 65.0.205.118 | App accessible on port 8080 |

### Why RDS Instead of PostgreSQL on EC2?

| Factor | PostgreSQL on EC2 | AWS RDS |
|---|---|---|
| Backups | Manual | Automatic (AWS handles it) |
| Updates | Manual | Automatic (AWS handles it) |
| If EC2 crashes | Data lost ❌ | Data safe ✅ |
| High Availability | No | Yes |

### SSH into EC2

```bash
ssh -i cinebook-key.pem ubuntu@65.0.205.118
```

> ⚠️ `application.properties` is in `.gitignore` and is NOT pushed to GitHub.
> It is created directly on EC2 with production credentials. Git pull never deletes it because it is not tracked by git.

---

## 🔄 CI/CD Pipeline

Every push to the `main` branch automatically deploys to EC2 using **GitHub Actions**. No manual SSH required.

### How It Works

```
git push origin main
        ↓
GitHub Actions triggers deploy.yml
        ↓
Spins up ubuntu-latest runner
        ↓
SSHs into EC2 using GitHub Secrets
        ↓
git pull → docker-compose up --build -d
        ↓
App live at http://65.0.205.118:8080 ✅
```

### deploy.yml

```yaml
name: Deploy to EC2

on:
  push:
    branches:
      - main

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Deploy to EC2
        uses: appleboy/ssh-action@v1.0.0
        with:
          host: ${{ secrets.EC2_HOST }}
          username: ${{ secrets.EC2_USER }}
          key: ${{ secrets.EC2_KEY }}
          script: |
            cd ~/cinebook-api
            git pull origin main
            docker-compose up --build -d
```

### GitHub Secrets Required

| Secret | Description |
|---|---|
| `EC2_HOST` | Public IP of EC2 instance (`65.0.205.118`) |
| `EC2_USER` | SSH username (`ubuntu`) |
| `EC2_KEY` | Full contents of `cinebook-key.pem` |

---

## ⚙️ Environment Variables

When running via Docker on AWS, these environment variables override `application.properties`:

| Variable | Description |
|---|---|
| `SPRING_DATASOURCE_URL` | RDS PostgreSQL JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | DB username |
| `SPRING_DATASOURCE_PASSWORD` | DB password |
| `SPRING_DATA_REDIS_HOST` | Redis host (uses Docker service name `redis`) |
| `SPRING_DATA_REDIS_PORT` | Redis port (6379) |
| `JWT_SECRET` | Base64 encoded JWT signing key |
| `JWT_EXPIRATION` | Token expiry in ms (86400000 = 24 hours) |

> 🔒 Never commit `application.properties` to GitHub. All secrets are managed via environment variables or GitHub Secrets.

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
| GET | `/api/theatres/city/{city}` | Public | Get theatres by city |
| GET | `/api/screens` | Public | Get all screens |
| POST | `/api/screens` | ADMIN | Add screen |
| GET | `/api/screens/theatre/{theatreId}` | Public | Get screens by theatre |

### Shows
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/shows` | Public | Get all shows |
| POST | `/api/shows` | ADMIN | Create show |
| GET | `/api/shows/movie/{movieId}` | Public | Get shows by movie |
| GET | `/api/shows/city/{city}/movie/{movieId}` | Public | Get shows by city and movie |

### Seat Locking & Booking
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/show-seats/{showId}` | USER | Get seats for a show |
| GET | `/api/show-seats/{showId}/available` | USER | Get available seats |
| PUT | `/api/show-seats/{showId}/{seatId}/lock` | USER | Lock a seat (10 min TTL) |
| PUT | `/api/show-seats/{showId}/{seatId}/unlock` | USER | Unlock a seat |
| POST | `/api/bookings` | USER | Create booking |
| GET | `/api/bookings/{bookingId}` | USER | Get booking by ID |
| GET | `/api/bookings/my-bookings` | USER | Get my bookings |
| PUT | `/api/bookings/{bookingId}/cancel` | USER | Cancel booking |

> 💡 Use Swagger UI at `http://65.0.205.118:8080/swagger-ui/index.html` for interactive API testing.

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

### 7. Secrets Management
`application.properties` is in `.gitignore` and never pushed to GitHub. Production secrets are passed as environment variables in `docker-compose.yml` on EC2. CI/CD secrets are stored as encrypted GitHub Secrets.

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
| AWS Deployment (EC2 + RDS) | ✅ Done |
| CI/CD Pipeline (GitHub Actions) | ✅ Done |
| Database Indexes + Pagination | 📅 Planned |
| Payment Integration (Razorpay) | 📅 Planned |

---
