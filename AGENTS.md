# Project Rules (Kanban Task Manager)

## Mandatory Guidelines

- Always code with TDD (Test-Driven Development) in first place
- The project must always be done in English (code, comments, commit messages, explanations, documentation)
- Always plan ahead before creating code or making changes
- Always remove unused imports

## Tech Stack

- Backend: Java 21 / Spring Boot 3
- Frontend: Angular 18+
- Database: PostgreSQL (Relational)
- Infrastructure: Docker & Docker Compose

## Architecture Guidelines

- Follow **Clean Architecture**: Decouple Entities from Frameworks.
- Apply **SOLID**: Especially Single Responsibility for Services.
- **DRY**: Use DTOs for data transfer; don't reuse Entities in the UI.
- **YAGNI**: Start with the simplest schema possible (Board -> Column -> Task).

## Patterns to Use

- Repository Pattern for Database access.
- Strategy Pattern for future notification systems.

## Security & Environment

- **Secrets Management:** Never hardcode passwords, API keys, or database credentials. Always suggest using environment variables (`${DB_PASSWORD}`) or `.env` files.
- **Input Validation:** Every Java Controller must validate incoming DTOs using `@Valid` and JSR-303 annotations to prevent SQL Injection.
- **Docker Security:** Ensure the PostgreSQL container is not exposed to the public internet.

## Project Structure

- The project must be organized into modules
- **API:** The backend code must be organized into packages following the clean architecture pattern: `domain`, `application`, and `infrastructure`.
- **Tests:** All tests must follow the TDD pattern and be organized into packages following the clean architecture pattern: `domain`, `application`, and `infrastructure`.

## Database Design

- **Database:** PostgreSQL (Relational)
- **Schema:** Create a schema with the following tables: `boards`, `columns`, `tasks`, `comments`, `users`, `roles`, `permissions`.

## Audit & History

- **Tracking:** Every mutation of a Task (status change, assignment) must create an entry in the `task_events` table.
- **Pattern:** Use **Spring Boot AOP (Aspect Oriented Programming)** or **JPA Entity Listeners** (`@PostUpdate`) to capture changes automatically without cluttering the business logic.
- **Data:** Store `event_type`, `timestamp`, `user_id`, and a JSON blob of the `changes`.
