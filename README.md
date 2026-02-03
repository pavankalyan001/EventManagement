# Event Management API

Spring Boot REST API for creating, reading, updating, and deleting events. Includes OpenAPI/Swagger documentation.

## Features
- Create, read, update, delete events
- Validation with clear error responses
- OpenAPI/Swagger UI documentation
- In-memory repository (easy to swap for a database)

## Tech Stack
- Java 17
- Spring Boot 3
- Maven
- springdoc-openapi

## Getting Started

### Requirements
- Java 17+
- Maven 3.9+ (or use the Maven wrapper `./mvnw`)

### Run
```bash
./mvnw spring-boot:run
```

API base URL: `http://localhost:8080/api/events`

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

### Test
```bash
./mvnw test
```

## API Endpoints

| Method | Endpoint              | Description         |
|--------|-----------------------|---------------------|
| POST   | /api/events           | Create event        |
| GET    | /api/events/{id}      | Get event by id     |
| GET    | /api/events           | List all events     |
| PUT    | /api/events/{id}      | Update event        |
| DELETE | /api/events/{id}      | Delete event        |

## Event Schema
```json
{
  "id": 1,
  "title": "Tech Meetup",
  "description": "Monthly meetup",
  "location": "New York",
  "startTime": "2026-02-10T17:00:00Z",
  "endTime": "2026-02-10T19:00:00Z"
}
```

## Validation Rules
- `title` is required
- `location` is required
- `startTime` and `endTime` are required
- `endTime` must be after `startTime`

## Error Response Format
```json
{
  "timestamp": "2026-02-03T18:00:00Z",
  "status": 400,
  "message": "Validation failed",
  "errors": [
    "title: must not be blank",
    "location: must not be blank"
  ]
}
```

## Example Requests

Create an event:
```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Tech Meetup",
    "description": "Monthly meetup",
    "location": "New York",
    "startTime": "2026-02-10T17:00:00Z",
    "endTime": "2026-02-10T19:00:00Z"
  }'
```

Get an event by id:
```bash
curl http://localhost:8080/api/events/1
```

List events:
```bash
curl http://localhost:8080/api/events
```

Update an event:
```bash
curl -X PUT http://localhost:8080/api/events/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Meetup",
    "description": "Updated description",
    "location": "Boston",
    "startTime": "2026-02-10T18:00:00Z",
    "endTime": "2026-02-10T20:00:00Z"
  }'
```

Delete an event:
```bash
curl -X DELETE http://localhost:8080/api/events/1
```
