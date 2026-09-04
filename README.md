# Expense Tracker

A REST API for tracking personal expenses, built with Java 21 and Spring Boot.

Built by hand, one concern at a time — the aim is a production-style backend
understood **from the ground up**: *why* each piece exists, not just that it works.
It grows in small, reviewable phases.

## Run it

Requires Docker. Both the application and the database run in containers.

```bash
cp .env.example .env     # then set your own credentials
docker compose up --build
```

The API is then on `http://localhost:8080`, PostgreSQL 16 on `5432` with a named
volume, so data survives a restart.

Authentication is stateless **JWT**. On an empty database the seeder creates one user
— `test_user` / `test_password` — plus two categories and two example expenses. Log in
to get a token, then send it as a Bearer token:

```bash
# 1) get a token
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test_user","password":"test_password"}'

# 2) call a protected endpoint with it
curl http://localhost:8080/api/expenses -H "Authorization: Bearer <token>"
```

## Tech stack

- **Java 21**
- **Spring Boot** — Web, Data JPA, Validation, Security
- **PostgreSQL 16**, run via **Docker Compose**, credentials externalized to `.env`
- **Hibernate / JPA** (Jakarta Persistence)
- **Argon2id** password hashing (via **BouncyCastle**)
- **JWT** (jjwt, HS256) — stateless auth: `POST /auth/login` issues a signed token, a filter validates it per request
- **Maven**, multi-stage **Dockerfile**
- **JUnit 5** + **Mockito** — 22 tests across 8 test classes

## Architecture

Layered, one responsibility per layer:

```
Controller  →  Service  →  Repository  →  PostgreSQL
     ↑            ↑
    DTOs      domain model
```

- **DTOs** (`ExpenseRequest`, `CategorySummary`) keep the API contract separate
  from the entities
- **Bean Validation** on the request DTO, so invalid input never reaches the
  service layer
- **`GlobalExceptionHandler`** turns domain exceptions into HTTP responses in one
  place instead of scattering try/catch through the controller
- **`SecurityConfig`** — stateless; every request authenticated, passwords **Argon2id**-hashed,
  users loaded from the database via `CustomUserDetailsService`
- **`JwtAuthFilter`** — validates the `Bearer` token on each request and populates the
  security context (no server-side session)

## Domain

Three entities:

- **Category** — a spending category, e.g. "Groceries"
- **Expense** — a single expense (amount, description, date), belongs to a Category
- **User** — an application user with an Argon2id-hashed password and a role

One category has many expenses (one-to-many).

## API

**Auth:** `POST /auth/login` (public) — returns `{ "token": "..." }` (a signed JWT with the
user's roles) for valid credentials, `401` otherwise.

All `/api/expenses` paths require a valid token — send `Authorization: Bearer <token>`.

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/expenses` | all expenses; `?categoryId=` filters by category |
| `GET` | `/api/expenses/{id}` | one expense, `404` if it does not exist |
| `POST` | `/api/expenses` | create an expense, `201` with the saved entity |
| `DELETE` | `/api/expenses/{id}` | delete an expense, `204` or `404` |
| `GET` | `/api/expenses/summary` | totals per category |

## Roadmap

- [x] **Phase 1** — Project setup, Git/GitHub, domain entities (`@Entity`) + H2
- [x] **Phase 2** — Repository layer (Spring Data JPA)
- [x] **Phase 3** — REST Controller with full CRUD endpoints (GET, POST, DELETE)
- [x] **Phase 4** — Service layer, DTOs, validation, error handling
- [x] **Phase 5** — Category–Expense relationship, filtering, summaries
- [x] **Phase 6** — Testing (unit + integration)
- [x] **Phase 7** — Migrate to PostgreSQL (Docker Compose, externalized secrets)
- [x] **Phase 8** — Containerization (full stack via Docker Compose)
- [x] **Phase 8.1–8.6** — Authentication & authorization (Spring Security)
  - [x] DB-backed users, **Argon2id** password hashing
  - [x] JWT service — signed tokens, roles claim, signing key externalized to env
  - [x] JWT login endpoint (`POST /auth/login`) — issues a token, integration-tested
  - [x] Stateless JWT filter — validates the token on protected endpoints
- [ ] **Phase 9** — role-based authorization (restrict endpoints by role)

## Status

🚧 In development. **Phase 8 (authentication) complete**: stateless JWT end to end —
`POST /auth/login` issues a signed token (roles claim, key read from the environment) and a
filter validates it on every protected request. Passwords are Argon2id-hashed. 22 tests green.
