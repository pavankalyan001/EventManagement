package com.example.eventmanagement.service;

import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.EventRequest;
import com.example.eventmanagement.repository.EventRepository;
import com.example.eventmanagement.exception.InvalidEventException;
import com.example.eventmanagement.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Event create(EventRequest request) {
        validateTiming(request.getStartTime(), request.getEndTime());
        Event event = new Event(null, request.getTitle(), request.getDescription(), request.getLocation(),
                request.getStartTime(), request.getEndTime());
        return repository.save(event);
    }

    public Event getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found: " + id));
    }

    public List<Event> getAll() {
        return repository.findAll();
    }

    public Event update(Long id, EventRequest request) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found: " + id);
        }
        validateTiming(request.getStartTime(), request.getEndTime());
        Event event = new Event(id, request.getTitle(), request.getDescription(), request.getLocation(),
                request.getStartTime(), request.getEndTime());
        return repository.save(event);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found: " + id);
        }
        repository.deleteById(id);
    }

    private void validateTiming(Instant startTime, Instant endTime) {
        if (startTime == null || endTime == null) {
            throw new InvalidEventException("Start time and end time are required.");
        }
        if (!endTime.isAfter(startTime)) {
            throw new InvalidEventException("End time must be after start time.");
        }
    }
}
