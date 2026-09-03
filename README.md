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

Every endpoint requires authentication (HTTP Basic). On an empty database the
seeder creates one user — `test_user` / `test_password` — plus two categories
and two example expenses:

```bash
curl -u test_user:test_password http://localhost:8080/api/expenses
```

## Tech stack

- **Java 21**
- **Spring Boot** — Web, Data JPA, Validation, Security
- **PostgreSQL 16**, run via **Docker Compose**, credentials externalized to `.env`
- **Hibernate / JPA** (Jakarta Persistence)
- **Argon2id** password hashing (via **BouncyCastle**)
- **JWT** (jjwt, HS256) — token service built; login endpoint + filter *in progress*
- **Maven**, multi-stage **Dockerfile**
- **JUnit 5** + **Mockito** — 18 tests across 6 test classes

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
- **`SecurityConfig`** — every request authenticated, passwords **Argon2id**-hashed,
  users loaded from the database via `CustomUserDetailsService`

## Domain

Three entities:

- **Category** — a spending category, e.g. "Groceries"
- **Expense** — a single expense (amount, description, date), belongs to a Category
- **User** — an application user with an Argon2id-hashed password and a role

One category has many expenses (one-to-many).

## API

All paths are under `/api/expenses` and require authentication.

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
- [ ] **Phase 8.1+** — Authentication & authorization (Spring Security)
  - [x] HTTP Basic auth (DB-backed users)
  - [x] Password hashing hardened to **Argon2id**
  - [x] JWT service — signed tokens, role claim, signing key externalized to env
  - [ ] JWT login endpoint (`POST /auth/login`) + stateless filter — *next*

## Status

🚧 In development — Phase 8: Authentication. HTTP Basic auth works end to end and
passwords are Argon2id-hashed. The JWT service (signed tokens with a role claim, key
read from the environment) is built and tested, but not yet wired into the filter
chain — the login endpoint and JWT filter are next.
