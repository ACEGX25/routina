<div align="center">

# 🔁 Routina

**A habit-tracking API built with Spring Boot, PostgreSQL, and JWT auth — built to ship a real product while mastering backend fundamentals.**

[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-database-4169E1?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Status](https://img.shields.io/badge/status-in%20development-yellow)](#roadmap)
[![License](https://img.shields.io/badge/license-MIT-blue)](#license)

</div>

---

## About

Routina is a habit-tracking application that lets users define habits, log activity against them, and track streaks and completion stats over time. It's a personal project with two goals: ship something genuinely usable, and build real backend engineering depth — every schema, security, and service-layer decision here was reasoned through deliberately rather than scaffolded and forgotten.

**Frontend is intentionally deferred** — the backend is being built to be feature-complete and well-tested first, with a Next.js client to follow.

## Features

- 🔐 JWT-based authentication with a stateless session model
- 🔗 Auth method separation, laying the groundwork for linking multiple sign-in methods (password, Google OAuth) to a single account via email
- ✅ Habit + activity tracking with flexible, multi-entry-per-day logging (not locked into a rigid once-a-day model)
- 📈 On-the-fly streak and completion calculation (no stale cached counters)
- 🛡️ Defense-in-depth on the data layer — the app's DB role can read/write data but cannot alter schema

## Tech Stack

| | |
|---|---|
| **Language / Runtime** | Java 21 |
| **Framework** | Spring Boot, Spring Data JPA, Spring Security |
| **Auth** | JWT (`io.jsonwebtoken`), OAuth2 Client, BCrypt |
| **Database** | PostgreSQL, HikariCP |
| **Frontend (planned)** | Next.js (App Router), TypeScript |
| **Testing** | JUnit 5, Mockito, Testcontainers *(planned)* |
| **Tooling** | Lombok, Jakarta Validation |

## Getting Started

### Prerequisites
- Java 21+
- PostgreSQL 14+
- Maven (or the included wrapper, `./mvnw`)

### Setup

```bash
# 1. Clone the repo
git clone https://github.com/ACEGX25/routina.git
cd routina

# 2. Create a PostgreSQL database and a DML-only app role
#    (see Routina_Coding_Standards.md for the exact grant conventions)

# 3. Set required environment variables
export DB_URL=jdbc:postgresql://localhost:5432/routina
export DB_USERNAME=routina_app
export DB_PASSWORD=your_password
export JWT_SECRET=your_jwt_secret

# 4. Run the app
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

> ⚠️ This project is under active development — endpoints, schema, and setup steps will change as new modules land.

## Project Structure

```
src/main/java/com/jin/routina/
├── config/          # Security filter chain, CORS, general beans
├── security/        # JwtUtil, JwtAuthenticationFilter, OAuth2 handlers
├── common/
│   ├── exception/   # Custom exceptions + global exception handler
│   └── response/    # ApiResponse wrapper, pagination helpers
└── modules/
    ├── user/
    ├── auth/
    ├── habit/
    ├── activity/
    └── habitlog/
```

## Roadmap

- [x] JWT auth, security config, `User` / `AuthMethod` entities
- [ ] Complete `AuthService` + `AuthController` (register/login)
- [ ] Global exception handling + standardized API responses
- [ ] Habit / Activity / HabitLog CRUD modules
- [ ] Streak & completion calculation logic
- [ ] Google OAuth2 login + account linking
- [ ] OTP email verification (Redis-backed)
- [ ] Flyway migrations, Testcontainers integration tests
- [ ] Next.js frontend

Full phase-by-phase breakdown lives in the project's internal scope doc.

## Contributing

This is currently a solo learning project and not open to external contributions, but feedback and suggestions via issues are welcome.

## License

MIT — see [`LICENSE`](LICENSE) for details.

---

<div align="center">
Built by <a href="https://github.com/ACEGX25">Sakai Jin</a>
</div>
