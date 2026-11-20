# workflowCore

A small Quarkus REST API for creating, searching and tracking tasks, backed by an in-memory H2 database.

![Java 21](https://img.shields.io/badge/Java-21-orange)
![Quarkus](https://img.shields.io/badge/Quarkus-3.29-4695EB)
![Gradle](https://img.shields.io/badge/Gradle-build-02303A)
![H2](https://img.shields.io/badge/H2-in--memory-blue)

## What it does

workflowCore exposes a JSON API under `/api/tasks` for managing a simple task list. Each task has a title, an optional description, a status and a priority, plus created/updated timestamps. Lists are paginated, and tasks can be filtered by status or priority, searched by title, and summarised as a count per status.

## Features

- Full CRUD on tasks (`POST`, `GET`, `PUT`, `DELETE`)
- Paginated listing with `page` (default 0) and `size` (default 10)
- Filter by status or priority
- Case-insensitive title search
- Task counts grouped by status
- Bean Validation on input (title 3-200 characters, description up to 1000)
- Partial updates: only the fields you send are changed
- Central exception mapper returning JSON errors (404 for unknown tasks, 500 otherwise)
- Automatic `createdAt`/`updatedAt` timestamps

Statuses: `PENDING`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`. Priorities: `LOW`, `MEDIUM`, `HIGH`, `URGENT`. New tasks default to `PENDING` and `MEDIUM`.

## API

| Method | Path | Description |
|---|---|---|
| POST | `/api/tasks` | Create a task |
| GET | `/api/tasks` | List tasks (`page`, `size`) |
| GET | `/api/tasks/{id}` | Get one task |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |
| GET | `/api/tasks/status/{status}` | Tasks with a given status |
| GET | `/api/tasks/priority/{priority}` | Tasks with a given priority |
| GET | `/api/tasks/search?keyword=...` | Search by title |
| GET | `/api/tasks/statistics` | Count of tasks per status |

Example:

```shell script
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Write docs", "priority": "HIGH"}'
```

## Tech stack

- Java 21
- Quarkus 3.29 (RESTEasy Reactive + Jackson, Arc CDI)
- Hibernate ORM with Panache
- Hibernate Validator
- H2 in-memory database
- Gradle

## Getting started

Requires JDK 21. The Gradle wrapper is included.

```shell script
./gradlew quarkusDev
```

The API is served on <http://localhost:8080> and the Quarkus Dev UI on <http://localhost:8080/q/dev/>.

The database is `jdbc:h2:mem:taskdb` with schema `drop-and-create`, so data is lost on every restart. To use a persistent database, change the datasource settings in `src/main/resources/application.properties`.

To package and run:

```shell script
./gradlew build
java -jar build/quarkus-app/quarkus-run.jar
```

Dockerfiles for JVM and native builds are in `src/main/docker/`. Native build: `./gradlew build -Dquarkus.native.enabled=true`.

## Project layout

```
src/main/java/org/workflowCore/task/
  resource/     TaskResource (REST endpoints)
  service/      TaskService (business logic)
  repository/   TaskRepository (Panache queries)
  entity/       Task, TaskStatus, TaskPriority
  dto/          TaskCreateDTO, TaskUpdateDTO, TaskDTO
  exception/    TaskNotFoundException, GlobalExceptionHandler
src/main/resources/application.properties
```

## Status

A learning/starter project. There are no tests yet and the data store is in-memory only.
