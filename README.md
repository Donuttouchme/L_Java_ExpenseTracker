# Expense Tracker

A REST API for tracking personal expenses, built with Java and Spring Boot.

This is a learning project: the goal is to build a production-style backend
**from the ground up**, understanding *why* each piece exists — not just making
it work. It is developed hands-on, layer by layer.

## Tech stack

- **Java 21**
- **Spring Boot** (Spring Web, Spring Data JPA, Validation)
- **H2** in-memory database (migrating to **PostgreSQL** later)
- **Maven** build tool
- **JUnit** for testing

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
- [ ] **Phase 7** — Migrate to PostgreSQL (Docker)
- [ ] **Phase 8** *(stretch)* — Authentication, containerization

## Status

🚧 In development — Phase 7.
