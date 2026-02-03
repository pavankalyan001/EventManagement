package com.example.eventmanagement.controller;

import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.EventRequest;
import com.example.eventmanagement.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Events", description = "Event management API")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new event")
    @ApiResponse(responseCode = "201", description = "Event created",
            content = @Content(schema = @Schema(implementation = Event.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventRequest request) {
        Event created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Get event by id")
    @ApiResponse(responseCode = "200", description = "Event retrieved",
            content = @Content(schema = @Schema(implementation = Event.class)))
    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "List all events")
    @ApiResponse(responseCode = "200", description = "Events retrieved",
            content = @Content(schema = @Schema(implementation = Event.class)))
    @GetMapping
    public ResponseEntity<List<Event>> listEvents() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Update an event")
    @ApiResponse(responseCode = "200", description = "Event updated",
            content = @Content(schema = @Schema(implementation = Event.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @Valid @RequestBody EventRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Delete an event")
    @ApiResponse(responseCode = "204", description = "Event deleted", content = @Content)
    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
