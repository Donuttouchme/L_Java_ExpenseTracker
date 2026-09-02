# Expense Tracker

A REST API for tracking personal expenses, built with Java and Spring Boot.

This is a learning project: the goal is to build a production-style backend
**from the ground up**, understanding *why* each piece exists — not just making
it work. It is developed hands-on, layer by layer.

## Tech stack

- **Java 21**
- **Spring Boot** (Spring Web, Spring Data JPA, Validation, Spring Security)
- **PostgreSQL** database, run via **Docker Compose** (externalized secrets)
- **JWT** authentication (jjwt) — *in progress*
- **Maven** build tool
- **JUnit 5** + **Mockito** for testing

## Domain

Two simple entities:

- **Category** — a spending category (e.g. "Groceries")
- **Expense** — a single expense (amount, description, date) that belongs to a Category

One category has many expenses (one-to-many).

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
  - [x] HTTP Basic auth (DB-backed users, BCrypt)
  - [ ] JWT (token generation & validation, login endpoint, stateless filter) — *in progress*

## Status

🚧 In development — Phase 8: Authentication (JWT).
